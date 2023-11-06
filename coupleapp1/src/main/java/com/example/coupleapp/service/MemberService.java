package com.example.coupleapp.service;

import com.example.coupleapp.security.JwtUtil;

import com.example.coupleapp.dto.LoginRequestDTO;
import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.dto.TokenDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.RefreshTokenEntity;
import com.example.coupleapp.exception.domian.MemberErrorCode;
import com.example.coupleapp.exception.domian.MemberException;
import com.example.coupleapp.repository.Member.MemberRepository;
import com.example.coupleapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String createMember(MemberDTO memberDTO) {
        // email 중복확인
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new MemberException(MemberErrorCode.USER_EMAIL_ALREADY_EXIST);
        }

        memberRepository.save(
                MemberEntity
                        .builder()
                        .email(memberDTO.getEmail())
                        .password(passwordEncoder.encode(memberDTO.getPassword()))
                        .name(memberDTO.getNote_name())
                        .my_phone_number(memberDTO.getMy_phone_number())
                        .your_phone_number(memberDTO.getYour_phone_number())
                        .start_date(memberDTO.getStart_date())
                        .created_at(LocalDateTime.now())
                        .build());
        return "회원가입 완료";

    }

    public TokenDTO login(LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        // 이메일 존재하는지 확인
        MemberEntity member = memberRepository.findUserByEmail(email);
        if (member == null) {
            throw new MemberException(MemberErrorCode.USER_NOT_FOUND);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 아이디 정보로 Token 생성
            TokenDTO tokenDTO = jwtUtil.creatAllToken(member.getEmail(), member.getId());

            // Refresh Token 있는지 확인
            Optional<RefreshTokenEntity> refreshToken = refreshTokenRepository.findByEmail(loginRequestDTO.getEmail());

            //  있다면 새 토큰 발급 후 업데이트 , 없다면 새로 만들고 디비 저장
            if (refreshToken.isPresent()) {
                refreshTokenRepository.save(refreshToken.get().updateToken(tokenDTO.getRefreshToken()));
            } else {
                RefreshTokenEntity newToken = new RefreshTokenEntity(tokenDTO.getRefreshToken(), loginRequestDTO.getEmail());
                refreshTokenRepository.save(newToken);
            }
            return tokenDTO;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MemberException(MemberErrorCode.LOGIN_FAIL);
        }
    }

}
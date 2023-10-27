package com.example.coupleapp.service;

import com.example.coupleapp.config.JwtUtil;

import com.example.coupleapp.dto.LoginRequestDTO;
import com.example.coupleapp.dto.MemberDTO;
import com.example.coupleapp.dto.TokenDTO;
import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.RefreshTokenEntity;
import com.example.coupleapp.exception.MemberErrorCode;
import com.example.coupleapp.exception.MemberException;
import com.example.coupleapp.repository.MemberRepository;
import com.example.coupleapp.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if(memberRepository.existsByEmail(memberDTO.getEmail())){
            throw new MemberException(MemberErrorCode.USER_EMAIL_ALREADY_EXIST);
        }
        // note-name 중복 확인
        if(memberRepository.existsByName(memberDTO.getNote_name())) {
            throw new MemberException(MemberErrorCode.USER_NICKNAME_ALREADY_EXIST);
        }

        memberRepository.save(
                MemberEntity.builder()
                        .email(memberDTO.getEmail())
                        .password(passwordEncoder.encode(memberDTO.getPassword()))
                        .name(memberDTO.getNote_name())
                        .my_phone_number(memberDTO.getMy_phone_number())
                        .your_phone_number(memberDTO.getYour_phone_number())
                        .start_date(memberDTO.getStart_date())
                        .build());
        return "회원가입 완료";

    }

    public TokenDTO login(LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        // 이메일 존재하는지 확인
        MemberEntity member = memberRepository.findUserByEmail(email);
        if(member == null){
            throw new MemberException(MemberErrorCode.USER_NOT_FOUND);
        }

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 아이디 정보로 Token 생성
            TokenDTO tokenDTO = jwtUtil.creatAllToken(member.getEmail(),member.getName());

            // Refresh Token 있는지 확인
            Optional<RefreshTokenEntity> refreshToken = refreshTokenRepository.findByEmail(email);

            //  있다면 새 토큰 발급 후 업데이트 , 없다면 새로 만들고 디비 저장
            if (refreshToken.isPresent()) {
                refreshTokenRepository.save(refreshToken.get().updateToken(tokenDTO.getRefreshToken()));
            } else {
                RefreshTokenEntity newToken = new RefreshTokenEntity(tokenDTO.getRefreshToken(),email);
                refreshTokenRepository.save(newToken);
            }
            return tokenDTO;

        }catch (Exception e){
            log.error(e.getMessage());
            throw new MemberException(MemberErrorCode.LOGIN_FAIL);
        }
    }


    // 여기에 필요한 다른 의존성 주입 가능
//    private final Map<String, String> users = new HashMap<>(); // 사용자 정보를 메모리에 저장
//
//
//    // 로그인 인증 메서드
//    public boolean isAuthenticationSuccessful(String email, String password) {
//        String storedPassword = users.get(email);
//        if (storedPassword != null && storedPassword.equals(password)) {
//            return true; // 사용자 인증 성공
//        }
//        return false; // 사용자 인증 실패
//    }
//
////    @Autowired
////    public MemberService(MemberRepository memberRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
////        this.memberRepository = memberRepository;
////        this.tokenService = tokenService;
////        this.passwordEncoder = passwordEncoder;
////    }
//
//    public MemberDTO getMemberById(Long id) {
//        // 멤버 ID를 사용하여 데이터베이스에서 멤버 정보를 조회합니다.
//        // Repository를 사용하여 데이터베이스 액세스를 수행합니다.
//        MemberEntity memberEntity = memberRepository.findById(id).orElse(null);
//
//        if (memberEntity != null) {
//            // MemberEntity를 MemberDTO로 변환하는 로직
//            MemberDTO memberDTO = convertToDTO(memberEntity);
//            return memberDTO;
//        } else {
//            // 해당 ID의 멤버가 존재하지 않는 경우에 대한 처리
//            throw new MemberNotFoundException("Member not found for ID: " + id);
//        }
//        // MemberEntity를 MemberDTO로 변환하는 로직이 필요합니다.
//        // 엔티티와 DTO 간의 매핑을 수행합니다.
//
//    }
//
//
//
//    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
//        // 멤버 ID를 사용하여 데이터베이스에서 해당 멤버 정보를 가져옵니다.
//        MemberEntity existingMember = memberRepository.findById(id).orElse(null);
//
//        if (existingMember != null) {
//            // MemberDTO를 MemberEntity로 변환하고, 업데이트할 필드를 설정합니다.
//            // 이후 데이터베이스에 업데이트를 적용합니다.
//            existingMember = updateEntityFromDTO(existingMember, memberDTO);
//            memberRepository.save(existingMember);
//
//            // 업데이트된 엔티티를 다시 DTO로 변환하여 반환합니다.
//            MemberDTO updatedMemberDTO = convertToDTO(existingMember);
//            return updatedMemberDTO;
//        } else {
//            // 해당 ID의 멤버가 존재하지 않는 경우에 대한 처리
//            throw new MemberNotFoundException("Member not found for ID: " + id);
//
//        }
//    }
//
//    public void deleteMember(Long id) {
//        // 멤버 ID를 사용하여 데이터베이스에서 해당 멤버를 삭제합니다.
//        memberRepository.deleteById(id);
//    }
//
//    public ResponseEntity<String> login(LoginRequestDTO loginRequest) {
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        // 실제 인증 및 토큰 생성 로직은 여기에 구현해야 합니다.
//
//        // 예시: 사용자 인증 성공한 경우
//        if (isAuthenticationSuccessful(email, password)) {
//            // 토큰 생성 로직을 호출 (토큰을 생성하고 클라이언트에게 반환하는 방법을 구현)
//            String token = tokenService.generateToken(email);
//            return ResponseEntity.ok(token); // 토큰을 클라이언트에게 반환
//        } else {
//            // 사용자 인증 실패한 경우
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//    }
//
//    public ResponseEntity<String> logout(HttpSession session) {
//        // 세션을 무효화하여 로그아웃
//        session.invalidate();
//        return ResponseEntity.ok("Logout successful");
//    }
//
//    private MemberDTO convertToDTO(MemberEntity memberEntity) {
//        MemberDTO memberDTO = new MemberDTO();
//        memberDTO.setMember_id(memberEntity.getMember_id());
//        memberDTO.setEmail(memberEntity.getEmail());
//        memberDTO.setMy_phone_number(memberEntity.getMy_phone_number());
//        memberDTO.setYour_phone_number(memberEntity.getYour_phone_number());
//        memberDTO.setStart_date(memberEntity.getStart_date());
//        memberDTO.setNote_name(memberEntity.getNote_name());
//        memberDTO.setCreated_at(memberEntity.getCreated_at());
//        // 나머지 필드도 필요에 따라 복사
//
//        return memberDTO;
//    }
//
//    private MemberEntity convertToEntity(MemberDTO memberDTO) {
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setMember_id(memberDTO.getMember_id());
//        memberEntity.setEmail(memberDTO.getEmail());
//        memberEntity.setMy_phone_number(memberDTO.getMy_phone_number());
//        memberEntity.setYour_phone_number(memberDTO.getYour_phone_number());
//        memberEntity.setStart_date(memberDTO.getStart_date());
//        memberEntity.setNote_name(memberDTO.getNote_name());
//        memberEntity.setCreated_at(memberDTO.getCreated_at());
//        // 나머지 필드도 필요에 따라 복사
//
//        return memberEntity;
//    }
//
//    private MemberEntity updateEntityFromDTO(MemberEntity existingEntity, MemberDTO memberDTO) {
//        if (memberDTO.getEmail() != null) {
//            existingEntity.setEmail(memberDTO.getEmail());
//        }
//        if (memberDTO.getMy_phone_number() != null) {
//            existingEntity.setMy_phone_number(memberDTO.getMy_phone_number());
//        }
//        if (memberDTO.getYour_phone_number() != null) {
//            existingEntity.setYour_phone_number(memberDTO.getYour_phone_number());
//        }
//        return existingEntity;
//    }
}
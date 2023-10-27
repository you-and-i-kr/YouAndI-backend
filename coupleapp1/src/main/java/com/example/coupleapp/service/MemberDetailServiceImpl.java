package com.example.coupleapp.service;

import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.exception.MemberErrorCode;
import com.example.coupleapp.exception.MemberException;
import com.example.coupleapp.repository.MemberDetailsImpl;
import com.example.coupleapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findUserByEmail(email);

        // 존재하지 않는 유저
        if(member == null) {
            throw new MemberException(MemberErrorCode.USER_NOT_FOUND);
        }

        return new MemberDetailsImpl(member);
    }
}

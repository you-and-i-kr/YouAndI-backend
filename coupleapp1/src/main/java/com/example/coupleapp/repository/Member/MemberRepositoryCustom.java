package com.example.coupleapp.repository.Member;

import com.example.coupleapp.entity.MemberEntity;

public interface MemberRepositoryCustom {
    MemberEntity findUserByEmail(String email);

    MemberEntity findMemberById(Long memberId);

}

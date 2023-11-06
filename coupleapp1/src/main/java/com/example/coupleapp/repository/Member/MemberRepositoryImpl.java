package com.example.coupleapp.repository.Member;

import com.example.coupleapp.entity.MemberEntity;
import com.example.coupleapp.entity.QMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }
    QMemberEntity qMemberEntity = QMemberEntity.memberEntity;
    @Override
    public MemberEntity findUserByEmail(String email) {
        return queryFactory
                .selectFrom(qMemberEntity)
                .where(qMemberEntity.email.eq(email))
                .fetchFirst();
    }
    @Override
    public MemberEntity findMemberById(Long memberId) {
        return queryFactory
                .selectFrom(qMemberEntity)
                .where(qMemberEntity.id.eq(memberId))
                .fetchFirst();
    }

}

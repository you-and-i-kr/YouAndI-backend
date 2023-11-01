package com.example.coupleapp.repository;

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

    @Override
    public MemberEntity findUserByEmail(String email) {
        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;
        return queryFactory
                .selectFrom(qMemberEntity)
                .where(qMemberEntity.email.eq(email))
                .fetchFirst();
    }
}

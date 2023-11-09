package com.example.coupleapp.repository.Memo;

import com.example.coupleapp.entity.MemberEntity;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemoRepositoryCustom {
    Page<Tuple> findMemoList(MemberEntity member, Pageable pageable);
}

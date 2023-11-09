package com.example.coupleapp.repository.Media;

import com.example.coupleapp.entity.MediaEntity;
import com.example.coupleapp.entity.MemberEntity;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MediaRepositoryCustom {
    Page<Tuple> findMediaList(MemberEntity member, Pageable pageable);

}

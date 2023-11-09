package com.example.coupleapp.repository.Photo;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepositoryCustom {
    Page<Tuple> findimglist(@Param("myPhoneNum") String myPhoneNum, @Param("yourPhoneNum") String yourPhoneNum, Pageable pageable);
}

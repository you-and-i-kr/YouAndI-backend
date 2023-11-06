package com.example.coupleapp.repository.Photo;

import com.example.coupleapp.entity.QPhotoEntity;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class PhotoRepositoryImpl implements PhotoRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PhotoRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QPhotoEntity photo = new QPhotoEntity("photo");


    @Override
    public List<Tuple> findimglist(String myPhoneNum, String yourPhoneNum, Pageable pageable) {
        return queryFactory
                .select(photo.id,photo.imgUrl)
                .from(photo)
                .where(photo.my_phone_number.eq(myPhoneNum)
                        .or(photo.my_phone_number.eq(yourPhoneNum)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}

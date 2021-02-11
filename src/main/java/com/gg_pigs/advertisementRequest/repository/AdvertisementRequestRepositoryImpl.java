package com.gg_pigs.advertisementRequest.repository;

import com.gg_pigs.advertisementRequest.dto.RetrieveConditionForAdvertisementRequest;
import com.gg_pigs.advertisementRequest.entity.AdvertisementRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.gg_pigs.advertisementRequest.entity.QAdvertisementRequest.advertisementRequest;
import static com.gg_pigs.user.entity.QUser.user;

public class AdvertisementRequestRepositoryImpl implements AdvertisementRequestRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AdvertisementRequestRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<AdvertisementRequest> findAllByCondition(RetrieveConditionForAdvertisementRequest condition) {

        BooleanBuilder builder = new BooleanBuilder();

        // 1. Page 조건을 가공합니다.
        if(!condition.isUnlimited()) {
            builder.and(goeColumnPosition(condition.getStartIndexOfPage()));
            builder.and(loeColumnPosition(condition.getLastIndexOfPage()));
        }

        // 2. UserEmail 조건을 가공합니다.
        if(condition.isHasUserEmail()) {
            builder.and(eqUserEmail(condition.getUserEmail()));
        }

        // 3. IsFilteredDate 조건을 가공합니다.
        if(condition.isFilteredDate()) {
            builder.and(loeStartedDate(condition.getCurrentDate()));
            builder.and(goeFinishedDate(condition.getCurrentDate()));
        }

        return jpaQueryFactory
                .select(advertisementRequest)
                .from(advertisementRequest)
                .leftJoin(advertisementRequest.user, user).fetchJoin()
                .where(builder)
                .fetch();
    }

    private BooleanExpression loeColumnPosition(Long indexOfPage) {
        return (indexOfPage != null) ? advertisementRequest.columnPosition.loe(indexOfPage) : null;
    }

    private BooleanExpression goeColumnPosition(Long indexOfPage) {
        return (indexOfPage != null) ? advertisementRequest.columnPosition.goe(indexOfPage) : null;
    }

    private BooleanExpression loeStartedDate(LocalDate localDate) {
        return (localDate != null) ? advertisementRequest.startedDate.loe(localDate) : null;
    }

    private BooleanExpression goeFinishedDate(LocalDate localDate) {
        return (localDate != null) ? advertisementRequest.finishedDate.goe(localDate) : null;
    }

    private BooleanExpression eqUserEmail(String userEmail) {
        return (userEmail != null) ? user.email.eq(userEmail) : null;
    }
}

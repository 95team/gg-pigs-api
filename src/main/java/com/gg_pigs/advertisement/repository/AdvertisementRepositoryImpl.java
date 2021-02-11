package com.gg_pigs.advertisement.repository;

import com.gg_pigs.advertisement.dto.RetrieveConditionForAdvertisement;
import com.gg_pigs.advertisement.entity.Advertisement;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.gg_pigs.advertisement.entity.QAdvertisement.advertisement;
import static com.gg_pigs.user.entity.QUser.user;
import static org.springframework.util.StringUtils.isEmpty;

public class AdvertisementRepositoryImpl implements AdvertisementRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AdvertisementRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Advertisement> findAllByCondition(RetrieveConditionForAdvertisement retrieveConditionForAdvertisement) {
        return jpaQueryFactory
                .select(advertisement)
                .from(advertisement)
                .leftJoin(advertisement.user, user).fetchJoin()
                .where(condition(retrieveConditionForAdvertisement))
                .fetch();
    }

    private BooleanBuilder condition(RetrieveConditionForAdvertisement condition) {
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

        return builder;
    }

    private BooleanExpression loeColumnPosition(Long indexOfPage) {
        return !(indexOfPage == null) ? advertisement.columnPosition.loe(indexOfPage) : null;
    }

    private BooleanExpression goeColumnPosition(Long indexOfPage) {
        return !(indexOfPage == null) ? advertisement.columnPosition.goe(indexOfPage) : null;
    }

    private BooleanExpression loeStartedDate(LocalDate localDate) {
        return !(localDate == null) ? advertisement.startedDate.loe(localDate) : null;
    }

    private BooleanExpression goeFinishedDate(LocalDate localDate) {
        return !(localDate == null) ? advertisement.finishedDate.goe(localDate) : null;
    }

    private BooleanExpression eqUserEmail(String userEmail) {
        return !isEmpty(userEmail) ? user.email.eq(userEmail) : null;
    }
}

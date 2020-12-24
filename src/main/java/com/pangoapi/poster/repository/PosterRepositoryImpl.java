package com.pangoapi.poster.repository;

import com.pangoapi.poster.dto.RetrieveConditionDtoPoster;
import com.pangoapi.poster.entity.Poster;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.pangoapi.user.entity.QUser.user;
import static com.pangoapi.poster.entity.QPoster.poster;
import static org.springframework.util.StringUtils.isEmpty;

public class PosterRepositoryImpl implements PosterRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PosterRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Poster> findAllByCondition(RetrieveConditionDtoPoster retrieveConditionForPoster) {
        return jpaQueryFactory
                .select(poster)
                .from(poster)
                .leftJoin(poster.user, user).fetchJoin()
                .where(condition(retrieveConditionForPoster))
                .fetch();
    }

    private BooleanBuilder condition(RetrieveConditionDtoPoster condition) {
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
        return !(indexOfPage == null) ? poster.columnPosition.loe(indexOfPage) : null;
    }

    private BooleanExpression goeColumnPosition(Long indexOfPage) {
        return !(indexOfPage == null) ? poster.columnPosition.goe(indexOfPage) : null;
    }

    private BooleanExpression loeStartedDate(LocalDate localDate) {
        return !(localDate == null) ? poster.startedDate.loe(localDate) : null;
    }

    private BooleanExpression goeFinishedDate(LocalDate localDate) {
        return !(localDate == null) ? poster.finishedDate.goe(localDate) : null;
    }

    private BooleanExpression eqUserEmail(String userEmail) {
        return !isEmpty(userEmail) ? user.email.eq(userEmail) : null;
    }
}

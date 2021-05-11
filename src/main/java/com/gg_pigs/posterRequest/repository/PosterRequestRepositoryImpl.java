package com.gg_pigs.posterRequest.repository;

import com.gg_pigs.posterRequest.dto.RetrieveConditionDtoPosterRequest;
import com.gg_pigs.posterRequest.entity.PosterRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.gg_pigs.posterRequest.entity.QPosterRequest.posterRequest;
import static com.gg_pigs.user.entity.QUser.user;

public class PosterRequestRepositoryImpl implements PosterRequestRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PosterRequestRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<PosterRequest> findAllByCondition(RetrieveConditionDtoPosterRequest condition) {

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
            builder.and(loeStartedDate(LocalDate.now()));
            builder.and(goeFinishedDate(LocalDate.now()));
        }

        return jpaQueryFactory
                .select(posterRequest)
                .from(posterRequest)
                .leftJoin(posterRequest.user, user).fetchJoin()
                .where(builder)
                .fetch();
    }

    private BooleanExpression loeColumnPosition(Long indexOfPage) {
        return (indexOfPage != null) ? posterRequest.columnPosition.loe(indexOfPage) : null;
    }

    private BooleanExpression goeColumnPosition(Long indexOfPage) {
        return (indexOfPage != null) ? posterRequest.columnPosition.goe(indexOfPage) : null;
    }

    private BooleanExpression loeStartedDate(LocalDate localDate) {
        return (localDate != null) ? posterRequest.startedDate.loe(localDate) : null;
    }

    private BooleanExpression goeFinishedDate(LocalDate localDate) {
        return (localDate != null) ? posterRequest.finishedDate.goe(localDate) : null;
    }

    private BooleanExpression eqUserEmail(String userEmail) {
        return (userEmail != null) ? user.email.eq(userEmail) : null;
    }
}

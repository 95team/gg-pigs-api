package com.gg_pigs.app.poster.repository;

import com.gg_pigs.app.poster.dto.RetrieveConditionDtoPoster;
import com.gg_pigs.app.poster.entity.Poster;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.gg_pigs.app.user.entity.QUser.user;
import static com.gg_pigs.app.poster.entity.QPoster.poster;
import static org.springframework.util.StringUtils.isEmpty;

public class PosterRepositoryImpl implements PosterRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PosterRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Poster> findAllByCondition(RetrieveConditionDtoPoster conditions) {
        return jpaQueryFactory
                .select(poster)
                .from(poster)
                .leftJoin(poster.user, user).fetchJoin()
                .where(condition(conditions))
                .fetch();
    }

    private BooleanBuilder condition(RetrieveConditionDtoPoster conditions) {
        BooleanBuilder builder = new BooleanBuilder();

        // 1. Page 조건을 가공합니다.
        if(!conditions.isUnlimited()) {
            builder.and(goeColumnPosition(conditions.getStartIndexOfPage()));
            builder.and(loeColumnPosition(conditions.getLastIndexOfPage()));
        }

        // 2. UserEmail 조건을 가공합니다.
        if(conditions.isHasUserEmail()) {
            builder.and(eqUserEmail(conditions.getUserEmail()));
        }

        // 3. IsFilteredDate 조건을 가공합니다.
        if(conditions.isFilteredDate()) {
            builder.and(loeStartedDate(conditions.getCurrentDate()));
            builder.and(goeFinishedDate(conditions.getCurrentDate()));
        }

        // 4. IsActivated 조건을 가공합니다.
        if(conditions.isFilteredByActivated()) {
            builder.and(eqIsActivated(conditions.getIsActivated()));
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

    private BooleanExpression eqIsActivated(char isActivated) {
        if(isActivated == 'Y') {
            return this.isActivatedEqTrue();
        }
        else if(isActivated == 'N') {
            return this.isActivatedEqFalse();
        }
        else {
            return null;
        }
    }

    private BooleanExpression isActivatedEqTrue() {
        return poster.isActivated.eq('Y');
    }

    private BooleanExpression isActivatedEqFalse() {
        return poster.isActivated.eq('N');
    }
}

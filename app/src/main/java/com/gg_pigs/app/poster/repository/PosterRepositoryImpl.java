package com.gg_pigs.app.poster.repository;

import com.gg_pigs.app.poster.dto.PosterDto;
import com.gg_pigs.app.poster.entity.Poster;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.gg_pigs.app.poster.entity.QPoster.poster;
import static com.gg_pigs.app.user.entity.QUser.user;
import static org.springframework.util.StringUtils.isEmpty;

public class PosterRepositoryImpl implements PosterRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PosterRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Poster> findAllByCondition(PosterDto.Read.SearchConditionDto condition) {
        BooleanBuilder whereclause = new BooleanBuilder();

        // 1. Page 조건을 가공합니다.
        whereclause.and(goeColumnPosition(condition.getStartIndexOfPage()));
        whereclause.and(loeColumnPosition(condition.getLastIndexOfPage()));

        // 2. UserEmail 조건을 가공합니다.
        whereclause.and(eqUserEmail(condition.getUserEmail()));

        // 3. IsFilteredDate 조건을 가공합니다.
        whereclause.and(loeStartedDate(condition.getCurrentDate()));
        whereclause.and(goeFinishedDate(condition.getCurrentDate()));

        // 4. IsActivated 조건을 가공합니다.
        whereclause.and(eqIsActivated(condition.getIsActivated()));

        return jpaQueryFactory
                .select(poster)
                .from(poster)
                .leftJoin(poster.user, user).fetchJoin()
                .where(condition(condition))
                .fetch();
    }

    private BooleanBuilder condition(PosterDto.Read.SearchConditionDto condition) {
        BooleanBuilder builder = new BooleanBuilder();

        // 1. Page 조건을 가공합니다.
        builder.and(goeColumnPosition(condition.getStartIndexOfPage()));
        builder.and(loeColumnPosition(condition.getLastIndexOfPage()));

        // 2. UserEmail 조건을 가공합니다.
        builder.and(eqUserEmail(condition.getUserEmail()));

        // 3. IsFilteredDate 조건을 가공합니다.
        builder.and(loeStartedDate(condition.getCurrentDate()));
        builder.and(goeFinishedDate(condition.getCurrentDate()));

        // 4. IsActivated 조건을 가공합니다.
        builder.and(eqIsActivated(condition.getIsActivated()));

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

    private BooleanExpression eqIsActivated(String isActivated) {
        return !isEmpty(isActivated) ? poster.isActivated.eq(isActivated) : null;
    }

    private BooleanExpression eqUserEmail(String userEmail) {
        return !isEmpty(userEmail) ? user.email.eq(userEmail) : null;
    }
}

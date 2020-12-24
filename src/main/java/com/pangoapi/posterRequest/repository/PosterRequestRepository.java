package com.pangoapi.posterRequest.repository;

import com.pangoapi.posterRequest.entity.PosterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PosterRequestRepository extends JpaRepository<PosterRequest, Long>, PosterRequestRepositoryCustom {
    @Query(value =
            "SELECT new Map(pt.type as posterType, pr.rowPosition as rowPosition, pr.columnPosition as columnPosition) " +
            "FROM PosterRequest pr left join pr.posterType pt " +
            "WHERE :startedDate <= pr.finishedDate AND :finishedDate >= pr.startedDate AND pr.columnPosition >= :startIndexOfPage AND pr.columnPosition <= :lastIndexOfPage")
    List<Map<String, String>> findAllImpossibleSeats(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage,
            @Param("startedDate") LocalDate startedDate,
            @Param("finishedDate") LocalDate finishedDate
    );

    @Query(value =
            "SELECT pr " +
            "FROM PosterRequest pr left join fetch pr.user " +
            "WHERE pr.columnPosition >= :startIndexOfPage AND pr.columnPosition <= :lastIndexOfPage AND pr.startedDate <= DATE(NOW()) AND pr.finishedDate >= DATE(NOW())")
    List<PosterRequest> findAllByPage(@Param("startIndexOfPage") Long startIndexOfPage, @Param("lastIndexOfPage") Long lastIndexOfPage);
}

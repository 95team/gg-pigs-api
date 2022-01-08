package com.gg_pigs.app.poster.repository;

import com.gg_pigs.app.poster.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PosterRepository extends JpaRepository<Poster, Long>, PosterRepositoryCustom {
    @Query(value =
            "SELECT new Map(pt.type as posterType, p.rowPosition as rowPosition, p.columnPosition as columnPosition) " +
            "FROM Poster p left join p.posterType pt " +
            "WHERE :startedDate <= p.finishedDate AND :finishedDate >= p.startedDate AND p.columnPosition >= :startIndexOfPage AND p.columnPosition <= :lastIndexOfPage")
    List<Map<String, String>> findAllImpossibleSeats(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage,
            @Param("startedDate") LocalDate startedDate,
            @Param("finishedDate") LocalDate finishedDate
    );

    @Query(value =
            "SELECT p " +
                    "FROM Poster p left join fetch p.user u " +
                    "WHERE p.columnPosition >= :startIndexOfPage AND p.columnPosition <= :lastIndexOfPage AND p.startedDate <= DATE(NOW()) AND p.finishedDate >= DATE(NOW())")
    List<Poster> findAllByPage(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage
    );
}

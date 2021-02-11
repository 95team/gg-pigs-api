package com.gg_pigs.advertisement.repository;

import com.gg_pigs.advertisement.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, AdvertisementRepositoryCustom {
    @Query(value =
            "SELECT new Map(adt.type as advertisementType, ad.rowPosition as rowPosition, ad.columnPosition as columnPosition) " +
            "FROM Advertisement ad left join ad.advertisementType adt " +
            "WHERE :startedDate <= ad.finishedDate AND :finishedDate >= ad.startedDate AND ad.columnPosition >= :startIndexOfPage AND ad.columnPosition <= :lastIndexOfPage")
    List<Map<String, String>> findAllImpossibleSeats(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage,
            @Param("startedDate") LocalDate startedDate,
            @Param("finishedDate") LocalDate finishedDate
    );

    @Query(value =
            "SELECT ad " +
                    "FROM Advertisement ad left join fetch ad.user u " +
                    "WHERE ad.columnPosition >= :startIndexOfPage AND ad.columnPosition <= :lastIndexOfPage AND ad.startedDate <= DATE(NOW()) AND ad.finishedDate >= DATE(NOW())")
    List<Advertisement> findAllByPage(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage
    );
}

package com.gg_pigs.advertisementRequest.repository;

import com.gg_pigs.advertisementRequest.entity.AdvertisementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdvertisementRequestRepository extends JpaRepository<AdvertisementRequest, Long>, AdvertisementRequestRepositoryCustom {
    @Query(value =
            "SELECT new Map(adt.type as advertisementType, adr.rowPosition as rowPosition, adr.columnPosition as columnPosition) " +
            "FROM AdvertisementRequest adr left join adr.advertisementType adt " +
            "WHERE :startedDate <= adr.finishedDate AND :finishedDate >= adr.startedDate AND adr.columnPosition >= :startIndexOfPage AND adr.columnPosition <= :lastIndexOfPage")
    List<Map<String, String>> findAllImpossibleSeats(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage,
            @Param("startedDate") LocalDate startedDate,
            @Param("finishedDate") LocalDate finishedDate
    );

    @Query(value =
            "SELECT adr " +
            "FROM AdvertisementRequest adr left join fetch adr.user " +
            "WHERE adr.columnPosition >= :startIndexOfPage AND adr.columnPosition <= :lastIndexOfPage AND adr.startedDate <= DATE(NOW()) AND adr.finishedDate >= DATE(NOW())")
    List<AdvertisementRequest> findAllByPage(@Param("startIndexOfPage") Long startIndexOfPage, @Param("lastIndexOfPage") Long lastIndexOfPage);
}

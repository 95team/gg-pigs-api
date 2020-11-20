package com.pangoapi.advertisementRequest.repository;

import com.pangoapi.advertisementRequest.entity.AdvertisementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdvertisementRequestRepository extends JpaRepository<AdvertisementRequest, Long> {
    @Query(value =
            "SELECT new Map(adt.type as advertisementType, adr.rowPosition as rowPosition, adr.columnPosition as columnPosition) " +
            "FROM AdvertisementRequest adr join adr.advertisementType adt " +
            "WHERE :startedDate <= adr.finishedDate AND :finishedDate >= adr.startedDate AND adr.rowPosition >= :startIndexOfPage AND adr.rowPosition <= :lastIndexOfPage")
    List<Map<String, String>> findAllImpossibleSeats(
            @Param("startIndexOfPage") Long startIndexOfPage,
            @Param("lastIndexOfPage") Long lastIndexOfPage,
            @Param("startedDate") LocalDate startedDate,
            @Param("finishedDate") LocalDate finishedDate
    );
}

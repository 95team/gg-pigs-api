package com.pangoapi.repository.advertisement;

import com.pangoapi.domain.entity.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @Query(value =
            "SELECT new Map(adt.type as advertisementType, ad.rowPosition as rowPostion, ad.columnPosition as columnPosition) " +
            "FROM Advertisement ad join ad.advertisementType adt " +
            "WHERE :startedDate <= ad.finishedDate AND :finishedDate >= ad.startedDate AND ad.rowPosition >= CONVERT(:startIndexOfPage, UNSIGNED) AND ad.rowPosition <= CONVERT(:lastIndexOfPage, UNSIGNED)")
    List<Map<String, String>> findAllImpossibleSeats(
            @Param("startIndexOfPage") String startIndexOfPage,
            @Param("lastIndexOfPage") String lastIndexOfPage,
            @Param("startedDate") LocalDate startedDate,
            @Param("finishedDate") LocalDate finishedDate
    );
}

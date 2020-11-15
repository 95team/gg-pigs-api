package com.pangoapi.advertisementType.repository;

import com.pangoapi.advertisementType.entity.AdvertisementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementTypeRepository extends JpaRepository<AdvertisementType, Long> {

    Optional<AdvertisementType> findByType(String type);
}

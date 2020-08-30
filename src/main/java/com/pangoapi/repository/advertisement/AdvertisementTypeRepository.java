package com.pangoapi.repository.advertisement;

import com.pangoapi.domain.entity.advertisement.AdvertisementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementTypeRepository extends JpaRepository<AdvertisementType, Long> {

    Optional<AdvertisementType> findByType(String type);
}

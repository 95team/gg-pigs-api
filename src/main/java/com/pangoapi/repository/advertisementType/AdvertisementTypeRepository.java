package com.pangoapi.repository.advertisementType;

import com.pangoapi.domain.entity.advertisementType.AdvertisementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementTypeRepository extends JpaRepository<AdvertisementType, Long> {

    Optional<AdvertisementType> findByType(String type);
}

package com.pangoapi.repository.advertisement;

import com.pangoapi.domain.entity.advertisement.AdvertisementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRequestRepository extends JpaRepository<AdvertisementRequest, Long> {
}

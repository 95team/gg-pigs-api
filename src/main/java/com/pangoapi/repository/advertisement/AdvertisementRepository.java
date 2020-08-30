package com.pangoapi.repository.advertisement;

import com.pangoapi.domain.entity.advertisement.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> { }

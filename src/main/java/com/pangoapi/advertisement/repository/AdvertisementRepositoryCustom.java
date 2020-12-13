package com.pangoapi.advertisement.repository;

import com.pangoapi.advertisement.dto.RetrieveConditionForAdvertisement;
import com.pangoapi.advertisement.entity.Advertisement;

import java.util.List;

public interface AdvertisementRepositoryCustom {

    List<Advertisement> findAllByCondition(RetrieveConditionForAdvertisement retrieveConditionForAdvertisement);
}

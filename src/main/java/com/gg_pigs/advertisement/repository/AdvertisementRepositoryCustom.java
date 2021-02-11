package com.gg_pigs.advertisement.repository;

import com.gg_pigs.advertisement.dto.RetrieveConditionForAdvertisement;
import com.gg_pigs.advertisement.entity.Advertisement;

import java.util.List;

public interface AdvertisementRepositoryCustom {

    List<Advertisement> findAllByCondition(RetrieveConditionForAdvertisement retrieveConditionForAdvertisement);
}

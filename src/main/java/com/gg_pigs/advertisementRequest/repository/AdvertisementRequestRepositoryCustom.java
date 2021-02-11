package com.gg_pigs.advertisementRequest.repository;

import com.gg_pigs.advertisementRequest.dto.RetrieveConditionForAdvertisementRequest;
import com.gg_pigs.advertisementRequest.entity.AdvertisementRequest;

import java.util.List;

public interface AdvertisementRequestRepositoryCustom {

    List<AdvertisementRequest> findAllByCondition(RetrieveConditionForAdvertisementRequest retrieveConditionForAdvertisementRequest);
}

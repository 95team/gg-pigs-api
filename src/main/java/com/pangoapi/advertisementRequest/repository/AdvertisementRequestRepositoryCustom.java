package com.pangoapi.advertisementRequest.repository;

import com.pangoapi.advertisementRequest.dto.RetrieveConditionForAdvertisementRequest;
import com.pangoapi.advertisementRequest.entity.AdvertisementRequest;

import java.util.List;

public interface AdvertisementRequestRepositoryCustom {

    List<AdvertisementRequest> findAllByCondition(RetrieveConditionForAdvertisementRequest retrieveConditionForAdvertisementRequest);
}

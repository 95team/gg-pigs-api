package com.pangoapi.posterRequest.repository;

import com.pangoapi.posterRequest.dto.RetrieveConditionDtoPosterRequest;
import com.pangoapi.posterRequest.entity.PosterRequest;

import java.util.List;

public interface PosterRequestRepositoryCustom {

    List<PosterRequest> findAllByCondition(RetrieveConditionDtoPosterRequest retrieveConditionForPosterRequest);
}

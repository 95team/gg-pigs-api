package com.gg_pigs.posterRequest.repository;

import com.gg_pigs.posterRequest.dto.RetrieveConditionDtoPosterRequest;
import com.gg_pigs.posterRequest.entity.PosterRequest;

import java.util.List;

public interface PosterRequestRepositoryCustom {

    List<PosterRequest> findAllByCondition(RetrieveConditionDtoPosterRequest retrieveConditionForPosterRequest);
}

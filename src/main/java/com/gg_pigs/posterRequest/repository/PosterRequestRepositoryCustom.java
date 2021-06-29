package com.gg_pigs.posterRequest.repository;

import com.gg_pigs.posterRequest.dto.ReadConditionDtoPosterRequest;
import com.gg_pigs.posterRequest.entity.PosterRequest;

import java.util.List;

public interface PosterRequestRepositoryCustom {

    List<PosterRequest> findAllByCondition(ReadConditionDtoPosterRequest retrieveConditionForPosterRequest);
}

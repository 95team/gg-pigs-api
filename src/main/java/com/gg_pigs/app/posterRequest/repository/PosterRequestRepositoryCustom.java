package com.gg_pigs.app.posterRequest.repository;

import com.gg_pigs.app.posterRequest.dto.ReadConditionDtoPosterRequest;
import com.gg_pigs.app.posterRequest.entity.PosterRequest;

import java.util.List;

public interface PosterRequestRepositoryCustom {

    List<PosterRequest> findAllByCondition(ReadConditionDtoPosterRequest retrieveConditionForPosterRequest);
}

package com.gg_pigs.poster.repository;

import com.gg_pigs.poster.dto.RetrieveConditionDtoPoster;
import com.gg_pigs.poster.entity.Poster;

import java.util.List;

public interface PosterRepositoryCustom {

    List<Poster> findAllByCondition(RetrieveConditionDtoPoster retrieveConditionForPoster);
}

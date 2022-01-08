package com.gg_pigs.app.poster.repository;

import com.gg_pigs.app.poster.dto.RetrieveConditionDtoPoster;
import com.gg_pigs.app.poster.entity.Poster;

import java.util.List;

public interface PosterRepositoryCustom {

    List<Poster> findAllByCondition(RetrieveConditionDtoPoster retrieveConditionForPoster);
}

package com.pangoapi.poster.repository;

import com.pangoapi.poster.dto.RetrieveConditionDtoPoster;
import com.pangoapi.poster.entity.Poster;

import java.util.List;

public interface PosterRepositoryCustom {

    List<Poster> findAllByCondition(RetrieveConditionDtoPoster retrieveConditionForPoster);
}

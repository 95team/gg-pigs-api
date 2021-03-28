package com.gg_pigs.poster.service;

import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.dto.RetrieveDtoPoster;
import com.gg_pigs.poster.dto.UpdateDtoPoster;

import java.util.HashMap;
import java.util.List;

public interface PosterService {

    /**
     * CREATE - Poster 단건 생성
     * */
    public Long createPoster(CreateDtoPoster createDtoPoster) throws Exception;

    /**
     * RETRIEVE - Poster 단건 조회
     * */
    public RetrieveDtoPoster retrievePoster(Long posterId);

    /**
     * RETRIEVE - Poster 전체 조회
     * */
    public List retrieveAllPosters(HashMap<String, String> condition);

    /**
     * UPDATE - Poster 수정
     * */
    public Long updatePoster(Long posterId, UpdateDtoPoster updateDtoPoster) throws Exception;

    /**
     * DELETE - Poster 삭제
     * */
    public void deletePoster(Long posterId);
}

package com.gg_pigs.app.poster.service;

import com.gg_pigs.app.poster.dto.CreateDtoPoster;
import com.gg_pigs.app.poster.dto.ReadDtoPoster;
import com.gg_pigs.app.poster.dto.UpdateDtoPoster;

import java.util.List;
import java.util.Map;

public interface PosterService {

    /** Poster 단건 생성 */
    Long createPoster(CreateDtoPoster createDtoPoster) throws Exception;

    /** Poster 단건 조회 */
    ReadDtoPoster readPoster(Long posterId);

    /** Poster 전체 조회 */
    List readPosters(Map<String, String> condition);

    /** Poster 단건 수정 */
    Long updatePoster(Long posterId, UpdateDtoPoster updateDtoPoster) throws Exception;

    /** Poster 단건 삭제 */
    void deletePoster(Long posterId);
}

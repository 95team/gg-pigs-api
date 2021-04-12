package com.gg_pigs.posterRequest.service;

import com.gg_pigs.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.RetrieveDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.UpdateDtoPosterRequest;

import java.util.HashMap;
import java.util.List;

public interface PosterRequestService {

    /** CREATE */
    /** PosterRequest 단건 생성 */
    Long createPosterRequest(CreateDtoPosterRequest createDtoPosterRequest) throws Exception;


    /** RETRIEVE */
    /** PosterRequest 단건 조회 */
    RetrieveDtoPosterRequest retrievePosterRequest(Long posterRequestId);

    /** PosterRequest 전체 조회 */
    List retrieveAllPosterRequests(HashMap<String, String> condition);


    /** UPDATE */
    /** PosterRequest 단건 수정 */
    Long updatePosterRequest(String work, String updaterEmail, Long posterRequestId, UpdateDtoPosterRequest updateDtoPosterRequest) throws Exception;


    /** DELETE */
    /** PosterRequest 단건 삭제 */
    void deletePosterRequest(Long posterRequestId);


    /** ETC */
    /** @description
     * 1. 신청 가능한 모든 자리를 조회합니다.
     * */
    List<String[]> retrieveAllPossibleSeats(HashMap<String, String> wantedDate) throws Exception;

    /**
     * @description
     * 1. '신청 가능한 모든 자리' 와 '요청이 들어온 자리' 와 비교하여 신청이 가능한지 확인합니다.
     */
    boolean isPossibleSeat(List<String[]> allPossibleSeats, Long rowPosition, Long columnPosition, String stringTypeOfPosterType);
}

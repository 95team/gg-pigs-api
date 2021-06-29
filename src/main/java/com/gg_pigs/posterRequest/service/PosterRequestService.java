package com.gg_pigs.posterRequest.service;

import com.gg_pigs.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.ReadDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.UpdateDtoPosterRequest;

import java.util.List;
import java.util.Map;

public interface PosterRequestService {

    /** PosterRequest 단건 생성 */
    Long createPosterRequest(CreateDtoPosterRequest createDtoPosterRequest) throws Exception;

    /** PosterRequest 단건 조회 */
    ReadDtoPosterRequest readPosterRequest(Long posterRequestId);

    /** PosterRequest 전체 조회 */
    List readPosterRequests(Map<String, String> condition);

    /** PosterRequest 단건 수정 */
    Long updatePosterRequest(String work, String updaterEmail, Long posterRequestId, UpdateDtoPosterRequest updateDtoPosterRequest) throws Exception;

    /** PosterRequest 단건 삭제 */
    void deletePosterRequest(Long posterRequestId);

    /** 신청가능한 자리 조회 */
    List<String[]> getAllPossibleSeats(Map<String, String> wantedDate) throws Exception;

    /** '신청 가능한 모든 자리' 와 '요청이 들어온 자리' 와 비교하여 신청이 가능한지 확인합니다. */
    boolean isPossibleSeat(List<String[]> allPossibleSeats, Long rowPosition, Long columnPosition, String stringTypeOfPosterType);
}

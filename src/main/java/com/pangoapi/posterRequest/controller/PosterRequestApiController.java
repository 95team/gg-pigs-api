package com.pangoapi.posterRequest.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi._common.exception.BadRequestException;
import com.pangoapi._common.utility.JwtProvider;
import com.pangoapi.posterRequest.dto.CreateDtoPosterRequest;
import com.pangoapi.posterRequest.dto.RetrieveDtoPosterRequest;
import com.pangoapi.posterRequest.dto.UpdateDtoPosterRequest;
import com.pangoapi.posterRequest.service.PosterRequestService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.pangoapi._common.CommonDefinition.POSTER_LAYOUT_SIZE;

@RequiredArgsConstructor
@RestController
public class PosterRequestApiController {

    private final JwtProvider jwtProvider;
    private final PosterRequestService posterRequestService;

    /**
     * CREATE
     * */
    @PostMapping("/api/v1/poster-requests")
    public ApiResponse createPosterRequest(@RequestBody CreateDtoPosterRequest createDtoPosterRequest) throws Exception {
        // 1. 요청이 들어온 Poster-Request 가 '신청 가능한 자리' 에 있는지 확인합니다.
        // 1-1. 현재 '신청 가능한 모든 자리' 를 조회합니다.
        int intTypeOfPage = Integer.parseInt(createDtoPosterRequest.getColumnPosition()) / POSTER_LAYOUT_SIZE + 1;
        if(Integer.parseInt(createDtoPosterRequest.getColumnPosition()) % POSTER_LAYOUT_SIZE == 0) {
            intTypeOfPage -= 1;
        }
        String page = Integer.toString(intTypeOfPage);
        String startedDate = createDtoPosterRequest.getStartedDate();
        String finishedDate = createDtoPosterRequest.getFinishedDate();

        List<String[]> allPossibleSeats = posterRequestService.retrieveAllPossibleSeats(
                new HashMap<String, String>() {{
                    put("page", page);
                    put("startedDate", startedDate);
                    put("finishedDate", finishedDate);
                }});

        // 1-2. '신청 가능한 모든 자리' 와 '요청이 들어온 자리' 와 비교하여 신청이 가능한지 확인합니다.
        if(!PosterRequestService.isPossibleSeat(
                allPossibleSeats,
                Long.parseLong(createDtoPosterRequest.getRowPosition()),
                Long.parseLong(createDtoPosterRequest.getColumnPosition()),
                createDtoPosterRequest.getPosterType())) {
            throw new BadRequestException("이미 사용 중인 자리입니다. (Please check the position, possible-seats)");
        }

        // 2. 신청이 가능하다면, Poster-Request 를 생성합니다.
        Long posterRequestId = posterRequestService.createPosterRequest(createDtoPosterRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterRequestId);
    }

    /**
     * RETRIEVE
     * */
    @GetMapping("/api/v1/poster-requests/{posterRequestId}")
    public ApiResponse retrievePosterRequest(@PathVariable("posterRequestId") Long _posterRequestId) {
        com.pangoapi.posterRequest.dto.RetrieveDtoPosterRequest retrieveDtoPosterRequest = posterRequestService.retrievePosterRequest(_posterRequestId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoPosterRequest);
    }

    @GetMapping("/api/v1/poster-requests")
    public ApiResponse retrieveAllPosterRequests(@RequestParam("page") Optional<String> page) {
        HashMap<String, String> retrieveOptions = new HashMap<>();
        if(page.isPresent()) {
            if (page.get().equalsIgnoreCase("0")) {
                retrieveOptions.put("page", "1");
            } else {
                retrieveOptions.put("page", page.get());
            }
        }

        List<com.pangoapi.posterRequest.dto.RetrieveDtoPosterRequest> allRetrieveDtoPosterRequests = posterRequestService.retrieveAllPosterRequests(retrieveOptions);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoPosterRequests);
    }

    @GetMapping("/api/v2/poster-requests")
    public ApiResponse retrieveAllPosterRequests(@RequestParam("page") Optional<String> page,
                                                       @RequestParam("userEmail") Optional<String> userEmail,
                                                       @RequestParam("isFilteredDate") Optional<String> isFilteredDate) {
        HashMap<String, String> retrieveCondition = new HashMap<>();

        if(page.isPresent()) retrieveCondition.put("page", page.get());
        else retrieveCondition.put("page", null);

        if(userEmail.isPresent()) retrieveCondition.put("userEmail", userEmail.get());
        else retrieveCondition.put("userEmail", null);

        if(isFilteredDate.isPresent()) retrieveCondition.put("isFilteredDate", isFilteredDate.get());
        else retrieveCondition.put("isFilteredDate", null);

        List<RetrieveDtoPosterRequest> allRetrieveDtoPosterRequests = posterRequestService.retrieveAllPosterRequests_v2(retrieveCondition);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoPosterRequests);
    }

    @GetMapping("/api/v1/poster-requests/seats")
    public ApiResponse retrieveAllPossibleSeats(@RequestParam("page") String page, @RequestParam("startedDate") String startedDate, @RequestParam("finishedDate") String finishedDate) throws Exception{
        List<String[]> allPossibleSeats = posterRequestService.retrieveAllPossibleSeats(
                new HashMap<String, String>() {{
                    put("page", page);
                    put("startedDate", startedDate);
                    put("finishedDate", finishedDate);
                }});

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allPossibleSeats);
    }

    /**
     * UPDATE
     * */
    @PutMapping("/api/v1/poster-requests/{posterRequestId}")
    public ApiResponse updatePosterRequest(
            @CookieValue("jwt") String token,
            @RequestParam("work") String work,
            @PathVariable("posterRequestId") Long _posterRequestId,
            @RequestBody UpdateDtoPosterRequest updateDtoPosterRequest) throws Exception {

        Claims payload = jwtProvider.getPayloadFromToken(token);
        String updaterEmail = payload.getAudience();

        Long posterRequestId = posterRequestService.updatePosterRequest(work, updaterEmail, _posterRequestId, updateDtoPosterRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterRequestId);
    }

    /**
     * DELETE
     * */
    @DeleteMapping("/api/v1/poster-requests/{posterRequestId}")
    public ApiResponse deletePosterRequest(@PathVariable("posterRequestId") Long _posterRequestId) {
        posterRequestService.deletePosterRequest(_posterRequestId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

package com.pangoapi.advertisementRequest.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi._common.exception.BadRequestException;
import com.pangoapi.advertisementRequest.dto.CreateDtoAdvertisementRequest;
import com.pangoapi.advertisementRequest.dto.RetrieveDtoAdvertisementRequest;
import com.pangoapi.advertisementRequest.dto.UpdateDtoAdvertisementRequest;
import com.pangoapi.advertisementRequest.service.AdvertisementRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

import static com.pangoapi._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;

@RequiredArgsConstructor
@RestController
public class AdvertisementRequestApiController {

    private final AdvertisementRequestService advertisementRequestService;

    /**
     * CREATE
     * */
    @PostMapping("/api/v1/advertisement-requests")
    public ApiResponse createOneAdvertisementRequest(@RequestBody CreateDtoAdvertisementRequest createDtoAdvertisementRequest) throws Exception {
        // 1. 요청이 들어온 Advertisement-Request 가 '신청 가능한 자리' 에 있는지 확인합니다.
        // 1-1. 현재 '신청 가능한 모든 자리' 를 조회합니다.
        int intTypeOfPage = Integer.parseInt(createDtoAdvertisementRequest.getColumnPosition()) / ADVERTISEMENT_LAYOUT_SIZE + 1;
        if(Integer.parseInt(createDtoAdvertisementRequest.getColumnPosition()) % ADVERTISEMENT_LAYOUT_SIZE == 0) {
            intTypeOfPage -= 1;
        }
        String page = Integer.toString(intTypeOfPage);
        String startedDate = createDtoAdvertisementRequest.getStartedDate();
        String finishedDate = createDtoAdvertisementRequest.getFinishedDate();

        List<String[]> allPossibleSeats = advertisementRequestService.retrieveAllPossibleSeats(
                new HashMap<String, String>() {{
                    put("page", page);
                    put("startedDate", startedDate);
                    put("finishedDate", finishedDate);
                }});

        // 1-2. '신청 가능한 모든 자리' 와 '요청이 들어온 자리' 와 비교하여 신청이 가능한지 확인합니다.
        if(!AdvertisementRequestService.isPossibleSeat(
                allPossibleSeats,
                Long.parseLong(createDtoAdvertisementRequest.getRowPosition()),
                Long.parseLong(createDtoAdvertisementRequest.getColumnPosition()),
                createDtoAdvertisementRequest.getAdvertisementType())) {
            throw new BadRequestException("이미 사용 중인 자리입니다. (Please check the position, possible-seats)");
        }

        // 2. 신청이 가능하다면, Advertisement-Request 를 생성합니다.
        Long advertisementRequestId = advertisementRequestService.createOneAdvertisementRequest(createDtoAdvertisementRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementRequestId);
    }

    /**
     * RETRIEVE
     * */
    @GetMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse retrieveOneAdvertisementRequest(@PathVariable("advertisementRequestId") Long _advertisementRequestId) {
        RetrieveDtoAdvertisementRequest retrieveDtoAdvertisementRequest = advertisementRequestService.retrieveOneAdvertisementRequest(_advertisementRequestId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoAdvertisementRequest);
    }

    @GetMapping("/api/v1/advertisement-requests")
    public ApiResponse retrieveAllAdvertisementRequest(@RequestParam("page") Optional<String> page) {
        HashMap<String, String> retrieveOptions = new HashMap<>();
        if(page.isPresent()) {
            if (page.get().equalsIgnoreCase("0")) {
                retrieveOptions.put("page", "1");
            } else {
                retrieveOptions.put("page", page.get());
            }
        }

        List<RetrieveDtoAdvertisementRequest> allRetrieveDtoAdvertisementRequests = advertisementRequestService.retrieveAllAdvertisementRequest(retrieveOptions);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoAdvertisementRequests);
    }

    @GetMapping("/api/v1/advertisement-requests/seats")
    public ApiResponse retrieveAllPossibleSeats(@RequestParam("page") String page, @RequestParam("startedDate") String startedDate, @RequestParam("finishedDate") String finishedDate) throws Exception{
        List<String[]> allPossibleSeats = advertisementRequestService.retrieveAllPossibleSeats(
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
    @PutMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse updateOneAdvertisementRequest(@PathVariable("advertisementRequestId") Long _advertisementRequestId, @RequestBody UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) throws Exception {
        Long advertisementRequestId = advertisementRequestService.updateOneAdvertisementRequest(_advertisementRequestId, updateDtoAdvertisementRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementRequestId);
    }

    /**
     * DELETE
     * */
    @DeleteMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse deleteOneAdvertisementRequest(@PathVariable("advertisementRequestId") Long _advertisementRequestId) {
        advertisementRequestService.deleteOneAdvertisementRequest(_advertisementRequestId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

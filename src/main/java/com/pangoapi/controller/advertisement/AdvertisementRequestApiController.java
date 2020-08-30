package com.pangoapi.controller.advertisement;

import com.pangoapi.dto.ApiResponse;
import com.pangoapi.dto.advertisement.CreateDtoAdvertisementRequest;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisement;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisementRequest;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisementRequest;
import com.pangoapi.service.advertisement.AdvertisementRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdvertisementRequestApiController {

    private final AdvertisementRequestService advertisementRequestService;

    /**
     * CREATE
     * */
    @PostMapping("/api/v1/advertisement-requests")
    public ApiResponse createOneAdvertisementRequest(@RequestBody CreateDtoAdvertisementRequest createDtoAdvertisementRequest) {
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
    public ApiResponse retrieveAllAdvertisementRequest() {
        List<RetrieveDtoAdvertisement> allRetrieveDtoAdvertisementRequests = advertisementRequestService.retrieveAllAdvertisementRequest();

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoAdvertisementRequests);
    }

    /**
     * UPDATE
     * */
    @PutMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse updateOneAdvertisementRequest(@PathVariable("advertisementRequestId") Long _advertisementRequestId, @RequestBody UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) {
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

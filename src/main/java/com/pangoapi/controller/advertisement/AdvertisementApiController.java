package com.pangoapi.controller.advertisement;

import com.pangoapi.dto.advertisement.CreateDtoAdvertisement;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisement;
import com.pangoapi.dto.ApiResponse;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisement;
import com.pangoapi.service.advertisement.AdvertisementService;
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
public class AdvertisementApiController {

    private final AdvertisementService advertisementService;

    /**
     * CREATE
     * */
    @PostMapping("/api/v1/advertisements")
    public ApiResponse createOneAdvertisement(@RequestBody CreateDtoAdvertisement createDtoAdvertisement) {
        Long advertisementId = advertisementService.createOneAdvertisement(createDtoAdvertisement);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementId);
    }

    /**
     * RETRIEVE
     * */
    @GetMapping("/api/v1/advertisements/{advertisementId}")
    public ApiResponse retrieveOneAdvertisement(@PathVariable("advertisementId") Long _advertisementId) {
        RetrieveDtoAdvertisement retrieveDtoAdvertisement = advertisementService.retrieveOneAdvertisement(_advertisementId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoAdvertisement);
    }

    @GetMapping("/api/v1/advertisements")
    public ApiResponse retrieveAllAdvertisement() {
        List<RetrieveDtoAdvertisement> allRetrieveDtoAdvertisements = advertisementService.retrieveAllAdvertisement();

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoAdvertisements);
    }

    /**
     * UPDATE
     * */
    @PutMapping("/api/v1/advertisements/{advertisementId}")
    public ApiResponse updateOneAdvertisement(@PathVariable("advertisementId") Long _advertisementId, @RequestBody UpdateDtoAdvertisement updateDtoAdvertisement) {
        Long advertisementId = advertisementService.updateOneAdvertisement(_advertisementId, updateDtoAdvertisement);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementId);
    }

    /**
     * DELETE
     * */
    @DeleteMapping("/api/v1/advertisements/{advertisementId}")
    public ApiResponse deleteOneAdvertisement(@PathVariable("advertisementId") Long _advertisementId) {
        advertisementService.deleteOneAdvertisement(_advertisementId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

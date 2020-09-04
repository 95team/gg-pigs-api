package com.pangoapi.controller.advertisement;

import com.pangoapi.dto.ApiResponse;
import com.pangoapi.dto.advertisement.CreateDtoAdvertisementRequest;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisement;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisementRequest;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisementRequest;
import com.pangoapi.service.advertisement.AdvertisementRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Advertisement-Request API")
public class AdvertisementRequestApiController {

    private final AdvertisementRequestService advertisementRequestService;

    /**
     * CREATE
     * */
    @ApiOperation(value = "Advertisement-Request 한 건 생성", notes = "Advertisement-Request 한 건을 생성합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/api/v1/advertisement-requests")
    public ApiResponse createOneAdvertisementRequest(@ApiParam(value = "Advertisement-Request 을/를 생성하기 위한 Json 데이터 포맷", required = true) @RequestBody CreateDtoAdvertisementRequest createDtoAdvertisementRequest) {
        Long advertisementRequestId = advertisementRequestService.createOneAdvertisementRequest(createDtoAdvertisementRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementRequestId);
    }

    /**
     * RETRIEVE
     * */
    @ApiOperation(value = "Advertisement-Request 한 건 조회", notes = "Advertisement-Request 한 건을 조회합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse retrieveOneAdvertisementRequest(@ApiParam(value = "Advertisement-Request 을/를 조회하기 위한 고유 ID", required = true) @PathVariable("advertisementRequestId") Long _advertisementRequestId) {
        RetrieveDtoAdvertisementRequest retrieveDtoAdvertisementRequest = advertisementRequestService.retrieveOneAdvertisementRequest(_advertisementRequestId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoAdvertisementRequest);
    }

    @ApiOperation(value = "Advertisement-Request 전체 건 조회", notes = "Advertisement-Request 전체 건을 조회합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api/v1/advertisement-requests")
    public ApiResponse retrieveAllAdvertisementRequest() {
        List<RetrieveDtoAdvertisement> allRetrieveDtoAdvertisementRequests = advertisementRequestService.retrieveAllAdvertisementRequest();

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoAdvertisementRequests);
    }

    /**
     * UPDATE
     * */
    @ApiOperation(value = "Advertisement-Request 한 건 업데이트", notes = "Advertisement-Request 한 건을 업데이트합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse updateOneAdvertisementRequest(@ApiParam(value = "Advertisement-Request 을/를 업데이트하기 위한 고유 ID", required = true) @PathVariable("advertisementRequestId") Long _advertisementRequestId,
                                                     @ApiParam(value = "Advertisement-Request 을/를 업데이트하기 위한 Json 데이터 포맷", required = true) @RequestBody UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) {
        Long advertisementRequestId = advertisementRequestService.updateOneAdvertisementRequest(_advertisementRequestId, updateDtoAdvertisementRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementRequestId);
    }

    /**
     * DELETE
     * */
    @ApiOperation(value = "Advertisement-Request 한 건 삭제", notes = "Advertisement-Request 한 건을 삭제합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/api/v1/advertisement-requests/{advertisementRequestId}")
    public ApiResponse deleteOneAdvertisementRequest(@ApiParam(value = "Advertisement-Request 을/를 삭제하기 위한 고유 ID", required = true) @PathVariable("advertisementRequestId") Long _advertisementRequestId) {
        advertisementRequestService.deleteOneAdvertisementRequest(_advertisementRequestId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

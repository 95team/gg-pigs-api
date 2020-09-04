package com.pangoapi.controller.advertisement;

import com.pangoapi.dto.advertisement.CreateDtoAdvertisement;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisement;
import com.pangoapi.dto.ApiResponse;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisement;
import com.pangoapi.service.advertisement.AdvertisementService;
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
@Api(value = "Advertisement API")
public class AdvertisementApiController {

    private final AdvertisementService advertisementService;

    /**
     * CREATE
     * */
    @ApiOperation(value = "Advertisement 한 건 생성", notes = "Advertisement 한 건을 생성합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/api/v1/advertisements")
    public ApiResponse createOneAdvertisement(@ApiParam(value = "Advertisement 을/를 생성하기 위한 Json 데이터 포맷", required = true) @RequestBody CreateDtoAdvertisement createDtoAdvertisement) {
        Long advertisementId = advertisementService.createOneAdvertisement(createDtoAdvertisement);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementId);
    }

    /**
     * RETRIEVE
     * */
    @ApiOperation(value = "Advertisement 한 건 조회", notes = "Advertisement 한 건을 조회합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api/v1/advertisements/{advertisementId}")
    public ApiResponse retrieveOneAdvertisement(@ApiParam(value = "Advertisement 을/를 조회하기 위한 고유 ID", required = true) @PathVariable("advertisementId") Long _advertisementId) {
        RetrieveDtoAdvertisement retrieveDtoAdvertisement = advertisementService.retrieveOneAdvertisement(_advertisementId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoAdvertisement);
    }

    @ApiOperation(value = "Advertisement 전체 건 조회", notes = "Advertisement 전체 건을 조회합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/api/v1/advertisements")
    public ApiResponse retrieveAllAdvertisement() {
        List<RetrieveDtoAdvertisement> allRetrieveDtoAdvertisements = advertisementService.retrieveAllAdvertisement();

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoAdvertisements);
    }

    /**
     * UPDATE
     * */
    @ApiOperation(value = "Advertisement 한 건 업데이트", notes = "Advertisement 한 건을 업데이트합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/api/v1/advertisements/{advertisementId}")
    public ApiResponse updateOneAdvertisement(@ApiParam(value = "Advertisement 을/를 업데이트하기 위한 고유 ID", required = true) @PathVariable("advertisementId") Long _advertisementId,
                                              @ApiParam(value = "Advertisement 을/를 업데이트하기 위한 Json 데이터 포맷", required = true) @RequestBody UpdateDtoAdvertisement updateDtoAdvertisement) {
        Long advertisementId = advertisementService.updateOneAdvertisement(_advertisementId, updateDtoAdvertisement);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), advertisementId);
    }

    /**
     * DELETE
     * */
    @ApiOperation(value = "Advertisement 한 건 삭제", notes = "Advertisement 한 건을 삭제합니다.")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/api/v1/advertisements/{advertisementId}")
    public ApiResponse deleteOneAdvertisement(@ApiParam(value = "Advertisement 을/를 삭제하기 위한 고유 ID", required = true) @PathVariable("advertisementId") Long _advertisementId) {
        advertisementService.deleteOneAdvertisement(_advertisementId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

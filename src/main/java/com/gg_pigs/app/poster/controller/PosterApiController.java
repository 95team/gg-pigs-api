package com.gg_pigs.app.poster.controller;

import com.gg_pigs.app.poster.dto.PosterDto;
import com.gg_pigs.app.poster.service.PosterService;
import com.gg_pigs.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PosterApiController {

    private final PosterService posterService;

    /** CREATE */
    @PostMapping("/api/v1/posters")
    public ApiResponse<?> create(@RequestBody PosterDto.Create.RequestDto requestDto) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterService.create(requestDto));
    }

    /** READ */
    @GetMapping("/api/v1/posters/{posterId}")
    public ApiResponse<?> read(@PathVariable("posterId") Long posterId) {
        PosterDto.Read.ResponseDto readDtoPoster = posterService.read(posterId);

        return new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), readDtoPoster);
    }

    @GetMapping({"/api/v1/posters", "/api/v2/posters"})
    public ApiResponse<?> readAll(@RequestParam(value = "page", required = false) String page,
                                  @RequestParam(value = "userEmail", required = false) String userEmail,
                                  @RequestParam(value = "isFilteredDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate isFilteredDate,
                                  @RequestParam(value = "isActivated", required = false) String isActivated) {

        PosterDto.Read.SearchConditionDto searchCondition = PosterDto.Read.SearchConditionDto.of(page, userEmail, isActivated, isFilteredDate);
        List<PosterDto.Read.ResponseDto> allReadDtoPosters = posterService.readAll(searchCondition);
        return new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allReadDtoPosters);
    }

    /** UPDATE */
    @PutMapping("/api/v1/posters/{posterId}")
    public ApiResponse<?> update(@PathVariable("posterId") Long posterId,
                                 @RequestBody PosterDto.Update.RequestDto requestDto) throws Exception {
        return new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterService.update(posterId, requestDto));
    }

    /** DELETE */
    @DeleteMapping("/api/v1/posters/{posterId}")
    public ApiResponse<?> delete(@PathVariable("posterId") Long posterId) {
        posterService.delete(posterId);

        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

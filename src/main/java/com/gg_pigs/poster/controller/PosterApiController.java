package com.gg_pigs.poster.controller;

import com.gg_pigs._common.dto.ApiResponse;
import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.dto.ReadDtoPoster;
import com.gg_pigs.poster.dto.UpdateDtoPoster;
import com.gg_pigs.poster.service.PosterService;
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
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PosterApiController {

    private final PosterService posterService;

    /** CREATE */
    @PostMapping("/api/v1/posters")
    public ApiResponse create(@RequestBody CreateDtoPoster createDtoPoster) throws Exception {
        Long posterId = posterService.createPoster(createDtoPoster);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterId);
    }

    /** READ */
    @GetMapping("/api/v1/posters/{posterId}")
    public ApiResponse read(@PathVariable("posterId") Long posterId) {
        ReadDtoPoster readDtoPoster = posterService.readPoster(posterId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), readDtoPoster);
    }

    @GetMapping({"/api/v1/posters", "/api/v2/posters"})
    public ApiResponse readAll(@RequestParam("page") Optional<String> page,
                               @RequestParam("userEmail") Optional<String> userEmail,
                               @RequestParam("isFilteredDate") Optional<String> isFilteredDate,
                               @RequestParam("isActivated") Optional<String> isActivated) {
        Map<String, String> condition = new HashMap<>();

        if(page.isPresent()) condition.put("page", page.get());
        else condition.put("page", null);

        if(userEmail.isPresent()) condition.put("userEmail", userEmail.get());
        else condition.put("userEmail", null);

        if(isFilteredDate.isPresent()) condition.put("isFilteredDate", isFilteredDate.get());
        else condition.put("isFilteredDate", null);

        if(isActivated.isPresent()) condition.put("isActivated", isActivated.get());
        else condition.put("isActivated", null);

        List allReadDtoPosters = posterService.readPosters(condition);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allReadDtoPosters);
    }

    /** UPDATE */
    @PutMapping("/api/v1/posters/{posterId}")
    public ApiResponse update(@PathVariable("posterId") Long posterId, @RequestBody UpdateDtoPoster updateDtoPoster) throws Exception {
        Long updatePosterId = posterService.updatePoster(posterId, updateDtoPoster);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), updatePosterId);
    }

    /** DELETE */
    @DeleteMapping("/api/v1/posters/{posterId}")
    public ApiResponse delete(@PathVariable("posterId") Long posterId) {
        posterService.deletePoster(posterId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

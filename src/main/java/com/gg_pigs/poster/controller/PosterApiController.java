package com.gg_pigs.poster.controller;

import com.gg_pigs._common.dto.ApiResponse;
import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.dto.RetrieveDtoPoster;
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
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PosterApiController {

    private final PosterService posterService;

    /**
     * CREATE
     * */
    @PostMapping("/api/v1/posters")
    public ApiResponse createPoster(@RequestBody CreateDtoPoster createDtoPoster) throws Exception {
        Long posterId = posterService.createPoster(createDtoPoster);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterId);
    }

    /**
     * RETRIEVE
     * */
    @GetMapping("/api/v1/posters/{posterId}")
    public ApiResponse retrievePoster(@PathVariable("posterId") Long _posterId) {
        RetrieveDtoPoster retrieveDtoPoster = posterService.retrievePoster(_posterId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoPoster);
    }

    @GetMapping("/api/v1/posters")
    public ApiResponse retrieveAllPosters(@RequestParam("page") Optional<String> page) {
        HashMap<String, String> retrieveOptions = new HashMap<String, String>();
        if(page.isPresent()) {
            if (page.get().equalsIgnoreCase("0")) {
                retrieveOptions.put("page", "1");
            } else {
                retrieveOptions.put("page", page.get());
            }
        }

        List<RetrieveDtoPoster> allRetrieveDtoPosters = posterService.retrieveAllPosters(retrieveOptions);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoPosters);
    }

    @GetMapping("/api/v2/posters")
    public ApiResponse retrieveAllPosters(@RequestParam("page") Optional<String> page,
                                          @RequestParam("userEmail") Optional<String> userEmail,
                                          @RequestParam("isFilteredDate") Optional<String> isFilteredDate,
                                          @RequestParam("isActivated") Optional<String> isActivated) {
        HashMap<String, String> retrieveCondition = new HashMap<>();

        if(page.isPresent()) retrieveCondition.put("page", page.get());
        else retrieveCondition.put("page", null);

        if(userEmail.isPresent()) retrieveCondition.put("userEmail", userEmail.get());
        else retrieveCondition.put("userEmail", null);

        if(isFilteredDate.isPresent()) retrieveCondition.put("isFilteredDate", isFilteredDate.get());
        else retrieveCondition.put("isFilteredDate", null);

        if(isActivated.isPresent()) retrieveCondition.put("isActivated", isActivated.get());
        else retrieveCondition.put("isActivated", null);

        List<RetrieveDtoPoster> allRetrieveDtoPosters = posterService.retrieveAllPosters_v2(retrieveCondition);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoPosters);
    }

    /**
     * UPDATE
     * */
    @PutMapping("/api/v1/posters/{posterId}")
    public ApiResponse updatePoster(@PathVariable("posterId") Long _posterId, @RequestBody UpdateDtoPoster updateDtoPoster) throws Exception {
        Long posterId = posterService.updatePoster(_posterId, updateDtoPoster);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterId);
    }

    /**
     * DELETE
     * */
    @DeleteMapping("/api/v1/posters/{posterId}")
    public ApiResponse deletePoster(@PathVariable("posterId") Long _posterId) {
        posterService.deletePoster(_posterId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

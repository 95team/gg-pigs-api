package com.gg_pigs.app.posterRequest.controller;

import com.gg_pigs.app.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.app.posterRequest.dto.ReadDtoPosterRequest;
import com.gg_pigs.app.posterRequest.dto.UpdateDtoPosterRequest;
import com.gg_pigs.app.posterRequest.service.PosterRequestService;
import com.gg_pigs.app.user.entity.UserRole;
import com.gg_pigs.global.dto.ApiResponse;
import com.gg_pigs.global.exception.BadRequestException;
import com.gg_pigs.global.utility.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
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
import java.util.Map;
import java.util.Optional;

import static com.gg_pigs.global.CommonDefinition.POSTER_LAYOUT_SIZE;

@RequiredArgsConstructor
@RestController
public class PosterRequestApiController {

    private final JwtProvider jwtProvider;
    private final PosterRequestService posterRequestService;

    /** CREATE */
    @PostMapping("/api/v1/poster-requests")
    public ApiResponse create(@RequestBody CreateDtoPosterRequest createDtoPosterRequest) throws Exception {
        // 1. 요청이 들어온 Poster-Request 가 '신청 가능한 자리' 에 있는지 확인합니다.
        // 1-1. 현재 '신청 가능한 모든 자리' 를 조회합니다.
        int intTypeOfPage = Integer.parseInt(createDtoPosterRequest.getColumnPosition()) / POSTER_LAYOUT_SIZE + 1;
        if(Integer.parseInt(createDtoPosterRequest.getColumnPosition()) % POSTER_LAYOUT_SIZE == 0) {
            intTypeOfPage -= 1;
        }
        String page = Integer.toString(intTypeOfPage);
        String startedDate = createDtoPosterRequest.getStartedDate();
        String finishedDate = createDtoPosterRequest.getFinishedDate();

        List<String[]> allPossibleSeats = posterRequestService.getAllPossibleSeats(
                new HashMap<String, String>() {{
                    put("page", page);
                    put("startedDate", startedDate);
                    put("finishedDate", finishedDate);
                }});

        // 1-2. '신청 가능한 모든 자리' 와 '요청이 들어온 자리' 와 비교하여 신청이 가능한지 확인합니다.
        if(!posterRequestService.isPossibleSeat(
                allPossibleSeats,
                Long.parseLong(createDtoPosterRequest.getRowPosition()),
                Long.parseLong(createDtoPosterRequest.getColumnPosition()),
                createDtoPosterRequest.getPosterType())) {
            throw new BadRequestException("이미 사용 중인 자리입니다. (Please check the position, possible-seats)");
        }

        // 2. 신청이 가능하다면, Poster-Request 를 생성합니다.
        Long posterRequestId = posterRequestService.create(createDtoPosterRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), posterRequestId);
    }

    /** RETRIEVE */
    @GetMapping("/api/v1/poster-requests/{posterRequestId}")
    public ApiResponse read(@PathVariable("posterRequestId") Long posterRequestId) {
        ReadDtoPosterRequest readDtoPosterRequest = posterRequestService.read(posterRequestId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), readDtoPosterRequest);
    }

    @GetMapping({"/api/v1/poster-requests", "/api/v2/poster-requests"})
    public ApiResponse readAll(@RequestParam("page") Optional<String> page,
                               @RequestParam("userEmail") Optional<String> userEmail,
                               @RequestParam("isFilteredDate") Optional<String> isFilteredDate) {
        Map<String, String> retrieveCondition = new HashMap<>();

        if(page.isPresent()) retrieveCondition.put("page", page.get());
        else retrieveCondition.put("page", null);

        if(userEmail.isPresent()) retrieveCondition.put("userEmail", userEmail.get());
        else retrieveCondition.put("userEmail", null);

        if(isFilteredDate.isPresent()) retrieveCondition.put("isFilteredDate", isFilteredDate.get());
        else retrieveCondition.put("isFilteredDate", null);

        List<ReadDtoPosterRequest> allReadDtoPosterRequests = posterRequestService.readAll(retrieveCondition);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allReadDtoPosterRequests);
    }

    @GetMapping("/api/v1/poster-requests/seats")
    public ApiResponse getAllPossibleSeats(@RequestParam("page") String page, @RequestParam("startedDate") String startedDate, @RequestParam("finishedDate") String finishedDate) throws Exception{
        List<String[]> allPossibleSeats = posterRequestService.getAllPossibleSeats(
                new HashMap<String, String>() {{
                    put("page", page);
                    put("startedDate", startedDate);
                    put("finishedDate", finishedDate);
                }});

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allPossibleSeats);
    }

    /** UPDATE */
    @PutMapping("/api/v1/poster-requests/{posterRequestId}")
    public ApiResponse update(
            @CookieValue("jwt") String token,
            @RequestParam("work") String work,
            @PathVariable("posterRequestId") Long posterRequestId,
            @RequestBody UpdateDtoPosterRequest updateDtoPosterRequest) throws Exception {

        Claims payload = jwtProvider.getPayloadFromToken(token);
        String updaterEmail = payload.getAudience();
        String updaterRole = (String) payload.get("role");

        if(!updaterRole.equalsIgnoreCase(String.valueOf(UserRole.ROLE_ADMIN))) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Please check the authorization)");
        }
        if(!StringUtils.hasText(updaterEmail)) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Please check the admin's email)");
        }
        if(!work.equalsIgnoreCase("review")) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Please check the parameter value)");
        }

        Long updatePosterRequestId = posterRequestService.update(work, updaterEmail, posterRequestId, updateDtoPosterRequest);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), updatePosterRequestId);
    }

    /** DELETE */
    @DeleteMapping("/api/v1/poster-requests/{posterRequestId}")
    public ApiResponse delete(@PathVariable("posterRequestId") Long posterRequestId) {
        posterRequestService.delete(posterRequestId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}

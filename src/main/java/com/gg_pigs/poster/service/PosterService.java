package com.gg_pigs.poster.service;

import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.dto.RetrieveConditionDtoPoster;
import com.gg_pigs.poster.dto.RetrieveDtoPoster;
import com.gg_pigs.poster.dto.UpdateDtoPoster;
import com.gg_pigs.poster.entity.Poster;
import com.gg_pigs.poster.repository.PosterRepository;
import com.gg_pigs.posterType.entity.PosterType;
import com.gg_pigs.posterType.repository.PosterTypeRepository;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.gg_pigs._common.CommonDefinition.POSTER_LAYOUT_SIZE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PosterService {

    private final UserRepository userRepository;
    private final PosterRepository posterRepository;
    private final PosterTypeRepository posterTypeRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createPoster(CreateDtoPoster createDtoPoster) throws Exception {
        User user = userRepository.findUserByEmail(createDtoPoster.getUserEmail()).orElse(null);
        PosterType posterType = posterTypeRepository.findPosterTypeByType(createDtoPoster.getPosterType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Long posterId = null;
        try {
            posterId = posterRepository.save(Poster.createPoster(createDtoPoster, user, posterType)).getId();
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)");
        }

        return posterId;
    }

    /**
     * RETRIEVE
     */
    public RetrieveDtoPoster retrievePoster(Long _posterId) {
        Poster poster = posterRepository.findById(_posterId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoPoster.createRetrieveDtoPoster(poster);
    }

    public List retrieveAllPosters(HashMap<String, String> retrieveOptions) {
        Long startIndexOfPage = 1L, lastIndexOfPage = Long.valueOf(POSTER_LAYOUT_SIZE);
        boolean isUnlimited = false;

        try {
            if(retrieveOptions.containsKey("page")) {
                if(retrieveOptions.get("page").equalsIgnoreCase("-1")) {
                    isUnlimited = true;
                }
                else {
                    startIndexOfPage = (Long.parseLong(retrieveOptions.get("page")) - 1) * POSTER_LAYOUT_SIZE + 1;
                    lastIndexOfPage = Long.parseLong(retrieveOptions.get("page")) * POSTER_LAYOUT_SIZE;
                }
            }
        } catch (Exception exception) {
            throw new IllegalArgumentException("적절하지 않은 요청입니다. (Please check the parameters)");
        }

        List<Poster> posters;
        if(isUnlimited == true) {
            posters = posterRepository.findAll();
        }
        else {
            posters = posterRepository.findAllByPage(startIndexOfPage, lastIndexOfPage);
        }

        return posters.stream().map(poster -> RetrieveDtoPoster.createRetrieveDtoPoster(poster)).collect(Collectors.toList());
    }

    public List retrieveAllPosters_v2(HashMap<String, String> retrieveCondition) {
        RetrieveConditionDtoPoster condition = new RetrieveConditionDtoPoster();

        // 1. Page 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("page"))) {
            if(retrieveCondition.get("page").equalsIgnoreCase("-1")) {
                condition.isUnlimitedIsTrue();
            }
            else if(retrieveCondition.get("page").equalsIgnoreCase("0")) {
                condition.isUnlimitedIsFalse();
                condition.pageIsDefault();
            }
            else {
                condition.isUnlimitedIsFalse();
                condition.setPage(retrieveCondition.get("page"));
                condition.calculatePage();
            }
        }
        else {
            condition.isUnlimitedIsFalse();
            condition.pageIsDefault();
        }

        // 2. UserEmail 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("userEmail"))) {
            condition.hasUserEmailIsTrue();
            condition.setUserEmail(retrieveCondition.get("userEmail"));
        }
        else {
            condition.hasUserEmailIsFalse();
        }

        // 3. IsFilteredDate 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("isFilteredDate"))) {
            if(retrieveCondition.get("isFilteredDate").equalsIgnoreCase("true") ||
                    retrieveCondition.get("isFilteredDate").equalsIgnoreCase("y")) {
                condition.isFilteredDateIsTrue();
            }
            else {
                condition.isFilteredDateIsFalse();
            }
        }
        else {
            condition.isFilteredDateIsTrue();
        }

        // 4. IsActivated 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("isActivated"))) {
            if(retrieveCondition.get("isActivated").equalsIgnoreCase("-1")) {
                condition.filteredByActivatedIsFalse();
            }
            else if(retrieveCondition.get("isActivated").equalsIgnoreCase("true") ||
                    retrieveCondition.get("isActivated").equalsIgnoreCase("y")) {
                condition.setIsActivated('Y');
                condition.filteredByActivatedIsTrue();
            }
            else {
                condition.setIsActivated('N');
                condition.filteredByActivatedIsTrue();
            }
        }
        else {
            condition.setIsActivated('Y');
            condition.filteredByActivatedIsTrue();
        }

        List<Poster> posters = posterRepository.findAllByCondition(condition);

        return posters.stream().map(poster -> RetrieveDtoPoster.createRetrieveDtoPoster(poster)).collect(Collectors.toList());
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updatePoster(Long _posterId, UpdateDtoPoster updateDtoPoster) throws Exception {
        Poster poster = posterRepository.findById(_posterId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        poster.changePoster(updateDtoPoster);

        return poster.getId();
    }

    /**
     * DELETE
     */
    public void deletePoster(Long _posterId) {
        posterRepository.deleteById(_posterId);
    }
}

package com.gg_pigs.app.poster.service;

import com.gg_pigs.app.poster.dto.CreateDtoPoster;
import com.gg_pigs.app.poster.dto.RetrieveConditionDtoPoster;
import com.gg_pigs.app.poster.dto.ReadDtoPoster;
import com.gg_pigs.app.poster.dto.UpdateDtoPoster;
import com.gg_pigs.app.poster.entity.Poster;
import com.gg_pigs.app.poster.repository.PosterRepository;
import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.posterType.repository.PosterTypeRepository;
import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PosterServiceImpl implements PosterService {

    private final UserRepository userRepository;
    private final PosterRepository posterRepository;
    private final PosterTypeRepository posterTypeRepository;

    /** CREATE */
    @Override
    @Transactional
    public Long createPoster(CreateDtoPoster createDtoPoster) throws Exception {
        User user = userRepository.findUserByEmail(createDtoPoster.getUserEmail()).orElse(null);
        PosterType posterType = posterTypeRepository.findPosterTypeByType(createDtoPoster.getPosterType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Long posterId;
        try {
            posterId = posterRepository.save(Poster.createPoster(createDtoPoster, user, posterType)).getId();
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)");
        }

        return posterId;
    }

    /** READ */
    @Override
    public ReadDtoPoster readPoster(Long posterId) {
        Poster poster = posterRepository.findById(posterId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return ReadDtoPoster.of(poster);
    }

    @Override
    public List readPosters(Map<String, String> retrieveCondition) {
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

        return posters.stream().map(ReadDtoPoster::of).collect(Collectors.toList());
    }

    /** UPDATE */
    @Override
    @Transactional
    public Long updatePoster(Long posterId, UpdateDtoPoster updateDtoPoster) throws Exception {
        Poster poster = posterRepository.findById(posterId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        poster.changePoster(updateDtoPoster);

        return poster.getId();
    }

    /** DELETE */
    @Override
    public void deletePoster(Long posterId) {
        posterRepository.deleteById(posterId);
    }
}

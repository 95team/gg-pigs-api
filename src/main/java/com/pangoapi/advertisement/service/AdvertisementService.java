package com.pangoapi.advertisement.service;

import com.pangoapi.advertisement.dto.CreateDtoAdvertisement;
import com.pangoapi.advertisement.dto.RetrieveConditionForAdvertisement;
import com.pangoapi.advertisement.dto.RetrieveDtoAdvertisement;
import com.pangoapi.advertisement.entity.Advertisement;
import com.pangoapi.advertisementType.entity.AdvertisementType;
import com.pangoapi.user.entity.User;
import com.pangoapi.advertisement.dto.UpdateDtoAdvertisement;
import com.pangoapi.advertisement.repository.AdvertisementRepository;
import com.pangoapi.advertisementType.repository.AdvertisementTypeRepository;
import com.pangoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.pangoapi._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdvertisementService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementTypeRepository advertisementTypeRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createOneAdvertisement(CreateDtoAdvertisement createDtoAdvertisement) throws Exception {
        User user = userRepository.findUserByEmail(createDtoAdvertisement.getUserEmail()).orElse(null);
        AdvertisementType advertisementType = advertisementTypeRepository.findByType(createDtoAdvertisement.getAdvertisementType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Long advertisementId = null;
        try {
            advertisementId = advertisementRepository.save(Advertisement.createAdvertisement(createDtoAdvertisement, user, advertisementType)).getId();
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)");
        }

        return advertisementId;
    }

    /**
     * RETRIEVE
     */
    public RetrieveDtoAdvertisement retrieveOneAdvertisement(Long _advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(_advertisementId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoAdvertisement.createRetrieveDtoAdvertisement(advertisement);
    }

    public List retrieveAllAdvertisement(HashMap<String, String> retrieveOptions) {
        Long startIndexOfPage = 1L, lastIndexOfPage = Long.valueOf(ADVERTISEMENT_LAYOUT_SIZE);
        boolean isUnlimited = false;

        try {
            if(retrieveOptions.containsKey("page")) {
                if(retrieveOptions.get("page").equalsIgnoreCase("-1")) {
                    isUnlimited = true;
                }
                else {
                    startIndexOfPage = (Long.parseLong(retrieveOptions.get("page")) - 1) * ADVERTISEMENT_LAYOUT_SIZE + 1;
                    lastIndexOfPage = Long.parseLong(retrieveOptions.get("page")) * ADVERTISEMENT_LAYOUT_SIZE;
                }
            }
        } catch (Exception exception) {
            throw new IllegalArgumentException("적절하지 않은 요청입니다. (Please check the parameters)");
        }

        List<Advertisement> advertisements;
        if(isUnlimited == true) {
            advertisements = advertisementRepository.findAll();
        }
        else {
            advertisements = advertisementRepository.findAllByPage(startIndexOfPage, lastIndexOfPage);
        }

        return advertisements.stream().map(advertisement -> RetrieveDtoAdvertisement.createRetrieveDtoAdvertisement(advertisement)).collect(Collectors.toList());
    }

    public List retrieveAllAdvertisement_v2(HashMap<String, String> retrieveCondition) {
        RetrieveConditionForAdvertisement retrieveConditionForAdvertisement = new RetrieveConditionForAdvertisement();

        // 1. Page 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("page"))) {
            if(retrieveCondition.get("page").equalsIgnoreCase("-1")) {
                retrieveConditionForAdvertisement.isUnlimitedIsTrue();
            }
            else if(retrieveCondition.get("page").equalsIgnoreCase("0")) {
                retrieveConditionForAdvertisement.isUnlimitedIsFalse();
                retrieveConditionForAdvertisement.pageIsDefault();
            }
            else {
                retrieveConditionForAdvertisement.isUnlimitedIsFalse();
                retrieveConditionForAdvertisement.setPage(retrieveCondition.get("page"));
            }
        }
        else {
            retrieveConditionForAdvertisement.isUnlimitedIsFalse();
            retrieveConditionForAdvertisement.pageIsDefault();
        }

        // 2. UserEmail 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("userEmail"))) {
            retrieveConditionForAdvertisement.hasUserEmailIsTrue();
            retrieveConditionForAdvertisement.setUserEmail(retrieveCondition.get("userEmail"));
        }
        else {
            retrieveConditionForAdvertisement.hasUserEmailIsFalse();
        }

        // 3. IsFilteredDate 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("isFilteredDate"))) {
            if(retrieveCondition.get("isFilteredDate").equalsIgnoreCase("true") ||
                    retrieveCondition.get("isFilteredDate").equalsIgnoreCase("y")) {
                retrieveConditionForAdvertisement.isFilteredDateIsTrue();
            }
            else {
                retrieveConditionForAdvertisement.isFilteredDateIsFalse();
            }
        }
        else {
            retrieveConditionForAdvertisement.isFilteredDateIsFalse();
        }

        List<Advertisement> advertisements = advertisementRepository.findAllByCondition(retrieveConditionForAdvertisement);

        return advertisements.stream().map(advertisement -> RetrieveDtoAdvertisement.createRetrieveDtoAdvertisement(advertisement)).collect(Collectors.toList());
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateOneAdvertisement(Long _advertisementId, UpdateDtoAdvertisement updateDtoAdvertisement) throws Exception {
        Advertisement advertisement = advertisementRepository.findById(_advertisementId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        advertisement.changeAdvertisement(updateDtoAdvertisement);

        return advertisement.getId();
    }

    /**
     * DELETE
     */
    public void deleteOneAdvertisement(Long _advertisementId) {
        advertisementRepository.deleteById(_advertisementId);
    }
}

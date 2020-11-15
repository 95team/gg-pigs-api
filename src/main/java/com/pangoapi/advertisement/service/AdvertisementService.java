package com.pangoapi.advertisement.service;

import com.pangoapi.advertisement.dto.CreateDtoAdvertisement;
import com.pangoapi.advertisement.dto.RetrieveDtoAdvertisement;
import com.pangoapi.advertisement.entity.Advertisement;
import com.pangoapi.advertisementType.entity.AdvertisementType;
import com.pangoapi.user.entity.User;
import com.pangoapi.advertisement.dto.UpdateDtoAdvertisement;
import com.pangoapi.advertisement.repository.AdvertisementRepository;
import com.pangoapi.advertisementType.repository.AdvertisementTypeRepository;
import com.pangoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
    public Long createOneAdvertisement(CreateDtoAdvertisement createDtoAdvertisement) {
        User user = userRepository.findByEmail(createDtoAdvertisement.getUserEmail()).orElse(null);
        AdvertisementType advertisementType = advertisementTypeRepository.findByType(createDtoAdvertisement.getAdvertisementType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Long advertisementId = advertisementRepository.save(Advertisement.createAdvertisement(createDtoAdvertisement, user, advertisementType)).getId();

        return advertisementId;
    }

    /**
     * RETRIEVE
     */
    public RetrieveDtoAdvertisement retrieveOneAdvertisement(Long _advertisementId) {
        Advertisement advertisement = advertisementRepository.findById(_advertisementId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoAdvertisement.createRetrieveDtoAdvertisement(advertisement);
    }

    public List retrieveAllAdvertisement() {
        List<Advertisement> advertisements = advertisementRepository.findAll();

        return advertisements.stream().map(advertisement -> RetrieveDtoAdvertisement.createRetrieveDtoAdvertisement(advertisement)).collect(Collectors.toList());
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateOneAdvertisement(Long _advertisementId, UpdateDtoAdvertisement updateDtoAdvertisement) {
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

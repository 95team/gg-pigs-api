package com.pangoapi.service.advertisementRequest;

import com.pangoapi.domain.entity.advertisementRequest.AdvertisementRequest;
import com.pangoapi.repository.advertisementRequest.AdvertisementRequestRepository;
import com.pangoapi.domain.entity.advertisementType.AdvertisementType;
import com.pangoapi.domain.entity.user.User;
import com.pangoapi.dto.advertisementRequest.CreateDtoAdvertisementRequest;
import com.pangoapi.dto.advertisementRequest.RetrieveDtoAdvertisementRequest;
import com.pangoapi.dto.advertisementRequest.UpdateDtoAdvertisementRequest;
import com.pangoapi.repository.advertisementType.AdvertisementTypeRepository;
import com.pangoapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdvertisementRequestService {

    private final UserRepository userRepository;
    private final AdvertisementRequestRepository advertisementRequestRepository;
    private final AdvertisementTypeRepository advertisementTypeRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createOneAdvertisementRequest(CreateDtoAdvertisementRequest createDtoAdvertisementRequest) {
        User user = userRepository.findByEmail(createDtoAdvertisementRequest.getUserEmail()).orElse(null);
        AdvertisementType advertisementType = advertisementTypeRepository.findByType(createDtoAdvertisementRequest.getAdvertisementType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Long advertisementRequestId = advertisementRequestRepository.save(AdvertisementRequest.createAdvertisementRequest(createDtoAdvertisementRequest, user, advertisementType)).getId();

        return advertisementRequestId;
    }

    /**
     * RETRIEVE
     */
    public RetrieveDtoAdvertisementRequest retrieveOneAdvertisementRequest(Long _advertisementRequestId) {
        AdvertisementRequest advertisementRequest = advertisementRequestRepository.findById(_advertisementRequestId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoAdvertisementRequest.createRetrieveDtoAdvertisementRequest(advertisementRequest);
    }

    public List retrieveAllAdvertisementRequest() {
        List<AdvertisementRequest> advertisementRequests = advertisementRequestRepository.findAll();

        return advertisementRequests.stream().map(advertisementRequest -> RetrieveDtoAdvertisementRequest.createRetrieveDtoAdvertisementRequest(advertisementRequest)).collect(Collectors.toList());
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateOneAdvertisementRequest(Long _advertisementRequestId, UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) {
        AdvertisementRequest advertisementRequest = advertisementRequestRepository.findById(_advertisementRequestId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        advertisementRequest.changeAdvertisementRequest(updateDtoAdvertisementRequest);

        return advertisementRequest.getId();
    }

    /**
     * DELETE
     */
    public void deleteOneAdvertisementRequest(Long _advertisementRequestId) {
        advertisementRequestRepository.deleteById(_advertisementRequestId);
    }
}

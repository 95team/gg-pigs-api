package com.pangoapi.advertisementRequest.service;

import com.pangoapi.advertisementRequest.entity.AdvertisementRequest;
import com.pangoapi.advertisement.repository.AdvertisementRepository;
import com.pangoapi.advertisementRequest.repository.AdvertisementRequestRepository;
import com.pangoapi.advertisementType.entity.AdvertisementType;
import com.pangoapi.user.entity.User;
import com.pangoapi.advertisementRequest.dto.CreateDtoAdvertisementRequest;
import com.pangoapi.advertisementRequest.dto.RetrieveDtoAdvertisementRequest;
import com.pangoapi.advertisementRequest.dto.UpdateDtoAdvertisementRequest;
import com.pangoapi.advertisementType.repository.AdvertisementTypeRepository;
import com.pangoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pangoapi._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdvertisementRequestService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementTypeRepository advertisementTypeRepository;
    private final AdvertisementRequestRepository advertisementRequestRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createOneAdvertisementRequest(CreateDtoAdvertisementRequest createDtoAdvertisementRequest) throws Exception {
        User user = userRepository.findUserByEmail(createDtoAdvertisementRequest.getUserEmail()).orElse(null);
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

    public List<String[]> retrieveAllPossibleSeats(HashMap<String, String> wantedDate) throws Exception {
        int[][] allSeats = new int[ADVERTISEMENT_LAYOUT_SIZE + 1][ADVERTISEMENT_LAYOUT_SIZE + 1];

        Long startIndexOfPage, lastIndexOfPage;
        LocalDate startedDate, finishedDate;

        try {
            startIndexOfPage = (Long.parseLong(wantedDate.get("page")) - 1) * ADVERTISEMENT_LAYOUT_SIZE + 1;
            lastIndexOfPage = Long.parseLong(wantedDate.get("page")) * ADVERTISEMENT_LAYOUT_SIZE;
            startedDate = LocalDate.parse(wantedDate.get("startedDate"));
            finishedDate = LocalDate.parse(wantedDate.get("finishedDate"));
        } catch (NullPointerException | DateTimeParseException | NumberFormatException exception) {
            throw new IllegalArgumentException("적절하지 않은 데이터 형식 입니다. (Please check the data)");
        }

        List<Map<String, String>> impossibleSeats = advertisementRepository.findAllImpossibleSeats(startIndexOfPage, lastIndexOfPage, startedDate, finishedDate);
        if (calculatePossibleSeats(allSeats, impossibleSeats) == false)
            throw new Exception("신청 가능한 자리을 조회할 수 없습니다.");

        impossibleSeats = advertisementRequestRepository.findAllImpossibleSeats(startIndexOfPage, lastIndexOfPage, startedDate, finishedDate);
        if (calculatePossibleSeats(allSeats, impossibleSeats) == false)
            throw new Exception("신청 가능한 자리를 조회할 수 없습니다.");

        List<String[]> allPossibleSeats = getPossibleSeatsToList(allSeats);

        return allPossibleSeats;
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateOneAdvertisementRequest(Long _advertisementRequestId, UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) throws Exception {
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

    /**
     * ETC
     */
    public boolean calculatePossibleSeats(int[][] allSeats, List<Map<String, String>> impossibleSeats) {
        int[][] allSeatsCopy = new int[allSeats.length][allSeats[0].length];

        for(int i = 0; i < allSeats.length; i++) {
            for(int j = 0; j < allSeats[0].length; j++) {
                allSeatsCopy[i][j] = allSeats[i][j];
            }
        }

        try {
            for (Map<String, String> impossibleSeat : impossibleSeats) {
                String advertisementType = impossibleSeat.get("advertisementType");
                int rowIndex = Integer.parseInt(String.valueOf(impossibleSeat.get("rowPosition")));
                int columnIndex = Integer.parseInt(String.valueOf(impossibleSeat.get("columnPosition")));
                int rangeOfIndex = advertisementType.charAt(1) - '0';

                rowIndex = (rowIndex % ADVERTISEMENT_LAYOUT_SIZE);
                columnIndex = (columnIndex % ADVERTISEMENT_LAYOUT_SIZE);
                if(rowIndex == 0) rowIndex = ADVERTISEMENT_LAYOUT_SIZE;
                if(columnIndex == 0) columnIndex = ADVERTISEMENT_LAYOUT_SIZE;

                if (advertisementType.charAt(0) == 'R') {
                    for (int i = 0; i < rangeOfIndex; i++) {
                        allSeats[rowIndex + i][columnIndex] = -1;
                    }
                }
                else if (advertisementType.charAt(0) == 'C') {
                    for (int i = 0; i < rangeOfIndex; i++) {
                        allSeats[rowIndex][columnIndex + i] = -1;
                    }
                }
            }

            return true;
        } catch (Exception exception) {
            System.out.println(exception);
            allSeats = allSeatsCopy;

            return false;
        }
    }

    public List<String[]> getPossibleSeatsToList(int[][] allSeats) {
        List<String[]> allPossibleSeats = new ArrayList<>();

        for(int i = 1; i < allSeats.length; i++) {
            for(int j = 1; j < allSeats[0].length; j++) {
                if(allSeats[i][j] != -1) {
                    allPossibleSeats.add(new String[]{Integer.toString(i), Integer.toString(j)});
                }
            }
        }

        return allPossibleSeats;
    }
}

package com.pangoapi.advertisementRequest.service;

import com.pangoapi.advertisementRequest.dto.RetrieveConditionForAdvertisementRequest;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    private static final int POSSIBLE_SEAT = 1;
    private static final int IMPOSSIBLE_SEAT = -1;

    /**
     * CREATE
     */
    @Transactional
    public Long createOneAdvertisementRequest(CreateDtoAdvertisementRequest createDtoAdvertisementRequest) throws Exception {
        User user = userRepository.findUserByEmail(createDtoAdvertisementRequest.getUserEmail()).orElse(null);
        AdvertisementType advertisementType = advertisementTypeRepository.findByType(createDtoAdvertisementRequest.getAdvertisementType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        Long advertisementRequestId = null;
        try {
            advertisementRequestId = advertisementRequestRepository.save(AdvertisementRequest.createAdvertisementRequest(createDtoAdvertisementRequest, user, advertisementType)).getId();
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)");
        }

        return advertisementRequestId;
    }

    /**
     * RETRIEVE
     */
    public RetrieveDtoAdvertisementRequest retrieveOneAdvertisementRequest(Long _advertisementRequestId) {
        AdvertisementRequest advertisementRequest = advertisementRequestRepository.findById(_advertisementRequestId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoAdvertisementRequest.createRetrieveDtoAdvertisementRequest(advertisementRequest);
    }

    public List retrieveAllAdvertisementRequest(HashMap<String, String> retrieveOptions) {
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

        List<AdvertisementRequest> advertisementRequests;
        if(isUnlimited == true) {
            advertisementRequests = advertisementRequestRepository.findAll();
        }
        else {
            advertisementRequests = advertisementRequestRepository.findAllByPage(startIndexOfPage, lastIndexOfPage);
        }

        return advertisementRequests.stream().map(advertisementRequest -> RetrieveDtoAdvertisementRequest.createRetrieveDtoAdvertisementRequest(advertisementRequest)).collect(Collectors.toList());
    }
    
    public List retrieveAllAdvertisementRequest_v2(HashMap<String, String> retrieveCondition) {
        RetrieveConditionForAdvertisementRequest condition = new RetrieveConditionForAdvertisementRequest();
        
        // 1. Page 정보를 가공합니다.
        if(StringUtils.hasText(retrieveCondition.get("page"))) {
            if(retrieveCondition.get("page").equalsIgnoreCase("-1")) {
                condition.isUnlimitedIsTrue();
                
            }
            else if (retrieveCondition.get("page").equalsIgnoreCase("0")) {
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
        
        List<AdvertisementRequest> advertisementRequests = advertisementRequestRepository.findAllByCondition(condition);
        
        return advertisementRequests.stream().map(RetrieveDtoAdvertisementRequest::createRetrieveDtoAdvertisementRequest).collect(Collectors.toList());
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

        List<String[]> allPossibleSeats = getPossibleSeatsAsList(allSeats);

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
                        allSeats[rowIndex + i][columnIndex] = IMPOSSIBLE_SEAT;
                    }
                }
                else if (advertisementType.charAt(0) == 'C') {
                    for (int i = 0; i < rangeOfIndex; i++) {
                        allSeats[rowIndex][columnIndex + i] = IMPOSSIBLE_SEAT;
                    }
                }
            }

            return true;
        } catch (Exception exception) {
            System.out.println(exception);

            // allSeats 배열을 초기화 합니다.
            for(int i = 0; i < allSeats.length; i++) {
                for(int j = 0; j < allSeats[0].length; j++) {
                    allSeats[i][j] = allSeatsCopy[i][j];
                }
            }

            return false;
        }
    }

    public static boolean isPossibleSeat(List<String[]> allPossibleSeats, Long rowPosition, Long columnPosition, String stringTypeOfAdvertisementType) {
        boolean isPossible = true;

        int[][] allSeats = new int[ADVERTISEMENT_LAYOUT_SIZE + 1][ADVERTISEMENT_LAYOUT_SIZE + 1];
        int rangeOfIndex = stringTypeOfAdvertisementType.charAt(1) - '0';
        int rowIndex = Math.toIntExact(rowPosition);
        int columnIndex = Math.toIntExact(columnPosition);


        for (String[] allPossibleSeat: allPossibleSeats) {
            allSeats[Integer.parseInt(allPossibleSeat[0])][Integer.parseInt(allPossibleSeat[1])] = POSSIBLE_SEAT;
        }

        if (stringTypeOfAdvertisementType.charAt(0) == 'R') {
            for (int i = 0; i < rangeOfIndex; i++) {
                if((rowIndex + i > ADVERTISEMENT_LAYOUT_SIZE) ||
                        (allSeats[rowIndex + i][columnIndex] != POSSIBLE_SEAT)) {
                    isPossible = false;
                    break;
                }
            }
        }
        else if (stringTypeOfAdvertisementType.charAt(0) == 'C') {
            for (int i = 0; i < rangeOfIndex; i++) {
                if((columnIndex + i > ADVERTISEMENT_LAYOUT_SIZE) ||
                        (allSeats[rowIndex][columnIndex + i] != POSSIBLE_SEAT)) {
                    isPossible = false;
                    break;
                }
            }
        }

        return isPossible;
    }

    public List<String[]> getPossibleSeatsAsList(int[][] allSeats) {
        List<String[]> allPossibleSeats = new ArrayList<>();

        for(int i = 1; i < allSeats.length; i++) {
            for(int j = 1; j < allSeats[0].length; j++) {
                if(allSeats[i][j] != IMPOSSIBLE_SEAT) {
                    allPossibleSeats.add(new String[]{Integer.toString(i), Integer.toString(j)});
                }
            }
        }

        return allPossibleSeats;
    }
}

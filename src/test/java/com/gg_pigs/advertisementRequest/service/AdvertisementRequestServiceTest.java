package com.gg_pigs.advertisementRequest.service;

import com.gg_pigs.advertisement.entity.Advertisement;
import com.gg_pigs.advertisement.repository.AdvertisementRepository;
import com.gg_pigs.advertisementRequest.dto.CreateDtoAdvertisementRequest;
import com.gg_pigs.advertisementRequest.entity.AdvertisementRequest;
import com.gg_pigs.advertisementRequest.repository.AdvertisementRequestRepository;
import com.gg_pigs.advertisementType.entity.AdvertisementType;
import com.gg_pigs.advertisementType.repository.AdvertisementTypeRepository;
import com.gg_pigs.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gg_pigs._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest(
        classes = {
                AdvertisementRequestService.class
        }
)
class AdvertisementRequestServiceTest {

    @Autowired AdvertisementRequestService advertisementRequestService;

    @MockBean UserRepository userRepository;
    @MockBean AdvertisementRepository advertisementRepository;
    @MockBean AdvertisementTypeRepository advertisementTypeRepository;
    @MockBean AdvertisementRequestRepository advertisementRequestRepository;

    @Mock CreateDtoAdvertisementRequest createDtoAdvertisementRequest;
    @Mock AdvertisementRequest advertisementRequest;
    @Mock AdvertisementType advertisementType;

    final int POSSIBLE_SEAT = 1;
    final int IMPOSSIBLE_SEAT = -1;

    @BeforeEach
    public void setUp() {
        // Configuration of AdvertisementTypeRepository
        Mockito.when(advertisementTypeRepository.findByType(any())).thenReturn(Optional.of(advertisementType));

        // Configuration of AdvertisementRequestRepository
        Mockito.when(advertisementRequestRepository.save(any())).thenReturn(advertisementRequest);

        // Configuration of AdvertisementRequest
        Mockito.when(advertisementRequest.getId()).thenReturn(1L);
    }

    /**
     * Test (case1): createOneAdvertisementRequest()
     * */
    @Test
    public void When_call_createOneAdvertisementRequest_Then_call_save() throws Exception {
        // Given
        Mockito.when(createDtoAdvertisementRequest.getAdvertisementType()).thenReturn("R1");
        Mockito.when(createDtoAdvertisementRequest.getRowPosition()).thenReturn("1");
        Mockito.when(createDtoAdvertisementRequest.getColumnPosition()).thenReturn("1");
        Mockito.when(createDtoAdvertisementRequest.getStartedDate()).thenReturn(String.valueOf(LocalDate.now()));
        Mockito.when(createDtoAdvertisementRequest.getFinishedDate()).thenReturn(String.valueOf(LocalDate.now().plusMonths(1)));

        // When
        advertisementRequestService.createOneAdvertisementRequest(createDtoAdvertisementRequest);

        // Then
        Mockito.verify(advertisementRequestRepository, times(1)).save(any(AdvertisementRequest.class));
    }

    /**
     * Test (case2): createOneAdvertisementRequest()
     * */
    @Test
    public void When_call_createOneAdvertisementRequest_Then_check_possibleSeats() throws Exception {
        // Given
        Mockito.when(createDtoAdvertisementRequest.getAdvertisementType()).thenReturn("R1");
        Mockito.when(createDtoAdvertisementRequest.getRowPosition()).thenReturn("1");
        Mockito.when(createDtoAdvertisementRequest.getColumnPosition()).thenReturn("1");
        Mockito.when(createDtoAdvertisementRequest.getStartedDate()).thenReturn(String.valueOf(LocalDate.now()));
        Mockito.when(createDtoAdvertisementRequest.getFinishedDate()).thenReturn(String.valueOf(LocalDate.now().plusMonths(1)));

        // When
        advertisementRequestService.createOneAdvertisementRequest(createDtoAdvertisementRequest);

        // Then
        Mockito.verify(advertisementRequestRepository, times(1)).save(any(AdvertisementRequest.class));
    }

    /**
     * Test: retrieveAllAdvertisementRequest_v2()
     * */
    @Test
    public void When_call_retrieveAllAdvertisementRequest_v2_Then_return_list() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", null);

        // When
        List<Advertisement> advertisementList = advertisementRequestService.retrieveAllAdvertisementRequest_v2(retrieveCondition);

        // Then
        assertThat(advertisementList.getClass()).isEqualTo(ArrayList.class);
        Mockito.verify(advertisementRequestRepository, times(1)).findAllByCondition(any());
    }

    /**
     * Test: getPossibleSeatsAsList()
     * */
    @Test
    public void When_call_getPossibleSeatsAsList_Then_return_possibleSeatsAsList() throws Exception {
        // Given
        int[][] allPossibleSeats2D = new int[ADVERTISEMENT_LAYOUT_SIZE + 1][ADVERTISEMENT_LAYOUT_SIZE + 1];
        for(int i = 1; i <= ADVERTISEMENT_LAYOUT_SIZE; i++) {
            for(int j = 1; j <= ADVERTISEMENT_LAYOUT_SIZE; j++) {
                allPossibleSeats2D[i][j] = IMPOSSIBLE_SEAT;
            }
        }

        int possibleRowIndex = 1; int possibleColumnIndex = 1;
        allPossibleSeats2D[possibleRowIndex][possibleColumnIndex] = POSSIBLE_SEAT;

        // When
        List<String[]> allPossibleSeatsAsList = advertisementRequestService.getPossibleSeatsAsList(allPossibleSeats2D);

        // Then
        assertThat(allPossibleSeatsAsList.get(0).length).isEqualTo(2);
    }

    /**
     * Test: isPossibleSeat()
     * */
    @Test
    public void When_call_isPossibleSeat_Then_check_possible_to_apply() {
        // Given
        int[][] allPossibleSeats2D = new int[ADVERTISEMENT_LAYOUT_SIZE + 1][ADVERTISEMENT_LAYOUT_SIZE + 1];
        for(int i = 1; i <= ADVERTISEMENT_LAYOUT_SIZE; i++) {
            for(int j = 1; j <= ADVERTISEMENT_LAYOUT_SIZE; j++) {
                allPossibleSeats2D[i][j] = IMPOSSIBLE_SEAT;
            }
        }

        int possibleRowIndex = 1; int possibleColumnIndex = 1;
        allPossibleSeats2D[possibleRowIndex][possibleColumnIndex] = POSSIBLE_SEAT;

        List<String[]> allPossibleSeatsAsList = advertisementRequestService.getPossibleSeatsAsList(allPossibleSeats2D);

        // When
        boolean isPossible = advertisementRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "R1");
        boolean isImpossible1 = advertisementRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 2L, "R2");
        boolean isImpossible2 = advertisementRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "R2");

        // Then
        assertThat(isPossible).isEqualTo(true);
        assertThat(isImpossible1).isEqualTo(false);
        assertThat(isImpossible2).isEqualTo(false);
    }

    /**
     * Test (case1): calculatePossibleSeats()
     * */
    @Test
    public void When_call_calculatePossibleSeats_Then_calculate_possibleSeats_case1() {
        // Given
        int[][] allSeats = new int[ADVERTISEMENT_LAYOUT_SIZE + 1][ADVERTISEMENT_LAYOUT_SIZE + 1];
        int impossibleRowIndex = 1; int impossibleColumnIndex = 1;
        List<Map<String, String>> impossibleSeats = new ArrayList<>();
        impossibleSeats.add(new HashMap<String, String>() {{
            put("advertisementType", "R1");
            put("rowPosition", String.valueOf(impossibleRowIndex));
            put("columnPosition", String.valueOf(impossibleColumnIndex));
        }});

        // When
        boolean resultOfCalculation = advertisementRequestService.calculatePossibleSeats(allSeats, impossibleSeats);

        // Then
        assertThat(resultOfCalculation).isEqualTo(true);
        assertThat(allSeats[impossibleRowIndex][impossibleColumnIndex]).isEqualTo(-1);


        impossibleSeats.add(new HashMap<String, String>() {{
            put("advertisementType", "R6");
            put("rowPosition", String.valueOf(impossibleRowIndex + 1));
            put("columnPosition", String.valueOf(impossibleColumnIndex + 1));
        }});
        boolean resultOfCalculation2 = advertisementRequestService.calculatePossibleSeats(allSeats, impossibleSeats);
        assertThat(resultOfCalculation2).isEqualTo(false);
    }

    /**
     * Test (case2): calculatePossibleSeats()
     * */
    @Test
    public void When_call_calculatePossibleSeats_Then_calculate_possibleSeats_case2() {
        // Given
        int[][] allSeats = new int[ADVERTISEMENT_LAYOUT_SIZE + 1][ADVERTISEMENT_LAYOUT_SIZE + 1];
        int impossibleRowIndex = 1; int impossibleColumnIndex = 1;
        List<Map<String, String>> impossibleSeats = new ArrayList<>();
        impossibleSeats.add(new HashMap<String, String>() {{
            put("advertisementType", "R6");
            put("rowPosition", String.valueOf(impossibleRowIndex + 1));
            put("columnPosition", String.valueOf(impossibleColumnIndex ));
        }});

        // When
        boolean resultOfCalculation = advertisementRequestService.calculatePossibleSeats(allSeats, impossibleSeats);

        // Then
        assertThat(resultOfCalculation).isEqualTo(false);
        assertThat(allSeats[impossibleRowIndex + 1][impossibleColumnIndex]).isEqualTo(0);
    }
}
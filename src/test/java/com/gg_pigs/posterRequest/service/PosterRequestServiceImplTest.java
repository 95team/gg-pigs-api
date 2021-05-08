package com.gg_pigs.posterRequest.service;

import com.gg_pigs._common.enums.PosterReviewStatus;
import com.gg_pigs._common.exception.BadRequestException;
import com.gg_pigs.historyLog.service.HistoryLogService;
import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.repository.PosterRepository;
import com.gg_pigs.poster.service.PosterService;
import com.gg_pigs.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.UpdateDtoPosterRequest;
import com.gg_pigs.posterRequest.entity.PosterRequest;
import com.gg_pigs.posterRequest.repository.PosterRequestRepository;
import com.gg_pigs.posterType.entity.PosterType;
import com.gg_pigs.posterType.repository.PosterTypeRepository;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gg_pigs._common.CommonDefinition.POSTER_LAYOUT_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@SpringBootTest(
        classes = {
                PosterRequestServiceImpl.class
        }
)
class PosterRequestServiceImplTest {

    @Autowired PosterRequestServiceImpl posterRequestServiceImpl;

    @MockBean UserRepository userRepository;
    @MockBean PosterRepository posterRepository;
    @MockBean PosterTypeRepository posterTypeRepository;
    @MockBean PosterRequestRepository posterRequestRepository;

    @MockBean PosterService posterService;
    @MockBean HistoryLogService historyLogService;

    @Mock CreateDtoPosterRequest createDtoPosterRequest;
    @Mock PosterRequest posterRequest;
    @Mock PosterType posterType;
    @Mock User user;

    final int POSSIBLE_SEAT = 1;
    final int IMPOSSIBLE_SEAT = -1;

    @BeforeEach
    public void setUp() {
        // Configuration of PosterTypeRepository
        Mockito.when(posterTypeRepository.findPosterTypeByType(any())).thenReturn(Optional.of(posterType));

        // Configuration of PosterRequestRepository
        Mockito.when(posterRequestRepository.findById(anyLong())).thenReturn(Optional.of(posterRequest));
        Mockito.when(posterRequestRepository.findByIdWithFetch(anyLong())).thenReturn(Optional.of(posterRequest));

        // Configuration of PosterRequest
        Mockito.when(posterRequest.getId()).thenReturn(1L);
        Mockito.when(posterRequest.getPosterType()).thenReturn(posterType);
        Mockito.when(posterRequest.getStartedDate()).thenReturn(LocalDate.now());
        Mockito.when(posterRequest.getFinishedDate()).thenReturn(LocalDate.now().plusMonths(1));

        // Configuration of PosterRequest
        Mockito.when(createDtoPosterRequest.getPosterType()).thenReturn("R1");
        Mockito.when(createDtoPosterRequest.getRowPosition()).thenReturn("1");
        Mockito.when(createDtoPosterRequest.getColumnPosition()).thenReturn("1");
        Mockito.when(createDtoPosterRequest.getStartedDate()).thenReturn(String.valueOf(LocalDate.now()));
        Mockito.when(createDtoPosterRequest.getFinishedDate()).thenReturn(String.valueOf(LocalDate.now().plusMonths(1)));
    }

    @DisplayName("[테스트] createOnePosterRequest() : save() 함수 1회 호출")
    @Test
    public void Test_createOnePosterRequest() throws Exception {
        // Given
        Mockito.when(posterRequestRepository.save(any())).thenReturn(posterRequest);

        // When
        posterRequestServiceImpl.createPosterRequest(createDtoPosterRequest);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).save(any(PosterRequest.class));
    }

    @DisplayName("[테스트] createOnePosterRequest() : DataIntegrityViolationException 에러 발생")
    @Test
    public void Test_createOnePosterRequest_with_DataIntegrityViolationException() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)";

        Mockito.when(posterRequestRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // When
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> posterRequestServiceImpl.createPosterRequest(createDtoPosterRequest));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] retrievePosterRequest() : findById() 1회 호출")
    @Test
    public void Test_retrievePosterRequest() {
        // Given
        Long prId = 1L;

        // When
        posterRequestServiceImpl.retrievePosterRequest(prId);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).findById(anyLong());
    }

    @DisplayName("[테스트] retrievePosterRequest() : EntityNotFoundException 에러 발생")
    @Test
    public void Test_retrievePosterRequest_with_EntityNotFoundException() {
        // Given
        String expectedMessage = "해당 데이터를 조회할 수 없습니다.";

        Long prId = 1L;
        Mockito.when(posterRequestRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> posterRequestServiceImpl.retrievePosterRequest(prId));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] retrieveAllPosterRequests() : findAllByCondition() 1회 호출 (page:null, userEmail:null, isFilteredDate:null)")
    @Test
    public void Test_retrieveAllPosterRequests() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", null);

        // When
        posterRequestServiceImpl.retrieveAllPosterRequests(retrieveCondition);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] retrieveAllPosterRequests() (page)")
    @Test
    public void Test_retrieveAllPosterRequests_with_page() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", "-1");
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", null);

        // When
        posterRequestServiceImpl.retrieveAllPosterRequests(retrieveCondition);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] retrieveAllPosterRequests() (userEmail)")
    @Test
    public void Test_retrieveAllPosterRequests_with_userEmail() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", "pigs95team@gmail.com");
        retrieveCondition.put("isFilteredDate", null);

        // When
        posterRequestServiceImpl.retrieveAllPosterRequests(retrieveCondition);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] retrieveAllPosterRequests() (isFilteredDate)")
    @Test
    public void Test_retrieveAllPosterRequests_with_isFilteredDate() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", "Y");

        // When
        posterRequestServiceImpl.retrieveAllPosterRequests(retrieveCondition);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] retrieveAllPossibleSeats() : Poster 타입 Row 타입")
    @Test
    public void Test_retrieveAllPossibleSeats_with_rowType() throws Exception {
        // Given
        String reservedPosterType = "R3";
        int expectedCounts = (POSTER_LAYOUT_SIZE * POSTER_LAYOUT_SIZE) - (reservedPosterType.charAt(1) - '0');

        HashMap<String, String> wantedDate = new HashMap<>();
        wantedDate.put("page", "1");
        wantedDate.put("startedDate", LocalDate.now().toString());
        wantedDate.put("finishedDate", LocalDate.now().plusMonths(1).toString());

        HashMap<String, String> impossibleSeat = new HashMap<>();
        impossibleSeat.put("posterType", reservedPosterType);
        impossibleSeat.put("rowPosition", "1");
        impossibleSeat.put("columnPosition", "1");

        List<Map<String, String>> impossibleSeats = new ArrayList<>();
        impossibleSeats.add(impossibleSeat);

        Mockito.when(posterRepository.findAllImpossibleSeats(anyLong(), anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(impossibleSeats);

        // When
        List<String[]> allPossibleSeats = posterRequestServiceImpl.retrieveAllPossibleSeats(wantedDate);

        // Then
        assertThat(allPossibleSeats.size()).isEqualTo(expectedCounts);
    }

    @DisplayName("[테스트] retrieveAllPossibleSeats() : Poster 타입 Cn (Column 타입)")
    @Test
    public void Test_retrieveAllPossibleSeats_with_columnType() throws Exception {
        // Given
        String reservedPosterType = "C3";
        int expectedCounts = (POSTER_LAYOUT_SIZE * POSTER_LAYOUT_SIZE) - (reservedPosterType.charAt(1) - '0');

        HashMap<String, String> wantedDate = new HashMap<>();
        wantedDate.put("page", "1");
        wantedDate.put("startedDate", LocalDate.now().toString());
        wantedDate.put("finishedDate", LocalDate.now().plusMonths(1).toString());

        HashMap<String, String> impossibleSeat = new HashMap<>();
        impossibleSeat.put("posterType", reservedPosterType);
        impossibleSeat.put("rowPosition", "1");
        impossibleSeat.put("columnPosition", "1");

        List<Map<String, String>> impossibleSeats = new ArrayList<>();
        impossibleSeats.add(impossibleSeat);

        Mockito.when(posterRepository.findAllImpossibleSeats(anyLong(), anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(impossibleSeats);

        // When
        List<String[]> allPossibleSeats = posterRequestServiceImpl.retrieveAllPossibleSeats(wantedDate);

        // Then
        assertThat(allPossibleSeats.size()).isEqualTo(expectedCounts);
    }

    @DisplayName("[테스트] retrieveAllPossibleSeats() : IllegalArgumentException 에러 발생 (with wrongStartedDate)")
    @Test
    public void Test_retrieveAllPossibleSeats_with_IllegalArgumentException() throws Exception {
        // Given
        String expectedMessage = "적절하지 않은 데이터 형식 입니다. (Please check the data)";
        String wrongStartedDate = "2021-01-01-01";

        HashMap<String, String> wantedDate = new HashMap<>();
        wantedDate.put("page", "1");
        wantedDate.put("startedDate", wrongStartedDate);
        wantedDate.put("finishedDate", LocalDate.now().plusMonths(1).toString());

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> posterRequestServiceImpl.retrieveAllPossibleSeats(wantedDate));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] updatePosterRequest() : Review-Status 를 승인(APPROVAL) 로 변경했을 경우, PosterRequest -> Poster 삽입이 진행됨을 체크")
    @Test
    public void Test_updatePosterRequest_with_ReviewStatus_APPROVAL() throws Exception {
        // Given
        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        String reviewStatus = String.valueOf(PosterReviewStatus.APPROVAL); // Review-Status : APPROVAL
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(reviewStatus);

        // When
        posterRequestServiceImpl.updatePosterRequest(work, updaterEmail, 1L, updateDtoPosterRequest);

        // Then
        Mockito.verify(posterRequest, times(1)).changeReviewer(anyString());
        Mockito.verify(posterRequest, times(1)).changeReviewStatusToApproval();
        Mockito.verify(posterService, times(1)).createPoster(any(CreateDtoPoster.class));
        Mockito.verify(historyLogService, times(2)).writeHistoryLog(any(), any(User.class), anyString(), anyString(), anyBoolean());
    }

    @DisplayName("[테스트] updatePosterRequest() : Review-Status 를 승인(APPROVAL) 이 아닌 값으로 변경했을 경우, 'review-status 변경', '이력의 삽입' 을 체크")
    @Test
    public void Test_updatePosterRequest_with_ReviewStatus_PENDING() throws Exception {
        // Given
        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        String reviewStatus = String.valueOf(PosterReviewStatus.PENDING); // Review-Status : PENDING
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(reviewStatus);

        // When
        posterRequestServiceImpl.updatePosterRequest(work, updaterEmail, 1L, updateDtoPosterRequest);

        // Then
        Mockito.verify(posterRequest, times(1)).changeReviewer(anyString());
        Mockito.verify(posterRequest, times(1)).changeReviewStatusToPending();
        Mockito.verify(historyLogService, times(1)).writeHistoryLog(any(), any(User.class), anyString(), anyString(), anyBoolean());
    }

    @DisplayName("[테스트] updatePosterRequest() : Review-Status 를 승인(APPROVAL) 이 아닌 값으로 변경했을 경우, 'review-status 변경', '이력의 삽입' 을 체크")
    @Test
    public void Test_updatePosterRequest_with_ReviewStatus_NON_APPROVAL() throws Exception {
        // Given
        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        String reviewStatus = String.valueOf(PosterReviewStatus.NON_APPROVAL); // Review-Status : NON_APPROVAL
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(reviewStatus);

        // When
        posterRequestServiceImpl.updatePosterRequest(work, updaterEmail, 1L, updateDtoPosterRequest);

        // Then
        Mockito.verify(posterRequest, times(1)).changeReviewer(anyString());
        Mockito.verify(posterRequest, times(1)).changeReviewStatusToNonApproval();
        Mockito.verify(historyLogService, times(1)).writeHistoryLog(any(), any(User.class), anyString(), anyString(), anyBoolean());
    }

    @DisplayName("[테스트] updatePosterRequest() : BadRequestException 에러 발생 (work is not review)")
    @Test
    public void Test_updatePosterRequest_with_BadRequestException_case1() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameter value)";

        String wrongWork = "not review";
        String updaterEmail = "pigs95team@gmail.com";
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> posterRequestServiceImpl.updatePosterRequest(wrongWork, updaterEmail, 1L, updateDtoPosterRequest));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] updatePosterRequest() : BadRequestException 에러 발생 (ReviewStatus is already APPROVAL)")
    @Test
    public void Test_updatePosterRequest_with_BadRequestException_case2() {
        // Given
        String expectedMessage = "이 항목은 이미 승인되었습니다. (This item is already approved)";

        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));

        Mockito.when(posterRequest.getReviewStatus()).thenReturn(PosterReviewStatus.APPROVAL); // ReviewStatus is already APPROVAL

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> posterRequestServiceImpl.updatePosterRequest(work, updaterEmail, 1L, updateDtoPosterRequest));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] updatePosterRequest() : BadRequestException 에러 발생 (ReviewStatus is invalid value)")
    @Test
    public void Test_updatePosterRequest_with_BadRequestException_case3() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameter value)";

        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(PosterReviewStatus.NEW.name()); // ReviewStatus is invalid value

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> posterRequestServiceImpl.updatePosterRequest(work, updaterEmail, 1L, updateDtoPosterRequest));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] deletePosterRequest()")
    @Test
    public void Test_deletePosterRequest() {
        // Given
        Long prId = 1L;

        // When
        posterRequestServiceImpl.deletePosterRequest(prId);

        // Then
        Mockito.verify(posterRequestRepository, times(1)).deleteById(anyLong());
    }

    /**
     * Test: getPossibleSeatsAsList()
     * */
    @Test
    public void When_call_getPossibleSeatsAsList_Then_return_possibleSeatsAsList() throws Exception {
        // Given
        int[][] allPossibleSeats2D = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
        for(int i = 1; i <= POSTER_LAYOUT_SIZE; i++) {
            for(int j = 1; j <= POSTER_LAYOUT_SIZE; j++) {
                allPossibleSeats2D[i][j] = IMPOSSIBLE_SEAT;
            }
        }

        int possibleRowIndex = 1; int possibleColumnIndex = 1;
        allPossibleSeats2D[possibleRowIndex][possibleColumnIndex] = POSSIBLE_SEAT;

        // When
        List<String[]> allPossibleSeatsAsList = posterRequestServiceImpl.getPossibleSeatsAsList(allPossibleSeats2D);

        // Then
        assertThat(allPossibleSeatsAsList.get(0).length).isEqualTo(2);
    }

    /**
     * Test (case1): calculatePossibleSeats()
     * */
    @Test
    public void When_call_calculatePossibleSeats_Then_calculate_possibleSeats_case1() {
        // Given
        int[][] allSeats = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
        int impossibleRowIndex = 1; int impossibleColumnIndex = 1;
        List<Map<String, String>> impossibleSeats = new ArrayList<>();
        impossibleSeats.add(new HashMap<String, String>() {{
            put("posterType", "R1");
            put("rowPosition", String.valueOf(impossibleRowIndex));
            put("columnPosition", String.valueOf(impossibleColumnIndex));
        }});

        // When
        boolean resultOfCalculation = posterRequestServiceImpl.calculatePossibleSeats(allSeats, impossibleSeats);

        // Then
        assertThat(resultOfCalculation).isEqualTo(true);
        assertThat(allSeats[impossibleRowIndex][impossibleColumnIndex]).isEqualTo(-1);


        impossibleSeats.add(new HashMap<String, String>() {{
            put("posterType", "R6");
            put("rowPosition", String.valueOf(impossibleRowIndex + 1));
            put("columnPosition", String.valueOf(impossibleColumnIndex + 1));
        }});
        boolean resultOfCalculation2 = posterRequestServiceImpl.calculatePossibleSeats(allSeats, impossibleSeats);
        assertThat(resultOfCalculation2).isEqualTo(false);
    }

    /**
     * Test (case2): calculatePossibleSeats()
     * */
    @Test
    public void When_call_calculatePossibleSeats_Then_calculate_possibleSeats_case2() {
        // Given
        int[][] allSeats = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
        int impossibleRowIndex = 1; int impossibleColumnIndex = 1;
        List<Map<String, String>> impossibleSeats = new ArrayList<>();
        impossibleSeats.add(new HashMap<String, String>() {{
            put("posterType", "R6");
            put("rowPosition", String.valueOf(impossibleRowIndex + 1));
            put("columnPosition", String.valueOf(impossibleColumnIndex ));
        }});

        // When
        boolean resultOfCalculation = posterRequestServiceImpl.calculatePossibleSeats(allSeats, impossibleSeats);

        // Then
        assertThat(resultOfCalculation).isEqualTo(false);
        assertThat(allSeats[impossibleRowIndex + 1][impossibleColumnIndex]).isEqualTo(0);
    }

    @DisplayName("[테스트] isPossibleSeat()")
    @Test
    public void Test_isPossibleSeat() {
        // Given
        int[][] allPossibleSeats = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
        for(int i = 1; i <= POSTER_LAYOUT_SIZE; i++) {
            for(int j = 1; j <= POSTER_LAYOUT_SIZE; j++) {
                allPossibleSeats[i][j] = IMPOSSIBLE_SEAT;
            }
        }

        int possibleRowIndex = 1; int possibleColumnIndex = 1;
        allPossibleSeats[possibleRowIndex][possibleColumnIndex] = POSSIBLE_SEAT;

        List<String[]> allPossibleSeatsAsList = posterRequestServiceImpl.getPossibleSeatsAsList(allPossibleSeats);

        // When
        boolean isPossible1 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "R1");
        boolean isPossible2 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 7L, 1L, "R1");
        boolean isPossible3 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 1L, 7L, "R1");
        boolean isPossible4 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "C1");
        boolean isImpossible1 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 1L, 2L, "R2");
        boolean isImpossible2 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "R2");
        boolean isImpossible3 = posterRequestServiceImpl.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "C2");

        // Then
        assertThat(isPossible1).isEqualTo(true);
        assertThat(isPossible2).isEqualTo(true);
        assertThat(isPossible3).isEqualTo(true);
        assertThat(isPossible4).isEqualTo(true);
        assertThat(isImpossible1).isEqualTo(false);
        assertThat(isImpossible2).isEqualTo(false);
        assertThat(isImpossible3).isEqualTo(false);
    }
}
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class PosterRequestServiceTest {

    @InjectMocks
    PosterRequestService posterRequestService;

    // Service
    @Mock PosterService posterService;
    @Mock HistoryLogService historyLogService;

    // Repository
    @Mock UserRepository userRepository;
    @Mock PosterRepository posterRepository;
    @Mock PosterTypeRepository ptRepository;
    @Mock PosterRequestRepository prRepository;

    // Entity & Dto
    @Mock User user;
    @Mock PosterType posterType;
    @Mock PosterRequest posterRequest;
    @Mock CreateDtoPosterRequest createDtoPR;

    final int POSSIBLE_SEAT = 1;
    final int IMPOSSIBLE_SEAT = -1;

    @BeforeEach
    public void setUp() {
        // Configuration of UserRepository
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(user));

        // Configuration of PRRepository
        Mockito.when(prRepository.findById(anyLong())).thenReturn(Optional.of(posterRequest));
        Mockito.when(prRepository.findByIdWithFetch(anyLong())).thenReturn(Optional.of(posterRequest));

        // Configuration of PR
        Mockito.when(posterRequest.getId()).thenReturn(1L);
        Mockito.when(posterRequest.getPosterType()).thenReturn(posterType);
        Mockito.when(posterRequest.getStartedDate()).thenReturn(LocalDate.now());
        Mockito.when(posterRequest.getFinishedDate()).thenReturn(LocalDate.now().plusMonths(1));

        // Configuration of createDtoPR
        Mockito.when(createDtoPR.getUserEmail()).thenReturn("pigs95team@gmail.com");
        Mockito.when(createDtoPR.getPosterType()).thenReturn("R1");
        Mockito.when(createDtoPR.getRowPosition()).thenReturn("1");
        Mockito.when(createDtoPR.getColumnPosition()).thenReturn("1");
        Mockito.when(createDtoPR.getStartedDate()).thenReturn(String.valueOf(LocalDate.now()));
        Mockito.when(createDtoPR.getFinishedDate()).thenReturn(String.valueOf(LocalDate.now().plusMonths(1)));
    }

    @DisplayName("[테스트] create() : save() 함수 1회 호출")
    @Test
    public void Test_create() throws Exception {
        // Given
        Mockito.when(ptRepository.findPosterTypeByType(any())).thenReturn(Optional.of(posterType));
        Mockito.when(prRepository.save(any())).thenReturn(posterRequest);

        // When
        posterRequestService.create(createDtoPR);

        // Then
        Mockito.verify(prRepository, times(1)).save(any(PosterRequest.class));
    }

    @DisplayName("[테스트] create() : EntityNotFoundException 에러 발생")
    @Test
    public void Test_create_with_EntityNotFoundException() {
        // Given
        String expectedMessage = "해당 데이터를 조회할 수 없습니다.";

        Mockito.when(prRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> posterRequestService.create(createDtoPR));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] create() : DataIntegrityViolationException 에러 발생")
    @Test
    public void Test_create_with_DataIntegrityViolationException() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)";

        Mockito.when(ptRepository.findPosterTypeByType(any())).thenReturn(Optional.of(posterType));
        Mockito.when(prRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // When
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> posterRequestService.create(createDtoPR));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] read() : findById() 1회 호출")
    @Test
    public void Test_read() {
        // Given
        Long prId = 1L;

        // When
        posterRequestService.read(prId);

        // Then
        Mockito.verify(prRepository, times(1)).findById(anyLong());
    }

    @DisplayName("[테스트] read() : EntityNotFoundException 에러 발생")
    @Test
    public void Test_read_with_EntityNotFoundException() {
        // Given
        String expectedMessage = "해당 데이터를 조회할 수 없습니다.";

        Long prId = 1L;
        Mockito.when(prRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> posterRequestService.read(prId));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] readAll() : findAllByCondition() 1회 호출 (page:null, userEmail:null, isFilteredDate:null)")
    @Test
    public void Test_readAll() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", null);

        // When
        posterRequestService.readAll(retrieveCondition);

        // Then
        Mockito.verify(prRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] readAll() (page)")
    @Test
    public void Test_readAll_with_page() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", "-1");
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", null);

        // When
        posterRequestService.readAll(retrieveCondition);

        // Then
        Mockito.verify(prRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] readAll() (userEmail)")
    @Test
    public void Test_readAll_with_userEmail() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", "pigs95team@gmail.com");
        retrieveCondition.put("isFilteredDate", null);

        // When
        posterRequestService.readAll(retrieveCondition);

        // Then
        Mockito.verify(prRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] readAll() (isFilteredDate)")
    @Test
    public void Test_readAll_with_isFilteredDate() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", "Y");

        // When
        posterRequestService.readAll(retrieveCondition);

        // Then
        Mockito.verify(prRepository, times(1)).findAllByCondition(any());
    }

    @DisplayName("[테스트] getAllPossibleSeats() : Poster 타입 Row 타입")
    @Test
    public void Test_getAllPossibleSeats_with_rowType() throws Exception {
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
        List<String[]> allPossibleSeats = posterRequestService.getAllPossibleSeats(wantedDate);

        // Then
        assertThat(allPossibleSeats.size()).isEqualTo(expectedCounts);
    }

    @DisplayName("[테스트] getAllPossibleSeats() : Poster 타입 Cn (Column 타입)")
    @Test
    public void Test_getAllPossibleSeats_with_columnType() throws Exception {
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
        List<String[]> allPossibleSeats = posterRequestService.getAllPossibleSeats(wantedDate);

        // Then
        assertThat(allPossibleSeats.size()).isEqualTo(expectedCounts);
    }

    @DisplayName("[테스트] getAllPossibleSeats() : IllegalArgumentException 에러 발생 (with wrongStartedDate)")
    @Test
    public void Test_getAllPossibleSeats_with_IllegalArgumentException() throws Exception {
        // Given
        String expectedMessage = "적절하지 않은 데이터 형식 입니다. (Please check the data)";
        String wrongStartedDate = "2021-01-01-01";

        HashMap<String, String> wantedDate = new HashMap<>();
        wantedDate.put("page", "1");
        wantedDate.put("startedDate", wrongStartedDate);
        wantedDate.put("finishedDate", LocalDate.now().plusMonths(1).toString());

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> posterRequestService.getAllPossibleSeats(wantedDate));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] update() : Review-Status 를 승인(APPROVAL) 로 변경했을 경우, PosterRequest -> Poster 삽입이 진행됨을 체크")
    @Test
    public void Test_update_with_ReviewStatus_APPROVAL() throws Exception {
        // Given
        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        String reviewStatus = String.valueOf(PosterReviewStatus.APPROVAL); // Review-Status : APPROVAL
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(reviewStatus);

        // When
        posterRequestService.update(work, updaterEmail, 1L, updateDtoPosterRequest);

        // Then
        Mockito.verify(posterRequest, times(1)).changeReviewer(anyString());
        Mockito.verify(posterRequest, times(1)).changeReviewStatusToApproval();
        Mockito.verify(posterService, times(1)).createPoster(any(CreateDtoPoster.class));
        Mockito.verify(historyLogService, times(2)).writeHistoryLog(any(), any(User.class), anyString(), anyString(), anyBoolean());
    }

    @DisplayName("[테스트] update() : Review-Status 를 승인(APPROVAL) 이 아닌 값으로 변경했을 경우, 'review-status 변경', '이력의 삽입' 을 체크")
    @Test
    public void Test_update_with_ReviewStatus_PENDING() throws Exception {
        // Given
        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        String reviewStatus = String.valueOf(PosterReviewStatus.PENDING); // Review-Status : PENDING
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(reviewStatus);

        // When
        posterRequestService.update(work, updaterEmail, 1L, updateDtoPosterRequest);

        // Then
        Mockito.verify(posterRequest, times(1)).changeReviewer(anyString());
        Mockito.verify(posterRequest, times(1)).changeReviewStatusToPending();
        Mockito.verify(historyLogService, times(1)).writeHistoryLog(any(), any(User.class), anyString(), anyString(), anyBoolean());
    }

    @DisplayName("[테스트] update() : Review-Status 를 승인(APPROVAL) 이 아닌 값으로 변경했을 경우, 'review-status 변경', '이력의 삽입' 을 체크")
    @Test
    public void Test_update_with_ReviewStatus_NON_APPROVAL() throws Exception {
        // Given
        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        String reviewStatus = String.valueOf(PosterReviewStatus.NON_APPROVAL); // Review-Status : NON_APPROVAL
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(reviewStatus);

        // When
        posterRequestService.update(work, updaterEmail, 1L, updateDtoPosterRequest);

        // Then
        Mockito.verify(posterRequest, times(1)).changeReviewer(anyString());
        Mockito.verify(posterRequest, times(1)).changeReviewStatusToNonApproval();
        Mockito.verify(historyLogService, times(1)).writeHistoryLog(any(), any(User.class), anyString(), anyString(), anyBoolean());
    }

    @DisplayName("[테스트] update() : BadRequestException 에러 발생 (ReviewStatus is already APPROVAL)")
    @Test
    public void Test_update_with_BadRequestException_case2() {
        // Given
        String expectedMessage = "이 항목은 이미 승인되었습니다. (This item is already approved)";

        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));

        Mockito.when(posterRequest.getReviewStatus()).thenReturn(PosterReviewStatus.APPROVAL); // ReviewStatus is already APPROVAL

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> posterRequestService.update(work, updaterEmail, 1L, updateDtoPosterRequest));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] update() : BadRequestException 에러 발생 (ReviewStatus is invalid value)")
    @Test
    public void Test_update_with_BadRequestException_case3() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameter value)";

        String work = "review";
        String updaterEmail = "pigs95team@gmail.com";
        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);

        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(updaterEmail);
        Mockito.when(updateDtoPosterRequest.getReviewStatus()).thenReturn(PosterReviewStatus.NEW.name()); // ReviewStatus is invalid value

        // When
        BadRequestException exception = assertThrows(BadRequestException.class, () -> posterRequestService.update(work, updaterEmail, 1L, updateDtoPosterRequest));

        // Then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] delete()")
    @Test
    public void Test_delete() {
        // Given
        Long prId = 1L;

        // When
        posterRequestService.delete(prId);

        // Then
        Mockito.verify(prRepository, times(1)).deleteById(anyLong());
    }

    @DisplayName("[테스트] getPossibleSeatsAsList()")
    @Test
    public void Test_getPossibleSeatsAsList() {
        // Given
        int[][] allPossibleSeats = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
        for(int i = 1; i <= POSTER_LAYOUT_SIZE; i++) {
            for(int j = 1; j <= POSTER_LAYOUT_SIZE; j++) {
                allPossibleSeats[i][j] = IMPOSSIBLE_SEAT;
            }
        }

        int possibleRowIndex = 1; int possibleColumnIndex = 1;
        allPossibleSeats[possibleRowIndex][possibleColumnIndex] = POSSIBLE_SEAT;

        // When
        List<String[]> allPossibleSeatsAsList = posterRequestService.getPossibleSeatsAsList(allPossibleSeats);

        // Then
        assertThat(allPossibleSeatsAsList.get(0).length).isEqualTo(2);
    }

    @DisplayName("[테스트] calculatePossibleSeats() : PR 타입 - R1")
    @Test
    public void Test_calculatePossibleSeats_with_R1_type() {
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
        boolean resultOfCalculation = posterRequestService.calculatePossibleSeats(allSeats, impossibleSeats);

        // Then
        assertThat(resultOfCalculation).isEqualTo(true);
        assertThat(allSeats[impossibleRowIndex][impossibleColumnIndex]).isEqualTo(-1);
    }

    @DisplayName("[테스트] calculatePossibleSeats() : PR 타입 - R6")
    @Test
    public void Test_calculatePossibleSeats_with_R6_type() {
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
        boolean resultOfCalculation = posterRequestService.calculatePossibleSeats(allSeats, impossibleSeats);

        // Then
        assertThat(resultOfCalculation).isEqualTo(false);
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

        List<String[]> allPossibleSeatsAsList = posterRequestService.getPossibleSeatsAsList(allPossibleSeats);

        // When
        boolean isPossible1 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "R1");
        boolean isPossible2 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 7L, 1L, "R1");
        boolean isPossible3 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 7L, "R1");
        boolean isPossible4 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "C1");
        boolean isImpossible1 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 2L, "R2");
        boolean isImpossible2 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "R2");
        boolean isImpossible3 = posterRequestService.isPossibleSeat(allPossibleSeatsAsList, 1L, 1L, "C2");

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
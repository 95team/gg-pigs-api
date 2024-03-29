//package com.gg_pigs.app.poster.service;
//
//import com.gg_pigs.app.historyLog.entity.HistoryLogAction;
//import com.gg_pigs.app.historyLog.service.HistoryLogService;
//import com.gg_pigs.app.poster.dto.CreateDtoPoster;
//import com.gg_pigs.app.poster.entity.PosterReviewStatus;
//import com.gg_pigs.app.poster.repository.PosterRepository;
//import com.gg_pigs.app.poster.service.PosterService;
//import com.gg_pigs.app.posterRequest.dto.CreateDtoPosterRequest;
//import com.gg_pigs.app.posterRequest.dto.ReadConditionDtoPosterRequest;
//import com.gg_pigs.app.posterRequest.dto.ReadDtoPosterRequest;
//import com.gg_pigs.app.posterRequest.dto.UpdateDtoPosterRequest;
//import com.gg_pigs.app.posterRequest.entity.PosterRequest;
//import com.gg_pigs.app.posterRequest.entity.PosterRequestEmsAlarm;
//import com.gg_pigs.app.posterRequest.repository.PosterRequestRepository;
//import com.gg_pigs.app.posterType.entity.PosterType;
//import com.gg_pigs.app.posterType.repository.PosterTypeRepository;
//import com.gg_pigs.app.user.entity.User;
//import com.gg_pigs.app.user.repository.UserRepository;
//import com.gg_pigs.global.exception.BadRequestException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityNotFoundException;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static com.gg_pigs.global.CommonDefinition.POSTER_LAYOUT_SIZE;
//
///**
// * [Note]
// * 1. HL == HistoryLog
// * 2. PR == PosterRequest
// * */
//
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//@Service
//public class PosterRequestService {
//
//    private static final int POSSIBLE_SEAT = 1;
//    private static final int IMPOSSIBLE_SEAT = -1;
//
//    private final UserRepository userRepository;
//    private final PosterRepository posterRepository;
//    private final PosterTypeRepository posterTypeRepository;
//    private final PosterRequestRepository posterRequestRepository;
//
//    private final PosterService posterService;
//    private final HistoryLogService historyLogService;
//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    /** CREATE */
//    @Transactional
//    public Long create(CreateDtoPosterRequest createDtoPosterRequest) throws Exception {
//        User user = userRepository.findUserByEmail(createDtoPosterRequest.getUserEmail()).orElse(null);
//        PosterType posterType = posterTypeRepository.findPosterTypeByType(createDtoPosterRequest.getPosterType()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
//
//        PosterRequest pr = posterRequestRepository.save(PosterRequest.createPosterRequest(createDtoPosterRequest, user, posterType));
//        applicationEventPublisher.publishEvent(PosterRequestEmsAlarm.getInstanceForCreate(pr));
//
//        return pr.getId();
//    }
//
//    /** READ */
//    public ReadDtoPosterRequest read(Long posterRequestId) {
//        PosterRequest posterRequest = posterRequestRepository.findById(posterRequestId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
//
//        return ReadDtoPosterRequest.of(posterRequest);
//    }
//
//    public List<ReadDtoPosterRequest> readAll(Map<String, String> retrieveCondition) {
//        ReadConditionDtoPosterRequest condition = new ReadConditionDtoPosterRequest();
//
//        // 1. Page 정보를 가공합니다.
//        condition.setPageCondition(retrieveCondition.get("page"));
//
//        // 2. UserEmail 정보를 가공합니다.
//        condition.setUserEmailCondition(retrieveCondition.get("userEmail"));
//
//        // 3. IsFilteredDate 정보를 가공합니다.
//        condition.setDateCondition(retrieveCondition.get("isFilteredDate"));
//
//        List<PosterRequest> posterRequests = posterRequestRepository.findAllByCondition(condition);
//
//        return posterRequests.stream().map(ReadDtoPosterRequest::of).collect(Collectors.toList());
//    }
//
//    /** UPDATE */
//    @Transactional
//    public Long update(String work, String updaterEmail, Long posterRequestId, UpdateDtoPosterRequest updateDtoPosterRequest) throws Exception {
//        PosterRequest posterRequest = posterRequestRepository.findByIdWithFetch(posterRequestId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
//        User reviewer = userRepository.findUserByEmail(updaterEmail).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
//
//        if(work.equalsIgnoreCase("review")) {
//            if(posterRequest.getReviewStatus() == PosterReviewStatus.APPROVAL) {
//                throw new BadRequestException("이 항목은 이미 승인되었습니다. (This item is already approved)");
//            }
//
//            String beforeReviewStatus = String.valueOf(posterRequest.getReviewStatus());
//            String afterReviewStatus = updateDtoPosterRequest.getReviewStatus().toUpperCase();
//            boolean isNotChangedReviewStatus = beforeReviewStatus.equalsIgnoreCase(afterReviewStatus);
//
//            if(isNotChangedReviewStatus) {
//                return posterRequestId;
//            }
//
//            posterRequest.changeReviewer(reviewer.getEmail());
//
//            boolean hasApproval = false;
//            long newPosterId = -1;
//            if(afterReviewStatus.equalsIgnoreCase(PosterReviewStatus.APPROVAL.name())) {
//                hasApproval = true;
//                posterRequest.changeReviewStatusToApproval();
//                newPosterId = this.InsertPRIntoPoster(posterRequest);
//            } else if (afterReviewStatus.equalsIgnoreCase(PosterReviewStatus.PENDING.name())) {
//                posterRequest.changeReviewStatusToPending();
//            } else if (afterReviewStatus.equalsIgnoreCase(PosterReviewStatus.NON_APPROVAL.name())) {
//                posterRequest.changeReviewStatusToNonApproval();
//            } else {
//                throw new BadRequestException("적절하지 않은 요청입니다. (Please check the parameter value)");
//            }
//
//            // HistoryLog 에 이력을 삽입합니다.
//            this.HLForUpdateReviewStatus(reviewer, posterRequestId, beforeReviewStatus, afterReviewStatus);
//            if(hasApproval) {
//                this.HLForInsertPRIntoPoster(reviewer, posterRequestId, newPosterId);
//            }
//        }
//
//        return posterRequestId;
//    }
//
//    /** DELETE */
//    public void delete(Long posterRequestId) {
//        posterRequestRepository.deleteById(posterRequestId);
//    }
//
//    /** ETC */
//    public boolean calculatePossibleSeats(int[][] allSeats, List<Map<String, String>> impossibleSeats) {
//        int[][] allSeatsCopy = new int[allSeats.length][allSeats[0].length];
//
//        for(int i = 0; i < allSeats.length; i++) {
//            for(int j = 0; j < allSeats[0].length; j++) {
//                allSeatsCopy[i][j] = allSeats[i][j];
//            }
//        }
//
//        try {
//            for (Map<String, String> impossibleSeat : impossibleSeats) {
//                String posterType = impossibleSeat.get("posterType");
//                int rowIndex = Integer.parseInt(String.valueOf(impossibleSeat.get("rowPosition")));
//                int columnIndex = Integer.parseInt(String.valueOf(impossibleSeat.get("columnPosition")));
//                int rangeOfIndex = posterType.charAt(1) - '0';
//
//                rowIndex = (rowIndex % POSTER_LAYOUT_SIZE);
//                columnIndex = (columnIndex % POSTER_LAYOUT_SIZE);
//                if(rowIndex == 0) rowIndex = POSTER_LAYOUT_SIZE;
//                if(columnIndex == 0) columnIndex = POSTER_LAYOUT_SIZE;
//
//                if (posterType.charAt(0) == 'R') {
//                    for (int i = 0; i < rangeOfIndex; i++) {
//                        allSeats[rowIndex + i][columnIndex] = IMPOSSIBLE_SEAT;
//                    }
//                }
//                else if (posterType.charAt(0) == 'C') {
//                    for (int i = 0; i < rangeOfIndex; i++) {
//                        allSeats[rowIndex][columnIndex + i] = IMPOSSIBLE_SEAT;
//                    }
//                }
//            }
//
//            return true;
//        } catch (Exception exception) {
//            System.out.println(exception);
//
//            // allSeats 배열을 초기화 합니다.
//            for(int i = 0; i < allSeats.length; i++) {
//                for(int j = 0; j < allSeats[0].length; j++) {
//                    allSeats[i][j] = allSeatsCopy[i][j];
//                }
//            }
//
//            return false;
//        }
//    }
//
//    public boolean isPossibleSeat(List<String[]> allPossibleSeats, Long rowPosition, Long columnPosition, String stringTypeOfPosterType) {
//        boolean isPossible = true;
//
//        int[][] allSeats = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
//        int rangeOfIndex = stringTypeOfPosterType.charAt(1) - '0';
//        int rowIndex = Math.toIntExact(rowPosition);
//        int columnIndex = Math.toIntExact(columnPosition);
//
//        if(rowIndex > POSTER_LAYOUT_SIZE) {
//            rowIndex %= POSTER_LAYOUT_SIZE;
//            if(rowIndex == 0) rowIndex = POSTER_LAYOUT_SIZE;
//        }
//
//        if(columnIndex > POSTER_LAYOUT_SIZE) {
//            columnIndex %= POSTER_LAYOUT_SIZE;
//            if(columnIndex == 0) columnIndex = POSTER_LAYOUT_SIZE;
//        }
//
//        for (String[] allPossibleSeat: allPossibleSeats) {
//            allSeats[Integer.parseInt(allPossibleSeat[0])][Integer.parseInt(allPossibleSeat[1])] = POSSIBLE_SEAT;
//        }
//
//        if (stringTypeOfPosterType.charAt(0) == 'R') {
//            for (int i = 0; i < rangeOfIndex; i++) {
//                if((rowIndex + i > POSTER_LAYOUT_SIZE) ||
//                        (allSeats[rowIndex + i][columnIndex] != POSSIBLE_SEAT)) {
//                    isPossible = false;
//                    break;
//                }
//            }
//        }
//        else if (stringTypeOfPosterType.charAt(0) == 'C') {
//            for (int i = 0; i < rangeOfIndex; i++) {
//                if((columnIndex + i > POSTER_LAYOUT_SIZE) ||
//                        (allSeats[rowIndex][columnIndex + i] != POSSIBLE_SEAT)) {
//                    isPossible = false;
//                    break;
//                }
//            }
//        }
//
//        return isPossible;
//    }
//
//    public List<String[]> getAllPossibleSeats(Map<String, String> wantedDate) throws Exception {
//        int[][] allSeats = new int[POSTER_LAYOUT_SIZE + 1][POSTER_LAYOUT_SIZE + 1];
//
//        Long startIndexOfPage, lastIndexOfPage;
//        LocalDate startedDate, finishedDate;
//
//        try {
//            startIndexOfPage = (Long.parseLong(wantedDate.get("page")) - 1) * POSTER_LAYOUT_SIZE + 1;
//            lastIndexOfPage = Long.parseLong(wantedDate.get("page")) * POSTER_LAYOUT_SIZE;
//            startedDate = LocalDate.parse(wantedDate.get("startedDate"));
//            finishedDate = LocalDate.parse(wantedDate.get("finishedDate"));
//        } catch (NullPointerException | DateTimeParseException | NumberFormatException exception) {
//            throw new IllegalArgumentException("적절하지 않은 데이터 형식 입니다. (Please check the data)");
//        }
//
//        List<Map<String, String>> impossibleSeats = posterRepository.findAllImpossibleSeats(startIndexOfPage, lastIndexOfPage, startedDate, finishedDate);
//        if (calculatePossibleSeats(allSeats, impossibleSeats) == false)
//            throw new Exception("신청 가능한 자리를 조회할 수 없습니다.");
//
//        impossibleSeats = posterRequestRepository.findAllImpossibleSeats(startIndexOfPage, lastIndexOfPage, startedDate, finishedDate);
//        if (calculatePossibleSeats(allSeats, impossibleSeats) == false)
//            throw new Exception("신청 가능한 자리를 조회할 수 없습니다.");
//
//        List<String[]> allPossibleSeats = getPossibleSeatsAsList(allSeats);
//
//        return allPossibleSeats;
//    }
//
//    public List<String[]> getPossibleSeatsAsList(int[][] allSeats) {
//        List<String[]> allPossibleSeats = new ArrayList<>();
//
//        for(int i = 1; i < allSeats.length; i++) {
//            for(int j = 1; j < allSeats[0].length; j++) {
//                if(allSeats[i][j] != IMPOSSIBLE_SEAT) {
//                    allPossibleSeats.add(new String[]{Integer.toString(i), Integer.toString(j)});
//                }
//            }
//        }
//
//        return allPossibleSeats;
//    }
//
//    private Long InsertPRIntoPoster(PosterRequest posterRequest) throws Exception {
//        return posterService.create(CreateDtoPoster.of(posterRequest));
//    }
//
//    private void HLForUpdateReviewStatus(User worker, Long posterRequestId, String beforeReviewStatus, String afterReviewStatus) {
//        String title = "Update posterRequest reviewStatus field";
//        String content = "PosterRequest: " + posterRequestId + "\n" +
//                "Before Review Status: " + beforeReviewStatus + "\n" +
//                "After Review Status: " + afterReviewStatus;
//        historyLogService.writeHistoryLog(HistoryLogAction.UPDATE, worker, title, content, true);
//    }
//
//    private void HLForInsertPRIntoPoster(User worker, Long posterRequestId, Long posterId) {
//        String title = "Insert PosterRequest into Poster";
//        String content = "PosterRequest: " + posterRequestId + "\n"
//                + "Poster: " + posterId;
//        historyLogService.writeHistoryLog(HistoryLogAction.CREATE, worker, title, content, true);
//    }
//}

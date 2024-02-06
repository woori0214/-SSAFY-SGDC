package com.ssafy.sgdc.competition.controller;

import com.ssafy.sgdc.category.dto.UserCategoryDto;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.dto.CompetitionDto;
import com.ssafy.sgdc.competition.dto.MatchingDto;
import com.ssafy.sgdc.competition.dto.request.CompetitionProgressDetailDto;
import com.ssafy.sgdc.competition.dto.request.FriendSendRequest;
import com.ssafy.sgdc.competition.dto.request.RandomSendRequest;
import com.ssafy.sgdc.competition.dto.response.*;
import com.ssafy.sgdc.competition.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/compet")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;

    @PostMapping("/random-send")
    public ResponseEntity<?> randomSend(@RequestBody RandomSendRequest requestData) {
        Matching result = competitionService.sendRandomMatching(requestData.getUserId(), requestData.getCategoryId());

        SendResponseData data = SendResponseData.builder()
                .user_nickname(result.getUser().getUserNickname())
                .build();

        return new ResponseEntity<>(SendResponse.builder()
                .status(200).message("랜덤 도전장 보내기 성공")
                .matching(data).build(), HttpStatus.OK
        );
    }
    @PostMapping("/friend-send")
    public ResponseEntity<?> friendSend(@RequestBody FriendSendRequest requestData) {
        Matching result = competitionService.sendFriendMatching(requestData.getUserId(), requestData.getFriendId(), requestData.getCategoryId());

        SendResponseData data = SendResponseData.builder()
                .user_nickname(result.getUser().getUserNickname())
                .build();

        return new ResponseEntity<>(SendResponse.builder()
                .status(200).message("친구 도전장 보내기 성공")
                .matching(data).build(), HttpStatus.OK
        );
    }

    @PostMapping("/accept/{matchingId}")
    public ResponseEntity<?> acceptMatching(@PathVariable int matchingId) {
        Matching result = competitionService.acceptMatching(matchingId);

        AcceptResponseData data = AcceptResponseData.builder()
                .matching_id(result.getMatchingId())
                .category_id(result.getCategory().getCategoryId())
                .category_name(result.getCategory().getCategoryName())
                .build();

        return new ResponseEntity<>(AcceptResponse
                .builder().status(200).message("도전장 수락 성공")
                .matching(data)
                .build(), HttpStatus.OK
        );
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getMatchingList(@PathVariable int userId) {
        List<MatchingDto> result = competitionService.getMatchingList(userId);
        return new ResponseEntity<>(MatchingListResponse
                .builder().status(200).message("도전장 리스트 조회")
                .matching(result).build()
                , HttpStatus.OK
        );
    }

    @GetMapping("/receive-list/{userId}")
    public ResponseEntity<?> getReceiveMatchingList(@PathVariable int userId) {
        List<MatchingDto> result = competitionService.getReceiveMatchingList(userId);
        return new ResponseEntity<>(MatchingListResponse
                .builder().status(200).message("받은 도전장 리스트 조회")
                .matching(result).build()
                , HttpStatus.OK
        );
    }


    @GetMapping("/finish-compet-list/{userId}")
    public ResponseEntity<?> getFinishCompetList(@PathVariable int userId) {
        List<CompetitionDto> result = competitionService.getCompleteCompetitionList(userId);
        return new ResponseEntity<>(CompetitionListResponse.builder()
                .status(200).message("종료된 경쟁 리스트 조회 완료").competitions(result)
                .build(), HttpStatus.OK
        );
    }

    @GetMapping("/finish-compet-detail/{userId}/{competId}")
    public ResponseEntity<?> getFinishCompetition(@PathVariable int userId, @PathVariable int competId) {
        CompetitionDto result = competitionService.getCompleteCompetition(userId, competId);
        return new ResponseEntity<>(CompetitionResponse.builder()
                .status(200).message("종료된 경쟁 조회 완료").competition(result).build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/progress-compet-detail/{userId}")
    public ResponseEntity<?> getProgressCompetition(@PathVariable int userId) {
        List<CompetitionProgressDetailDto> result = competitionService.getProgressCompetitionDetail(userId);
        return new ResponseEntity<>(ProgressCompetitionDetailResponse.builder()
                .status(200).message("경쟁 모드 인증 현황 조회 완료").competitions(result)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/analysis/{userId}")
    public ResponseEntity<?> getUserCategoryList(@PathVariable int userId) {
        List<UserCategoryDto> result = competitionService.getUserCategoryList(userId);
        return new ResponseEntity<>(UserCategoryListResponse.builder()
                .status(200).message("유저 카테고리 목록 조회 성공").user_categories(result)
                .build(), HttpStatus.OK
        );
    }

    @GetMapping("/analysis/{userId}/{categoryId}")
    public ResponseEntity<?> getUserCategory(@PathVariable int userId, @PathVariable int categoryId) {
        UserCategoryDto result = competitionService.getUserCategory(userId, categoryId);
        return new ResponseEntity<>(UserCategoryResponse.builder()
                .status(200).message("유저 카테고리 조회 성공").user_category(result)
                .build(), HttpStatus.OK
        );
    }

}

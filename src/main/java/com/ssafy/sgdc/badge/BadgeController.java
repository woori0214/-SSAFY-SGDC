package com.ssafy.sgdc.badge;


import com.ssafy.sgdc.badge.dto.BadgeListDto;
import com.ssafy.sgdc.badge.response.ListResponse;
import com.ssafy.sgdc.badge.response.ObjectResponse;
import com.ssafy.sgdc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/badge")
public class BadgeController {

    // 유저뱃지에서 찾기
    // 유저가 갖고있는 모든 뱃지 반환

    // 1. 유저ID로 유저뱃지table내에 유저가 가지고 있는 badgeIdList로 반환
    // 2. 반환된 badgeIdList로 뱃지테이블로 각 뱃지 정보 탐색 -> 뱃지 리스트로 반환

    @Autowired
    BadgeService badgeService;
    @Autowired
    UserService userService;

    // 뱃지 추가 (싸강두천에서 사용하는 뱃지들)
    @RequestMapping(value = "/add-badge/", method = RequestMethod.POST)
    public ResponseEntity<ObjectResponse> addBadge(@RequestBody Badge badge){
        Badge addBadge = badgeService.addBadge(badge);

        Map<String, Object> response = new HashMap<>();

        response.put("badge", addBadge);
        return new ResponseEntity<>(ObjectResponse.builder()
                .status(200)
                .message("뱃지 추가완료")
                .data(response)
                .build(), HttpStatus.OK);
    }

    // 유저뱃지에 뱃지 추가
    @RequestMapping(value = "/add-user-badge/{userId}/{badgeId}", method = RequestMethod.POST)
    public ResponseEntity<ObjectResponse> addUserBadge(@PathVariable int userId, @PathVariable int badgeId){
        badgeService.addUserBadge(userService.getUserById(userId), badgeService.getBadge(badgeId));

        return new ResponseEntity<>(ObjectResponse.builder()
                .status(200)
                .message("유저뱃지에 뱃지 추가완료")
                .data(null)
                .build(), HttpStatus.OK);
    }

    // 뱃지 리스트 조회
    @RequestMapping(value = "/{userId}/",  method = RequestMethod.GET)
    public ResponseEntity<ListResponse<BadgeListDto>> getUserBadges(@PathVariable int userId) {
        List<UserBadge> userBadgeList = badgeService.getUserBadgeList(userId);
        List<BadgeListDto> badgeList = new ArrayList<>();

        for (int i = 0; i < userBadgeList.size(); i++) {
            UserBadge userBadge = userBadgeList.get(i);
            int badgeId = userBadge.getBadgeId().getBadgeId();

            Badge badge = badgeService.getBadge(badgeId);
            BadgeListDto badgeListDto = new BadgeListDto(
                    badge.getBadgeId(),badge.getBadgeName(),
                    badge.getBadgeDetail(), badge.getBadgeImg());

            badgeList.add(badgeListDto);
        }
//         사용자가 보유한 뱃지 목록과 함께 200 OK 응답//
        return new ResponseEntity<>(ListResponse.<BadgeListDto>builder()
                 .status(200)
                 .message("유저 뱃지 리스트")
                 .data(badgeList)
                 .build(), HttpStatus.OK);
    }

}
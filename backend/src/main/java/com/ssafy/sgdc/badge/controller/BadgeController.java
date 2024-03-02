package com.ssafy.sgdc.badge.controller;

import com.ssafy.sgdc.badge.dto.BadgeDto;
import com.ssafy.sgdc.badge.dto.response.BadgeListResponse;
import com.ssafy.sgdc.badge.dto.response.BadgeResponse;
import com.ssafy.sgdc.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/badge")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("/{badgeId}")
    public ResponseEntity<?> getBadge(@PathVariable int badgeId) {
        BadgeDto result = badgeService.getBadge(badgeId);
        return new ResponseEntity<>(BadgeResponse.builder()
                .status(200).message("뱃지 조회 완료").badge(result).build(),
                HttpStatus.OK);
    }

    @GetMapping("badge-list")
    public ResponseEntity<?> getBadgeList() {
        List<BadgeDto> result = badgeService.getBadgeList();
        return new ResponseEntity<>(BadgeListResponse.builder()
                .status(200).message("뱃지 리스트 조회 완료").badges(result).build(),
                HttpStatus.OK);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getUserBadgeList(@PathVariable int userId) {
        List<BadgeDto> result = badgeService.getUserBadgeList(userId);
        return new ResponseEntity<>(BadgeListResponse.builder()
                .status(200).message("유저 뱃지 리스트 조회 완료").badges(result).build(),
                HttpStatus.OK);
    }

}
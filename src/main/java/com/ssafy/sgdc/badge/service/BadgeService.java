package com.ssafy.sgdc.badge.service;

import com.ssafy.sgdc.badge.domain.Badge;
import com.ssafy.sgdc.badge.domain.UserBadge;
import com.ssafy.sgdc.badge.dto.BadgeDto;
import com.ssafy.sgdc.badge.dto.UserBadgeDto;
import com.ssafy.sgdc.badge.repository.BadgeRepo;
import com.ssafy.sgdc.badge.repository.UserBadgeRepo;
import com.ssafy.sgdc.category.UserCategory;
import com.ssafy.sgdc.category.repository.UserCategoryRepo;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepo badgeRepo;

    private final UserBadgeRepo userBadgeRepo;

    private final UserCategoryRepo userCategoryRepo;

    private final UserRepo userRepo;

    // 경쟁이 끝나면 어떤 카테고리로 가야하는 지 분기하는 메소드
    public void branchCategory(int userId, int categoryId) {

        switch (categoryId) {
            case 1:
                addMorningBadge(userId);
                break;
            case 2:
                addAlgorithimBadge(userId);
                break;
            case 3:
                addRunningBadge(userId);
                break;
            case 4:
                addStudyTimeBadge(userId);
                break;
            case 5:
                addDietBadge(userId);
                break;
            case 6:
                addSelfControlBadge(userId);
                break;
            default:
                throw new RuntimeException("해당 카테고리는 존재하지 않습니다.");
        }

    }

    // TODO: UserCategory 불러와서 winCount, failCount 부분이 반복임

    // 기상 관련 뱃지 추가
    private void addMorningBadge(int userId) {

        UserCategory userCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, 1)
                        .orElseThrow(() -> new RuntimeException("addMorningBadge -> 해당 유저카테고리는 존재하지 않습니다."));

        int winCount = userCategory.getCategoryWinCnt();
        int failCount = userCategory.getCategoryFailCnt();

        // 해당 뱃지를 찾아서 유저 뱃지에 넣어줌
        // 승리 관련
        switch (winCount) {
            case 1:
                addUserBadge(1, userId);
                break;
            case 2:
                addUserBadge(2, userId);
                break;
            default:
                System.out.println("기상 성공 뱃지 변경 없음!");
        }

        switch (failCount) {
            case 1:
                addUserBadge(3, userId);
                break;
            case 2:
                addUserBadge(4, userId);
                break;
            default:
                System.out.println("기상 실패 뱃지 변경 없음!");
        }

    }

    // 알고리즘 관련 뱃지 추가
    private void addAlgorithimBadge(int userId) {
        UserCategory userCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, 2)
                        .orElseThrow(() -> new RuntimeException("addAlgorithmBadge -> 해당 유저카테고리는 존재하지 않습니다."));

        int winCount = userCategory.getCategoryWinCnt();
        int failCount = userCategory.getCategoryFailCnt();

        // 해당 뱃지를 찾아서 유저 뱃지에 넣어줌
        // 승리 관련
        switch (winCount) {
            case 1:
                addUserBadge(5, userId);
                break;
            default:
                System.out.println("알고리즘 성공 뱃지 변경 없음!");
        }

        switch (failCount) {
            case 1:
                addUserBadge(6, userId);
                break;
            default:
                System.out.println("알고리즘 실패 뱃지 변경 없음!");
        }
    }

    // 운동 관련 뱃지 추가
    private void addRunningBadge(int userId) {
        UserCategory userCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, 3)
                        .orElseThrow(() -> new RuntimeException("addRunningBadge -> 해당 유저카테고리는 존재하지 않습니다."));

        int winCount = userCategory.getCategoryWinCnt();
        int failCount = userCategory.getCategoryFailCnt();

        // 해당 뱃지를 찾아서 유저 뱃지에 넣어줌
        // 승리 관련
        switch (winCount) {
            case 1:
                addUserBadge(12, userId);
                break;
            case 2:
                addUserBadge(13, userId);
                break;
            default:
                System.out.println("운동 성공 뱃지 변경 없음!");
        }

        switch (failCount) {
            case 1:
                addUserBadge(14, userId);
                break;
            case 2:
                addUserBadge(15, userId);
                break;
            default:
                System.out.println("운동 실패 뱃지 변경 없음!");
        }
    }

    // 공부 시관 관련 뱃지 추가
    private void addStudyTimeBadge(int userId) {
        UserCategory userCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, 4)
                        .orElseThrow(() -> new RuntimeException("addStudyTimeBadge -> 해당 유저카테고리는 존재하지 않습니다."));

        int winCount = userCategory.getCategoryWinCnt();
        int failCount = userCategory.getCategoryFailCnt();

        // 해당 뱃지를 찾아서 유저 뱃지에 넣어줌
        // 승리 관련
        switch (winCount) {
            case 1:
                addUserBadge(16, userId);
                break;
            default:
                System.out.println("공부 시간 성공 뱃지 변경 없음!");
        }
    }

    // 식단 관련 뱃지 추가
    private void addDietBadge(int userId) {
        UserCategory userCategory =
                userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(userId, 5)
                        .orElseThrow(() -> new RuntimeException("addDietBadge -> 해당 유저카테고리는 존재하지 않습니다."));

        int winCount = userCategory.getCategoryWinCnt();
        int failCount = userCategory.getCategoryFailCnt();

        // 해당 뱃지를 찾아서 유저 뱃지에 넣어줌
        // 승리 관련
        switch (winCount) {
            case 1:
                addUserBadge(7, userId);
                break;
            case 2:
                addUserBadge(8, userId);
                break;
            case 3:
                addUserBadge(9, userId);
                break;
            case 4:
                addUserBadge(10, userId);
                break;
            default:
                System.out.println("식단 성공 뱃지 변경 없음!");
        }

        switch (failCount) {
            case 1:
                addUserBadge(11, userId);
                break;
            default:
                System.out.println("식단 실패 뱃지 변경 없음!");
        }

    }

    // TODO: 절제 관련 뱃지 추가 필요
    private void addSelfControlBadge(int userId) {
    }

    private void addUserBadge(int badgeId, int userId) {
        userBadgeRepo.save(
                UserBadgeDto.from(
                        badgeRepo.findBadgeByBadgeId(badgeId)
                                .orElseThrow(() -> new RuntimeException("해당 뱃지를 찾을 수 없습니다.")),
                        userRepo.findByUserId(userId)
                                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."))
                )
        );
    }

    // 뱃지 정보가져오기
    public BadgeDto getBadge(int badgeId){
        return BadgeDto.of(badgeRepo.findBadgeByBadgeId(badgeId).orElseThrow(() -> new RuntimeException("해당 뱃지는 존재하지 않습니다.")));
    }

    // 전체 뱃지 정보 가져오기
    public List<BadgeDto> getBadgeList() {
        return badgeRepo.findAll().stream().map(BadgeDto::of).toList();
    }

    // 해당 유저가 가지고 있는 뱃지 리스트 조회
    public List<BadgeDto> getUserBadgeList(int userId) {

        List<BadgeDto> badgeList = new ArrayList<>();

        List<UserBadge> userBadgeList =
                userBadgeRepo.findUserBadgeByUserUserId(userId);

        for (UserBadge userBadge : userBadgeList) {
            Badge badge = badgeRepo.findBadgeByBadgeId(userBadge.getBadge().getBadgeId())
                    .orElseThrow(() -> new RuntimeException("getUserBadgeList -> 해당 뱃지가 존재하지 않습니다."));
            badgeList.add(BadgeDto.of(badge));
        }

        return badgeList;
    }

}

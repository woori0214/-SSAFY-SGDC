package com.ssafy.sgdc.aspect;

import com.ssafy.sgdc.enums.SoloStatus;
import com.ssafy.sgdc.solo.repository.SoloRepo;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DayScheduler {

    private final UserRepo userRepo;
    private final SoloRepo soloRepo;

    // 매일 00시 정각마다 업데이트
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateDay() {

        // 유저 도전장 횟수 업데이트
        userRepo.updateAllUsersChallengeCount();

        // 솔로모드 상태 업데이트
        soloRepo.updateSoloStatus(SoloStatus.PROGRESS, SoloStatus.DONE);

    }
}

package com.ssafy.sgdc.competition;

import com.ssafy.sgdc.category.CategoryRepo;
import com.ssafy.sgdc.domain.entity.Matching;
import com.ssafy.sgdc.domain.entity.enums.CompetKind;
import com.ssafy.sgdc.domain.entity.enums.IsSender;
import com.ssafy.sgdc.domain.entity.enums.MatchStatus;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MatchingService {

    @Autowired
    private MatchingRepo matchingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Matching sendRandomMatching(Long userId, Long categoryId) {
        User user = userRepo.findByUserId(userId);

        if (user.getChallengeCnt() > 0) {
            user.setChallengeCnt(user.getChallengeCnt() - 1);
        } else {
            throw new RuntimeException("남은 도전장 없음");
//            log.error("[ERROR][랜덤도전장보내기]도전장 없음");
        }

        User randomUser = getRandomUser(userId);

        Matching sendMatching = new Matching();
        sendMatching.setUser(userRepo.findByUserId(userId));
        sendMatching.setCategory(categoryRepo.findByCategoryId(categoryId));
        sendMatching.setCompetKind(CompetKind.RANDOM);
        sendMatching.setIsSender(IsSender.Y);
        sendMatching.setCompetExpirationTime(LocalDateTime.now().plusHours(2));
        sendMatching.setMatchStatus(MatchStatus.WAIT);

        Matching receiveMatching = new Matching();
        receiveMatching.setUser(randomUser);
        receiveMatching.setCategory(categoryRepo.findByCategoryId(categoryId));
        receiveMatching.setCompetKind(CompetKind.RANDOM);
        receiveMatching.setIsSender(IsSender.N);
        receiveMatching.setCompetExpirationTime(LocalDateTime.now().plusHours(2));
        receiveMatching.setMatchStatus(MatchStatus.WAIT);

        matchingRepo.save(receiveMatching);

        return matchingRepo.save(sendMatching);

    }

    private User getRandomUser(Long userId) {

        Long minId = userRepo.findMinUserId();
        Long maxId = userRepo.findMaxUserId();
        Random random = new Random();

        User randomUser;
        do {
            long randomId = minId + (long) (random.nextDouble() * (maxId - minId));
            randomUser = userRepo.findByUserId(randomId);
        } while (randomUser == null || randomUser.getUserId() == userId);

        return randomUser;

    }

}

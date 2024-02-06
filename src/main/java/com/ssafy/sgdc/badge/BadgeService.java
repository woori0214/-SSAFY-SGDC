package com.ssafy.sgdc.badge;

import com.ssafy.sgdc.badge.repo.BadgeRepo;
import com.ssafy.sgdc.badge.repo.UserBadgeRepo;
import com.ssafy.sgdc.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {
    @Autowired
    BadgeRepo badgeRepo;

    @Autowired
    UserBadgeRepo userBadgeRepo;

    // 뱃지 추가
    public Badge addBadge(Badge badge){
        return badgeRepo.save(badge);
    }

    // 뱃지 정보가져오기
    public Badge getBadge(int badgeId){
        return badgeRepo.findByBadgeId(badgeId);
    }

    // 유저 뱃지 리스트에 넣기
    @Transactional
    public void addUserBadge(User userId, Badge badgeId) {
        UserBadge userBadge = new UserBadge();

        userBadge.setBadgeId(badgeId);
        userBadge.setUserId(userId);

        userBadgeRepo.save(userBadge);

    }

    @Transactional
    public List<UserBadge> getUserBadgeList(int userId){
        return userBadgeRepo.findByUserId(userId).get();
    }
}

package com.ssafy.sgdc.badge;

import com.ssafy.sgdc.badge.repo.BadgeRepo;
import com.ssafy.sgdc.badge.repo.UserBadgeRepo;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BadgeData implements CommandLineRunner {

    @Autowired
    private BadgeRepo badgeRepository;

    @Autowired
    private UserBadgeRepo userBadgeRepo;

    @Autowired
    private UserRepo userRepository; // 예시를 위해 추가, 실제 사용자 엔티티와 리포지토리가 필요합니다.

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 뱃지 더미 데이터 생성
        Badge badge1 = new Badge(0, "기상", "매일 아침 8시 이전 기상", "morning.png");
        Badge badge2 = new Badge(1, "알고리즘", "알고리즘 문제 5개 이상 해결", "algorithm.png");
        if(!badgeRepository.existsById(0)){
            badge1 = badgeRepository.save(badge1);
        }

        // 유저 뱃지 더미 데이터 생성
        UserBadge userBadge1 = new UserBadge(0, badge1, new User());
        UserBadge userBadge2 = new UserBadge(1, badge2, new User());

        userBadgeRepo.save(userBadge1);
    }
}

//    INSERT INTO badge (badge_id, badge_name, badge_detail, badge_img) VALUES ('0', '기상', '기상 잘한사람', 'null');
//    INSERT INTO badge (badge_id, badge_name, badge_detail, badge_img) VALUES ('1', '알고리즘', '알고리즘 잘한사람', 'null');
//    INSERT INTO badge (badge_id, badge_name, badge_detail, badge_img) VALUES ('2', '운동', '만보기', 'null');
//    INSERT INTO badge (badge_id, badge_name, badge_detail, badge_img) VALUES ('3', '공부시간', '공부체크', 'null');
//    INSERT INTO badge (badge_id, badge_name, badge_detail, badge_img) VALUES ('4', '식단', '다이어트식단', 'null');
//    INSERT INTO badge (badge_id, badge_name, badge_detail, badge_img) VALUES ('5', '절제', '절제하기', 'null');


package com.ssafy.sgdc.badge.repo;

import com.ssafy.sgdc.badge.Badge;
import com.ssafy.sgdc.badge.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepo extends JpaRepository<Badge, Integer> {


//    뱃지 아이디 찾기
    Badge findByBadgeId(int badgeId);

}

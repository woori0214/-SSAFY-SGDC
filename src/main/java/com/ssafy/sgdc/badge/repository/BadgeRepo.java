package com.ssafy.sgdc.badge.repository;

import com.ssafy.sgdc.badge.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepo extends JpaRepository<Badge, Integer> {
//    뱃지 아이디 찾기
    Optional<Badge> findBadgeByBadgeId(int badgeId);

}

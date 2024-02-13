package com.ssafy.sgdc.badge.repository;

import com.ssafy.sgdc.badge.domain.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBadgeRepo extends JpaRepository<UserBadge, Integer> {

    // 사용자 ID에 따라 UserBadge 엔티티를 조회하는 메소드
    List<UserBadge> findUserBadgeByUserUserId(int userId);
//    @Query("SELECT EXISTS (SELECT ub.badge.badgeId from UserBadge ub WHERE ub.user.userId=:userId AND ub.badge.badgeId=:badgeId)")
//    boolean existsUserBadgeByUserIdAndBadgeId(int userId, int badgeId);

//    Optional<UserBadge> findUserBadgeByUserUserIdAndserBadgeBadgeId(int userId, int badgeId);
    Optional<UserBadge> findUserBadgeByUserUserIdAndBadgeBadgeId(int userId, int badgeId);


}

package com.ssafy.sgdc.badge.repo;

import com.ssafy.sgdc.badge.Badge;
import com.ssafy.sgdc.badge.UserBadge;
import com.ssafy.sgdc.competition.domain.Matching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBadgeRepo extends JpaRepository<UserBadge, Integer> {

    // 사용자 ID에 따라 UserBadge 엔티티를 조회하는 메소드
    @Query("SELECT ub FROM UserBadge ub WHERE ub.userId.userId = :userId")
    Optional <List<UserBadge>> findByUserId(int userId);

}

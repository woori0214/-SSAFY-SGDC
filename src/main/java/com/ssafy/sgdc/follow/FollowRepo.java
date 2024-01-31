package com.ssafy.sgdc.follow;

import com.ssafy.sgdc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Long> {


    // 팔로잉
    @Modifying
    @Query(value="INSERT INTO follow(user_id,following_id) VALUES(:userId, :followingId)", nativeQuery=true)
    void mFollow(@Param("userId") Long userId, @Param("followingId") Long followingId);

    // 언팔로잉
    @Modifying
    @Query(value="DELETE FROM follow  WHERE user_id = :userId AND following_id = :followingId", nativeQuery=true)
    void mUnFollow(@Param("userId") Long userId, @Param("followingId") Long followingId);

    // 팔로워 조회
    @Query(value = "SELECT * from follow f  where f.following_id = :userId", nativeQuery = true)
    List<Follow> getListByFromUserId(Long userId);

    // 팔로잉 조회
    @Query(value = "SELECT * from follow f  where f.user_id = :userId", nativeQuery = true)
    List<Follow> getListByToUserId(Long userId);

    // 팔로잉 팔로워 수 조회
    Long countByUserId(User userId); // 팔로잉 수
    Long countByFollowingId(User followingId); // 팔로워 수
}
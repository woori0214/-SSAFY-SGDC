package com.ssafy.sgdc.follow;

import com.ssafy.sgdc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Integer> {


    // 팔로잉
    @Modifying
    @Query(value="INSERT INTO follow(user_id,following_id) VALUES(:userId, :followingId)", nativeQuery=true)
    void mFollow(@Param("userId") int userId, @Param("followingId") int followingId);

    // 언팔로잉
    @Modifying
    @Query(value="DELETE FROM follow  WHERE user_id = :userId AND following_id = :followingId", nativeQuery=true)
    void mUnFollow(@Param("userId") int userId, @Param("followingId") int followingId);

    // 팔로워 조회
    @Query(value = "SELECT * from follow f  where f.following_id = :userId", nativeQuery = true)
    List<Follow> getListByFromUserId(int userId);

    // 팔로잉 조회
    @Query(value = "SELECT * from follow f  where f.user_id = :userId", nativeQuery = true)
    List<Follow> getListByToUserId(int userId);

    // 팔로잉 팔로워 수 조회
    int countByUserId(User userId); // 팔로잉 수
    int countByFollowingId(User followingId); // 팔로워 수

    // 팔로우 했는지 확인
    Optional<Follow> findByFollowingIdAndUserId(User followingId, User userId);

}
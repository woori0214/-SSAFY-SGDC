package com.ssafy.sgdc.follow;

import com.ssafy.sgdc.follow.dto.FollowerListResponseDto;
import com.ssafy.sgdc.follow.dto.FollowingListResponseDto;
import com.ssafy.sgdc.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface FollowService {

    // 팔로잉
    void follow(int toUserId, int fromUserId);

    // 팔로워
    void unFollow(int toUserId, int fromUserId);

    // 팔로잉 조회
    List<FollowingListResponseDto> followingList(int fromUserId);

    // 팔로워 리스트
    List<FollowerListResponseDto> followerList(int toUserId);

    // 팔로잉,팔로워 수 조회
    int followingCount(User userId); // 팔로잉 수
    int followerCount(User userId); // 팔로워 수

    // 팔로우했는지 확인
    boolean isFollow(User userId, User followingId);
}

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
    void follow(Long toUserId, Long fromUserId);

    // 팔로워
    void unFollow(Long toUserId, Long fromUserId);

    // 팔로잉 조회
    List<FollowingListResponseDto> followingList(Long fromUserId);

    // 팔로워 리스트
    List<FollowerListResponseDto> followerList(Long toUserId);

    // 팔로잉,팔로워 수 조회
    Long followingCount(User userId); // 팔로잉 수
    Long followerCount(User userId); // 팔로워 수
}

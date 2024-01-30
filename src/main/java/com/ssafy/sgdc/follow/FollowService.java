package com.ssafy.sgdc.follow;

import com.ssafy.sgdc.follow.dto.FollowerListResponseDto;
import com.ssafy.sgdc.follow.dto.FollowingListResponseDto;
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
    @Transactional
    List<FollowerListResponseDto> followerList(Long toUserId);
}

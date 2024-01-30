package com.ssafy.sgdc.follow;

import com.ssafy.sgdc.follow.dto.CMRespDto;
import com.ssafy.sgdc.follow.dto.FollowerListResponseDto;
import com.ssafy.sgdc.follow.dto.FollowingListResponseDto;
import com.ssafy.sgdc.util.response.GeneralResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "CREATED"),
        @ApiResponse(code = 400, message = "BAD REQUEST"),
        @ApiResponse(code = 500, message = "SERVER ERROR")
})
@Api(tags = {"팔로우 정보를 제공하는 Controller"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/following")
class FollowController {

    private final FollowService followService;

    /**
     * 팔로우 걸기
     */
    @ApiOperation(value = "팔로우 성공", notes="다른 유저에게 팔로우를 합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toUserId", dataType = "Long", value = "팔로우 받는 유저 PK", paramType = "path"),
            @ApiImplicitParam(name = "fromUserId", dataType = "Long", value = "팔로우 하는 유저 PK", paramType = "path")
    })
    @PostMapping("/follow/{toUserId}/{fromUserId}")
    public ResponseEntity<?> follow(@PathVariable Long toUserId, @PathVariable Long fromUserId) {
        followService.follow(toUserId, fromUserId);
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("팔로우 성공")
                .build(), HttpStatus.OK);
    }

    /**
     * 팔로우 취소
     */
    @ApiOperation(value = "팔로우 취소", notes="다른 유저에게 건 팔로우를 취소합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "toUserId", dataType = "Long", value = "팔로우 받는 유저 PK", paramType = "path"),
            @ApiImplicitParam(name = "fromUserId", dataType = "Long", value = "팔로우 하는 유저 PK", paramType = "path")
    })
    @DeleteMapping("/follow/{toUserId}/{fromUserId}")
    public ResponseEntity<?> unfollow(@PathVariable Long toUserId, @PathVariable Long fromUserId){
        followService.unFollow(toUserId, fromUserId);
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("언팔로우 성공")
                .build(), HttpStatus.OK);
    }


    /**
     * 팔로워 리스트
     */
    @ApiOperation(value = "팔로워 리스트", notes="팔로워 리스트를 확인합니다.")
    @ApiImplicitParam(name = "toUserId", dataType = "Long", value = "팔로우 받는 유저 PK", paramType = "path")
    @GetMapping("/followerList/{toUserId}")
    public ResponseEntity<?> followerList(@PathVariable Long toUserId){
        List<FollowerListResponseDto> followerListResponseDtoList = followService.followerList(toUserId);
        return new ResponseEntity<>(new CMRespDto<>(200, "팔로워리스트 조회", followerListResponseDtoList), HttpStatus.OK);
    }

    /**
     * 팔로잉 리스트
     */
    @ApiOperation(value = "팔로잉 리스트", notes="팔로잉 리스트를 확인합니다.")
    @ApiImplicitParam(name = "fromUserId", dataType = "Long", value = "팔로우 하는 유저 PK", paramType = "path")
    @GetMapping("/followingList/{fromUserId}")
    public ResponseEntity<?>followingList(@PathVariable Long fromUserId){
        List<FollowingListResponseDto> followingListResponseDtoList = followService.followingList(fromUserId);
        return new ResponseEntity<>(new CMRespDto<>(200, "팔로잉리스트 조회", followingListResponseDtoList), HttpStatus.OK);
    }


}
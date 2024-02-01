package com.ssafy.sgdc.follow;

import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.base.dto.ResponseDto;
import com.ssafy.sgdc.follow.dto.FollowCountDto;
import com.ssafy.sgdc.follow.dto.FollowerListResponseDto;
import com.ssafy.sgdc.follow.dto.FollowingListResponseDto;
import com.ssafy.sgdc.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;


@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "500", description = "SERVER ERROR")
})
@Tag(name="팔로우 API", description = "팔로우 정보를 제공하는 Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/follow")
class FollowController {

    private final FollowService followService;

    /**
     * 팔로우 걸기
     */
    @Operation(summary = "팔로우 성공", description="다른 유저에게 팔로우를 합니다.")
    @Parameters({
            @Parameter(name = "toUserId", schema=@Schema(implementation = int.class), description = "팔로우 받는 유저 PK", in = ParameterIn.PATH),
            @Parameter(name = "fromUserId",  schema=@Schema(implementation = int.class), description = "팔로우 하는 유저 PK", in = ParameterIn.PATH)
    })
    @PostMapping("/{toUserId}/{fromUserId}")
    public DataResponseDto<Objects> follow(@PathVariable int toUserId, @PathVariable int fromUserId) {
            followService.follow(toUserId, fromUserId);
            return DataResponseDto.of(null, "팔로우 성공");
    }

    /**
     * 팔로우 취소
     */
    @Operation(summary= "팔로우 취소", description="다른 유저에게 건 팔로우를 취소합니다.")
    @Parameters({
            @Parameter(name = "toUserId", schema = @Schema(implementation = int.class), description = "팔로우 받는 유저 PK", in = ParameterIn.PATH),
            @Parameter(name = "fromUserId", schema = @Schema(implementation = int.class), description = "팔로우 하는 유저 PK", in = ParameterIn.PATH)
    })
    @DeleteMapping("/{toUserId}/{fromUserId}")
    public DataResponseDto<Objects> unfollow(@PathVariable int toUserId, @PathVariable int fromUserId){
        followService.unFollow(toUserId, fromUserId);
        return DataResponseDto.of(null, "팔로우 취소");
    }


    /**
     * 팔로워 리스트
     */
    @Operation(summary = "팔로워 리스트", description="팔로워 리스트를 확인합니다.")
    @Parameter(name = "toUserId", schema = @Schema(implementation = int.class), description = "팔로우 받는 유저 PK", in = ParameterIn.PATH)
    @GetMapping("/follower-list/{toUserId}")
    public DataResponseDto<?> followerList(@PathVariable int toUserId) {
        List<FollowerListResponseDto> followerListResponseDtoList = followService.followerList(toUserId);
        return DataResponseDto.of(followerListResponseDtoList, "팔로워리스트 조회");
    }

    /**
     * 팔로잉 리스트
     */
    @Operation(summary = "팔로잉 리스트", description="팔로잉 리스트를 확인합니다.")
    @Parameter(name = "fromUserId", schema = @Schema(implementation = int.class), description = "팔로우 하는 유저 PK", in = ParameterIn.PATH)
    @GetMapping("/following-list/{fromUserId}")
    public DataResponseDto<?> followingList(@PathVariable int fromUserId){
        List<FollowingListResponseDto> followingListResponseDtoList = followService.followingList(fromUserId);
        return DataResponseDto.of(followingListResponseDtoList, "팔로잉리스트 조회");
    }


    /**
     * 팔로잉 팔로워 수 조회
     */
    @Operation(summary = "팔로잉 팔로워 수", description="팔로잉 팔로워 수를 확입합니다.")
    @Parameter(name = "userId", schema = @Schema(implementation = int.class), description = "유저 본인의 PK", in = ParameterIn.PATH)
    @GetMapping("/follow-count/{userId}")
    public DataResponseDto<?> followCount(@PathVariable User userId){
        int following = followService.followingCount(userId);
        int follower = followService.followerCount(userId);
        return DataResponseDto.of(new FollowCountDto(follower,following), "팔로잉 팔로워 수 조회");
    }

    /**
     * 사용자가 팔로잉 했는지 조회
     */
    @Operation(summary = "사용자가 팔로잉 했는지 조회", description="사용자가 팔로잉한 사용자인지 확인합니다.")
    @Parameters({
            @Parameter(name = "toUserId", schema = @Schema(implementation = int.class), description = "팔로우 받는 유저 PK", in = ParameterIn.PATH),
            @Parameter(name = "fromUserId", schema = @Schema(implementation = int.class), description = "팔로우 하는 유저 PK", in = ParameterIn.PATH)
    })
    @GetMapping("/follow-check/{userId}/{followingId}")
    public ResponseDto isFollow(@PathVariable User userId, @PathVariable User followingId){
        boolean follow = followService.isFollow(followingId,userId);
        return new ResponseDto(follow,0,"팔로잉 체크");
    }
}
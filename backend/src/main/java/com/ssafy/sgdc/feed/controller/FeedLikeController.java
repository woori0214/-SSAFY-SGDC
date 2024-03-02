package com.ssafy.sgdc.feed.controller;

import com.ssafy.sgdc.base.constant.Code;
import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.base.dto.ResponseDto;
import com.ssafy.sgdc.feed.service.FeedLikeService;
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

import java.util.Objects;

@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "500", description = "SERVER ERROR")
})
@Tag(name="피드 좋아요 API", description = "피드 좋아요 정보를 제공하는 Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/feed")
public class FeedLikeController {

    private final FeedLikeService feedLikeService;

    /**
     * 피드 좋아요 생성
     */
    @Operation(summary = "피드 좋아요 기능", description="특정 피드에 좋아요 표시")
    @Parameters({
            @Parameter(name = "userId",  schema=@Schema(implementation = int.class), description = "좋아요 하는 사용자 PK", in = ParameterIn.PATH),
            @Parameter(name = "feedId", schema=@Schema(implementation = int.class), description = "좋아요 하는 피드 PK", in = ParameterIn.PATH)
    })
    @PostMapping("/feed-like/{feedId}/{userId}")
    public ResponseDto toggleLike(@PathVariable int userId, @PathVariable int feedId) {
        return ResponseDto.of(true, Code.OK, feedLikeService.toggleLike(userId, feedId));
    }

}

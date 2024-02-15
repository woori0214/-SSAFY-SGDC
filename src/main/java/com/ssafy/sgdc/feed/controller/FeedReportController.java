package com.ssafy.sgdc.feed.controller;

import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.feed.entity.FeedReport;
import com.ssafy.sgdc.feed.service.FeedReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "500", description = "SERVER ERROR")
})
@Tag(name="피드 신고하기 API", description = "피드 신고하기 정보를 제공하는 Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/feed-report")
public class FeedReportController {

    private final FeedReportService feedReportService;

    /**
     * 피드 게시물 신고
     */
    @Operation(summary = "피드 신고하기 기능", description="특정 피드 신고")
    @Parameters({
            @Parameter(name = "userId",  schema=@Schema(implementation = int.class), description = "신고하는 사용자 PK"),
            @Parameter(name = "reportUserId",  schema=@Schema(implementation = int.class), description = "신고당한 사용자 PK"),
            @Parameter(name = "feedId", schema=@Schema(implementation = int.class), description = "신고하는 하는 피드 PK"),
            @Parameter(name = "reportDetail", schema=@Schema(implementation = String.class), description = "신고 사유"),
    })
    @PostMapping("/report")
    public DataResponseDto<FeedReport> reportFeed(
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "feedId") int feedId,
            @RequestParam(value = "reportUserId") int reportUserId,
            @RequestParam(value = "reportDetail") String reportDetail
            ) {
        return DataResponseDto.of(feedReportService.createFeedReport(userId,reportUserId,feedId,reportDetail),"피드 신고 성공");
    }
}

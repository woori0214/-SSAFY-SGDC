package com.ssafy.sgdc.feed.controller;


import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.feed.dto.FeedOneDto;
import com.ssafy.sgdc.feed.dto.UpdateViewDto;
import com.ssafy.sgdc.feed.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "500", description = "SERVER ERROR")
})
@Tag(name="피드 API", description = "피드 정보를 제공하는 Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/feed")
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "특정 피드 조회", description="특정한 피드를 확인합니다.")
    @Parameter(name = "feedId", schema = @Schema(implementation = int.class), description = "특정 피드의 PK", in = ParameterIn.PATH)
    @GetMapping("/feed-info/{feedId}/{userId}")
    public DataResponseDto<FeedOneDto> getFeedOne(@PathVariable int feedId, @PathVariable int userId) {
        FeedOneDto feedData = feedService.getFeedOne(feedId,userId);
        return DataResponseDto.of(feedData, "특정 피드 조회");
    }

    @Operation(summary = "피드 10개씩 조회", description="피드를 10개씩 확인합니다.")
    @Parameters({
            @Parameter(name = "page", schema = @Schema(implementation = int.class), description = "기준 페이지"),
            @Parameter(name = "size", schema = @Schema(implementation = int.class), description = "가져올 페이지 수")
    })
    @GetMapping("/feed-list/pages")
    public DataResponseDto<Page<FeedOneDto>> getFeeds(
            @RequestParam(value="userId") int userId,
            @RequestParam(defaultValue = "0") int feedId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        return DataResponseDto.of(feedService.findItemsAfter(feedId, userId, pageable),"피드 10개씩 조회");
    }


    @Operation(summary = "피드 조회수 업데이트", description="피드의 조회수를 업데이트합니다.")
    @Parameter(name = "feedId", schema = @Schema(implementation = int.class), description = "특정 피드의 PK", in = ParameterIn.PATH)
    @PatchMapping("/feed-views/{feedId}")
    public DataResponseDto<?> follow(@PathVariable int feedId) {
        UpdateViewDto updateViewsData = new UpdateViewDto(feedService.updateView(feedId));
        return DataResponseDto.of(updateViewsData, "조회수 증가");
    }


}

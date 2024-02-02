package com.ssafy.sgdc.competition.controller;


import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.competition.service.ImageAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "500", description = "SERVER ERROR")
})
@Tag(name="사진 인증 API", description = "사진 인증 정보를 제공하는 Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("api/v1/image-auth")
public class ImageAuthController {

    private final ImageAuthService imageAuthService;


    /**
     * 사진 인증
     */

    @Operation(summary = "업로드 성공", description = "챌린지 인증 사진을 업로드 합니다.")
    @Parameters({
            @Parameter(name = "userId", schema = @Schema(implementation = Integer.class), description = "유저 PK"),
            @Parameter(name = "competId", schema = @Schema(implementation = Integer.class), description = "유저 경쟁 PK"),
            @Parameter(name = "authImg", schema = @Schema(implementation = MultipartFile.class), description = "업로드 하는 이미지 파일")
    })
    @PostMapping("/upload")
    public DataResponseDto<String> imageAuth(
            @RequestParam("user_id") int userId,
            @RequestParam("compet_id") int competId,
            @RequestParam("auth_img") MultipartFile authImg
    ) {
        String imageUrl = imageAuthService.saveImageAuth(userId,
                competId, authImg);
        return DataResponseDto.of(imageUrl, "이미지 업로드 성공");
    }
}


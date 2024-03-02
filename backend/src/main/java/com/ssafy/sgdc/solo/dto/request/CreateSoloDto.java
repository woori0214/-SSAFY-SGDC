package com.ssafy.sgdc.solo.dto.request;

import com.ssafy.sgdc.category.Category;
import com.ssafy.sgdc.enums.SoloResult;
import com.ssafy.sgdc.enums.SoloStatus;
import com.ssafy.sgdc.solo.domain.Solo;
import com.ssafy.sgdc.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateSoloDto {

    private LocalDateTime createAt;
    private SoloStatus soloStatus;
    private SoloResult soloResult;
    private Category category;
    private User user;

    public static Solo from(CreateSoloDto request) {
        return new Solo(
                request.getCreateAt(),
                request.getSoloStatus(),
                request.getSoloResult(),
                request.getCategory(),
                request.getUser()
        );
    }


}

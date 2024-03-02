package com.ssafy.sgdc.competition.dto.request;

import com.ssafy.sgdc.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionProgressDetailDto {

    private int compet_id;
    private int category_id;
    private int user_auth_id;
    private String user_auth_image;
    private int user_id;
    private String user_img;
    private String user_nickname;
    private int other_auth_id;
    private String other_auth_image;
    private int other_user_id;
    private String other_user_img;
    private String other_user_nickname;

    public static CompetitionProgressDetailDto of(int compet_id, int category_id, int user_auth_id,
                                                  String user_auth_image, int user_id, String user_img,
                                                  String user_nickname, int other_auth_id, String other_auth_image,
                                                  int other_user_id, String other_user_img, String other_user_nickname) {
        return new CompetitionProgressDetailDto(
                compet_id, category_id, user_auth_id,
                user_auth_image, user_id, user_img,
                user_nickname, other_auth_id, other_auth_image,
                other_user_id, other_user_img, other_user_nickname
        );
    }
}

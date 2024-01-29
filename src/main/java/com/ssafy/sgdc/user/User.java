package com.ssafy.sgdc.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "user_id")
    private long userId;

    @Column(name = "login_id", length = 20)
    private String loginId;

    @Column(name = "user_ssafy_id")
    private int userSsafyId;

    @Column(name = "user_email", length = 20)
    private String userEmail;

    @Column(name = "user_nickname", length = 20)
    private String userNickname;

    @Column(name = "user_name", length = 20)
    private String userName;

    @Column(name = "user_phone", length = 20)
    private String userPhone;

    @Column(name = "user_password", length = 250)
    private String userPassword;

    @Column(name = "user_img", length = 200)
    private String userImg;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "sign_out")
    private Boolean signOut;

//    @OneToOne
//    @JoinColumn(name = "badge_id")
    @Column(name = "badge_id")
    private long badgeId;

    @Column(name = "kakao_push")
    private Boolean kakaoPush;

    @Column(name = "challenge_cnt")
    private int challengeCnt;

    @Column(name = "complain_cnt")
    private int complainCnt;

    @Column(name = "token")
    private String token;
}
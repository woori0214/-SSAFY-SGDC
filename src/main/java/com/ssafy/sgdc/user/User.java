package com.ssafy.sgdc.user;

import com.ssafy.sgdc.badge.Badge;
import com.ssafy.sgdc.badge.UserBadge;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "user_id")
    private int userId;

    @Column(name = "login_id", unique = true, length = 20)
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

    @Column(name = "kakao_push")
    private Boolean kakaoPush;

    @Column(name = "signout")
    private Boolean signOut;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private UserBadge badgeId;

    @Column(name = "challenge_cnt")
    private int challengeCnt;

    @Column(name = "complain_cnt")
    private int complainCnt;

    @Column(name = "token")
    private String token;

    @Column(name = "auth")
    private String auth;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return roles;
    }
}
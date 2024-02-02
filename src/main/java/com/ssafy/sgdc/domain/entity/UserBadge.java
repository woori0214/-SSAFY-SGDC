package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_badge")
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "user_badge_id")
    private int userBadgeId;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badgeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}

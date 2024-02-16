package com.ssafy.sgdc.domain.entity;

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
    private long userBadgeId;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badgeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}

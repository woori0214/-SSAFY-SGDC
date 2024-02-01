package com.ssafy.sgdc.competition.domain;

import com.ssafy.sgdc.domain.entity.Category;
import com.ssafy.sgdc.enums.CompetKind;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.MatchStatus;
import com.ssafy.sgdc.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matching")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "matching_id")
    private int matchingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @Setter
    @JoinColumn(name = "compet_id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "compet_kind")
    @Enumerated(EnumType.STRING)
    private CompetKind competKind;

    @Column(name = "is_sender")
    @Enumerated(EnumType.STRING)
    private IsSender isSender;

    @Column(name = "compet_expiration_time")
    private LocalDateTime competExpirationTime;

    @Setter
    @Column(name = "match_status")
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    public Matching(User user, Category category, CompetKind competKind, IsSender isSender, LocalDateTime competExpirationTime, MatchStatus matchStatus) {
        this.user = user;
        this.category = category;
        this.competKind = competKind;
        this.isSender = isSender;
        this.competExpirationTime = competExpirationTime;
        this.matchStatus = matchStatus;
    }

}
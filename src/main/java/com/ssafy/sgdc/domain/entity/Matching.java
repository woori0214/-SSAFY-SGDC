package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.domain.entity.enums.CompetKind;
import com.ssafy.sgdc.domain.entity.enums.IsSender;
import com.ssafy.sgdc.domain.entity.enums.MatchStatus;
import com.ssafy.sgdc.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matching")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "matching_id")
    private Long matchingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
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

    @Column(name = "match_status")
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

}
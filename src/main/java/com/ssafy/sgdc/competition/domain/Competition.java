package com.ssafy.sgdc.competition.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competition")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "compet_id")
    private int competId;

    @OneToOne
    @JoinColumn(name = "compet_detail_id")
    private CompetDetail competDetail;

    private LocalDateTime createAt;

    private LocalDateTime doneAt;

    public static Competition of(LocalDateTime now, LocalDateTime doneAt) {
        return new Competition(now, doneAt, null);
    }

    private Competition(LocalDateTime createAt, LocalDateTime doneAt, CompetDetail competDetail) {
        this.competDetail = competDetail;
        this.createAt = createAt;
        this.doneAt = doneAt;
    }

    public void updateCompetitionDetail(CompetDetail competDetail) {
        this.competDetail = competDetail;
    }

    public void updateCompetitionDoneAt(LocalDateTime doneAt) {
        this.doneAt = doneAt;
    }

}
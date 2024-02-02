package com.ssafy.sgdc.domain.entity;

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

}
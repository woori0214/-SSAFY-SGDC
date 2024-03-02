package com.ssafy.sgdc.competition.domain;

import com.ssafy.sgdc.enums.CompetResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "compet_detail")
public class CompetDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "compet_result_id")
    private int competResultId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "compet_result")
    private CompetResult competResult;

    public CompetDetail(CompetResult competResult) {
        this.competResult = competResult;
    }
}
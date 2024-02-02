package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.domain.entity.enums.CompetResult;
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
@Table(name = "compet_detail")
public class CompetDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "compet_result_id")
    private int competResultId;

    @Enumerated(EnumType.STRING)
    @Column(name = "compet_result")
    private CompetResult competResult;

}
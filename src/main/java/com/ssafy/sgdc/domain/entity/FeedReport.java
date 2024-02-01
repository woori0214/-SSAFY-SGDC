package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.enums.ComplainType;
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
@Table(name = "feed_report")
public class FeedReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "feed_report_id")
    private int feedReportId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feedId;

    @Column(name = "complain_type")
    @Enumerated(EnumType.STRING)
    private ComplainType complainType;

}

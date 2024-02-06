package com.ssafy.sgdc.badge;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "badge")
public class Badge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "badge_id")
    private int badgeId;

    @Column(name = "badge_name", length = 20)
    private String badgeName;

    @Column(name = "badge_detail", length = 200)
    private String badgeDetail;

    @Column(name = "badge_img", length = 200)
    private String badgeImg;

}

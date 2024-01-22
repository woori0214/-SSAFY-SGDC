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
@Table(name = "image_auth")
public class ImageAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "auth_img")
    private String authImg;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "compet_id")
    private Competition competId;

    @OneToOne
    @JoinColumn(name = "matcing_id")
    private Matching matcingId;

}
package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.category.Category;
import com.ssafy.sgdc.enums.SoloResult;
import com.ssafy.sgdc.enums.SoloStatus;
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
@Table(name = "solo")
public class Solo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "solo_id")
    private int soloId;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "solo_status")
    @Enumerated(EnumType.STRING)
    private SoloStatus soloStatus;

    @Column(name = "solo_result")
    @Enumerated(EnumType.STRING)
    private SoloResult soloResult;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Column(name = "solo_auth_img", length = 200)
    private String soloAuthImg;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}

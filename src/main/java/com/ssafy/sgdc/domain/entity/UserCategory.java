package com.ssafy.sgdc.domain.entity;

import com.ssafy.sgdc.domain.entity.enums.CategoryStatus;
import com.ssafy.sgdc.user.model.User;
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
@Table(name = "user_category")

public class UserCategory {

    @Id
    @Column(name = "user_category_id")
    private long userCategoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Column(name = "category_win_cnt")
    private int categoryWinCnt;

    @Column(name = "category_fail_cnt")
    private int categoryFailCnt;

    @Column(name = "category_status")
    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

}

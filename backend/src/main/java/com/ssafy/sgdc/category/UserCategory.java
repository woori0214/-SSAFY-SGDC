package com.ssafy.sgdc.category;

import com.ssafy.sgdc.category.Category;
import com.ssafy.sgdc.enums.CategoryStatus;
import com.ssafy.sgdc.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_category")
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_id")
    private int userCategoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "category_win_cnt")
    private int categoryWinCnt;

    @Column(name = "category_fail_cnt")
    private int categoryFailCnt;

    @Column(name = "category_status")
    @Enumerated(EnumType.STRING)
    private CategoryStatus categoryStatus;

    public UserCategory(User user, Category category, int categoryWinCnt, int categoryFailCnt, CategoryStatus categoryStatus) {
        this.user = user;
        this.category = category;
        this.categoryWinCnt = categoryWinCnt;
        this.categoryFailCnt = categoryFailCnt;
        this.categoryStatus = categoryStatus;
    }

    public void increaseWinCount() {
        this.categoryWinCnt += 1;
    }

    public void increaseFailCount() {
        this.categoryFailCnt += 1;
    }

    public void updateCategoryStatus(CategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

}

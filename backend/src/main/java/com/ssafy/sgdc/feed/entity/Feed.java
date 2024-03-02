package com.ssafy.sgdc.feed.entity;

import com.ssafy.sgdc.competition.domain.Competition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "feed_id")
    private int feedId;

    @Column(name = "feed_title", length = 200)
    private String feedTitle;

    @Column(name = "feed_content", length = 200)
    private String feedContent;

    @Column(name = "feed_like_num")
    private int feedLikeNum;

    @Column(name = "views")
    private int views;

    @Column(name = "privacy")
    private boolean privacy;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "feed_img", length = 200)
    private String feedImg;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compet_id")
    private Competition competId;

    private Feed(String feedTitle, String feedContent, int feedLikeNum, int views, boolean privacy, LocalDateTime createAt, LocalDateTime updateAt, String feedImg, Competition competId) {
        this.feedTitle = feedTitle;
        this.feedContent = feedContent;
        this.feedLikeNum = feedLikeNum;
        this.views = views;
        this.privacy = privacy;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.feedImg = feedImg;
        this.competId = competId;
    }

    public static Feed of(String feedTitle,
                          String feedContent,
                          int feedLikeNum,
                          int views,
                          boolean privacy,
                          LocalDateTime createAt,
                          LocalDateTime updateAt,
                          String feedImg,
                          Competition competId
                          )
    { return new Feed(feedTitle, feedContent, feedLikeNum, views, privacy, createAt, updateAt, feedImg, competId); }

}

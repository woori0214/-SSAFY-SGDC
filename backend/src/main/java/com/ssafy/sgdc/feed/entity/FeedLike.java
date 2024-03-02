package com.ssafy.sgdc.feed.entity;


import com.ssafy.sgdc.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_like")
public class FeedLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_like_id")
    private int feedLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    private FeedLike(User user, Feed feed) {
        this.user=user;
        this.feed=feed;
    }

    public static FeedLike of(User user, Feed feed)
    { return new FeedLike(user, feed);}


}

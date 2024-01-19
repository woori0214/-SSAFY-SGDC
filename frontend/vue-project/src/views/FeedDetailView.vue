<template>
  <div class="feed_frame">
    <div class="feed_profile">
      <div class="user_profile">
        <img :src="feedImage_d" class="feed_profile_image" />
        <img :src="feedImage_d" class="feed_profile_badge" />
        <div class="feed_user_name">{{ userName_d }}</div>
      </div>
      <button>신고하기</button>
    </div>
    <div class="feed_content">{{ content_d }}</div>
    <div class="feed_image_frame" @click="routeDetailFeed">
      <img :src="feedImage_d" class="feed_detail_image" />
    </div>
    <div class="feed_footbar">
      <div class="feed_heart_cnt">좋아요 {{ heartCnt_d }}</div>
      <div class="feed_view_cnt">조회수 {{ viewCnt_d }}</div>
    </div>
  </div>
</template>
  
  <script>
export default {
  name: "feed-detail",
  props: {
    userName_d: {
      type: String,
      default: "XXX",
    },
    content_d: {
      type: String,
      default: "content",
    },
    feedImage_d: {
      type: String,
      default: "/src/components/Feed/FeedImage/no_image_logo.png",
    },
    heartCnt_d: {
      type: String,
      default: "heartCnt",
    },
    viewCnt_d: {
      type: String,
      default: "viewCnt",
    },
  },
  methods: {
    routeDetailFeed() {
      this.$router.push({
        path: "/",
      });
    },
  },
};
</script>

<style>
.feed_detail_image{
  width: 70vw;
  height: 42vh;
}
</style>
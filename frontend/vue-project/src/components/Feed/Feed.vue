<template>
  <div class="feed_frame">
    <div class="feed_profile">
      <div class="user_profile">
        <img :src="feedImage" class="feed_profile_image" />
        <img :src="feedImage" class="feed_profile_badge" />
        <div class="feed_user_name">{{ userName }}</div>
      </div>
      <button>신고하기</button>
    </div>
    <div class="feed_content">{{ content }}</div>
    <div class="feed_image_frame" @click="routeDetailFeed(

    )">
      <img :src="feedImage" class="feed_image" />
    </div>
    <div class="feed_footbar">
      <div class="feed_heart_cnt">좋아요 {{ heartCnt }}</div>
      <div class="feed_view_cnt">조회수 {{ viewCnt }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "feed_item",
  props: {
    userName: {
      type: String,
      default: "XXX",
    },
    content: {
      type: String,
      default: "content",
    },
    feedImage: {
      type: String,
      default: "/src/components/Feed/FeedImage/no_image_logo.png",
    },
    heartCnt: {
      type: String,
      default: "heartCnt",
    },
    viewCnt: {
      type: String,
      default: "viewCnt",
    },
  },
  methods: {
    routeDetailFeed() {
      this.$router.push({
        name: "FeedDetail",
        params: {
          userName_d: this.userName,
          content_d: this.content,
          feedImage_d: this.feedImage,
          heartCnt_d: this.heartCnt,
          viewCnt_d: this.viewCnt,
        }
      });
    },
  },
};
</script>

<style>
.feed_frame {
  /* border: 2px solid skyblue; */
}
.feed_profile {
  /* border: 2px solid skyblue; */
  display: flex;
  justify-content: space-between;
}
.user_profile{
    display: flex;
    gap: 10px;
}
.feed_profile_image {
  border: 2px solid gray;
  border-radius: 100%;
  width: 50px;
  height: 50px;
}
.feed_profile_badge {
  width: 50px;
  height: 50px;
}
.feed_user_name {
  /* border: 2px solid purple; */
  display: flex;
  align-items: center;
}
.feed_content {
  /* border: 2px solid purple; */
}
.feed_image_frame {
  border: 2px solid whitesmoke;
  border-radius: 30px;
  background-color: azure;
  display: flex;
  justify-content: center;
  margin: 10px;
  padding: 20px;
}
.feed_image {
  /* border: 2px solid blue; */
  max-width: 300px;
  max-height: 500px;
}
.feed_footbar {
  /* border: 2px solid purple; */
  display: flex;
  justify-content: space-between;
}
.feed_heart_cnt {
  /* border: 2px solid blue; */
}
.feed_view_cnt {
  /* border: 2px solid blue; */
}
</style>
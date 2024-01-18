<template>
  <div class="feed_frame">
    <div class="feed_user_name">{{ userName }}</div>
    <div class="feed_content">{{ content }}</div>
    <div class="feed_image_frame" @click="routeDetailFeed()">
      <img :src="feedImage" class="feed_image" />
    </div>
    <div class="feed_footbar">
      <div class="feed_heart_cnt">{{ heartCnt }}</div>
      <div class="feed_view_cnt">{{ viewCnt }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "feed-item",
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
      default: "@/FeedImage/search_mushimegane.png",
    },
    heartCnt: {
      type: Number,
      default: "heartCnt",
    },
    viewCnt: {
      type: Number,
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
.feed_frame {
  border: 2px solid skyblue;
}
.feed_user_name {
  /* border: 2px solid purple; */
}
.feed_content {
  /* border: 2px solid purple; */
}
.feed_image_frame {
  /* border: 2px solid purple; */
  display: flex;
  justify-content: center;
}
.feed_image {
  /* border: 2px solid blue; */
  width: 150px;
}
.feed_footbar {
  /* border: 2px solid purple; */
  display: flex;
}
.feed_heart_cnt {
  /* border: 2px solid blue; */
  margin-right: 10px;
}
.feed_view_cnt {
  /* border: 2px solid blue; */
}
</style>
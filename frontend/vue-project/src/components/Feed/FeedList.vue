<template>
  <div>
    <div>피드 리스트</div>
    <div class="feed_container">
      <Feed
        class="feed"
        v-for="(item, index) in feeds"
        :key="index"
        :userName="item.userName"
        :content="item.content"
        :feedImage="item.feedImage"
        :heartCnt="item.heartCnt"
        :viewCnt="item.viewCnt"
      ></Feed>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import Feed from "@/components/Feed/Feed.vue";

const feeds = ref([
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/ai_search.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/jitensya_kuma.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage:
      "/src/components/Feed/FeedImage/KakaoTalk_20240112_103126424.jpg",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/kyosyu_smartphone_man.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/kyosyu_smartphone_woman.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/search_mushimegane.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/ai_search.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/jitensya_kuma.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage:
      "/src/components/Feed/FeedImage/KakaoTalk_20240112_103126424.jpg",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/kyosyu_smartphone_man.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/kyosyu_smartphone_woman.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/search_mushimegane.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/ai_search.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/jitensya_kuma.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage:
      "/src/components/Feed/FeedImage/KakaoTalk_20240112_103126424.jpg",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/kyosyu_smartphone_man.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/kyosyu_smartphone_woman.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
  {
    // userImage : "./..",
    userName: "김메달",
    // userBadge: "./..",
    content: "[카테고리]를 [12:43]에 성공 하였습니다!",
    feedImage: "/src/components/Feed/FeedImage/search_mushimegane.png",
    heartCnt: 1520,
    viewCnt: 130,
  },
]);
</script>

<style scoped>
.feed_container {
  display: flex;
  flex-direction: column;
  gap: 30px;
  margin: 20px;
}
</style>
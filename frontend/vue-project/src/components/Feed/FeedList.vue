<template>
  <div>
    <!-- <div>피드 리스트</div> -->
    <div class="feed_container" ref="feedContainer">
      <Feed
        class="feed"
        v-for="(item, index) in init_feeds"
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
import { ref, onMounted, onUnmounted } from "vue";
import Feed from "@/components/Feed/Feed.vue";
import { useFeedStore } from "@/stores/feed";

const feedContainer = ref(null);

const init_feeds = ref([
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
    // feedImage:
    //   "/src/components/Feed/FeedImage/KakaoTalk_20240112_103126424.jpg",
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

// 피드 무한 스크롤 테스트를 위한 더미 데이터
const plus_feeds = ref([
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
    // feedImage:
    //   "/src/components/Feed/FeedImage/KakaoTalk_20240112_103126424.jpg",
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
]);

const feeds = ref([]);

const feedjs = useFeedStore();

onMounted(() => {
  //Feed List 스크롤 핸들러
  if (feedContainer.value) {
    feedContainer.value.addEventListener("scroll", handleScroll);
  }
  // 피드 리스트 init
  resetFeedList();
});

onUnmounted(() => {
  //Feed List 스크롤 핸들러
  if (feedContainer.value) {
    feedContainer.value.removeEventListener("scroll", handleScroll);
  }
});

// 피드 조회할 함수 << onMounted
const resetFeedList = () => {
  feedjs
    .getFeedList()
    .then((res) => {
      feeds.value = [...res.data];

      console.log("feed 데이터가 초기화 되었습니다.");
      console.log(feeds.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 피드 추가 요청할 함수
const moreFeedList = (page) => {
  feedjs
    .getFeedListPage(page)
    .then((res) => {
      feeds.value = [...feeds.value, ...res.data];
      console.log("feed 데이터가 추가되었습니다.");
      console.log(feeds.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

// 현재 피드 리스트에 저장된 양이 넘칠 경우 FIFO할 함수
const deleteFeedItem = () => {
  if (feeds.value.length > 10) {
    feeds.value.splice(0, 10);
  } else {
    feeds.value.splice(0, feeds.value.length);
  }
};

//피드 추가 테스트 함수(무한 스크롤)
const handleScroll = () => {
  const { scrollTop, scrollHeight, clientHeight } = feedContainer.value;
  if (scrollTop + clientHeight >= scrollHeight - 10) {
    // 스크롤이 맨 아래에 도달했을 때
    console.log("스크롤 맨 아래에 도착");
    plusFeedItem();
  }
};

const plusFeedItem = () => {
  init_feeds.value = [...init_feeds.value, ...plus_feeds.value];
  console.log("피드 추가 테스트");

  if (init_feeds.value.length > 30) {
    init_feeds.value.splice(0, 10);
    console.log("피드 넘쳐서 삭제함");
  }
};
//
</script>

<style scoped>
.feed_container {
  display: flex;
  flex-direction: column;
  gap: 50px;
  margin-inline: 20px;
  background-color: #e1ecf7;
  border-radius: 15px;
  height: calc(87vh - 180px);
  overflow: auto;
}
.feed_frame.feed{
  margin-bottom: 80px;
}

</style>
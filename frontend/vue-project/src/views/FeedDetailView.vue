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
    <div class="feed_image_frame">
      <img :src="feedImage_d" class="feed_detail_image" />
    </div>
    <div class="feed_footbar">
      <div class="feed_heart_cnt">
        <button @click="pushFeedLike">♥</button>
        {{ heartCnt_d }}
      </div>
      <div class="feed_view_cnt">조회수 {{ viewCnt_d }}</div>
    </div>
  </div>
</template>
  
<script>
import { onMounted, ref } from "vue";
import { useFeedStore } from "@/stores/feed";
import { useUserStorageStore } from "@/stores/userStorage";

export default {
  name: "feed-detail",
  props: {
    feed_id: {
      type: Number,
      default: 0,
    },
    // userName_d: {
    //   type: String,
    //   default: "XXX",
    // },
    // content_d: {
    //   type: String,
    //   default: "content",
    // },
    // feedImage_d: {
    //   type: String,
    //   default: "/src/components/Feed/FeedImage/no_image_logo.png",
    // },
    // heartCnt_d: {
    //   type: String,
    //   default: "heartCnt",
    // },
    // viewCnt_d: {
    //   type: String,
    //   default: "viewCnt",
    // },
  },
  setup(props) {
    const feedData = ref();
    const userName_d = ref("XXX");
    const content_d = ref("content");
    const feedImage_d = ref("/src/components/Feed/FeedImage/no_image_logo.png");
    const heartCnt_d = ref("heartCnt");
    const viewCnt_d = ref("viewCnt");

    const feedjs = useFeedStore();
    const userStorage = useUserStorageStore();

    // 피드 넘어올 때 조회수 업데이트 함수
    const patchViewCnt = () => {
      feedjs
        .updateFeedView(props.feed_id)
        .then((res) => {
          console.log("feed 조회수를 추가하였습니다.");
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    // 피드 정보 가져오는 함수
    const getFeedDetail = () => {
      feedjs
        .getFeed(props.feed_id)
        .then((res) => {
          feedData.value = res.data;
          userName_d = res.data.content_d = res.data.content;
          feedImage_d = res.data.feed_img;
          heartCnt_d = res.data.feed_like_num;
          viewCnt_d = res.data.views;

          console.log("feed 정보를 받았습니다.");
          console.log(feedData.value);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    // 피드 좋아요 누르기 함수
    const pushFeedLike = () => {
      alert("좋아요 누름" + props.feed_id);

      feedjs
        .updateFeedLike(props.feed_id)
        .then((res) => {
          console.log("feed 좋아요 누름");

          const userData = userStorage.getUserInformation();

          feedjs
            .addfeedLikeUser(props.feed_id, userData.user_id)
            .then((res) => {
              console.log("feed 좋아요 유저 추가");
            })
            .catch((err) => {
              console.log(err);
            });

          //증가된 좋아요 수 표시하도록 하기
        })
        .catch((err) => {
          console.log(err);
        });
    };

    onMounted(() => {
      console.log("FeedDetail onMounte 실행");
      patchViewCnt();
      getFeedDetail();
    });

    return {
      userName_d,
      content_d,
      feedImage_d,
      heartCnt_d,
      viewCnt_d,
      pushFeedLike,
      getFeedDetail,
    };
  },
};
</script>

<style>
.feed_detail_image {
  width: 70vw;
  height: 42vh;
}
</style>
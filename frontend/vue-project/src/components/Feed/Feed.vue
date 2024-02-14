<template>
  <div class="feed_frame">
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <div class="feed_frame2">
      <div class="feed_profile">
        <div class="user_profile" @click="moveProfile(userId)">
          <img :src="userImg" class="feed_profile_image" />
          <img
            v-if="userBadgeImg !== 'null'"
            :src="userBadgeImg"
            class="feed_profile_badge"
          />
          <div class="feed_user_name">{{ userNickname }}</div>
        </div>
        <button @click="handleDeclareClick" class="declare_button">
          <!-- <img
            src="@/assets/siren.png"
            alt="Declare Icon"
            class="declare_icon"
          /> -->
        </button>
      </div>
      <div class="feed_content">{{ feedTitle }}</div>
    </div>
    <div class="feed_image_frame">
      <img :src="feedImg" class="feed_image" />
    </div>
    <div class="feed_footbar">
      <div class="feed_heart_cnt">
        <button @click="pushFeedLike" class="heart_button">
          <img
            src="@/assets/favorite.svg"
            class="heart_icon_yet"
            v-if="!refIsLiked"
          />
          <img src="@/assets/heart_check.svg" class="heart_icon_done" v-else />
        </button>
        <div>
          {{ refFeedLikedNum }}
        </div>
      </div>
      <div class="feed_view_cnt">조회수 {{ views }}</div>
    </div>
    <PopUpComplaint :showModal="showComplaintBox" :close="closeComplaintBox" />
  </div>
</template>

<script>
import { useFeedStore } from "@/stores/feed";
import { useUserStorageStore } from "@/stores/userStorage";
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import fullHeart from "@/assets/fullHeart.png";
import emptyHeart from "@/assets/emptyHeart.png";
import PopUpComplaint from "../PopUp/PopUpComplaint.vue";

export default {
  name: "feed_item",
  props: {
    feedId: {
      type: Number,
      default: null,
    },
    feedTitle: {
      type: String,
      default: "XXX",
    },
    feedContent: {
      type: String,
      default: "XXX",
    },
    feedLikeNum: {
      type: Number,
      default: -1,
    },
    views: {
      type: Number,
      default: -1,
    },
    privacy: {
      type: Boolean,
      default: false,
    },
    createAt: {
      type: String,
      default: "2000-01-01T01:00:00.000001",
    },
    updateAt: {
      type: String,
      default: null,
    },
    feedImg: {
      type: String,
      default: "/src/components/Feed/FeedImage/no_image_logo.png",
    },
    userId: {
      type: Number,
      default: 0,
    },
    userNickname: {
      type: String,
      default: "NULL",
    },
    userImg: {
      type: String,
      default: "/src/components/Feed/FeedImage/no_image_logo.png",
    },
    userBadgeImg: {
      type: String,
      default: null,
    },
    isLiked: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    routeDetailFeed() {
      this.$router.push({
        name: "FeedDetail",
        params: {
          feed_id: this.feedId,
        },
      });
    },
  },
  components: {
    PopUpComplaint,
  },
  setup(props) {
    const feedjs = useFeedStore();
    const userStorage = useUserStorageStore();
    const router = useRouter();
    const showComplaintBox = ref(false);

    const refIsLiked = ref(props.isLiked);
    const refFeedLikedNum = ref(props.feedLikeNum);

    // 피드 좋아요 누르기 함수
    const pushFeedLike = () => {
      // alert("좋아요 누름" + props.feedId);

      const userData = userStorage.getUserInformation();

      feedjs
        .addfeedLikeUser(props.feedId, userData.user_id)
        .then((res) => {
          // console.log(refIsLiked.value);
          refIsLiked.value = !refIsLiked.value;

          if (refIsLiked.value) {
            // console.log("feed 좋아요 유저 추가");
          } else {
            // console.log("feed 좋아요 취소");
          }

          // console.log(refIsLiked.value);

          feedjs
            .getFeed(props.feedId, userData.user_id)
            .then((res) => {
              refFeedLikedNum.value = res.data.data.feedLikeNum;
            })
            .catch((err) => {
              console.log(err);
            });
          ////////////////////
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const handleDeclareClick = () => {
      showComplaintBox.value = true;
    };

    const closeComplaintBox = () => {
      showComplaintBox.value = false;
    };

    // 프로필페이지 이동
    const moveProfile = (userId) => {
      router.push({ name: 'MyPage', params: { userId: userId } });
    }
    return {
      pushFeedLike,
      handleDeclareClick,
      showComplaintBox,
      closeComplaintBox,
      refIsLiked,
      refFeedLikedNum,
      moveProfile,
    };
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

.user_profile {
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
.heart_button {
  width: 40px;
  height: 40px;
  margin-left: 10px;
  border-radius: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.feed_image_frame {
  border: 2px solid white;
  border-radius: 30px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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
.declare_button {
  border: 2px solid whitesmoke;
  border-radius: 10px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 32px;
  height: 32px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('@/assets/siren.png');
  background-size: contain;
  /* 이미지가 컴포넌트를 완전히 채우도록 설정 */
  background-position: center;
  /* 이미지 중앙 정렬 */
  background-repeat: no-repeat;
  transition: border 0.3s ease;
}
.declare_button:hover{
  border: 2px solid #ff5c5c;
}

.feed_footbar {
  /* border: 2px solid purple; */
  display: flex;
  justify-content: space-between;
}

.feed_heart_cnt {
  /* border: 2px solid blue; */
  display: flex;
  align-items: center;
  gap: 5px;
}

.feed_view_cnt {
  /* border: 2px solid blue; */
}

.declare_icon {
  width: 25px;
  /* Adjust the width of the image */
  height: 25px;
  /* Adjust the height of the image */
}
.feed_frame2 {
  background-color: #aecbeb;
  border-radius: 20px;
  padding: 10px;
  margin: 10px 11px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
/* .material-symbols-outlined {
  font-variation-settings:
    /* 'FILL' 0, */
/* 'wght' 400,
    'GRAD' 0,
    'opsz' 24;
  color: red; 
} */
</style>

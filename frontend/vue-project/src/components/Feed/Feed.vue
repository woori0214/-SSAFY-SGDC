<template>
  <div class="feed_frame">
    <div class="feed_frame2">
      <div class="feed_profile">
        <div class="user_profile">
          <img :src="feedImage" class="feed_profile_image" />
          <img :src="feedImage" class="feed_profile_badge" />
          <div class="feed_user_name">{{ userName }}</div>
        </div>
        <button @click="handleDeclareClick" class="declare_button">
          <img src="@/assets/siren.png" alt="Declare Icon" class="declare_icon" />
        </button>
      </div>
      <div class="feed_content">{{ content }}</div>
    </div>
    <div class="feed_image_frame" @click="routeDetailFeed()">
      <img :src="feedImage" class="feed_image" />
    </div>
    <div class="feed_footbar">
      <div class="feed_heart_cnt">
        <button @click="pushFeedLike" class="heart_button">
          <img :src="heartIcon" alt="heart Icon" class="heart_icon" />
          <!-- <span class="material-symbols-outlined">favorite</span> -->
        </button>
        {{ heartCnt }}
      </div>
      <div class="feed_view_cnt">조회수 {{ viewCnt }}</div>
    </div>
    <PopUpComplaint :showModal="showComplaintBox" :close="closeComplaintBox"/>
  </div>
</template>

<script>
import { useFeedStore } from "@/stores/feed";
import { useUserStorageStore } from "@/stores/userStorage";
import { ref, watch } from 'vue';
import fullHeart from "@/assets/fullHeart.png";
import emptyHeart from "@/assets/emptyHeart.png";
import PopUpComplaint from "../PopUp/PopUpComplaint.vue";

export default {
  name: "feed_item",
  props: {
    feed_id: {
      type: Number,
      default: 9999,
    },
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
          feed_id: this.feed_id,
        },
      });
    },
  },
  components:{
    PopUpComplaint,
  },
  setup(props) {
    const feedjs = useFeedStore();
    const userStorage = useUserStorageStore();
    const heartIcon = ref(emptyHeart);
    const showComplaintBox = ref(false);
    const isLiked = ref(false); // 좋아요 상태를 추적하는 ref 추가

    watch(isLiked, (newValue) => {
      heartIcon.value = newValue ? fullHeart : emptyHeart;
    });
    // 피드 좋아요 누르기 함수
    const pushFeedLike = () => {
      alert("좋아요 누름" + props.feed_id);

      feedjs
        .updateFeedLike(props.feed_id)
        .then((res) => {
          console.log("feed 좋아요 누름");

          const userData = userStorage.getUserInformation();
          isLiked.value = !isLiked.value;
          feedjs
            .addfeedLikeUser(props.feed_id, userData.user_id)
            .then((res) => {
              console.log("feed 좋아요 유저 추가");
              // heartIcon.value = heartIcon.value === emptyHeart
              //   ? fullHeart
              //   : emptyHeart;
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

    const handleDeclareClick = () => {
      showComplaintBox.value = true;
    };

    const closeComplaintBox = () => {
      showComplaintBox.value = false;
    };

    return {
      pushFeedLike,
      heartIcon,
      handleDeclareClick,
      showComplaintBox,
      closeComplaintBox,
      isLiked,
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
.heart_button{
  margin-left: 10px;
}
.feed_image_frame {
  border: 2px solid white;
  border-radius: 30px;
  background-color: white;
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
.declare_button{
  border-radius: 15px;
  background-color: white;
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

.declare_icon {
  width: 40px;
  /* Adjust the width of the image */
  height: 40px;
  /* Adjust the height of the image */
  
}

.heart_icon {
  width: 25px;
  height: 25px;
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

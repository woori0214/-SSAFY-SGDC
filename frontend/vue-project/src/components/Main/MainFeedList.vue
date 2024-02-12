<template>
  <div class="feed_board">
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <div class="more_feed">
      <div class="more_feed_head">
        <!-- <img
          src="@/assets/alarmmachine.png"
          alt="Alarm Machine Icon"
          class="alarm-icon"
        /> -->
        <div class="material-symbols-outlined" style="font-size: 44px">
          lightbulb
        </div>
        알려드립니다
      </div>
      <div>
        <button
          v-if="userStored_id != null"
          @click="more_feed"
          class="more_feed_btn"
        >
          더보기<span class="material-symbols-outlined"> exit_to_app </span>
        </button>
      </div>
    </div>

    <div class="betweenBlock">
      <div class="betweenBlock-item"></div>
    </div>

    <div class="feed_list">
      <div
        v-for="feed_item in feedlist"
        :key="feed_item.feed_id"
        class="feed_item"
      >
        <div class="feed_item_context">{{ feed_item.feedTitle }}</div>
      </div>
      <!-- <div class="feed_item_foot"></div> -->
    </div>
  </div>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import router from "@/router";
import { useFeedStore } from "@/stores/feed";
import alarmmachine from "@/assets/alarmmachine.png";
import { useUserStorageStore } from "@/stores/userStorage";

const useUserStorage = useUserStorageStore();
const userStored_id = ref(null);
const feedInfo = useFeedStore();
const feedlist = ref([
  {
    feed_id: 0,
    feedTitle: "오화석0 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 1,
    feedTitle: "오화석1 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 2,
    feedTitle: "오화석2 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 3,
    feedTitle: "오화석3 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 4,
    feedTitle: "오화석4 님이 기상챌린지를 성공하셨습니다.",
  },
]);
const feedlist_ = ref([]);

const more_feed = () => {
  router.push({ name: "Feed" });
};

const resetMainFeedList = () => {
  feedInfo
    .getFeedListPage(0, 5)
    .then((res) => {
      feedlist.value = [...res.data.data.content];

      console.log("feed 데이터가 초기화 되었습니다.");
      console.log(feedlist.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

onMounted(async () => {
  resetMainFeedList();

  try {
    userStored_id.value = useUserStorage.getUserInformation().user_id;
  } catch (error) {
    console.error("유저 정보를 가져오는 중 오류가 발생했습니다:", error);
  }
});
</script>
  
<style>
.feed_board {
  /* 임시 스타일 */
  /* border: 2px solid #aecbeb; */
  background-color: #e1ecf7;
  border-radius: 25px;
  /* ---------- */
}

.betweenBlock {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.betweenBlock-item {
  background-color: #f8f9fb;
  border-radius: 5px;
  width: 98%;
  height: 4px;
}

.more_feed {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-inline: 20px;
  padding-block: 10px;
}

.more_feed_head {
  font-size: 44px;
  font-weight: 600;
  margin-left: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.feed_list {
  /* 임시 스타일 */
  /* border: 2px solid rgb(255, 194, 125);
  background-color: rgb(255, 194, 125); */
  border-radius: 25px;
  /* ---------- */
  /* margin-inline: 20px; */
  margin-block: 20px;
  display: flex;
  flex-direction: column;
  /* padding-block: 10px; */
  gap: 15px;
}

.feed_item {
  /* 임시 스타일 */
  /* border: 2px solid #aecbeb; */
  background-color: #f8f9fb;
  border-radius: 10px;
  /* border-top-right-radius: 20px;
  border-top-left-radius: 20px; */
  /* ---------- */
  margin-inline: 15px;
  padding-inline: 20px;
  padding-block: 5px;
  font-size: 20px;
  font-weight: 500;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* .feed_item_foot {
  background-color: #83b0e1;
  border-radius: 15px;
  height: 25px;
  margin-inline: 15px;
  margin-top: -15px;
} */

.feed_item_context {
  border: 0px;
  margin-block: 5px;
}

.alarm-icon {
  max-width: 45px;
  /* Set your desired max-width */
  max-height: 45px;
  /* Set your desired max-height */
  vertical-align: middle;
  /* Align the image vertically */
}
.more_feed_btn {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
  border-radius: 5px;
  background-color: #f8f9fb;
  border: 2px solid gray;
  padding-block: 5px;
  transition: background-color 0.3s ease;
}
.more_feed_btn:hover {
  background-color: #83b0e1;
  color: white;
  border: #f8f9fb;
}
</style>
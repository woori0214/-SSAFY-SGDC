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
        <div class="feed_item_context">{{ feed_item.feed_content }}</div>
      </div>
      <!-- <div class="feed_item_foot"></div> -->
    </div>
  </div>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useFeedStore } from "@/stores/feed";
import alarmmachine from "@/assets/alarmmachine.png";
import { useUserStorageStore } from "@/stores/userStorage";

const useUserStorage = useUserStorageStore();
const userStored_id = ref(null);
const router = useRouter();
const feedInfo = useFeedStore();
const feedlist = ref([
  {
    feed_id: 0,
    feed_content: "오화석0 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 1,
    feed_content: "오화석1 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 2,
    feed_content: "오화석2 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 3,
    feed_content: "오화석3 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 4,
    feed_content: "오화석4 님이 기상챌린지를 성공하셨습니다.",
  },
]);
const feedlist_ = ref([]);

onMounted(async () => {
  try {
    const response = await feedInfo.getFeedListPage(0);
    if (response.message === "success") {
      // const sortedFeed = response.feed.sort((a, b) => new Date(b.update_at) - new Date(a.update_at));
      // feedlist.value = sortedFeed.slice(0, 10);
    } else {
      console.error("피드 목록을 가져오지 못했습니다:", response.message);
    }
  } catch (error) {
    console.error("피드 목록을 가져오는 중 오류가 발생했습니다:", error);
  }

  try {
    userStored_id.value = useUserStorage.getUserInformation().user_id;
  } catch (error) {
    console.error("유저 정보를 가져오는 중 오류가 발생했습니다:", error);
  }
});

const more_feed = () => {
  router.push({ name: "Feed" });
};
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
  font-weight: 700;
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
  font-weight: 600;
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
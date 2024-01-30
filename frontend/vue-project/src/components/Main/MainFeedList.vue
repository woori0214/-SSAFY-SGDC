<template>
  <div class="feed_board">
    <div class="more_feed">
      <h1>
        <img
          src="@/assets/alarmmachine.png"
          alt="Alarm Machine Icon"
          class="alarm-icon"
        />
        알려드립니다
      </h1>
      <div>
        <button @click="more_feed">+더보기</button>
      </div>
    </div>
    <div class="feed_list">
      <div
        v-for="feed_item in feedlist"
        :key="feed_item.feed_id"
        class="feed_item"
      >
        <p class="feed_item_context">{{ feed_item.feed_content }}</p>
      </div>
      <div class="feed_item_foot"></div>
    </div>
  </div>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useFeedStore } from "@/stores/feed";
import alarmmachine from "@/assets/alarmmachine.png";
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
  {
    feed_id: 5,
    feed_content: "오화석5 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 6,
    feed_content: "오화석6 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 7,
    feed_content: "오화석7 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 8,
    feed_content: "오화석8 님이 기상챌린지를 성공하셨습니다.",
  },
  {
    feed_id: 9,
    feed_content: "오화석9 님이 기상챌린지를 성공하셨습니다.",
  },
]);
const feedlist_ = ref([]);

onMounted(async () => {
  try {
    const response = await feedInfo.getFeedList();
    if (response.message === "success") {
      // const sortedFeed = response.feed.sort((a, b) => new Date(b.update_at) - new Date(a.update_at));
      // feedlist.value = sortedFeed.slice(0, 10);
    } else {
      console.error("피드 목록을 가져오지 못했습니다:", response.message);
    }
  } catch (error) {
    console.error("피드 목록을 가져오는 중 오류가 발생했습니다:", error);
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

.more_feed {
  /* 임시 스타일 */
  border-bottom: 5px solid whitesmoke;
  /* background-color: rgb(255, 195, 195); */
  /* ---------- */
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
}

.feed_list {
  /* 임시 스타일 */
  /* border: 2px solid rgb(255, 194, 125);
  background-color: rgb(255, 194, 125); */
  border-radius: 25px;
  /* ---------- */
  /* margin-inline: 20px; */
  margin-top: 20px;
  margin-bottom: 5px;
  display: flex;
  flex-direction: column;
  padding-block: 10px;
}

.feed_item {
  /* 임시 스타일 */
  border: 2px solid #aecbeb;
  background-color: #f8f9fb;
  border-top-right-radius: 20px;
  border-top-left-radius: 20px;
  /* ---------- */
  margin-inline: 15px;
  padding-inline: 20px;
  padding-bottom: 10px;
  font-size: 20px;
  font-weight: 600;
  margin-top: -15px;
}

.feed_item_foot {
  background-color: #83b0e1;
  /* 볼더 스타일1 */
  border-radius: 15px;
  /*  */
  /* 볼더 스타일2 */
  /* border-bottom-right-radius: 15px;
  border-bottom-left-radius: 15px; */
  /*  */
  height: 25px;
  margin-inline: 15px;
  margin-top: -15px;
}

.feed_item_context {
}

.alarm-icon {
  max-width: 45px;
  /* Set your desired max-width */
  max-height: 45px;
  /* Set your desired max-height */
  vertical-align: middle;
  /* Align the image vertically */
}
</style>
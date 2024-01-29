<template>
  <div class="ranking-body">
    <div class="ranking-head">Ranking</div>
    <div class="rank-categori-btn-list">
      <button
        class="rank-categori-btn"
        v-for="(categori, index) in categoris"
        :key="index"
        @click="selectCategory(categori.id)"
      >
        {{ categori.name }}
      </button>
    </div>
    <div class="rank-board">
      <div class="rank-board-menu">
        <div class="rank-board-menu-item">순위</div>
        <div class="rank-board-menu-item">이름</div>
        <div class="rank-board-menu-item">점수</div>
      </div>
      <div class="rank-board-list">
        <div
          class="rank-board-list-item"
          :style="
            index === 0
              ? 'background-color: #83b0e1;border-top-left-radius: 10px;border-top-right-radius: 10px;'
              : index === 1
              ? 'background-color: #aecbeb;'
              : index === 2
              ? 'background-color: #e1ecf7;'
              : ''
          "
          v-for="(item, index) in currentRank"
          :key="index"
        >
          <div class="rank-board-list-item-rank">{{ item.rank }}</div>
          <div class="rank-board-list-item-username">{{ item.userName }}</div>
          <div class="rank-board-list-item-score">{{ item.score }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";

const categoris = ref([
  {
    id: "wake",
    name: "기상",
  },
  {
    id: "algo",
    name: "알고리즘",
  },
  {
    id: "exercise",
    name: "운동",
  },
  {
    id: "diet",
    name: "식단",
  },
  {
    id: "study",
    name: "스터디",
  },
  {
    id: "moderation",
    name: "절제",
  },
]);

const rank_wake = ref([
  {
    rank: 1,
    userName: "화석",
    score: 1302,
  },
  {
    rank: 2,
    userName: "코끼리",
    score: 890,
  },
  {
    rank: 3,
    userName: "일찍새",
    score: 788,
  },
  {
    rank: 4,
    userName: "잠없쥐",
    score: 765,
  },
  {
    rank: 5,
    userName: "석화",
    score: 489,
  },
  {
    rank: 6,
    userName: "춘춘",
    score: 90,
  },
  {
    rank: 7,
    userName: "꾿나잇",
    score: 8,
  },
]);

const rank_algo = ref([
  {
    rank: 1,
    userName: "하리보",
    score: 1302,
  },
  {
    rank: 2,
    userName: "석화",
    score: 890,
  },
  {
    rank: 3,
    userName: "춘춘",
    score: 788,
  },
  {
    rank: 4,
    userName: "코끼리",
    score: 765,
  },
  {
    rank: 5,
    userName: "라이드온바이크",
    score: 489,
  },
  {
    rank: 6,
    userName: "파이어스톤",
    score: 90,
  },
  {
    rank: 7,
    userName: "꾿나잇",
    score: 8,
  },
]);

const rank_exercise = ref([
  {
    rank: 1,
    userName: "코끼리",
    score: 1302,
  },
  {
    rank: 2,
    userName: "일찍새",
    score: 890,
  },
  {
    rank: 3,
    userName: "갤럭시 버즈",
    score: 788,
  },
  {
    rank: 4,
    userName: "파이어스톤",
    score: 765,
  },
  {
    rank: 5,
    userName: "꾿나잇",
    score: 489,
  },
  {
    rank: 6,
    userName: "석화",
    score: 90,
  },
  {
    rank: 7,
    userName: "싸다리",
    score: 8,
  },
]);

const rank_diet = ref([
  {
    rank: 1,
    userName: "꾿나잇",
    score: 1302,
  },
  {
    rank: 2,
    userName: "일찍새",
    score: 890,
  },
  {
    rank: 3,
    userName: "잠없쥐",
    score: 788,
  },
  {
    rank: 4,
    userName: "춘춘",
    score: 765,
  },
  {
    rank: 5,
    userName: "하마아저씨",
    score: 489,
  },
  {
    rank: 6,
    userName: "석화",
    score: 90,
  },
  {
    rank: 7,
    userName: "코끼리",
    score: 8,
  },
]);

const rank_study = ref([
  {
    rank: 1,
    userName: "일찍새",
    score: 1302,
  },
  {
    rank: 2,
    userName: "잠없쥐",
    score: 890,
  },
  {
    rank: 3,
    userName: "코끼리",
    score: 788,
  },
  {
    rank: 4,
    userName: "꾿나잇",
    score: 765,
  },
  {
    rank: 5,
    userName: "석화",
    score: 489,
  },
  {
    rank: 6,
    userName: "가을남자",
    score: 90,
  },
  {
    rank: 7,
    userName: "춘춘",
    score: 8,
  },
]);

const rank_moderation = ref([
  {
    rank: 1,
    userName: "코끼리",
    score: 1302,
  },
  {
    rank: 2,
    userName: "석화",
    score: 890,
  },
  {
    rank: 3,
    userName: "파이어스톤",
    score: 788,
  },
  {
    rank: 4,
    userName: "잠없쥐",
    score: 765,
  },
  {
    rank: 5,
    userName: "잠없쥐",
    score: 489,
  },
  {
    rank: 6,
    userName: "이름없음",
    score: 90,
  },
  {
    rank: 7,
    userName: "흰색 시바견",
    score: 8,
  },
]);

const selectedCategory = ref("wake");

const currentRank = computed(() => {
  switch (selectedCategory.value) {
    case "wake":
      return rank_wake.value;
    case "algo":
      return rank_algo.value;
    case "exercise":
      return rank_exercise.value;
    case "diet":
      return rank_diet.value;
    case "study":
      return rank_study.value;
    case "moderation":
      return rank_moderation.value;
    default:
      return rank_deit.value;
  }
});

// 카테고리 선택 함수
const selectCategory = (categoryId) => {
  selectedCategory.value = categoryId;
};
</script>

<style scoped>
.ranking-body {
  background-color: #e1ecf7;
  border-radius: 25px;
  padding-bottom: 20px;
}
.ranking-head {
  font-size: 44px;
  font-weight: 700;
  margin-left: 15px;
  padding-inline: 20px;
  padding-block: 10px;
}
.rank-categori-btn-list {
  background-color: #f8f9fb;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  margin-left: 20px;
  display: inline-flex;
  justify-content: left;
}
.rank-categori-btn {
  background-color: #f8f9fb;
  border: 0px;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
  font-size: 22px;
  font-weight: 600;
  padding-top: 12px;
  padding-inline: 12px;
  padding-bottom: 5px;
}
.rank-board {
  border-radius: 15px;
  margin-inline: 10px;
  padding-inline: 5px;

  display: flex;
  flex-direction: column;
}
.rank-board-menu {
  border: 3px solid #aecbeb;
  background-color: #aecbeb;
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  padding: 8px;
}
.rank-board-menu-item {
  color: rgb(17, 17, 17);
  font-size: 17px;
  padding-inline: 10px;
  font-weight: 800;
}
.rank-board-list {
  background-color: #f8f9fb;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  margin-top: 12px;
}
.rank-board-list-item {
  /* border: 3px solid blue; */
  display: flex;
  justify-content: space-between;
  padding-inline: 8px;
  padding-block: 10px;
}
.rank-board-list-item-rank {
  /* border: 3px solid green; */
  font-size: 18px;
  font-weight: 600;
  width: 50px;

  text-align: center;
}
.rank-board-list-item-username {
  /* border: 3px solid green; */
  font-size: 20px;
  font-weight: 700;
}
.rank-board-list-item-score {
  /* border: 3px solid green; */
  font-size: 18px;
  font-weight: 600;
  width: 50px;

  text-align: center;
}
</style>
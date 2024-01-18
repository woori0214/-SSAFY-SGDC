<template>
  <div id="main_box">
    <div>매칭 시작하기</div>
    <div class="categori_list">
      <button
        id="categori_btn"
        v-for="categori in MyCategories"
        :key="categori"
      >
        {{ categori }}
      </button>
    </div>

    <div id="matching_btn_list">
      <button id="matching_btn" @click="randomMatching()">랜덤 매치</button>
      <div class="matching_btn_gap"></div>
      <button id="matching_btn" @click="friendMatching()">친구와 매치</button>

      <!-- <router-link :to="{ name: 'Main' }" class="matching_btn router-link-button">
        랜덤 매치
      </router-link>
      <div class="matching_btn_gap"></div>
      <router-link :to="{ name: 'Main' }" class="matching_btn router-link-button">
        친구와 매치
      </router-link> -->
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const MyCategories = ref([
  "식단",
  "스터디",
  "알고리즘",
  "기상",
  "절제",
  "운동",
]);

const router = useRouter();
const randomMatching = () => {
    router.push({
        path:"/",
    });
};
const friendMatching = () => {
    router.push({
        path:"/",
    });
};

</script>

<style scoped>
#main_box {
  border: 2px solid greenyellow;
  width: 95%;

  display: flex;
  flex-direction: column;

  margin: auto;
}

.categori_list {
  /* border: 2px solid purple; */
  display: flex;
  justify-content: space-between;
  width: 100%;
  gap: 10px;
  margin-bottom: 10px;
}

#categori_btn {
  flex: 1;
}

#matching_btn_list {
  border: 2px solid orange;
  display: flex;
  justify-content: center;
}

#matching_btn {
  width: fit-content;
  height: 50px;
  flex: 13;
}

.matching_btn_gap {
  flex: 2;
}
</style>
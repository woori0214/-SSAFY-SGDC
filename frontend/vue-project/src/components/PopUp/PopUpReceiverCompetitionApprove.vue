<template>
  <div v-if="showModal" class="reciveChallengeBox-wrap">
    <div class="reciveChallengeBox-body">
      <div class="reciveChallengeBox-title">도전장 수락 페이지 - {{ now_category }}</div>

      <!-- 카테고리별 문구 표시 -->
      <div class="reciveChallengeBox-content">
        <template v-if="now_category === '기상'">ssafy입실시간을 찍어 인증해주세요!</template>
        <template v-else-if="now_category === '알고리즘'">알고리즘 문제를 하나 해결해 보세요!</template>
        <template v-else-if="now_category === '운동'">운동한 사진을 인증해보세요!</template>
        <template v-else-if="now_category === '스터디'">오늘 하루 공부한 시간을 인증해주세요!</template>
        <template v-else-if="now_category === '식단'">샐러드 빈그릇을 인증해주세요!</template>
        <template v-else-if="now_category === '절제'">오늘 무엇을 절제했는지 인증해주세요!</template>
        <template v-else>선택하신 도전에 최선을 다하세요!</template>
      </div>

      <div class="reciveChallengeBox-closeBtn">
        <button @click="close_reciveChallengeBox">닫기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

const props = defineProps({
  showModal: Boolean,
  now_category: String,
  close: Function,
});

const close_reciveChallengeBox = () => {
  props.close();
};

const now_category = ref('알고리즘');
</script>

<style>
.reciveChallengeBox-wrap {
  /* border: 2px solid red; */

  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 12;
}

.reciveChallengeBox-body {
  /* border: 2px solid blue; */
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 40%;
  max-height: 40%;
  background: #fff;
  border-radius: 10px;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  gap: 10px;
}

.reciveChallengeBox-title {
  /* border: 2px solid red; */
  background-color: #aecbeb;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  padding-block: 5px;
  padding-inline: 10px;
  font-size: 18px;
  font-weight: 600;
}

.reciveChallengeBox-content {
  background-color: #aecbeb;
  border-radius: 10px;
  margin-inline: 30px;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin-block: 20px;
  padding-block: 20px;
  font-size: 24px;
}

.reciveChallengeBox-closeBtn {
  display: flex;
  justify-content: end;
  padding-bottom: 10px;
  padding-right: 20px;
}</style>
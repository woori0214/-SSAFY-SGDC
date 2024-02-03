<template>
    <div v-if="showModal" class="reciveChallengeBox-wrap">
  <div class="reciveChallengeBox-body">
    <div class="reciveChallengeBox-title">도전장 수락 페이지 - {{ now_category }}</div>

    <div class="reciveChallengeBox-content">도전장을 수락했습니다.<br>알고리즘 4352번 문제를 풀어 인증해주세요!</div>

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
.reciveChallengeBox-title{
    /* border: 2px solid red; */
    background-color: #aecbeb;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
    padding-block: 5px;
    padding-inline: 10px;
    font-size: 18px;
    font-weight: 600;
}
.reciveChallengeBox-content{
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
.reciveChallengeBox-closeBtn{
    display: flex;
    justify-content: end;
    padding-bottom: 10px;
    padding-right: 20px;
}
</style>
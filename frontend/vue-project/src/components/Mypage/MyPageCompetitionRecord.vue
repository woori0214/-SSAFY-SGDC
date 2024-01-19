<template>
    <div>
      <div class="accordion-header" @click="toggleAccordion">
        <h2>싸강두천 전적</h2>
        <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
      </div>
      <div class="com_accordion-content" :class="{ 'open': isOpen }">
        <div v-for="compet_record in compet_records" :key="compet_record.id" class="result_div">
                <img v-if="compet_record.result === '1'" src="@/assets/pose_win_boy.png" alt="" class="win_img">
                <img v-else-if="compet_record.result === '0'" src="@/assets/pose_lose_boy.png" alt="" class="lose_img">
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import { ref } from 'vue';

const isOpen = ref(true);

const toggleAccordion = () => {
    isOpen.value = !isOpen.value;
};

const compet_records = [
    { id: 1, player1: '우리다', player2: '지은', result: '1'},
    { id: 2, player1: '우리다', player2: '태범', result: '0'},
    { id: 3, player1: '우리다', player2: '화석', result: '1'},
    { id: 4, player1: '우리다', player2: '현춘', result: '0'},
    { id: 5, player1: '우리다', player2: '수안', result: '1'},
]
</script>
  
<style scoped>
/* 아코디언 헤더 스타일링 */
.accordion-header {
    display: flex;
    align-items: center;
    background-color: #f1f1f1;
    padding: 10px;
    border: 1px solid #ccc;
    cursor: pointer;
}

.rotate-icon {
    transition: transform 0.3s ease;
    margin-left: auto;
    /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
}

.rotate-icon::before {
    content: '\25BC';
    /* 기본 화살표 아래 방향 유니코드 */
    display: inline-block;
}

.rotate-icon.rotate::before {
    content: '\25B2';
    /* 화살표 위 방향 유니코드 */
}

/* 아코디언 내용 스타일링 */
.com_accordion-content {
    display: flex;
    padding: 10px;
    border: 1px solid #ccc;
    border-top: none;
    overflow: hidden;
    max-height: 0;
    transition: max-height 0.3s ease;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.com_accordion-content.open {
    max-height: 1000px;
    /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
    transition: max-height 0.3s ease;
}

.result_div {
    display: flex;
    flex-direction: row;
}
.win_img {
    width: 100px;
}

.lose_img {
    width: 100px;
}
</style>
  
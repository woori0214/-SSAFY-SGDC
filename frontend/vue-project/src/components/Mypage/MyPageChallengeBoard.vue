<template>
  <div>
    <div class="accordion-header" @click="toggleAccordion">
      <h2>도전 현황</h2>
      <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
    </div>
    <transition>
      <div class="accordion-content" :class="{ 'open': isOpen }" v-show="isOpen">
        <MainChallengeBoard />
      </div>
    </transition>
  </div>
</template>
  
<script setup>
import { ref } from 'vue';
import MainChallengeBoard from '../Main/MainChallengeBoard.vue';

const isOpen = ref(true);

const toggleAccordion = () => {
  isOpen.value = !isOpen.value;
};
</script>
  
<style scoped>
/* 아코디언 헤더 스타일링 */
.accordion-header {
  display: flex;
  align-items: center;
  background-color: #83b0e1;
  padding: 10px;
  cursor: pointer;
  border-radius: 15px;
}

.rotate-icon {
  transition: transform 0.3s ease;
  margin-left: auto;
  /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
  font-family: sans-serif;
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
.accordion-content {
  padding: 10px;
  border-top: none;
  overflow: hidden;
  max-height: 0;
  transition: max-height 0.3s ease;
  border-radius: 15px;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.accordion-content.open {
  max-height: 1000px;
  /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
  transition: max-height 0.3s ease;
}</style>
  
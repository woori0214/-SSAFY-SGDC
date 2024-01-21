<template>
    <div>
      <div class="accordion-header" @click="toggleAccordion">
        <h2>솔로모드 내역</h2>
        <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
      </div>
      <div class="accordion-content" :class="{ 'open': isOpen }">
        <div v-for="catego in categories" :key="catego.id" class="solo_record">
          <img :src="getImageUrl(catego.name)" alt=""  class="soloimg"/>
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

  const categories = ref([
    {id: 1, name: "wake", category: '기상'},
    {id: 2, name: 'algo', category: '알고리즘'},
    {id: 3, name: 'exercise', category: '운동'},
    {id: 4, name: 'diet', category: '식단'},
    {id: 5, name: 'study', category: '스터디'},
    {id: 6, name: 'wake', category: '기상'},
    {id: 7, name: 'algo', category: '알고리즘'},
  ])

  const getImageUrl = (name) => {
  const imageMap = {
    wake: "src/assets/timer.png",
    algo: "src/assets/algo.png",
    exercise: "src/assets/health.png",
    diet: "src/assets/diet.png",
    study: "src/assets/study.png",
    moderation: "src/assets/fighting.png",
  };

  // 이름에 따라 해당하는 이미지 URL을 반환합니다.
  return imageMap[name];
};
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
    margin-left: auto; /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
  }
  
  .rotate-icon::before {
    content: '\25BC'; /* 기본 화살표 아래 방향 유니코드 */
    display: inline-block;
  }
  
  .rotate-icon.rotate::before {
    content: '\25B2'; /* 화살표 위 방향 유니코드 */
  }
  
  /* 아코디언 내용 스타일링 */
  .accordion-content {
    padding: 10px;
    border: 1px solid #ccc;
    border-top: none;
    overflow: auto;
    max-height: 0;
    transition: max-height 0.3s ease;
    display: flex;
    flex-direction: row;

  }
  
  /* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
  .accordion-content.open {
    max-height: 1000px; /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
    transition: max-height 0.3s ease;
  }

  .soloimg {
    width: 70px;
    margin: 10px;

  }
  </style>
  
<template>
    <div>
      <div class="category_grid">
        <div v-for="(category, index) in categories" :key="index" class="category_item">
          <button @click="navigateToCategory(category)">
            <img :src="getImage(category)" alt="category">
            <div class="category_text">
              <div>{{ category }}</div>
              <div class="extra_text">{{ getAdditionalText(category) }}</div>
            </div>
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  
  import timerImage from '@/assets/timer.png';
  import algoImage from '@/assets/algo.png';
  import dietImage from '@/assets/diet.png';
  import fightingImage from '@/assets/fighting.png';
  import studyImage from '@/assets/study.png';
  import healthImage from '@/assets/health.png';
  
  export default {
    setup() {
      const categories = ref(['기상', '알고리즘', '식단', '절제', '스터디', '운동']);
      const additionalTexts = ref({
        '기상': '싸피 멀캠에 와서 입실체크 빨리하기',
        '알고리즘': '알고 1일 1문제 풀기',
        '식단': '10층가서 빈그릇 인증샷 찍기',
        '절제': '오늘 나는 ㅇㅇㅇ을 참았다.',
        '스터디': '하루 몇시간 공부했는지 인증하기',
        '운동': '운동 인증샷ㄱㄱㄱ',
      });
  
      const navigateToCategory = (category) => {
        console.log(`Go to ${category} category`);
      };
  
      // 카테고리에 해당하는 이미지 URL을 반환하는 함수
      const getImage = (category) => {
        const images = {
          '기상': timerImage,
          '알고리즘': algoImage,
          '식단': dietImage,
          '절제': fightingImage,
          '스터디': studyImage,
          '운동': healthImage,
        };
        return images[category] || timerImage;
      };
  
      // 카테고리에 해당하는 추가 텍스트 반환
      const getAdditionalText = (category) => {
        return additionalTexts.value[category] || '';
      };
  
      return {
        categories,
        navigateToCategory,
        getImage,
        getAdditionalText,
      };
    },
  };
  </script>
  
  <style scoped>
  .category_grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(2, 1fr);
    gap: 16px;
  }
  
  .category_item {
    margin-top: 0%;
    text-align: center;
  }
  
  button {
    padding: 10px;
    font-size: 16px;
    width: 100%;
    height: 0;
    padding-top: 100%;
    position: relative;
    cursor: pointer;
  }
  
  button img {
    position: absolute;
    top: 35%;
    left: 50%;
    transform: translate(-50%, -50%);
    max-width: 50%;
    max-height: 50%;
    margin: 0%;
  }
  
  .category_text {
    position: absolute;
    bottom: 10px;
    width: 100%;
    text-align: center;
    color: black; /* Adjust the color as needed */
    transform: translateX(-50%);
    left: 50%;
  }
  
  .extra_text {
    margin-top: 5px; /* Adjust the margin as needed */
    color: black; /* Adjust the color as needed */
  }
  </style>
  
<template>
    <div>
      <!-- 첫 번째 모달 버튼 -->
      <button @click="isModalOpen = true">모달 열기</button>
      
      <!-- 두 번째 모달 버튼 -->
      <button @click="isModalOpen2 = true">또 다른 모달 열기</button>
  
      <!-- 첫 번째 모달 -->
      <test2 :show="isModalOpen" @update:show="isModalOpen = $event">
        <!-- 모달 내용 -->
      </test2>
  
      <!-- 두 번째 모달 -->
      <alarm :show="isModalOpen2" @update:show="isModalOpen2 = $event">
        <!-- 모달 내용 -->
      </alarm>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import test2 from '@/components/PopUp/test2.vue';
  import alarm from '@/components/PopUp/alarm.vue';
  
  const isModalOpen = ref(false);
  const isModalOpen2 = ref(false);
  </script>
  
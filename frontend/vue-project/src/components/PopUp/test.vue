<!-- 부모 컴포넌트 -->
<template>
    <div>
      <button @click="isModalOpen = true">모달 열기</button>
      <test2 :show="isModalOpen" @update:show="isModalOpen = $event">
        <!-- 모달 내용 -->
        <h1>인증</h1>
        <p>모달 내용</p>
      </test2>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import test2 from '@/components/PopUp/test2.vue';
  
  const isModalOpen = ref(false);
  </script>
  
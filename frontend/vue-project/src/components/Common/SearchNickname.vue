<template>
    <div>
      <input type="text" v-model="nickname" placeholder="친구 닉네임 검색..." @input="searchFriends">
      <div v-if="searchResults">
        <ul>
          <li v-for="result in searchResults" :key="result.user_id">
            {{ result.user_nickname }}
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from "vue";
  import { useRouter } from "vue-router";
  import { useUserStore } from "@/stores/user";
  
  const nickname = ref(''); // 사용자 입력을 저장할 반응형 참조입니다.
  const searchResults = ref(null); // 검색 결과를 저장할 반응형 참조입니다.
  const userStore = useUserStore(); // useUserStore를 사용하여 store에 접근합니다.
  
  // 닉네임 검색을 수행하는 메소드입니다.
  const searchFriends = () => {
    if (nickname.value.trim() === '') {
      searchResults.value = null; // 입력 값이 없으면 검색 결과를 비웁니다.
      return;
    }
    
    // findAllfriends 함수를 호출하여 닉네임으로 친구를 검색합니다.
    userStore.findAllfriends({user_nickname: nickname.value})
      .then(response => {
        searchResults.value = response; // 검색 결과를 저장합니다.
      })
      .catch(error => {
        console.error('검색 중 오류 발생:', error);
        searchResults.value = null; // 오류 발생 시 검색 결과를 비웁니다.
      });
  };
  </script>
  
  <style scoped>
  
  </style>
  
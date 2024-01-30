<template>
  <div id="main_box">
    <div class="select_category">
      <p>STEP1. 카테고리를 선택해주세요</p>
      <div class="category_list_box">
        <button v-for="category in MyCategories" :key="category.id" :class="{ active: selectedCategory === category.id }"
          @click="selectCategory(category.id)" class="category-button">
          {{ category.name }}
        </button>
      </div>
    </div>

    <div id="matching_btn_list">
      <p class="matching_title">STEP2. 매칭모드를 선택해주세요</p>
      <div class="buttons_container">
        <button id="matching_btn" @click="openRandomMatchingModal">랜덤 매치</button>
        <div class="matching_btn_gap"></div>
        <button id="matching_btn" @click="openFriendMatchingModal">친구와 매치</button>
      </div>
    </div>

    <PopUpRequestMessage :showModal="isRandomMatchingModalVisible || isFriendMatchingModalVisible" :close="closeModal"
      :modalType="modalType" :category_id="selectedCategory !== null ? selectedCategory : null" :user_id="userId" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import PopUpRequestMessage from '@/components/PopUp/PopUpRequestMessage.vue';
import { useCompetionStore } from '@/stores/competition';

import { useUserStorageStore } from '@/stores/userStorage';

const MyCategories = ref([
  {
    id: 1,
    name: '기상',
  },
  {
    id: 2,
    name: '알고리즘',
  },
  {
    id: 3,
    name: '운동',
  },
  {
    id: 4,
    name: '스터디',
  },
  {
    id: 5,
    name: '식단',
  },
  {
    id: 6,
    name: '절제',
  },
]);

const router = useRouter();
const isRandomMatchingModalVisible = ref(false);
const isFriendMatchingModalVisible = ref(false);
const modalType = ref('');
const selectedCategory = ref(null);

const competSelect = useCompetionStore();
const userStorage = useUserStorageStore();
const userInformation = userStorage.getUserInformation();
const userId = userInformation.user_Id;

const openRandomMatchingModal = () => {
  if (selectedCategory !== null) {
    const randomSendData = {
      category_id: selectedCategory.value,
      user_id: userId,
    };
    console.log('도전장', randomSendData);
    // Add logic to handle the random matching modal
    competSelect
      .randomSend(randomSendData)
      .then((response) => {
        console.log('Random matching request sent successfully:', response);
      })
      .catch((error) => {
        console.error('Error sending random matching request:', error);
      });
  }

  isRandomMatchingModalVisible.value = true;
  modalType.value = 'randomMatching';
};

const openFriendMatchingModal = () => {
  if (selectedCategory !== null) {
    isFriendMatchingModalVisible.value = true;
    modalType.value = 'friendMatching';
  }
};

const closeModal = () => {
  isRandomMatchingModalVisible.value = false;
  isFriendMatchingModalVisible.value = false;
  modalType.value = '';
};

const selectCategory = (categoryId) => {
  selectedCategory.value = selectedCategory.value === categoryId ? null : categoryId;
};
</script>

<style scoped>
#main_box {
  width: 100%;
  display: flex;
  flex-direction: column;
  
  /* margin: auto; */
}

.select_category {
  background-color:#e1ecf7;
  border: 2px solid #e1ecf7;
  width: 93%;
  /* margin: auto; */
  display: flex;
  flex-direction: column;
  padding: 30px;
  border-radius: 25px;
  margin-bottom: 5%;

}

.category_list_box {
  display: flex;
  flex-wrap: wrap;
  /* Allow flex items to wrap onto multiple lines */
  gap: 10px;
}

.category-button {
  flex: 1;
  padding: 10px;
  border: 2px solid #83b0e1;
  border-radius: 25px;
  cursor: pointer;
  background-color: #83b0e1;
}

.category-button.active {
  background-color: #83b0e1;
}

#matching_btn_list {
  display: flex;
  flex-direction: column; /* 세로 방향으로 정렬 */
  align-items: center; /* 가운데 정렬 */
  background-color: #e1ecf7;
  border: 2px solid #e1ecf7;
  border-radius: 25px;
  padding: 20px;
  /* margin: 20px 0; */
  margin-bottom: 5%;
  width: 93%;
}

.matching_title {
  margin-bottom: 15px; /* 제목과 버튼 사이 간격 조정 */
  font-size: 1em; /* 글꼴 크기 조정 */
}

.buttons_container {
  display: flex;
  justify-content: space-around; /* 버튼들을 균등하게 분배 */
  width: 100%; /* 컨테이너 너비를 전체로 설정 */
}

#matching_btn {
  width: fit-content;
  height: 50px;
  border-radius: 25px;
  flex: 13;
  background-color: #83b0e1;
 
}

.matching_btn_gap {
  display: none; /* 별도 간격 요소 제거 */
}
</style>
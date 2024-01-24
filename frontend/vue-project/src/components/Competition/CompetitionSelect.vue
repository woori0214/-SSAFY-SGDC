<template>
  <div id="main_box">
    <div>매칭 시작하기</div>
    <div class="categori_list">
      <button v-for="category in MyCategories" :key="category.id" :class="{ active: selectedCategory === category.id }"
        @click="selectCategory(category.id)">
        {{ category.name }}
      </button>
    </div>

    <div id="matching_btn_list">
      <button id="matching_btn" @click="openRandomMatchingModal">랜덤 매치</button>
      <div class="matching_btn_gap"></div>
      <button id="matching_btn" @click="openFriendMatchingModal">친구와 매치</button>
    </div>

    <PopUpRequestMessage :showModal="isRandomMatchingModalVisible || isFriendMatchingModalVisible" :close="closeModal"
      :modalType="modalType" :category_id="selectedCategory !== null ? selectedCategory : null"
      :user_id="login.loginUser" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import PopUpRequestMessage from '@/components/PopUp/PopUpRequestMessage.vue';
import { useCompetionStore } from '@/stores/competition';
import { useLoginStore } from '@/stores/login';

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
const login = useLoginStore();

const openRandomMatchingModal = () => {
  if (selectedCategory !== null) {
    const randomSendData = {
      category_id: selectedCategory.value,
      user_id: login.loginUser,
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
  border: 2px solid greenyellow;
  width: 95%;
  display: flex;
  flex-direction: column;
  margin: auto;
}

.categori_list {
  display: flex;
  justify-content: space-between;
  width: 100%;
  gap: 10px;
  margin-bottom: 10px;
}

#categori_btn {
  flex: 1;
}

#categori_list button.active {
  background-color: lightblue;
}

#matching_btn_list {
  border: 2px solid orange;
  display: flex;
  justify-content: center;
}

#matching_btn {
  width: fit-content;
  height: 50px;
  flex: 13;
}

.matching_btn_gap {
  flex: 2;
}
</style>

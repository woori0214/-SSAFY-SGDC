<template>
  <div id="main_box">
    <div class="select_category">
      <p>STEP1. 카테고리를 선택해주세요</p>
      <div class="category_list_box">
        <button
          v-for="category in Categories"
          :key="category.id"
          :class="{ active: selectedCategory === category.id }"
          @click="selectCategory(category)"
          class="category-button"
        >
          {{ category.name }}
        </button>
      </div>
    </div>

    <div class="matching_btn_list">
      <p class="matching_title">STEP2. 매칭모드를 선택해주세요</p>
      <div class="buttons_container">
        <button class="matching_btn" @click="openRandomMatchingModal">
          랜덤 매치
        </button>

        <button class="matching_btn" @click="openFriendMatchingModal">
          친구와 매치
        </button>
      </div>
    </div>

    <PopUpRequestMessage
      :showModal="
        isRandomMatchingMessageVisible || isFriendMatchingMessageVisible
      "
      :close="closeMessage"
      :modalType="modalType"
      :category_id="selectedCategory !== null ? selectedCategory : null"
      :category_name="
        selectedCategoryName !== null ? selectedCategoryName : null
      "
      :user_id="userId"
      :friendId="selectedFriendId"
    />

    <PopUpFriendsList
      :userId="userId"
      :showModal="isFriendMatchingListVisible"
      :Listclose="closeFriendsList"
      :selectedCategory="selectedCategory !== null ? selectedCategory : null"
      @friend-selected="handleFriendSelect"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useCompetionStore } from "@/stores/competition";
import { useUserStorageStore } from "@/stores/userStorage";

import PopUpRequestMessage from "@/components/PopUp/PopUpRequestMessage.vue";
import PopUpFriendsList from "@/components/PopUp/PopUpFriendsList.vue";

const Categories = ref([
  {
    id: 1,
    name: "기상",
  },
  {
    id: 2,
    name: "알고리즘",
  },
  {
    id: 3,
    name: "운동",
  },
  {
    id: 4,
    name: "스터디",
  },
  {
    id: 5,
    name: "식단",
  },
  {
    id: 6,
    name: "절제",
  },
]);

const router = useRouter();
const competSelect = useCompetionStore();
const userStorage = useUserStorageStore();
const userInformation = userStorage.getUserInformation();

const isRandomMatchingMessageVisible = ref(false);
const isFriendMatchingMessageVisible = ref(false);
const isFriendMatchingListVisible = ref(false);

const modalType = ref("");
const selectedCategory = ref(null);
const selectedCategoryName = ref(null);
const selectedFriendId = ref(null);

const userId = userInformation.user_id;

// 랜덤 매칭
const openRandomMatchingModal = () => {
  if (selectedCategory.value === null) {
    alert("카테고리를 선택해주세요.");
    return;
  }

  const randomSendData = {
    category_id: selectedCategory.value,
    user_id: userId,
  };

  competSelect
    .randomSend(randomSendData)
    .then((response) => {
      console.log("Random matching request sent successfully:", response);
      // 여기에 매칭 성공 시 수행할 로직을 추가할 수 있습니다.
    })
    .catch((error) => {
      console.error("Error sending random matching request:", error);
      // 오류 처리 로직을 추가할 수 있습니다.
    });

  isRandomMatchingMessageVisible.value = true;
  modalType.value = "randomMatching";
};

const closeMessage = () => {
  isRandomMatchingMessageVisible.value = false;
  isFriendMatchingMessageVisible.value = false;
  modalType.value = "";
};

// 친구 매칭
const openFriendMatchingModal = () => {
  if (selectedCategory.value === null) {
    // 카테고리가 선택되지 않았을 경우 경고 메시지 표시
    alert("카테고리를 선택해주세요.");
    return; // 함수 실행 중지
  }
  // 카테고리가 선택되었을 경우 친구와 매치 모달을 표시
  isFriendMatchingListVisible.value = true;
  console.log(isFriendMatchingListVisible.value);
  modalType.value = "friendMatching";
  console.log(modalType.value);
};

// 친구 선택
const handleFriendSelect = (friendId) => {
  selectedFriendId.value = friendId;
  isFriendMatchingMessageVisible.value = true;
  isFriendMatchingListVisible.value = false;
  closeFriendsList();
};

const closeFriendsList = () => {
  isFriendMatchingListVisible.value = false;
};

const selectCategory = (category) => {
  selectedCategory.value =
    selectedCategory.value === category.id ? null : category.id;
  selectedCategoryName.value =
    selectedCategoryName.value === category.name ? null : category.name;
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
  background-color: #e1ecf7;
  border: 2px solid #e1ecf7;
  width: calc(100% - 64px);
  /* margin: auto; */
  display: flex;
  flex-direction: column;
  border-radius: 25px;
  margin-bottom: 5%;
  
  padding: 30px;
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

.matching_btn_list {
  display: flex;
  flex-direction: column;
  /* 세로 방향으로 정렬 */
  align-items: center;
  /* 가운데 정렬 */
  background-color: #e1ecf7;
  border: 2px solid #e1ecf7;
  border-radius: 25px;
  /* padding: 20px; */
  /* margin: 20px 0; */
  margin-bottom: 5%;
  width: calc(100% - 4px);
}

.matching_title {
  margin-bottom: 15px;
  /* 제목과 버튼 사이 간격 조정 */
  font-size: 1em;
  /* 글꼴 크기 조정 */
}

.buttons_container {
  display: flex;
  justify-content: space-around;
  gap: 30px;
  /* 버튼들을 균등하게 분배 */
  width: calc(100% - 40px);
  /* 컨테이너 너비를 전체로 설정 */
  padding: 20px;
}

.matching_btn {
  height: 50px;
  border-radius: 25px;
  flex: 1;
  background-color: #83b0e1;
}
</style>
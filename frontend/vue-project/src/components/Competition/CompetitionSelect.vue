User
<template>
  <div id="main_box">

    <div class="select_category">
      <p class="select_category_title">STEP1. 카테고리를 선택해주세요</p>

      <div class="category_list_box">
        <button v-for="category in Categories" :key="category.id"
          :class="{ 'active': selectedCategory === category.id, 'disabled': disabledCategories.includes(category.id) }"
          @click="selectCategory(category)" class="category-button">
          <span>{{ category.name }}</span>
        </button>
      </div>
    </div>

    <div class="matching_btn_list">
      <p class="matching_title">STEP2. 매칭모드를 선택해주세요</p>
      <div class="buttons_container">
        <button class="matching_btn" @click="openRandomMatchingModal">
          <span>랜덤 매치</span>
        </button>

        <button class="matching_btn" @click="openFriendMatchingModal">
          <span>친구와 매치</span>
        </button>
      </div>
    </div>

    <PopUpRequestMessage :showModal="isRandomMatchingMessageVisible || isFriendMatchingMessageVisible
      " :close="closeMessage" :modalType="modalType" :category_id="selectedCategory !== null ? selectedCategory : null"
      :category_name="selectedCategoryName !== null ? selectedCategoryName : null
        " :user-id="userId" :friend-id="selectedFriendId" :friend-nickname="selectedFriendNickname"/>

    <PopUpFriendsList :user-id="userId" :showModal="isFriendMatchingListVisible" :Listclose="closeFriendsList"
      :selectedCategory="selectedCategory !== null ? selectedCategory : null" @friend-selected="handleFriendSelect" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
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
const selectedFriendNickname = ref(null);

const userId = userInformation.user_id;
const disabledCategories = ref([]);
const userMatchingStatus = () => {
  const userId = userInformation.user_id;
  competSelect.competitionMailbox(userId)
    .then((response) => {
      const matching = response.data.matching;
      const playStatusCategories = matching
        .filter(match => match.match_status === "PLAY_STATUS")
        .map(match => match.category_id);
      disabledCategories.value = playStatusCategories;
      console.log("진행 중인 카테고리를 가져왔습니다.");
    })
    .catch((error) => {
      console.error("이미 진행 중인 카테고리를 가져오지 못했습니다.", error);
    });
};

// 랜덤 매칭
const openRandomMatchingModal = () => {
  if (selectedCategory.value === null) {
    alert("카테고리를 선택해주세요.");
    return;
  }

  const randomSendData = {
    userId: userId,
    categoryId: selectedCategory.value,

  };
  console.log(randomSendData);

  competSelect
    .randomSend(randomSendData)
    .then((response) => {
      console.log("Random matching request sent successfully:", response);
      // 여기에 매칭 성공 시 수행할 로직을 추가할 수 있습니다.
    })
    .catch((error) => {
      console.error("Error sending random matching request:", error);
      console.log('잘 안되는데요')
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
  console.log('친구매칭')
  modalType.value = "friendMatching";
  console.log(modalType.value);
};

// 친구 선택
const handleFriendSelect = (friend) => {
  console.log(friend)
  selectedFriendId.value = friend.userId;
  selectedFriendNickname.value = friend.userNickname;
  console.log(selectedFriendNickname.value)
  isFriendMatchingMessageVisible.value = true;
  isFriendMatchingListVisible.value = false;
  closeFriendsList();
};

const closeFriendsList = () => {
  isFriendMatchingListVisible.value = false;
};
onMounted(() => {
  userMatchingStatus();
});

const selectCategory = (category) => {
  if (disabledCategories.value.includes(category.id)) {
    // alert("이미 매칭 요청을 한 카테고리입니다.");
    return;
  }
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
  border: 2px solid #83b0e1;
  border-radius: 25px;
  margin: 30px 10px;
  background-color: #e1ecf7;
  padding: 0px;


}

.select_category {
  background-color: #f8f9fb;
  /* border: 2px solid #ffffff; 테두리 색상을 흰색(#ffffff)으로 변경 */
  width: calc(100% - 64px);
  display: flex;
  flex-direction: column;
  border-radius: 25px;
  margin-bottom: 5%;
  padding: 30px;
  margin-top: 3%;
}

.select_category_title {
  font-size: 20px;
  margin-top: 0px;
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
  border: 2px solid #e1ecf7;
  border-radius: 25px;
  cursor: pointer;
  background-color: #e1ecf7;
  color: black;

}

.category-button.active {
  background-color: #83b0e1;
  color: white;
}

.matching_btn_list {
  display: flex;
  flex-direction: column;
  /* 세로 방향으로 정렬 */
  align-items: center;
  /* 가운데 정렬 */
  background-color: #f8f9fb;
  border: 2px solid #e1ecf7;
  border-radius: 25px;
  /* padding: 20px; */
  /* margin: 20px 0; */
  margin-bottom: 5%;

  margin-right: 2%;
  width: calc(100% - 4px);
}

.matching_title {
  margin-bottom: 15px;
  /* 제목과 버튼 사이 간격 조정 */
  font-size: 20px;
  /* 글꼴 크기 조정 */
}

.buttons_container {
  display: flex;
  justify-content: space-between;
  gap: 5%;
  /* 버튼들을 균등하게 분배 */
  width: calc(100% - 40px);
  /* 컨테이너 너비를 전체로 설정 */
  padding: 20px;
}

.matching_btn {
  width: 100px;
  /* 원하는 너비로 조절 */
  height: 50px;
  border-radius: 25px;
  flex: 1;
  background-color: #e1ecf7;
  border-color: #e1ecf7;
  margin-left: 50px;
  margin-right: 50px;
  border: none;
}

.matching_btn:hover {
  background-color: #83b0e1;
  /* 마우스를 갖다댔을 때 배경색상 */
  color: white;
  /* 마우스를 갖다댔을 때 텍스트 색상 */
}

.category-button.disabled {
  background-color: #ff5c5c;
  color: white;
  cursor: not-allowed;
}

span {
  font-family: "jua";
}
</style>
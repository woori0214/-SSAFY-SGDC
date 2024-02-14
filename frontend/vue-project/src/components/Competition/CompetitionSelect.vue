User
<template>
  <div class="main_box">
    <div class="hasChallengeCount-warp">
      <div class="hasChallengeCount-box">
        남은 도전장 개수 : {{ hasChallengeCnt }}
      </div>
    </div>
    <div class="select_category">
      <p class="select_category_title">STEP1. 카테고리를 선택해주세요</p>

      <div class="category_list_box">
        <button
          v-for="category in Categories"
          :key="category.id"
          :class="{
            active: selectedCategory === category.id,
            disabled: disabledCategories.includes(category.id),
          }"
          @click="selectCategory(category)"
          class="category-button"
        >
          <span class="category_list_box_font">{{ category.name }}</span>
        </button>
      </div>
    </div>

    <div class="matching_btn_list">
      <p class="matching_title">STEP2. 매칭모드를 선택해주세요</p>
      <div class="buttons_container">
        <button class="matching_btn" @click="openRandomMatchingModal">
          <span class="matching_btn-item">랜덤 매치</span>
        </button>

        <button class="matching_btn" @click="openFriendMatchingModal">
          <span class="matching_btn-item">친구와 매치</span>
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
      :user-id="userId"
      :friend-id="selectedFriendId"
      :friend-nickname="selectedFriendNickname"
    />

    <PopUpFriendsList
      :user-id="userId"
      :showModal="isFriendMatchingListVisible"
      :Listclose="closeFriendsList"
      :selectedCategoryName="selectedCategoryName"
      :selectedCategory="selectedCategory !== null ? selectedCategory : null"
      @friend-selected="handleFriendSelect"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useCompetionStore } from "@/stores/competition";
import { useUserStorageStore } from "@/stores/userStorage";
import { useUserStore } from "@/stores/user";

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
const userInfoObj = useUserStore();

const isRandomMatchingMessageVisible = ref(false);
const isFriendMatchingMessageVisible = ref(false);
const isFriendMatchingListVisible = ref(false);

const modalType = ref("");
const selectedCategory = ref(null);
const selectedCategoryName = ref(null);
const selectedFriendId = ref(null);
const selectedFriendNickname = ref(null);
const hasChallengeCnt = ref(-1);

const userId = userInformation.user_id;
const disabledCategories = ref([]);
const userMatchingStatus = () => {
  const userId = userInformation.user_id;

  // 경기 중인 카테고리 정보 가져오기
  competSelect.competitionAnalysis(userId)
    .then((response) => {
      const userCategories = response.data.user_categories;
      const playStatusCategoryIds = userCategories
        .filter((category) => category.categoryStatus === "PLAY_STATUS")
        .map((category) => category.category_id);

      // 오늘 경쟁 종료한 카테고리 리스트 가져오기
      return competSelect.competitionFinishToday(userId)
        .then((finishTodayResponse) => {
          const finishTodayCategoryIds = finishTodayResponse.data.competitions.map((comp) => comp.category_id);
          return [...playStatusCategoryIds, ...finishTodayCategoryIds];
        });
    })
    .then((combinedCategoryIds) => {
      // 중복 제거
      const uniqueCategoryIds = Array.from(new Set(combinedCategoryIds));

      // 비활성화할 카테고리 설정
      disabledCategories.value = uniqueCategoryIds;
      console.log("비활성화할 카테고리:", uniqueCategoryIds);
    })
    .catch((error) => {
      console.error("카테고리 상태 가져오기 실패:", error);
    });
};

//현재 도전장 개수 최신화 함수
function resetHasChallengeCount() {
  userInfoObj
    .userData(userStorage.getUserInformation().user_id)
    .then((res) => {
      hasChallengeCnt.value = res.data.data.challeng_cnt;
    })
    .catch((error) => {
      console.error("도전장 최신화 오류", error);
    });
}

// 랜덤 매칭
const openRandomMatchingModal = () => {
  if (hasChallengeCnt.value == '0' || hasChallengeCnt.value == 0) {
    alert("남은 도전장이 없습니다.");
    return; // 함수 실행 중지
  }
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
      console.log("잘 안되는데요");
      // 오류 처리 로직을 추가할 수 있습니다.
    });

  isRandomMatchingMessageVisible.value = true;
  modalType.value = "randomMatching";
};

const closeMessage = () => {
  isRandomMatchingMessageVisible.value = false;
  isFriendMatchingMessageVisible.value = false;
  modalType.value = "";
  resetHasChallengeCount();
};

// 친구 매칭
const openFriendMatchingModal = () => {
  if (hasChallengeCnt.value == '0' || hasChallengeCnt.value == 0) {
    alert("남은 도전장이 없습니다.");
    return; // 함수 실행 중지
  }
  if (selectedCategory.value === null) {
    // 카테고리가 선택되지 않았을 경우 경고 메시지 표시
    alert("카테고리를 선택해주세요.");
    return; // 함수 실행 중지
  }
  // 카테고리가 선택되었을 경우 친구와 매치 모달을 표시
  isFriendMatchingListVisible.value = true;
  console.log(isFriendMatchingListVisible.value);
  console.log("친구매칭");
  modalType.value = "friendMatching";
  console.log(modalType.value);
};

// 친구 선택
const handleFriendSelect = (friend) => {
  console.log(friend);
  selectedFriendId.value = friend.userId;
  selectedFriendNickname.value = friend.userNickname;
  console.log(selectedFriendNickname.value);
  isFriendMatchingMessageVisible.value = true;
  isFriendMatchingListVisible.value = false;
  closeFriendsList();
};

const closeFriendsList = () => {
  isFriendMatchingListVisible.value = false;
  resetHasChallengeCount();
};

onMounted(() => {
  userMatchingStatus();
  resetHasChallengeCount();
});

const selectCategory = (category) => {
  // 이미 비활성화된 카테고리를 선택하려고 할 경우 아무런 동작도 하지 않음
  if (disabledCategories.value.includes(category.id)) {
    alert("이미 매칭 요청을 한 카테고리입니다.");
    return;
  }

  // 선택된 카테고리를 토글
  selectedCategory.value =
    selectedCategory.value === category.id ? null : category.id;
  selectedCategoryName.value =
    selectedCategoryName.value === category.name ? null : category.name;
};
</script>

<style scoped>
.hasChallengeCount-warp {
  display: flex;
  justify-content: end;
  padding-right: 25px;
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
}
.hasChallengeCount-box {
  font-size: 1.6rem;
  margin-block: 5px;
  background-color: #a7c3df;
  padding: 10px;
  border-radius: 15px;
}
.select_category {
  background-color: #f8f9fb;
  /* border: 2px solid #ffffff; 테두리 색상을 흰색(#ffffff)으로 변경 */
  width: calc(100% - 64px);
  display: flex;
  flex-direction: column;
  border-radius: 25px;
  margin-bottom: 20px;
  padding: 30px;
  /* margin-top: 3%; */
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
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

.category_list_box_font {
  font-size: 1.4rem;
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
  /* margin-bottom: 5%; */

  /* margin-right: 2%; */
  width: calc(100% - 4px);
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
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
  gap: 10%;
  /* 버튼들을 균등하게 분배 */
  width: calc(100% - 40px);
  /* 컨테이너 너비를 전체로 설정 */
  padding-block: 20px;
}

.matching_btn {
  width: 60%;
  /* 원하는 너비로 조절 */
  height: 50px;
  border-radius: 25px;
  flex: 1;
  background-color: #e1ecf7;
  border-color: #e1ecf7;
  /* margin-left: 50px;
  margin-right: 50px; */
  border: none;
}

.matching_btn-item {
  font-size: 2rem;
}

.matching_btn:hover {
  background-color: #83b0e1;
  /* 마우스를 갖다댔을 때 배경색상 */
  color: white;
  /* 마우스를 갖다댔을 때 텍스트 색상 */
}

.main_box {
  width: calc(100% - 30px);
  display: flex;
  flex-direction: column;
  border: none;
  border-radius: 25px;
  margin: 30px 10px;
  background-color: #e1ecf7;
  padding: 0px;
}
.category-button.disabled {
  background-color: #ff5c5c;
  color: white;
  cursor: not-allowed;
}

span {
  font-family: "jua";
}

@media (max-width: 500px) {
  .category_list_box {
    flex-direction: column;
  }
  .matching_btn-item {
    font-size: 1.6rem;
  }
}
</style>
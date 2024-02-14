<template>
  <div v-if="showCategoryModal" class="mission-category-modal">
    <h2>{{ nowCategory }}</h2>
    
    <!-- 카테고리별 문구 표시 -->
    <div v-if="nowCategory">
      <template v-if="nowCategory === '기상'">ssafy입실시간을 찍어 인증해주세요!</template>
      <template v-else-if="nowCategory === '알고리즘'">알고리즘 문제를 하나 해결해 보세요!</template>
      <template v-else-if="nowCategory === '운동'">운동한 사진을 인증해보세요!</template>
      <template v-else-if="nowCategory === '스터디'">오늘 하루 공부한 시간을 인증해주세요!</template>
      <template v-else-if="nowCategory === '식단'">샐러드 빈그릇을 인증해주세요!</template>
      <template v-else-if="nowCategory === '절제'">오늘 무엇을 절제했는지 인증해주세요!</template>
      <template v-else>선택하신 도전에 최선을 다하세요!</template>
    </div>
    <button @click="closeCategoryModal">Close</button>
  </div>
  <!-- 메인 페이지 총 알림함 -->
  <div v-if="showModal" class="mainMailBox-body">
    <link rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <link rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <div class="mainMailBox-wrap">
      <div class="mainMailBox-title">
        <span class="material-symbols-outlined">
          local_post_office
        </span>
        받은 알림메세지
        <div>{{ mainmailList.length }} / {{ max_mail_cnt }}</div>
      </div>
      <div class="mainMailBox-list">
        <div v-for="(mail_item, index) in mainmailList" :key="index">
          <!-- 쌀로우 신청 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'follow'">
            <div class="mainMailBox-list-item-content">
              {{ mail_item.content }}
            </div>
            <button class="mainMailBox-list-item-clear">
              <span class="material-symbols-outlined"> close </span>
            </button>
          </div>


          <!-- 경쟁시작 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'matchChallenge'">
            <div class="mainMailBox-list-item-content">
              [{{ categoryMapping[mail_item.category_id] }}] {{ mail_item.other_user_nickname }}님과의 경쟁이 시작되었습니다.
            </div>
            <button class="mainMailBox-list-item-accept" @click="checkChallenge(mail_item.id)">
              미션확인
            </button>
            <button class="mainMailBox-list-item-clear">
              <span class="material-symbols-outlined"> close </span>
            </button>
          </div>




          <!-- 매칭 종료 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'closeChallenge'">
            <div class="mainMailBox-list-item-content">
              [{{ categoryMapping[mail_item.category_id] }}] {{ mail_item.other_nickname }}님과의 경쟁 결과가 나왔습니다.
            </div>
            <button class="mainMailBox-list-item-subBtn" @click="showResultModal(mail_item)">결과확인</button>
            <button class="mainMailBox-list-item-clear">
              <span class="material-symbols-outlined"> close </span>
            </button>
          </div>



          <!-- 보낸 도전장 확인 알림
        <div
          
          class="mainMailBox-list-item"
          v-if="mail_item.kind == 'sendChallenge'"
        >
          <div class="mainMailBox-list-item-content"></div>
          <button class="mainMailBox-list-item-clear"></button>
        </div> -->
        </div>
      </div>

      <div class="mainMailBox-closeBtn">
        <button @click="close_mainMailBox">닫기</button>
      </div>
    </div>
  </div>

  <div v-if="showWinModal" class="modal2">
    <p>{{ modalData.other_nickname }}님과의 경쟁에서 이겼습니다!</p>
    <img :src="winnerImage" alt="Winning Image" style="width: 100%; max-width: 300px;" />
    <button @click="closeModal">닫기</button>
  </div>

  <!-- '졌습니다' 모달 -->
  <div v-if="showLoseModal" class="modal2">
    <p>{{ modalData.other_nickname }}님과의 경쟁에서 졌습니다.</p>
    <img :src="loserImage" alt="losing Image" style="width: 100%; max-width: 300px;" />
    <button @click="closeModal">닫기</button>
  </div>
</template>

<script setup>
import { defineProps, ref, onMounted, watch, onUnmounted } from "vue";
import { useCompetionStore } from "@/stores/competition";
import { useUserStorageStore } from "@/stores/userStorage";
import { useLoginStore } from "@/stores/login"; // 로그인 스토어 사용
import winnerImage from '@/assets/winner.gif';
import loserImage from '@/assets/loser.gif';
const userStorage = useUserStorageStore();
const max_mail_cnt = ref(20);
const userLogin = useLoginStore();
const userCompet = useCompetionStore();
const showWinModal = ref(false);
const showLoseModal = ref(false);
const modalData = ref({});
const showCategoryModal = ref(false);
const nowCategory = ref("");
const props = defineProps({
  showModal: Boolean,
  close: Function,
});

const close_mainMailBox = () => {
  props.close();
};

//받은 도전장 함수 불러오기
const checkChallenge = (mailItemId) => {
  const selectedItem = mainmailList.value.find(item => item.id === mailItemId);
  if (selectedItem) {
    nowCategory.value = categoryMapping[selectedItem.category_id];
    showCategoryModal.value = true;
  } else {
    console.error("Selected mail item not found.");
  }
};
const closeCategoryModal = () => {
  showCategoryModal.value = false;
};
const categoryMapping = {
  1: '기상',
  2: '알고리즘',
  3: '운동',
  4: '스터디',
  5: '식단',
  6: '절제',
};

//받은 도전장 수락하기
// const acceptChallenge = (matchingId) => {
//   const selectedItem = mainmailList.value.find(item => item.matchingId === matchingId);
//   if (!selectedItem) {
//     console.error("선택된 도전장이 목록에 없습니다.");
//     return;
//   }
//   userCompet.competitionAnalysisCategory(userId, selectedItem.category)
//     .then(analysis => {
//       const categoryStatus = analysis.data.user_category.categoryStauts;
//       if (categoryStatus === "NONE_STATUS" || categoryStatus === "MATCH_STATUS") {
//         return userCompet.bothAccept(matchingId);
//       } else if (categoryStatus === "PLAY_STATUS") {
//         throw new Error("유효하지 않은 도전장입니다.");
//       }
//     })
//     .then(() => {
//       console.log("Challenge accepted:", matchingId);
//       // 도전과제 목록에서 해당 항목 제거
//       const index = mainmailList.value.findIndex(mail_item => mail_item.id === matchingId);
//       if (index !== -1) {
//         mainmailList.value.splice(index, 1);
//       }
//     })
//     .catch(error => {
//       if (error.message === "유효하지 않은 도전장입니다.") {
//         alert(error.message);
//         // 해당 리스트 항목 제거
//         const index = mainmailList.value.findIndex(item => item.matchingId === matchingId);
//         if (index !== -1) {
//           mainmailList.value.splice(index, 1);
//         }
//       } else {
//         console.error("Error processing challenge:", error);
//       }
//     });


// };
const mainmailList = ref([

]);

const showResultModal = (mailItem) => {
  modalData.value = mailItem;
  if (mailItem.compet_result === 'SEND_WIN' && mailItem.is_sender === 'Y' || mailItem.compet_result === 'RECEIVE_WIN' && mailItem.is_sender === 'Y' || mailItem.compet_result === 'BOTH_WIN') {
    showWinModal.value = true;
  } else {
    showLoseModal.value = true;
  }
};

const closeModal = () => {
  showWinModal.value = false;
  showLoseModal.value = false;
};

const fetchMailbox = () => {
  if (userStorage.getUserInformation().user_id) {
    userCompet.competitionFinish(userStorage.getUserInformation().user_id)
      .then(response => {

        const finishCompetitions = response.data.competitions.map(item => ({
          ...item,
          kind: 'closeChallenge'
        })); // 예시에서는 competitions 배열을 직접 할당합니다.
        console.log('잘 갖고왔습니다');
        userCompet.competitionProgressDetail(userStorage.getUserInformation().user_id)
          .then(response => {
            const progressCompetitions = response.data.competitions.map(item2 => ({
              ...item2,
              kind: 'matchChallenge'
            }));
            console.log('잘 갖고왔습니다.');
            mainmailList.value = [...finishCompetitions, ...progressCompetitions].reverse();
          })
          .catch(error => {
            console.error("Failed to fetch competition progress list:", error);
          });
      })
      .catch(err => {
        console.error("Failed to fetch competition finish list:", err);
      });


  }
};
onMounted(() => {
  // 로그인 상태 확인 후 데이터 로드
  if (userStorage.getUserInformation().user_id) {
    fetchMailbox();
  }
});
</script>

<style>
.mainMailBox-body {
  /* border: 2px solid red; */

  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 12;
}

.mainMailBox-wrap {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 40%;
  max-height: 40%;
  background: #fff;
  border-radius: 10px;
  padding-inline: 20px;
  padding-block: 15px;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  gap: 10px;
}

.mainMailBox-title {
  /* border: 2px solid orange; */
  display: flex;
  gap: 10px;
}

.mainMailBox-list {
  /* border: 2px solid orange; */
  border-radius: 5px;
  background-color: #e1ecf7;
  display: flex;
  padding-inline: 5px;
  padding-block: 5px;
  flex-direction: column;
  overflow: auto;
  gap: 5px;
}

.mainMailBox-list::-webkit-scrollbar {
  width: 10px;
}

.mainMailBox-list::-webkit-scrollbar-thumb {
  background-color: #71a5de;
  border-radius: 10px;
  background-clip: padding-box;
}

.mainMailBox-list::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
  border-radius: 10px;
}

.mainMailBox-list-item {
  /* border: 2px solid yellow; */
  background-color: #aecbeb;
  border-radius: 5px;
  padding-block: 3px;
  padding-inline: 5px;
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.mainMailBox-list-item-content {
  /* border: 2px solid green; */

  flex-grow: 1;
}

.mainMailBox-list-item-subBtn {
  /* border: 2px solid blue; */
}

.mainMailBox-list-item-clear {
  /* border: 2px solid purple; */
  display: flex;
  justify-content: center;
  align-items: center;
  width: 24px;
  height: 24px;
}

.mainMailBox-closeBtn {
  display: flex;
  justify-content: end;
}

.modal2 {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
  padding: 30px;
  border-radius: 15px;
  z-index: 100;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 80%;
  max-width: 400px;
  text-align: center;
  border: 2px solid #007BFF;
}

.mission-category-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: auto;
  max-width: 600px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  z-index: 100;
}

.mission-category-modal h2 {
  margin-top: 0;
  color: #333;
  font-size: 24px;
  text-align: center;
}

.mission-category-modal p {
  margin: 20px 0;
  font-size: 18px;
  line-height: 1.5;
  text-align: center;
}

.mission-category-modal button {
  display: block;
  margin: 20px auto;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.mission-category-modal button:hover {
  background-color: #0056b3;
}
</style>
<template>
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


          <!-- 도전장 도착 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'reciveChallenge'">
            <div class="mainMailBox-list-item-content">
              {{ mail_item.content }}
            </div>
            <button class="mainMailBox-list-item-accept" @click="acceptChallenge(mail_item.id)">
              수락
            </button>
            <button class="mainMailBox-list-item-clear">
              <span class="material-symbols-outlined"> close </span>
            </button>
          </div>




          <!-- 매칭 종료 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'closeChallenge'">
            <div class="mainMailBox-list-item-content">
              {{ mail_item.other_nickname }}님과의 경쟁 결과가 나왔습니다.
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

  <div v-if="showWinModal" class="modal">
    <p>{{ modalData.other_nickname }}님과의 경쟁에서 이겼습니다!</p>
    <img :src="winnerImage" alt="Winning Image" style="width: 100%; max-width: 300px;" />
    <button @click="closeModal">닫기</button>
  </div>

  <!-- '졌습니다' 모달 -->
  <div v-if="showLoseModal" class="modal">
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

const props = defineProps({
  showModal: Boolean,
  close: Function,
});

const close_mainMailBox = () => {
  props.close();
};

//받은 도전장 함수 불러오기


// onMounted(() => {



// });
const categoryMapping = {
  1: '기상',
  2: '알고리즘',
  3: '운동',
  4: '스터디',
  5: '식단',
  6: '절제',
};

//받은 도전장 수락하기
const acceptChallenge = (matchingId) => {
  const selectedItem = mainmailList.value.find(item => item.matchingId === matchingId);
  if (!selectedItem) {
    console.error("선택된 도전장이 목록에 없습니다.");
    return;
  }
  userCompet.competitionAnalysisCategory(userId, selectedItem.category)
    .then(analysis => {
      const categoryStatus = analysis.data.user_category.categoryStauts;
      if (categoryStatus === "NONE_STATUS" || categoryStatus === "MATCH_STATUS") {
        return userCompet.bothAccept(matchingId);
      } else if (categoryStatus === "PLAY_STATUS") {
        throw new Error("유효하지 않은 도전장입니다.");
      }
    })
    .then(() => {
      console.log("Challenge accepted:", matchingId);
      // 도전과제 목록에서 해당 항목 제거
      const index = mainmailList.value.findIndex(mail_item => mail_item.id === matchingId);
      if (index !== -1) {
        mainmailList.value.splice(index, 1);
      }
    })
    .catch(error => {
      if (error.message === "유효하지 않은 도전장입니다.") {
        alert(error.message);
        // 해당 리스트 항목 제거
        const index = mainmailList.value.findIndex(item => item.matchingId === matchingId);
        if (index !== -1) {
          mainmailList.value.splice(index, 1);
        }
      } else {
        console.error("Error processing challenge:", error);
      }
    });


};
const mainmailList = ref([

]);

const showResultModal = (mailItem) => {
  modalData.value = mailItem;
  if (mailItem.compet_result === 'SEND_WIN' && mailItem.is_sender === 'Y' || mailItem.compet_result === 'RECEIVE_WIN' && mailItem.is_sender === 'Y'|| mailItem.compet_result === 'BOTH_WIN') {
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

        mainmailList.value = response.data.competitions.map(item => ({
          ...item,
          kind: 'closeChallenge'
        })); // 예시에서는 competitions 배열을 직접 할당합니다.
        console.log('잘 갖고왔습니다');
        console.log(mainmailList.value);
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

]);
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

.modal {
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
</style>
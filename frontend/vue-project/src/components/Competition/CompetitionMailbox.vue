<template>
  <div class="mail_box_body">
    <div class="main_box">
      <div class="mailbox_title">
        <img
          src="@/assets/mailbox.png"
          alt="Mailbox Icon"
          class="mailbox_icon"
        />
        <span>받은 도전장함 [ {{ mailParameters.length }} / 20 ]</span>
      </div>
      <div class="mail_box">
        <!-- 여기서 v-for 디렉티브를 사용하여 각 메일 항목을 반복 렌더링합니다. -->
        <CompetitionMailboxItem
          v-for="(item, index) in mailParameters"
          :key="index"
          :mail-sender="item.nickname"
          :mail-category="item.category"
          :mail-remain-time="item.remainingTime"
          :match-kind="item.matchkind"
          @acceptChallenge="() => acceptChallenge(index)"
        />
      </div>
    </div>
  </div>
  <PopUpReceiverCompetitionApprove
    v-if="showModal"
    :now-category="selectedCategorytoauth"
    @close="() => (showModal = false)"
  />
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useCompetionStore } from "@/stores/competition";
import mailbox from "@/assets/mailbox.png";
import { useUserStorageStore } from "@/stores/userStorage";
import CompetitionMailboxItem from "./CompetitionMailboxItem.vue";
import PopUpReceiverCompetitionApprove from "@/components/PopUp/PopUpReceiverCompetitionApprove.vue";
const competitionStore = useCompetionStore();
const userStorage = useUserStorageStore();
const mailParameters = ref([]);
const userInformation = userStorage.getUserInformation();
const userId = userInformation.user_id;
// const matchingId = ref(null);
// const category = ref(null);
// const expirationTime = ref(null);
// const nickname = ref(null);
// const matchkind = ref(null);
// const categorynum = ref(null);
// const kind = ref(null);
// const content = ref(null);

const mapCategoryIdToName = (categoryId) => {
  const categoryNames = {
    1: "기상",
    2: "알고리즘",
    3: "운동",
    4: "스터디",
    5: "식단",
    6: "절제",
  };

  return categoryNames[categoryId] || "알 수 없는 카테고리";
};
const calculateRemainingTime = (expirationTimeString) => {
  const expirationTime = new Date(expirationTimeString);
  const currentTime = new Date();
  const remainingTimeMillis = expirationTime - currentTime;

  // Check if the remaining time is negative or zero
  if (remainingTimeMillis <= 0) {
    return "만료"; // Expired
  }

  const remainingHours = Math.floor(remainingTimeMillis / (1000 * 60 * 60));
  const remainingMinutes = Math.floor(
    (remainingTimeMillis % (1000 * 60 * 60)) / (1000 * 60)
  );
  return `${remainingHours}시간 ${remainingMinutes}분`;
};

function reset_mailBox() {
  // console.log('도전장 최신화 하기', new Date());
  const userId = userStorage.getUserInformation().user_id;
  
  if (userId != null) {
    competitionStore
      .competitionMailbox(userId)
      .then((response) => {
        const mailbox = response.data.matching.map((item) => ({
          matchingId: item.matching_id,
          category: mapCategoryIdToName(item.category_id),
          categorynum: item.category_id,
          expirationTime: item.compet_expriation_time,
          remainingTime: calculateRemainingTime(item.compet_expiration_time),
          nickname: item.sender_nickname,
          matchkind: item.compet_kind,
          kind: "reciveChallenge",
          content: `[${item.compet_kind}]${
            item.user_nickname
          }님이 당신에게 ${mapCategoryIdToName(
            item.category_id
          )}를 신청하였습니다. 만료시간: ${item.competExpriationTime}`,
        }));
        mailParameters.value = mailbox;
        // console.log('도전장 잘 갖고왔다');
        // console.log(matchingId);
      })
      .catch((error) => {
        console.error("도전장을 갖고오지 못했습니다", error);
      });
  }
}

onMounted(() => {
  // reset_mailBox();

  setInterval(reset_mailBox, 30000);
});
const showModal = ref(false);
const selectedCategorytoauth = ref("");

const acceptChallenge = (index) => {
  return new Promise((resolve, reject) => {
    const item = mailParameters.value[index];
    const expirationTime = new Date(item.compet_expiration_time);
    const currentTime = new Date();

    // 도전장의 만료 시간을 체크하여 만료된 경우 경고창을 표시하고 수락 버튼을 비활성화
    if (expirationTime <= currentTime) {
      alert("유효시간이 만료되어 수락할 수 없습니다.");
      reject(new Error("Challenge expired"));
      return;
    }
    let categoryStatus;
    // 도전장의 카테고리 번호와 사용자 ID를 이용하여 경기 상태를 분석
    competitionStore
      .competitionAnalysisCategory(userId, item.categorynum)
      .then((response) => {
        categoryStatus = response.data.user_category.categoryStatus;
        // console.log(categoryStatus);
        if (categoryStatus === "PLAY_STATUS") {
          // 경기가 이미 진행 중인 경우
          alert("이미 진행 중인 경기입니다. 도전장을 수락할 수 없습니다.");
          reject(new Error("Challenge already in progress"));
        } else {
          // 경기가 진행 중이 아닌 경우, 도전장을 수락하고 팝업을 표시
          competitionStore
            .bothAccept(item.matchingId)
            .then(() => {
              selectedCategorytoauth.value = item.category;
              showModal.value = true;
              // console.log(`showModal is now ${showModal.value}`);
              resolve();
            })
            .catch((error) => {
              console.error("Error accepting challenge:", error);
              alert("유효하지 않은 도전장입니다.");
              reject(error);
            });
        }
      })
      .catch((error) => {
        console.error("Error analyzing competition status:", error);
        alert("유효하지 않은 도전장입니다.");
        reject(error);
      });
  });
};

// const acceptChallenge = async (index) => {
//   const item = mailParameters.value[index];
//   const acceptUser = {
//     userId: userId,
//     matchingId: item.matchingId,
//   };
//   try {
//     let isAccepted = false; // 수락 성공 여부를 추적하기 위한 변수
//     if (item.competKind === "random") {
//       await competitionStore.randomAccept(acceptUser);
//       isAccepted = true;
//     } else if (item.competKind === "friend") {
//       await competitionStore.friendAccept(acceptUser);
//       isAccepted = true;
//     }

//     if (isAccepted) {
//       console.log(`인덱스 ${index}의 도전을 수락했습니다.`);
//       // 해당 아이템을 배열에서 제거
//       mailParameters.value.splice(index, 1);
//     }
//   } catch (error) {
//     console.error("도전 수락 중 오류 발생:", error);
//   }
// };
</script>

<style scoped>
.main_box {
  background-color: #e1ecf7;
  border: none;
  width: calc(100% - 44px);
  display: flex;
  flex-direction: column;
  max-height: 400px;
  flex-grow: 1;
  padding: 20px;
  border-radius: 25px;
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
}

.mailbox_title {
  display: flex;
  align-items: center;
  font-size: 1.4rem;
}

.mailbox_icon {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.mail_box {
  background-color: #e1ecf7;
  border: none;
  max-height: 100%;
  overflow: auto;
  /* border-radius: 25px; */
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;

  padding: 10px;
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
  width: calc(100% - 24px);
  /*Set width to 100% */
}

.mail_box::-webkit-scrollbar {
  width: 10px;
}

.mail_box::-webkit-scrollbar-thumb {
  background-color: #71a5de;
  border-radius: 10px;
  background-clip: padding-box;
}

.mail_box::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
  border-radius: 10px;
}

.mail_box_body {
  height: auto;
  margin: 0;
  display: flex;
}
</style>

<!-- display: flex; /* Flex 컨테이너 설정 */
flex-direction: column; /* 자식 요소들을 세로 방향으로 정렬 */
align-items: stretch; /* 자식 요소들이 부모 요소의 크기에 맞게 늘어나도록 설정 */
margin: 0;
height: auto; -->
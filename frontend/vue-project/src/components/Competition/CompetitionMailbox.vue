<template>
  <div class="mail_box_body">
    <div class="main_box">
      <div class="mailbox_title">
        <img src="@/assets/mailbox.png" alt="Mailbox Icon" class="mailbox_icon" />
        <span>받은 도전장함 [ {{ mailParameters.length }} / 20 ]</span>
      </div>
      <div class="mail_box">
        <div v-for="(item, index) in mailParameters" :key="index">
          <CompetitionMailboxItem :mail_sender="item.is_sender" :mail_category="mapCategoryIdToName(item.category_id)"
            :mail_remain_time="item.compet_expiration_time" @acceptChallenge="() => acceptChallenge(index)" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useCompetionStore } from "@/stores/competition";
import mailbox from "@/assets/mailbox.png";
import { useUserStorageStore } from "@/stores/userStorage";
import CompetitionMailboxItem from "./CompetitionMailboxItem.vue";

const competitionStore = useCompetionStore();
const userStorage = useUserStorageStore();
const mailParameters = ref([
  // {
  //   is_sender: "John Doe",
  //   category_id: 1,
  //   compet_expiration_time: "02:00",
  // },
  // {
  //   is_sender: "Alice Smith",
  //   category_id: 2,
  //   compet_expiration_time: "02:00",
  // },
  // {
  //   is_sender: "Alice Smith",
  //   category_id: 2,
  //   compet_expiration_time: "02:00",
  // },
  // {
  //   is_sender: "Alice Smith",
  //   category_id: 2,
  //   compet_expiration_time: "02:00",
  // },
  // {
  //   is_sender: "Alice Smith",
  //   category_id: 2,
  //   compet_expiration_time: "02:00",
  // },
  // {
  //   is_sender: "Alice Smith",
  //   category_id: 2,
  //   compet_expiration_time: "02:00",
  // },
  // {
  //   is_sender: "Alice Smith",
  //   category_id: 2,
  //   compet_expiration_time: "02:00",
  // },
]);
const userInformation = userStorage.getUserInformation();
const userId = userInformation.user_id;

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
const removeExpiredChallenges = () => {
  const currentTime = new Date();
  mailParameters.value = mailParameters.value.filter((item) => {
    const expirationTime = new Date(item.compet_expiration_time); // compet_expiration_time 형식 확인 필요
    return expirationTime > currentTime;
  });
};
onMounted(() => {
  const userId = userStorage.getUserInformation().user_id;
  competitionStore.competitionMailbox(userId)
    .then(response => {
      const mailbox = response.data.matching.map(item => ({
        matchingId: item.matchingId,
        // category: categoryMapping[item.category_id], // 이전 코드
        category: mapCategoryIdToName(item.category_id), // 수정된 코드
        expirationTime: item.competExpriationTime,
        nickname: item.userNickname,
        matchkind: item.competKind,
        kind: 'reciveChallenge',
        content: `[${item.competKind}]${item.userNickname}님이 당신에게 ${mapCategoryIdToName(item.category_id)}를 신청하였습니다. 만료시간: ${item.competExpriationTime}`,
      }));
      mailParameters.value = mailbox;
      console.log('도전장 잘 갖고왔따');
      setInterval(removeExpiredChallenges, 60000);
    })
    .catch(error => {
      console.error("도전장을 갖고오지 못했습니다", error);
    });
});
const acceptChallenge = (matchingId) => {
  competitionStore.bothAccept(matchingId)
    .then(() => {
      console.log("Challenge accepted:", matchingId);
      // 도전과제 목록에서 해당 항목 제거
      const index = mainmailList.value.findIndex(mail_item => mail_item.id === matchingId);
      if (index !== -1) {
        mainmailList.value.splice(index, 1);
      }
    })
    .catch(error => {
      console.error("Error accepting challenge:", error);
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
  border: 2px solid #83b0e1;
  width: calc(100% - 44px);
  display: flex;
  flex-direction: column;
  max-height: 400px;
  flex-grow: 1;
  padding: 20px;
  border-radius: 25px;
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
  margin: 0 10px;
}

.mailbox_title {
  display: flex;
  align-items: center;
}

.mailbox_icon {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.mail_box {
  background-color: #e1ecf7;
  border: 2px solid #aecbeb;
  max-height: 100%;
  overflow: auto;
  /* border-radius: 25px; */
  border-top-left-radius: 25px;
  border-top-right-radius: 8px;
  border-bottom-left-radius: 25px;
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
  height: 380px;
  margin: 0;
  display: flex;
}
</style>
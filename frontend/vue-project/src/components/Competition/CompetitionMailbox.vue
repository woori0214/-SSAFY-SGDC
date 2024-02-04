<template>
  <div class="main_box">
    <div class="mailbox_title">
      <img src="@/assets/mailbox.png" alt="Mailbox Icon" class="mailbox_icon" />
      <span>받은 도전장함 [ {{ mailParameters.length }} / 20 ]</span>
    </div>
    <div class="mail_box">
      <div v-for="(item, index) in mailParameters" :key="index">
        <CompetitionMailboxItem
          :mail_sender="item.is_sender"
          :mail_category="mapCategoryIdToName(item.category_id)"
          :mail_remain_time="item.compet_expiration_time"
          @acceptChallenge="() => acceptChallenge(index)"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useCompetionStore } from "@/stores/competition";
import mailbox from "@/assets/mailbox.png";
import { useUserStorageStore } from "@/stores/userStorage";
import CompetitionMailboxItem from "./CompetitionMailboxItem.vue";

const competitionStore = useCompetionStore();
const userStorage = useUserStorageStore();
const mailParameters = ref([
  {
    is_sender: "John Doe",
    category_id: 1,
    compet_expiration_time: "02:00",
  },
  {
    is_sender: "Alice Smith",
    category_id: 2,
    compet_expiration_time: "02:00",
  },
  {
    is_sender: "Alice Smith",
    category_id: 2,
    compet_expiration_time: "02:00",
  },
  {
    is_sender: "Alice Smith",
    category_id: 2,
    compet_expiration_time: "02:00",
  },
  {
    is_sender: "Alice Smith",
    category_id: 2,
    compet_expiration_time: "02:00",
  },
  {
    is_sender: "Alice Smith",
    category_id: 2,
    compet_expiration_time: "02:00",
  },
  {
    is_sender: "Alice Smith",
    category_id: 2,
    compet_expiration_time: "02:00",
  },
]);
const userInformation = userStorage.getUserInformation();
const userId = userInformation.user_Id;

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

const acceptChallenge = async (index) => {
  const item = mailParameters.value[index];
  const acceptUser = {
    userId: userId,
    matchingId: item.matchingId,
  };
  try {
    if (item.competKind === "경쟁") {
      await competitionStore.randomAccept(acceptUser);
    } else if (item.competKind === "친구") {
      await competitionStore.friendAccept(acceptUser);
    }
    console.log(`인덱스 ${index}의 도전을 수락했습니다.`);
  } catch (error) {
    console.error("도전 수락 중 오류 발생:", error);
  }
};
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
  width: calc(100% - 24px); /* Set width to 100% */
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
</style>
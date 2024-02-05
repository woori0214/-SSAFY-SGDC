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

          <!-- 매칭이 완료되어 매칭 내용을 확인하는 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'getChallenge'">
            <div class="mainMailBox-list-item-content">
              {{ mail_item.content }}
            </div>
            <button class="mainMailBox-list-item-subBtn">미션확인</button>

            <button class="mainMailBox-list-item-clear">
              <span class="material-symbols-outlined"> close </span>
            </button>
          </div>

          <!-- 매칭 종료 알림 -->
          <div class="mainMailBox-list-item" v-if="mail_item.kind == 'completeChallenge'">
            <div class="mainMailBox-list-item-content">
              {{ mail_item.content }}
            </div>
            <button class="mainMailBox-list-item-subBtn">결과확인</button>
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
</template>

<script setup>
import { defineProps, ref } from "vue";

const max_mail_cnt = ref(20);

const props = defineProps({
  showModal: Boolean,
  close: Function,
});

const close_mainMailBox = () => {
  props.close();
};

const mainmailList = ref([
  {
    kind: "follow",
    content: "나나양(이)가 당신을 팔로우 했습니다",
  },
  {
    kind: "reciveChallenge",
    content: "고차비(이)가 당신에게 [알고리즘]을 신청했습니다.",
  },
  {
    kind: "getChallenge",
    content: "김뚜띠(이)가 도전장을 수락했습니다.",
  },
  {
    kind: "completeChallenge",
    content: "램램(이)과의 [스터디] 경쟁이 끝났습니다.",
  }, {
    kind: "follow",
    content: "나나양(이)가 당신을 팔로우 했습니다",
  },
  {
    kind: "reciveChallenge",
    content: "고차비(이)가 당신에게 [알고리즘]을 신청했습니다.",
  },
  {
    kind: "getChallenge",
    content: "김뚜띠(이)가 도전장을 수락했습니다.",
  },
  {
    kind: "completeChallenge",
    content: "램램(이)과의 [스터디] 경쟁이 끝났습니다.",
  }, {
    kind: "follow",
    content: "나나양(이)가 당신을 팔로우 했습니다",
  },
  {
    kind: "reciveChallenge",
    content: "고차비(이)가 당신에게 [알고리즘]을 신청했습니다.",
  },
  {
    kind: "getChallenge",
    content: "김뚜띠(이)가 도전장을 수락했습니다.",
  },
  {
    kind: "completeChallenge",
    content: "램램(이)과의 [스터디] 경쟁이 끝났습니다.",
  },
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
</style>
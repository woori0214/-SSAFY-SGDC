<template>
  <div class="mail">
    <div class="mail_content">
      <div class="mail_content_text">
        {{ mail_sender }}가 {{ mail_category }}를 신청하였습니다.
      </div>
      <div class="mail_content_details">
        <div class="mail_content_time">
          남은시간: {{ mail_remain_time }}
          <button class="accept_btn" @click="acceptChallenge">수락</button>
        </div>
      </div>
    </div>
    <PopUpReceiverCompetitionApprove :showModal="showChallengeAcceptBox" :close="closeChallengeAcceptBox" />
  </div>
</template>

<script>
import { ref } from "vue";
import PopUpReceiverCompetitionApprove from "../PopUp/PopUpReceiverCompetitionApprove.vue";

export default {
  name: "mail-item",
  props: {
    mail_sender: {
      type: String,
      default: "XXX",
    },
    mail_category: {
      type: Number,
      default: "X",
    },
    mail_remain_time: {
      type: String,
      default: "[??:??]",
    },
  },
  methods: {
    acceptChallenge() {
      this.$emit("acceptChallenge");
      this.popUpChallengeAcceptBox();
    },
  },
  components: {
    PopUpReceiverCompetitionApprove,
  },
  setup() {
    const showChallengeAcceptBox = ref(false);

    const popUpChallengeAcceptBox = () => {
      showChallengeAcceptBox.value = true;
    };

    const closeChallengeAcceptBox = () => {
      showChallengeAcceptBox.value = false;
    };

    return {
      showChallengeAcceptBox,
      popUpChallengeAcceptBox,
      closeChallengeAcceptBox,
    }
  },
};
</script>

<style>
.mail {
  display: flex;
  margin: 10px;
  gap: 10px;
  padding: 15px;
  background-color: #ffffff;
  border-radius: 25px;
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
}

.mail_content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.mail_content_text {
  font-size: 16px;
}

.accept_btn {
  background-color: #e1ecf7;
  /* 기본 배경색 변경 */
  color: #000000;
  /* 기본 텍스트 색 변경 */
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease-in-out, color 0.3s ease-in-out;
  /* 부드러운 색상 전환 추가 */
}

.accept_btn:hover {
  background-color: #83b0e1;
  /* 호버 시 배경색 변경 */
  color: #ffffff;
  /* 호버 시 텍스트 색 변경 */
}</style>

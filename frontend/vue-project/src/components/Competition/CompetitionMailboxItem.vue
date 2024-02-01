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
    <PopUpReciverCompetAcceptModal
      :showModal="isAcceptChallenge"
      :close="closeAcceptChallengeModal"
    />
  </div>
</template>

<script>
import { ref } from "vue";
import PopUpReciverCompetAcceptModal from "@/components/PopUp/PopUpReceiverCompetitionApprove.vue";

export default {
  name: "mail-item",
  props: {
    mail_sender: {
      type: String,
      default: "XXX",
    },
    mail_category: {
      type: Number,
      default: 0,
    },
    mail_remain_time: {
      type: String,
      default: "[??:??]",
    },
    mail_id: {
      type: Number,
      default: 12312,
    },
  },
  methods: {
    // acceptChallenge() {
    //   this.$emit("acceptChallenge");
    //   this.isAcceptChallenge.value = true;
    // },
    // closeAcceptChallengeModal() {
    //   this.isAcceptChallenge.value = false;
    // }
  },
  components: {
    PopUpReciverCompetAcceptModal,
  },
  setup({emit}) {
    const isAcceptChallenge = ref(false);

    const acceptChallenge = () => {
      $emit("acceptChallenge");
      isAcceptChallenge.value = true;
      console.log(isAcceptChallenge.value);
    };

    const closeAcceptChallengeModal = () => {
      isAcceptChallenge.value = false;
    };

    return {
      isAcceptChallenge,
      acceptChallenge,
      closeAcceptChallengeModal,
    };
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
  background-color: #25aae1;
  color: #ffffff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}
</style>

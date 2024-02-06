<template>
  <div class="competition-mailbox-item">
    <div class="mail-item-content">
      <p>{{ mail_sender }}님이 {{ mail_category }}를 신청하였습니다.</p>
      <p>만료시간: {{ mail_remain_time }}</p>
    </div>
    <button class="accept-button" @click="emitAcceptChallenge">수락</button>
  </div>
</template>

<script>
export default {
  name: 'CompetitionMailboxItem',
  props: {
    mail_sender: {
      type: String,
      required: true
    },
    mail_category: {
      type: String,
      required: true
    },
    mail_remain_time: {
      type: String,
      required: true
    },
  },
  methods: {
    emitAcceptChallenge() {
      this.$emit('acceptChallenge');
    }
  }
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
}
</style>

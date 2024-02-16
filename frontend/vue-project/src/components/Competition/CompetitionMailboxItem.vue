<template>
  <div class="competition-mailbox-item">
    <div class="mail-item-content">
      <span>[{{matchKind}}] {{ mailSender }}님이 {{ mailCategory }}을(를) 신청하였습니다.</span>
      
    </div>
    <span class="mail-item-content-time" :style="(mailRemainTime == '만료' ? 'color: red' : '')">유효 시간: {{ mailRemainTime }}</span>
    <button v-if="mailRemainTime != '만료'"
      class="accept_button" 
      
      @click="emitAcceptChallenge">
      수락
    </button>
  </div>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
  mailSender: String,
  mailCategory: String,
  mailRemainTime: String,
  
  matchKind: String,
});

const emit = defineEmits(['acceptChallenge']);



const emitAcceptChallenge = () => {

    emit('acceptChallenge');
  
};
</script>

<style>
.competition-mailbox-item {
  display: flex;
  align-items: center; /* 세로 중앙 정렬을 위해 추가 */
  margin: 10px;
  gap: 20px; /* 내부 요소 간의 간격 조정 */
  padding: 15px;
  background-color: #ffffff;
  border-radius: 25px;
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.1);
}

.mail-item-content {
  flex-grow: 1; /* 내용이 더 많은 공간을 차지하도록 설정 */
  font-size: 1.3rem;
}

.mail-item-content-time{
  font-size: 1.3rem;
}

.accept_button { /* 클래스 명이 .accept_btn에서 .accept_button으로 변경 */
  background-color: #e1ecf7;
  color: #000000;
  border: none;
  padding: 10px 20px; /* 버튼 내부 패딩 조정 */
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;

}

.accept_button:hover { /* 클래스 명이 .accept_btn에서 .accept_button으로 변경 */
  background-color: #83b0e1;
  color: #ffffff;
}

</style>

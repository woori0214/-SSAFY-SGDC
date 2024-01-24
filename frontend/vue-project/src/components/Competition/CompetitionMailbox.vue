<template>
  <div class="main_box">
    <div>받은 도전장함 [ {{ mailParameters.length }} / 20 ]</div>
    <div class="mail_box">
      <div v-for="(item, index) in mailParameters" :key="index">
        <CompetitionMailboxItem :mail_sender="item.is_sender" :mail_category="item.category_id"
          :mail_remail_time="item.compet_expiration_time">
        </CompetitionMailboxItem>
        <button @click="acceptChallenge(index)">수락</button>
      </div>



    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useCompetionStore } from '@/stores/competition';
import { useLoginStore } from '@/stores/login'
import CompetitionMailboxItem from './CompetitionMailboxItem.vue';

const competitionStore = useCompetionStore();
const login = useLoginStore();
const mailParameters = ref([]);
const userId = login.loginUser;
onMounted(async () => {
  try {
    const response = await competitionStore.competitionMailbox(userId); // '사용자ID'는 실제 사용자 ID로 교체
    mailParameters.value = response.data.matching;
  } catch (error) {
    console.error('Error fetching competition mailbox:', error);
  }
});

const acceptChallenge = async (item, index) => {
  const acceptUser = {
    userId: userId,
    matchingId: mailParameters.value.matchingId,
  }
  try {
    if (item.competKind === '경쟁') {

      await competitionStore.randomAccept(acceptUser); // Assuming a method like postSomeValue exists in your competitionStore
    } else if (item.competKind === '친구') {

      await competitionStore.friendAccept(acceptUser); // Assuming a method like postAnotherValue exists in your competitionStore
    }
    // Add more conditions based on response.competKind values
    console.log(`Challenge accepted for index ${index}`);
  } catch (error) {
    console.error('Error accepting challenge:', error);
  }
};
</script>

<style scoped>
.main_box {
  border: 2px solid blue;
  width: 95%;
  margin: auto;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.mail_box {
  border: 2px solid skyblue;
  max-height: 400px;
  overflow: auto;
}
</style>

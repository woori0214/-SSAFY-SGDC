<template>
  <div class="main_box">
    <div>받은 도전장함 [ {{ mailParameters.length }} / 20 ]</div>
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
import { ref } from 'vue';
import { useCompetionStore } from '@/stores/competition';
import { useLoginStore } from '@/stores/login'
import CompetitionMailboxItem from './CompetitionMailboxItem.vue';

const competitionStore = useCompetionStore();
const login = useLoginStore();
const mailParameters = ref([
  {
    is_sender: 'John Doe',
    category_id: 1,
    compet_expiration_time: '02:00',
  },
  {
    is_sender: 'Alice Smith',
    category_id: 2,
    compet_expiration_time: '02:00',
  },
]);

const userId = login.loginUser;

const mapCategoryIdToName = (categoryId) => {
  const categoryNames = {
    1: '기상',
    2: '알고리즘',
    3: '운동',
    4: '스터디',
    5: '식단',
    6: '절제',
  };

  return categoryNames[categoryId] || '알 수 없는 카테고리';
};

const acceptChallenge = async (index) => {
  const item = mailParameters.value[index];
  const acceptUser = {
    userId: userId,
    matchingId: item.matchingId,
  }
  try {
    if (item.competKind === '경쟁') {
      await competitionStore.randomAccept(acceptUser);
    } else if (item.competKind === '친구') {
      await competitionStore.friendAccept(acceptUser);
    }
    console.log(`인덱스 ${index}의 도전을 수락했습니다.`);
  } catch (error) {
    console.error('도전 수락 중 오류 발생:', error);
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
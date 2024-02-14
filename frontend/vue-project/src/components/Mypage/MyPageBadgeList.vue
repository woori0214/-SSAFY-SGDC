<template>
  <div>
    <div class="accordion-header" @click="toggleAccordion">
      <h2>보유 뱃지</h2>
      <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
    </div>
    <transition>
      <div class="badge-accordion-content" :class="{ 'open': isOpen }" v-show="isOpen">
        <div v-for="badge in badge_list" :key="badge.badgeName" class="badge">
          <img :src="getUserBadgeImage(badge)" alt="뱃지" class="badge_img">
          <h3 class="badge_info">{{ badge.badgeName }}</h3>
          <p>{{ badge.badgeDetail }}</p>
        </div>
      </div>
    </transition>
  </div>
</template>
  
<script>
import { ref, onMounted } from 'vue';
import { useBadgeStore } from '@/stores/badge';
import nobadgeimg from '@/assets/badges/badge_lock.png';


export default {
  props: ['userId',],

  setup(props) {
    const userId = ref(props.userId)
    const badgeStore = useBadgeStore();
    const badge_list = ref(badgeStore.badgeList)
    const user_badge = ref([]);

    // 사용자의 뱃지 이미지를 반환하는 함수
    const getUserBadgeImage = (badge) => {
      // user_badge가 정의되지 않았거나 비어 있는 경우를 처리
      if (!user_badge.value || user_badge.value.length === 0) {
        return nobadgeimg;
      }

      const userBadge = user_badge.value.find(userBadge => userBadge.badge_id === badge.badgeId);
      if (!userBadge) return nobadgeimg;
      // console.log(userBadge)
      return badge.badgeImg
      
    };
    // 페이지 열었을 때 정보 가져오기(유저 뱃지 리스트)
    onMounted(() => {
      badge_list.value = badgeStore.badgeList

      badgeStore.getUserBadgeList(userId.value)
        .then((res) => {
          // console.log('유저뱃지 가져옴')
          // console.log(res.data)
          user_badge.value = res.data.badges
          // console.log(user_badge.value)
        })
        .catch((err) => {
          // console.log('뱃지못가져옴')
          console.log(err)
        });
    });

    // 아코디언 펼치기/접기
    const isOpen = ref(true);

    const toggleAccordion = () => {
      isOpen.value = !isOpen.value;
    };


    return { userId, badge_list, user_badge, isOpen, getUserBadgeImage, toggleAccordion }
  }
};



</script>
  
<style scoped>
.badge {
  text-align: center;
  padding: 5px;
  margin: 5px; /* 뱃지 간 간격 조정 */
  width: 120px; /* 뱃지의 너비, 필요에 따라 조정 */
  margin-left: 20px;
  margin-right: 20px;
  margin-bottom: 0;
}

.badge_img {
  width: 100px;
  margin-bottom: 0;
}

.badge_info {
  margin-top: 0;
}

/* 아코디언 헤더 스타일링 */
.accordion-header {
  display: flex;
  align-items: center;
  background-color: #83b0e1;
  padding: 10px;
  cursor: pointer;
  border-radius: 15px;
}


.rotate-icon {
  transition: transform 0.3s ease;
  margin-left: auto;
  /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
  font-family: sans-serif;
}

.rotate-icon::before {
  content: '\25BC';
  /* 기본 화살표 아래 방향 유니코드 */
  display: inline-block;
}

.rotate-icon.rotate::before {
  content: '\25B2';
  /* 화살표 위 방향 유니코드 */
}

/* 아코디언 내용 스타일링 */
.badge-accordion-content {
  padding: 10px;
  border-top: none;
  overflow-x: hidden; /* 가로 스크롤바 비활성화, 필요에 따라 조정 */
  overflow-y: auto; /* 세로 스크롤바 자동 활성화 */
  max-height: 1000px; /* 내용에 따라 조절 가능 */
  display: flex;
  flex-direction: row; /* 내용을 가로 방향으로 정렬 */
  flex-wrap: wrap; /* 내용이 넘칠 경우 다음 줄로 넘김 */
}

.badge-accordion-content::-webkit-scrollbar {
  height: 5px;
}

.badge-accordion-content::-webkit-scrollbar-thumb {
  background-color: #71a5de;
  border-radius: 10px;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.badge-accordion-content.open {
  max-height: 1000px;
  /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
  transition: max-height 0.3s ease;
  overflow: scroll;
}
</style>
  
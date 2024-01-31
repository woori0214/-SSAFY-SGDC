<template>
  <div>
    <div class="accordion-header" @click="toggleAccordion">
      <h2>보유 뱃지</h2>
      <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
    </div>
    <div class="accordion-content" :class="{ 'open': isOpen }">
      <div v-for="badge in badge_list" :key="badge.badge_name" class="badge">
        <img :src="getUserBadgeImage(badge)" alt="뱃지" class="badge_img">
        <h3 class="badge_info">{{ badge.badge_name }}</h3>
        <p>{{ badge.badge_detail }}</p>
      </div>
    </div>
  </div>
</template>
  
<script>
import { ref, onMounted } from 'vue';
import { useBadgeStore } from '@/stores/badge';
import nobadgeimg from '@/assets/no_badge.png';

import wakebadge from '@/assets/badges/wake1.png';
import algobadge from '@/assets/badges/algo1.png';
import healthbadge from '@/assets/badges/health1.png';
import studybadge from '@/assets/badges/study1.png';
import dietbadge from '@/assets/badges/diet1.png';
import fightingbadge from '@/assets/badges/wake1.png';

export default {
  props: ['userId',],

  setup(props) {
    const userId = ref(props.userId)
    const badge = useBadgeStore();
    const badge_list = badge.badgeList
    const user_badge = ref([
      {
        badge_name: "기린이",
        badge_detail: "기상 챌린지",
        badge_img: wakebadge
      },
      {
        badge_name: "코끼리",
        badge_detail: "식단 챌린지",
        badge_img: dietbadge
      },
      {
        badge_name: "짐종국",
        badge_detail: "운동 챌린지",
        badge_img: healthbadge
      },
      {
        badge_name: "절제왕",
        badge_detail: "절제 챌린지",
        badge_img: fightingbadge
      },
    ]);

    // 사용자의 뱃지 이미지를 반환하는 함수
    const getUserBadgeImage = (badge) => {
      // 사용자의 뱃지 리스트를 순회하면서 뱃지 이름이 일치하는지 확인
      const userBadge = user_badge.value.find(userBadge => userBadge.badge_name === badge.badge_name);
      // 일치하는 뱃지가 있으면 해당 이미지를 반환, 없으면 기본 이미지 반환
      return userBadge ? userBadge.badge_img : nobadgeimg;
    };

    // 페이지 열었을 때 정보 가져오기(유저 뱃지 리스트)
    onMounted(() => {
      badge.getBadgeList()
        .then((res) => {
          console.log(res)
        })
        .catch((err) => {
          console.log(err)
        });

      badge.getUserBadgeList(userId)
        .then((res) => {
          console.log(res)
          user_badge.value = res.badge_List
        })
        .catch((err) => {
          console.log(err)
        });
    });

    // 아코디언 펼치기/접기
    const isOpen = ref(true);

    const toggleAccordion = () => {
      isOpen.value = !isOpen.value;
    };


    return { userId, badge, badge_list, user_badge, isOpen, getUserBadgeImage, toggleAccordion }
  }
};



</script>
  
<style scoped>
.badge {
  /* flex-basis: calc(100% / 6); /* 뱃지 너비를 전체 너비의 1/6로 설정 */
  /* max-width: calc(100% / 6); 최대 너비도 전체 너비의 1/6로 설정 */ 
  text-align: center;
  box-sizing: border-box; /* 패딩과 테두리를 너비에 포함 */
  padding: 5px; /* 뱃지 간격을 위한 패딩 추가 */
}

.badge_img {
  width: 100px;
  margin-left: 20px;
  margin-right: 20px;
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
.accordion-content {
  display: flex;
  justify-content: left;
  flex-wrap: wrap;
  padding: 10px;
  border-top: none;
  overflow: auto;
  max-height: 0;
  transition: max-height 0.3s ease;
  margin-top: 3px;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.accordion-content.open {
  max-height: 1000px;
  /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
  transition: max-height 0.3s ease;
}
</style>
  
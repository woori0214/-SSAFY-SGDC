<template>
  <div>
    <div class="category_grid">
      <div v-for="category in categories" :key="category.id" class="category_item">
        <!-- 버튼을 클릭하면 모달창으로 재확인-->
        <button @click="openModal(category)" class="solo_btn" :class="{ 'active': isActive(category.id) }" :disabled="isActive(category.id)">
          <img :src="category.img" alt="category">
          <div class="category_text">
            <div>{{ category.name }}</div>
            <div class="extra_text">{{ category.contents }}</div>
          </div>
        </button>
      </div>
    </div>

    <!-- 확인 모달 창 -->
    <div v-if="isModalOpen" class="modal">
      <div class="modal-content">
        <p>진짜로 도전하시겠습니까?</p>
        <button @click="confirmChallenge" class="check_btn">네</button>
        <button @click="closeModal" class="check_btn">아니오</button>
      </div>
    </div>

    <!-- PopUpProofPicture.vue 모달로 추가 -->
    <PopUpProofPicture :show="isTestModalOpen" @update:show="closeTestModal" @uploadImage="handleImageUpload"
      :selectedCategory="selectedCategory" :isSoloMode="true" />

  </div>
</template>
  
<script>
import { ref, onMounted, computed } from 'vue';
import { useSoloStore } from '@/stores/solo';
import { useLoginStore } from '@/stores/login';

import timerImage from '@/assets/timer.png';
import algoImage from '@/assets/algo.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';
import studyImage from '@/assets/study.png';
import healthImage from '@/assets/health.png';
import PopUpProofPicture from '@/components/PopUp/PopUpProofPicture.vue';

export default {
  // PopUpProofPicture.vue 컴포넌트로 등록
  components: {
    PopUpProofPicture
  },

  setup() {
    const solo = useSoloStore();
    const login = useLoginStore();
    const isModalOpen = ref(false);
    const isTestModalOpen = ref(false);
    const selectedCategory = ref(null);
    const user_id = login.loginUser;
    const soloTodayData = solo.soloTodayData

    console.log(soloTodayData)
    // 카테고리
    const categories = ref([
      {
        id: 1,
        name: '기상',
        contents: 'SSAFY 입실체크 빨리하기',
        img: timerImage
      },
      {
        id: 2,
        name: '알고리즘',
        contents: '알고리즘 1일 1문제 풀기',
        img: algoImage
      },
      {
        id: 3,
        name: '운동',
        contents: '운동 인증샷 찍기(헬스장, 공원 등)',
        img: healthImage
      },
      {
        id: 4,
        name: '스터디',
        contents: '하루 몇시간 공부했는지 인증하기',
        img: studyImage
      },
      {
        id: 5,
        name: '식단',
        contents: '10층 샐러드 빈그릇 인증샷 찍기',
        img: dietImage
      },
      {
        id: 6,
        name: '절제',
        contents: '오늘 나는 ㅇㅇㅇ을 참았다.',
        img: fightingImage
      },
    ]);

    // 페이지클릭 시 솔로 모드 내역(오늘) 함수 실행
    // onMounted(() => {
    //   solo
    //     .soloToday(user_id)
    //     .then(() => {
    //       // Now soloTodayData should be updated in the store
    //       console.log(solo.soloTodayData)
    //     })
    //     .catch((error) => {
    //       console.error('Error fetching soloTodayData:', error);
    //     });
    // });
    
    // 확인 모달창
    const openModal = (category) => {
      // console.log(category)
      selectedCategory.value = category;
      console.log(selectedCategory)
      isModalOpen.value = true;
    };
    // 확인 모달창
    const closeModal = () => {
      isModalOpen.value = false;
    };

    // 현황
    const soloStatusMap = computed(() => {
      const statusMap = {};
      soloTodayData.forEach(item => {
        statusMap[item.category_id] = item.solo_status;
      });
      return statusMap;
    });

    // 솔로모드 도전
    const confirmChallenge = () => {
      if (selectedCategory.value) {
        const challenge = { user_id: user_id, category_id: selectedCategory.value.id };
        solo.soloChallenge(challenge);
        openTestModal()
      }
      closeModal();
    };

    // 인증 모달창
    const openTestModal = () => {
      isTestModalOpen.value = true;
    };

    // 인증 모달창
    const closeTestModal = () => {
      isTestModalOpen.value = false;
    };

    // 이미지 업로드
    const handleImageUpload = (uploadedImageSrc) => {
      // 이미지 업로드 이벤트 핸들러
      console.log('이미지 업로드 완료:', uploadedImageSrc);
      const challengeData = {
        user_id: user_id,
        category_id: selectedCategory.value.id,
        isSoloMode: true,
        uploadedImage: uploadedImageSrc,
      };
      solo.soloAuth(challengeData);
      console.log(challengeData)
      closeTestModal();
    };

    // 버튼 활성화
    const isActive = (categoryId) => {
      return soloStatusMap.value[categoryId] === 1;
    };
    
    return {
      categories,
      openModal,
      closeModal,
      confirmChallenge,
      isModalOpen,
      isTestModalOpen,
      openTestModal,
      closeTestModal,
      handleImageUpload,
      selectedCategory,
      isActive,
    };
  },
};
</script>
  
<style scoped>
.category_grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 60px;
}

.category_item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100%;
  height: 100%;
  margin-top: 0%;

}

.solo_btn {
  padding: 10px;
  font-size: 16px;
  width: 100%;
  height: 0;
  padding-top: 100%;
  position: relative;
  cursor: pointer;
}

button img {
  position: absolute;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 50%;
  max-height: 50%;
  margin: 0%;
}

.category_text {
  position: absolute;
  bottom: 10px;
  width: 100%;
  text-align: center;
  color: black;
  transform: translateX(-50%);
  left: 50%;
}

.extra_text {
  margin-top: 5px;
  color: black;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.check_btn {
  margin: 5px;
}
</style>
  
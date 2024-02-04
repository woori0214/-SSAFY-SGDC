<template>
  <div class="solomode_container">
    <div class="category_container">
      <div v-for="category in categories" :key="category.id" class="category_item">
        <!-- 버튼을 클릭하면 모달창으로 재확인-->
        <button @click="openModal(category)" class="solo_btn" :style="solobtncolor(category.id)">
          <div class="img_container">
            <img :src="category.img" alt="category" class="solo_bnt_img">
          </div>
          <div class="category_text">
            <h2>{{ category.name }}</h2>
            <p class="extra_text">{{ category.contents }}</p>
          </div>
        </button>
      </div>
    </div>

    <!-- 확인 모달 창 -->
    <div v-if="isModalOpen" class="modal">
      <div class="modal-content">
        <h2>진짜로 도전하시겠습니까?</h2>
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

import timerImage from '@/assets/wake.png';
import algoImage from '@/assets/algo.png';
import healthImage from '@/assets/health.png';
import studyImage from '@/assets/study.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';
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
    onMounted(() => {
      solo.soloToday(user_id)
        .then((res) => {
          console.log(solo.soloTodayData)
          soloTodayData.value = res.solo_id
        })
        .catch((error) => {
          console.error('Error fetching soloTodayData:', error);
        });
    });
    
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

    // 버튼 활성화 상태 및 스타일 조정
    const solobtncolor = (categoryId) => {
      return {
        backgroundColor: soloStatusMap.value[categoryId] === 1 ? '#71a5de' : '#aecbeb',
        pointerEvents: soloStatusMap.value[categoryId] === 1 ? 'none' : 'auto',
      };
    };

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
    
    return {
      categories,
      openModal,
      closeModal,
      confirmChallenge,
      solobtncolor,
      isModalOpen,
      isTestModalOpen,
      openTestModal,
      closeTestModal,
      handleImageUpload,
      selectedCategory,
    };
  },
};
</script>
  
<style scoped>

.solomode_container{
  max-height: 400px;
}
.category_container {
  height: 80%;
  overflow: auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between; /* 카테고리 아이템을 간격을 벌리며 레이아웃 */
  background-color: #f8f9fb;
  margin: 20px;
  border-radius: 30px;
  border: #aecbeb 2px solid;
  padding: 20px;
}


.category_item {
  background-color: #aecbeb;
  flex-basis: calc(50% - 30px); /* 한 줄에 두 개의 아이템이 나오도록 설정, 간격을 조절해야합니다. */
  margin: 15px; /* 원하는 간격으로 설정 */
  border-radius: 20px;
}

.solo_btn {
  display: flex;
  flex-direction: column; /* 이미지와 텍스트를 수직으로 배치 */
  align-items: center; /* 가운데 정렬 */
  padding: 10px;
  font-size: 16px;
  width: 100%;
  position: relative;
  cursor: pointer;
  border: none;
  background: none;
  border-radius: 20px;
}

.img_container {
  width: 200px; /* 조정 가능한 크기 */
  height: 200px; /* 조정 가능한 크기 */
  border-radius: 50%; /* 동그라미 모양으로 만듭니다. */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden; /* 이미지가 동그라미를 벗어나지 않도록 합니다. */
  background-color: #f8f9fb; /* 이미지가 없을 때를 위한 배경 색상 */
  margin-top: 10px;
}

.solo_bnt_img {
  max-width: 90%;
  max-height: 90%;
}

.category_text {
  text-align: center;
  color: white;
  font-size: x-large;
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
  display: flex;
  flex-direction: column;
  background-color: #e1ecf7;
  border: #aecbeb 2px solid;
  padding: 50px;
  border-radius: 8px;
  text-align: center;
}

.check_btn {
  background: #71a5de;
  border: #71a5de 1px solid;
  border-radius: 5px;
  padding: 10px;
  margin: 10px;
  color: white;
  font-size: medium;
}
</style>
  
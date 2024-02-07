<template>
  <div class="solomode_container">
    <div class="category_container">
      <div
        v-for="category in today_categories"
        :key="category.id"
        class="category_item"
      >
        <!-- 버튼을 클릭하면 모달창으로 재확인-->
        <!-- 완료 미션 -->
        <div class="solo-btn-wrap" v-if="category.soloResult === 'COMPLETE'">
          <button
            @click="openModal(category.id, category.soloStatus)"
            class="solo_btn"
            style="background-color: #71a5de; pointer-events: none"
          >
            <div class="solo_btn_content">
              <img :src="category.img" alt="category" class="solo_bnt_img" />
              <div class="category_text">{{ category.name }}</div>
            </div>
            <div class="extra_text">{{ category.contents }}</div>
          </button>
        </div>

        <!-- 미완료 미션 -->
        <div class="solo-btn-wrap" v-else>
          <button
            @click="openModal(category.id, category.soloStatus)"
            class="solo_btn"
            style="background-color: #aecbeb; pointer-events: auto"
          >
            <div class="solo_btn_content">
              <img :src="category.img" alt="category" class="solo_bnt_img" />
              <div class="category_text">{{ category.name }}</div>
            </div>
            <div class="extra_text">{{ category.contents }}</div>
          </button>
        </div>
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
    <PopUpProofPicture
      :show="isTestModalOpen"
      @update:show="closeTestModal"
      @uploadImage="handleImageUpload"
      :selectedCategory="selectedCategory"
      :isSoloMode="true"
    />
  </div>
</template>
  
<script>
import { ref, onMounted, computed } from "vue";
import { useSoloStore } from "@/stores/solo";

import timerImage from "@/assets/wake.png";
import algoImage from "@/assets/algo.png";
import healthImage from "@/assets/health.png";
import studyImage from "@/assets/study.png";
import dietImage from "@/assets/diet.png";
import fightingImage from "@/assets/fighting.png";
import PopUpProofPicture from "@/components/PopUp/PopUpProofPicture.vue";
import { useUserStorageStore } from "../../stores/userStorage";

export default {
  // PopUpProofPicture.vue 컴포넌트로 등록
  components: {
    PopUpProofPicture,
  },

  setup() {
    const solo = useSoloStore();
    const isModalOpen = ref(false);
    const userStorage = useUserStorageStore();
    const isTestModalOpen = ref(false);
    const selectedCategory = ref(null);
    const soloTodayData = ref([]);

    // 카테고리
    const today_categories = ref([
      {
        id: 1,
        name: "기상",
        contents: "SSAFY 입실체크 인증",
        img: timerImage,
        soloStatus: null,
        soloResult: "INCOMPLETE",
      },
      {
        id: 2,
        name: "알고리즘",
        contents: "1일 1알고리즘",
        img: algoImage,
        soloStatus: null,
        soloResult: "INCOMPLETE",
      },
      {
        id: 3,
        name: "운동",
        contents: "운동 인증샷",
        img: healthImage,
        soloStatus: null,
        soloResult: "INCOMPLETE",
      },
      {
        id: 4,
        name: "스터디",
        contents: "공부시간 인증샷",
        img: studyImage,
        soloStatus: null,
        soloResult: "INCOMPLETE",
      },
      {
        id: 5,
        name: "식단",
        contents: "샐러드 빈그릇 인증샷",
        img: dietImage,
        soloStatus: null,
        soloResult: "INCOMPLETE",
      },
      {
        id: 6,
        name: "절제",
        contents: "오늘 나는 ___ 을 참았다.",
        img: fightingImage,
        soloStatus: null,
        soloResult: "INCOMPLETE",
      },
    ]);

    // 페이지클릭 시 솔로 모드 내역(오늘) 함수 실행
    onMounted(() => {
      solo
        .soloToday(userStorage.getUserInformation().user_id)
        .then((res) => {
          console.log('오늘의 진행중 리스트는?');
          console.log(res);
          soloTodayData.value = res.data.solos;

          soloTodayData.value.forEach((soloItem) => {
            today_categories.value.forEach((todayItem) => {
              if (todayItem.id == soloItem.category_id) {
                todayItem.soloStatus = soloItem.solo_status;
                todayItem.soloResult = soloItem.solo_result;
              }
            });
          });
        })
        .catch((error) => {
          console.error("Error fetching soloTodayData:", error);
        });
    });

    // 확인 모달창
    const closeModal = () => {
      isModalOpen.value = false;
    };

    // 현황
    // const soloStatusMap = computed(() => {
    //   const statusMap = {};
    //   soloTodayData.forEach((item) => {
    //     statusMap[item.category_id] = item.solo_status;
    //   });
    //   return statusMap;
    // });

    // 버튼 활성화 상태 및 스타일 조정
    // const solobtncolor = (categoryId) => {
    //   return {
    //     backgroundColor:
    //       soloStatusMap.value[categoryId] === 1 ? "#71a5de" : "#aecbeb",
    //     pointerEvents: soloStatusMap.value[categoryId] === 1 ? "none" : "auto",
    //   };
    // };

    // 솔로모드 도전
    const confirmChallenge = () => {
      if (selectedCategory.value) {
        const challenge = {
          user_id: userStorage.getUserInformation().user_id,
          category_id: selectedCategory.value.id,
        };
        solo.soloChallenge(challenge);
        openTestModal();
      }
      closeModal();
    };

    // 확인 모달창
    const openModal = (category, solo_stauts) => {
      // console.log(category)
      selectedCategory.value = category;
      console.log(selectedCategory);

      if (solo_stauts === null) {
        isModalOpen.value = true;
      } else {
        confirmChallenge();
      }
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
      console.log("이미지 업로드 완료:", uploadedImageSrc);
      const challengeData = {
        user_id: userStorage.getUserInformation().user_id,
        category_id: selectedCategory.value.id,
        isSoloMode: true,
        uploadedImage: uploadedImageSrc,
      };
      solo.soloAuth(challengeData);
      console.log(challengeData);
      closeTestModal();
    };

    return {
      today_categories,
      openModal,
      closeModal,
      confirmChallenge,
      // solobtncolor,
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
.solomode_container {
  /* max-height: 500px; */
}

.category_container {
  /* height: 80%; */
  /* overflow: auto; */
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  /* 카테고리 아이템을 간격을 벌리며 레이아웃 */
  background-color: #f8f9fb;
  margin: 20px;
  border-radius: 30px;
  border: #aecbeb 2px solid;
  padding: 20px;
}

.category_item {
  background-color: #aecbeb;
  margin: 15px;
  /* 원하는 간격으로 설정 */
  border-radius: 20px;
}

.solo_btn {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  cursor: pointer;

  border: none;
  border-radius: 20px;

  width: 250px;
  height: 150px;
}

.solo_btn_content {
  display: flex;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  width: 100%;
  height: 80%;

  font-size: 25px;
  font-weight: bold;
  color: black;
}

.solo_bnt_img {
  max-width: 100px;
  max-height: 100px;
  border-radius: 100%;
  background-color: #f8f9fb;
}

.extra_text {
  font-size: 15px;
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

@media screen and (max-width: 820px) {
  .solo_btn {
    padding: 5px;
    width: 200px;
    height: 150px;
  }

  .solo_btn_content {
    font-size: 18px;
  }
}

@media screen and (max-width: 768px) {
  .solo_btn {
    width: 189px;
    height: 150px;
  }
}

@media screen and (max-width: 540px) {
  .category_container {
    justify-content: center;
  }

  .solo_btn {
    width: 300px;
    height: 150px;
  }

  .solo_btn_content {
    font-size: 25px;
  }
}

@media screen and (max-width: 360px) {
  .solo_btn {
    width: 250px;
    height: 150px;
  }
}
* {
  font-family: "jua";
}
</style>
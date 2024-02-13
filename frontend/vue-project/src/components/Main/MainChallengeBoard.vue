<template>
  <div class="challenge_board">
    <div class="challenge_board_head">현황게시판</div>

    <div class="betweenBlock">
      <div class="betweenBlock-item"></div>
    </div>

    <div class="not-logined-box" v-show="userId == null">
      로그인이 필요한 기능입니다 :) {{ userId }}
    </div>
    <div class="current_board" v-show="userId != null">
      <div class="solo">
        <div class="solo_head">
          <div class="solo_head_item">솔로 모드 현황</div>
        </div>
        <div class="solo_board">
          <div class="success_graph">
            <!-- <div class="donut" data-percent="85.4"> -->
            <div class="donut">
              <div class="donut_percent" ref="donut_percent_component">
                <p class="donut_percent_text">{{ solo_percent }}%</p>
              </div>
            </div>
          </div>
          <div class="categories">
            <button ref="categori_btn_component" class="category_btn" v-for="category in categories" :key="category.id"
              :class="{ completed: category.isResult === 'COMPLETE' }" :disabled="category.isAuthenticated"
              @click="proofSolo(category.id, category.isStatus)">
              {{ category.name }}
            </button>
          </div>
        </div>
      </div>
      <div class="compet">
        <div class="compet_head">
          <div class="compet_head_item">경쟁 모드 현황</div>
        </div>
        <!-- 경쟁모드 아이템 -->
        <div class="compet_board">
          <div v-if="competData.length > 0" class="carousel_container">
            <div class="carousel_slide" :style="slideStyle">
              <!-- Carousel 아이템, v-for로 competData를 반복 -->
              <div class="carousel_item" v-for="(item, index) in competData" :key="index">
                <!-- 로그인한 유저 부분 -->
                <div class="player1">
                  <img :src="item.user_img || '@/assets/defaultFace.png'" alt="user image" class="player_img" />
                  <p>{{ item.user_nickname }}</p>
                  <button v-if="!isAuthenticated" @click="authenticate(item, true)">
                    인증하기
                  </button>
                  <button v-else>인증 완료</button>
                </div>
                <div class="item_category_vs">
                  <h1 class="item_category">{{ item.category }}</h1>
                  <!-- <h1>{{ item.category_id }}</h1> -->
                  <h1 class="item_vs">vs</h1>
                </div>
                <!--receiver부분-->
                <div class="player2">
                  <img :src="item.other_user_img || '@/assets/defaultFace.png'" alt="other user image"
                    class="player_img" />
                  <p>{{ item.other_user_nickname }}</p>
                  <div>
                    {{ item.other_user_authenticated ? "인증 완료" : "진행중" }}
                  </div>
                </div>
              </div>
            </div>
            <!-- Carousel 컨트롤 -->
            <button @click="prev">＜</button>
            <button @click="next">＞</button>
            <!-- 인디케이터 -->
            <div class="indicators">
              <span v-for="(item, index) in competData" :key="index" :class="{ active: index === currentIndex }"
                @click="goTo(index)"></span>
            </div>
          </div>
          <div v-else class="default_image_container">
            <img src="@/assets/tung.png" alt="No competition data" class="default_image" />
          </div>
        </div>
        <!-- 경쟁모드 아이템 end -->
      </div>
    </div>
    <PopUpProofPictureCompet :show="isTestModalOpencompet" :userId="userIdtoauth" :competId="competIdtoauth"
      @competAuthImage="handleCompetAuthImage" @update:show="closeTestModalcompet" />
    <PopUpProofPicture :show="isTestModalOpen" @update:show="closeTestModal" @soloAuthImage="handleSoloAuthImage"
      :selectedCategory="selectedCategory" :isSoloMode="true" />
  </div>
</template>
  
<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useCompetionStore } from "@/stores/competition";
import { useUserStorageStore } from "@/stores/userStorage";
import { useSoloStore } from "@/stores/solo";
import { useLoginStore } from "@/stores/login";
import PopUpProofPicture from "../PopUp/PopUpProofPicture.vue";
import PopUpProofPictureCompet from "../PopUp/PopUpProofPictureCompet.vue";
// selectedCompetItem 정의 추가
// selectedCompetItem의 초기값 설정
const selectedCompetItem = ref({ userId: null, competId: null });

const userStorage = useUserStorageStore();
const competitionStore = useCompetionStore();
const soloStore = useSoloStore();
const loginStore = useLoginStore();
const userIdtoauth = ref(null);
const competIdtoauth = ref(null);
const todayChallenges = ref([]);
const userId = ref(userStorage.getUserInformation().user_id);
const profile = ref(null);
const competData = ref([]);
const currentIndex = ref(0);
const router = useRouter();
const chartRef = ref(null);
const isTestModalOpen = ref(false);
const isTestModalOpencompet = ref(false);
const showModal = ref(false);
const selectedCategory = ref(null);
const isAuthenticated = ref(false);

const categories = ref([
  { id: 1, name: "기상", isStatus: null, isResult: "INCOMPLETE", isAuthenticated: false },
  { id: 2, name: "알고리즘", isStatus: null, isResult: "INCOMPLETE", isAuthenticated: false },
  { id: 3, name: "운동", isStatus: null, isResult: "INCOMPLETE", isAuthenticated: false },
  { id: 4, name: "스터디", isStatus: null, isResult: "INCOMPLETE", isAuthenticated: false },
  { id: 5, name: "식단", isStatus: null, isResult: "INCOMPLETE", isAuthenticated: false },
  { id: 6, name: "절제", isStatus: null, isResult: "INCOMPLETE", isAuthenticated: false },
]);

// 솔로모드 인증 바로가기
const proofSolo = function (categoryId, isStatus) {
  selectedCategory.value = categoryId;
  const challenge = {
    user_id: userStorage.getUserInformation().user_id,
    category_id: selectedCategory.value,
  };

  console.log("선택한 솔로 도전의 상태는");
  console.log(isStatus);

  if (isStatus === null) {
    try {
      soloStore.soloChallenge(challenge);
    } catch (error) {
      console.log(error);
    }
  }

  openTestModal();
};

// 솔로모드 인증
const handleSoloAuthImage = file => {
  const userId = userStorage.getUserInformation().user_id;
  profile.value = file; // 반응형 참조에 파일 할당
  // 이미지 업로드 이벤트 핸들러
  console.log('이미지 업로드 완료:', profile);
  const challengeRequestJson = {
    userId: userId,
    categoryId: selectedCategory.value,
    soloAuthImg: ''
  };
  console.log('잘 담아있나?');

  const formData = new FormData();
  formData.append('challengeRequestJson', JSON.stringify(challengeRequestJson));
  formData.append('soloAuthImage', file); // file 직접 사용
  console.log('여기까지는 왔다');
  soloStore.soloAuth(formData)
    .then((response) => {
      console.log('이미지 통신 완료:', response);

      closeTestModal();
      //쓸까말까?
      window.location.reload();
    })
    .catch((error) => {
      console.error('이미지 업로드 실패:', error);
      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }

    });

};

// 인증 모달창
const openTestModal = () => {
  isTestModalOpen.value = true;
};
const openTestModalcompet = () => {
  isTestModalOpencompet.value = true;
};
const closeTestModal = () => {
  isTestModalOpen.value = false;
};

const closeTestModalcompet = () => {
  isTestModalOpencompet.value = false;
};

// // 인증 상태를 확인하는 함수
// const isAuthenticated = (authImage) => {
//   return authImage !== null && authImage !== "";
// };

// // 사용자가 sender인지 receiver인지 확인하는 함수
// const isCurrentUser = (msgUserId) => {
//   return userId === msgUserId;
// };

// 인증하기 버튼의 클릭 이벤트 핸들러
// const authenticate = (item) => {
//   showModal.value = true;
//   // selectedCompetItem에 현재 아이템의 정보 저장
//   selectedCompetItem.value = { userId: item.userId, competId: item.competId };

//   userIdtoauth = selectedCompetItem.value.userId;
//   competIdtoauth = selectedCompetItem.value.competId // 현재 선택된 아이템의 competId 로깅
// };
const authenticate = (item) => {
  if (item.userId && item.competId) {
    selectedCompetItem.value = { userId: item.userId, competId: item.competId };
    showModal.value = true;
    console.log("여긴 잘 담아오나");
    console.log(selectedCompetItem.value.userId);
    console.log(selectedCompetItem.value.competId);
    userIdtoauth.value = selectedCompetItem.value.userId;
    competIdtoauth.value = selectedCompetItem.value.competId;
    console.log("to" + userIdtoauth);
    console.log(competIdtoauth);
    openTestModalcompet();
  } else {
    console.error("Missing userId or competId in the item");
  }
};
const fetchCompetitionData = () => {
  competitionStore
    .competitionProgressDetail(userStorage.getUserInformation().user_id)
    .then((response) => {
      if (response.status === 200 && response.data.competitions) {
        competData.value = response.data.competitions.map((comp) => ({
          ...comp,
          category: getCategoryNameById(comp.category_id), // 카테고리 이름 변환
          user_authenticated:
            comp.user_auth_image !== null && comp.user_auth_image !== "",
          other_user_authenticated:
            comp.other_auth_image !== null && comp.other_auth_image !== "",
          competId: comp.compet_id,
          userId: comp.user_id,
        }));
      }
      console.log("경쟁데이터 잘 갖고옴");
      console.log(competData.value);
    })
    .catch((error) => {
      console.error("Error fetching competition data:", error);
    });
};

// onMounted(() => {
//   fetchCompetitionData();
//   // 기타 onMounted 로직
// });

// const navigateToPage = (category) => {
//     if (category.isActive == false) {
//         // Navigate to the desired route
//         // 예를 들어:
//         // router.push({ name: 'CategoryPage', params: { id: category.id } });
//         router.push({ name: 'Competiton' })
//     }
// };

const prev = () => {
  currentIndex.value =
    (currentIndex.value - 1 + competData.value.length) %
    competData.value.length;
};

const next = () => {
  currentIndex.value = (currentIndex.value + 1) % competData.value.length;
};

const goTo = (index) => {
  currentIndex.value = index;
};

const slideStyle = computed(() => {
  return {
    transform: `translateX(-${currentIndex.value * 100}%)`,
  };
});

const handleUpload = (imageSrc) => {
  const data = {
    user_id: selectedCompetItem.value.userId,
    compet_id: selectedCompetItem.value.competId,
    auth_img: imageSrc,
  };
  competitionStore
    .competitionImage(data)
    .then((response) => {
      console.log("이미지 업로드 성공:", response);
    })
    .catch((error) => {
      console.error("이미지 업로드 실패:", error);
    });
};
// const updateAuthenticationStatus = (userId, competId) => {
//   competData.value = competData.value.map(item => {
//     if (item.userId === userId && item.competId === competId) {
//       // 객체 내의 user_authenticated 속성을 업데이트
//       return { ...item, user_authenticated: true };
//     }
//     return item;
//   });
// };
const handleCompetAuthImage = (file) => {
  const userId = userStorage.getUserInformation().user_id;
  profile.value = file; // 반응형 참조에 파일 할당
  // 이미지 업로드 이벤트 핸들러
  const competId = selectedCompetItem.value.competId;
  console.log("이미지 업로드 완료:", profile);

  console.log("잘 담아있나?");

  const formData = new FormData();
  console.log(userId);
  console.log(competId);
  console.log(file);
  formData.append("userId", userId);
  formData.append("competId", competId); // file 직접 사용
  formData.append("authImg", profile.value);
  console.log("여기까지는 왔다");
  competitionStore
    .competitionImage(formData)
    .then((response) => {
      console.log("이미지 통신 완료:", response);

      closeTestModalcompet();
      isAuthenticated.value = true;
    })
    .catch((error) => {
      console.error("이미지 업로드 실패:", error);
      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }
    });
};

function getCategoryNameById(id) {
  const categoryMap = {
    1: "기상",
    2: "알고리즘",
    3: "운동",
    4: "스터디",
    5: "식단",
    6: "절제",
  };
  return categoryMap[id] || "알 수 없는 카테고리";
}

const categori_btn_component = ref(null);
const donut_percent_component = ref(null);

function setFontSize() {
  categori_btn_component.value.forEach((element) => {
    const componentWidth = element.offsetWidth;
    const fontSize = componentWidth / 5;
    element.style.fontSize = fontSize + "px";
  });

  if (donut_percent_component.value) {
    const donutComponentWidth = donut_percent_component.value.offsetWidth;
    const donutFontSize = donutComponentWidth / 4; // 예시: 컴포넌트 너비의 10분의 1
    donut_percent_component.value.style.fontSize = donutFontSize + "px";
  }
}

// function adjustDonutFontSize() {
//   const donut = document.querySelector('.donut');
//   if (donut) {
//     const size = donut.offsetWidth; // donut 요소의 너비
//     const fontSize = size / 10; // 폰트 크기 계산
//     donut.style.setProperty('--donut-font-size', `${fontSize}px`); // CSS 변수 설정
//   }
// }
const solo_percent = ref(0);
const completed_solo = ref(0);

onMounted(() => {
  fetchCompetitionData();
  const userInformation = userStorage.getUserInformation();

  const donut = document.querySelector(".donut");
  const totalMinwon = ref(0); //현재 진행도 값 << ref와 동기화 시켜줘야함

  const donut_parent = document.getElementsByClassName("donut_percent")[0];
  const donut_child = document.getElementsByClassName("donut_percent_text")[0];

  userId.value = userInformation.user_id;

  if (userId.value) {
    // soloStore
    //   .soloToday(userId.value)
    //   .then((response) => {
    //     console.log("솔로 모드 현황 받은 데이터");
    //     console.log(response);
    //     response.data.solos.forEach((soloItem) => {
    //       console.log(soloItem);

    //       categories.value.forEach((todayItem) => {
    //         if (todayItem.id == soloItem.category_id) {
    //           todayItem.isStatus = soloItem.solo_status;
    //           todayItem.isResult = soloItem.solo_result;
    //           console.log(todayItem.isStatus);
    //           console.log(todayItem.isResult);

    //           if (soloItem.solo_result == "COMPLETE") {
    //             completed_solo.value++;
    //             todayItem.isAuthenticated = true;
    //             console.log(todayItem.isAuthenticated);
    //           }
    //         }
    //       }
    //       );
    //       // const found = soloData.find(solo => solo.category_id === category.id);
    //       // if (found && found.solo_result === "COMPLETE") {
    //       //   category.isAuthenticated = true; // 인증 완료 상태로 설정
    //       // }
    //     });
    soloStore.soloToday(userId.value).then((response) => {
      console.log("솔로 모드 현황 받은 데이터", response);

      response.data.solos.forEach((soloItem) => {
        const categoryIndex = categories.value.findIndex(category => category.id === soloItem.category_id);

        if (categoryIndex !== -1) {
          // 카테고리 상태와 결과 업데이트
          const updatedCategory = {
            ...categories.value[categoryIndex],
            isStatus: soloItem.solo_status,
            isResult: soloItem.solo_result,
            isAuthenticated: soloItem.solo_result === "COMPLETE"
          };

          // Vue가 변경을 감지할 수 있도록 categories 배열 내의 항목을 새 객체로 교체
          categories.value[categoryIndex] = updatedCategory;

          // solo_result가 "COMPLETE"인 경우 completed_solo.value 증가
          if (soloItem.solo_result === "COMPLETE") {
            completed_solo.value++;
          }
        }
      });

      console.log("업데이트한 솔로 모드 현황 테이블");
      console.log(categories.value);

      totalMinwon.value = (completed_solo.value / 6) * 100; //현재 진행도 값 << ref와 동기화 시켜줘야함

      if (donut) {
        solo_percent.value = 0;
        console.log("퍼센트지");
        console.log(totalMinwon.value);
        const donutAnimation = setInterval(() => {
          donut.style.background = `conic-gradient(#3f8bc9 0 ${solo_percent.value}%, #f8f9fb ${solo_percent.value}% 100% )`;

          if (solo_percent.value >= totalMinwon.value) {
            solo_percent.value--;
            clearInterval(donutAnimation);
          }
          solo_percent.value++;
        }, 30);
      } // 창 크기 조절 시 마진 재조정
    })
      .catch((error) => {
        console.error("Error fetching solo today data:", error);
      });
  } else {
    console.error("User ID not found");
  }

  setFontSize();
  // adjustDonutFontSize();
  window.addEventListener("resize", setFontSize);
  // window.addEventListener('resize', adjustDonutFontSize);

  function adjustChildMargin() {
    const parentHeight = donut_parent.offsetHeight;
    donut_child.style.marginTop = (parentHeight / 3) * -2 + "px"; // 예시: 부모 높이의 절반을 마진 탑으로 설정
  }

  adjustChildMargin(); // 초기 마진 설정
  window.addEventListener("resize", adjustChildMargin);
});
// if (chartRef.value) {
//   const ctx = chartRef.value.getContext("2d");
//   new Chart(ctx, {
//     type: "doughnut",
//     data: {
//       labels: ["Category 1"],
//       datasets: [
//         {
//           data: [60, 40],
//           backgroundColor: ["lightblue", "transparent"],
//         },
//       ],
//     },
//     options: {
//       cutout: "60%",
//       responsive: true,
//       maintainAspectRatio: false,
//     },
//   });
// }
</script>


<style>
.challenge_board {
  background-color: #e1ecf7;
  border-radius: 25px;
  padding-bottom: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.challenge_board_head {
  font-size: 44px;
  font-weight: 600;
  margin-left: 15px;
  padding-inline: 20px;
  padding-top: 10px;
}

.current_board {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-inline: 20px;
  /* padding-inline: 10px; */
  gap: 20px;
}

.solo {
  /* border: 1px #aecbeb solid; */
  width: 50%;
  height: 100%;
  /* margin: 5px; */
  text-align: center;
}

.solo_head {
  display: flex;
  justify-content: left;
  margin-left: 18px;
}

.solo_head_item {
  border-inline: #3f8bc9 solid 2px;
  border-top: #3f8bc9 solid 2px;
  background-color: #f8f9fb;
  border-top-right-radius: 15px;
  border-top-left-radius: 15px;
  padding-inline: 8px;
  padding-top: 8px;
  padding-bottom: 5px;

  font-size: 22px;
  font-weight: 600;
}

.solo_board {
  background-color: #aecbeb;
  border-radius: 20px;
  padding: 15px;
  display: flex;
  height: calc(100% - 74px);
}

.success_graph {
  flex: 2;
  height: auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.donut {
  width: 90%;
  height: 0;
  padding-top: 90%;
  border-radius: 50%;
  position: relative;
  text-align: center;
  transition: background 0.3s ease-in-out;
  /* background: conic-gradient(#3f8bc9 0% 30%, #f2f2f2 30% 100%); */
  border: 1px solid black;
  /* --donut-font-size: 16px; */
}

.donut_percent {
  color: #2e2e2e;
  width: 70%;
  height: 0;
  padding-top: 70%;
  background: #e1ecf7;
  border-radius: 50%;
  position: absolute;
  left: 15%;
  top: 15%;
  display: block;
  /* font-size: var(--donut-font-size); */
  font-weight: 600;
}

.categories {
  flex: 3;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
}

.category_btn {
  border: 0px;
  background-color: #e1ecf7;
  border-radius: 20px;
  cursor: pointer;
  flex-basis: calc(50% - 10px);
  font-size: 20px;
  /* font-size: 2.0vw;
  font-size: 1.7vh; */
  font-weight: 600;
  text-align: center;
  transition: background-color 0.3s ease;
}

.category_btn:hover {
  background-color: #3f8bc9;
  color: #f8f9fb;
}

.compet {
  /* border: 1px rgb(50, 248, 255) solid; */
  width: 50%;
  /* margin: 5px; */

  text-align: center;
}

.compet_head {
  display: flex;
  justify-content: left;
  margin-left: 18px;
}

.compet_head_item {
  border-inline: #3f8bc9 solid 2px;
  border-top: #3f8bc9 solid 2px;
  background-color: #f8f9fb;
  border-top-right-radius: 15px;
  border-top-left-radius: 15px;
  padding-inline: 8px;
  padding-top: 8px;
  padding-bottom: 5px;

  font-size: 22px;
  font-weight: 600;
}

.active {
  background-color: rgb(66, 66, 250);
  color: #ababab;
}

.player_img {
  width: 100px;
  height: 100px;
}

.carousel_item {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  text-align: center;
}

.compet_board {
  /* 기존 스타일 유지하고 overflow 추가 */
  overflow: hidden;
  /* carousel 이 해당 영역을 넘어가지 않도록 */

  background-color: #aecbeb;
  border-radius: 20px;
  /* height: 80%; */
}

.carousel_container {
  position: relative;
  /* overflow: hidden; */
  /* 필요에 따라 여기도 추가할 수 있습니다 */
  width: 100%;
  /* 혹은 필요한 너비로 조정하세요 */
  margin: auto;
  /* 가운데 정렬 */
}

.carousel_slide {
  display: flex;
  transition: transform 0.3s ease;
}

.carousel_item {
  flex: 0 0 auto;
  /* 각 아이템의 너비를 자동으로 설정 */
  width: 100%;
  /* 부모 컨테이너의 100% 너비를 가짐 */
}

.category_btn.completed {
  background-color: #3f8bc9;
  /* 도전 완료된 카테고리의 배경색 */
  color: white;
  /* 텍스트 색상 */
}

.player1,
.player2 {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
  /* 요소들 사이의 간격을 조정합니다. */
}

.player_img {
  margin-top: 13px;
  width: 100px;
  /* 이미지의 너비 */
  height: 100px;
  /* 이미지의 높이, 너비와 동일하게 설정하여 정사각형 형태로 만듦 */
  border-radius: 50%;
  /* 모서리를 완전히 둥글게 만들어 원형 형태로 표시 */
  object-fit: cover;
  /* 이미지가 컨테이너를 꽉 채우도록 하면서 비율을 유지 */
  background-color: white;
  border: #2e2e2e;
}

.player1 p,
.player2 p {
  margin: 0;
  /* 이름과 버튼 사이의 기본 마진을 제거 또는 조정 */
  font-size: 1rem;
  /* 필요에 따라 폰트 사이즈 조정 */
}

.player1 button,
.player2 button {
  margin-top: 0px;
  /* 버튼 위의 간격을 조정 */
  padding: 4px 8px;
  /* 버튼 내부 패딩 조정 */
  font-size: 0.875rem;
  /* 버튼 폰트 사이즈 조정 */
}

.item_category {
  margin-bottom: 1px;
}

.item_vs {
  margin-top: 1px;
}

.item_category_vs {
  display: flex;
  /* Flexbox 레이아웃 사용 */
  flex-direction: column;
  /* 자식 요소들을 세로 방향으로 쌓음 */
  align-items: center;
  /* 가로 방향에서 가운데 정렬 */
  justify-content: center;
  /* 세로 방향에서 가운데 정렬 */
  height: 100%;
  /* 필요한 경우, 높이 설정 */
}

/* @media (max-width: 768px) {  */
@media (max-width: 768px) {
  .current_board {
    flex-direction: column;
  }

  .solo,
  .compet {
    width: 100%;
  }

  .carousel_item {
    flex-direction: row;
    align-items: center;
    text-align: center;
  }

  .player1,
  .player2 {
    margin-bottom: 10px;
  }

  .player_img {
    width: 70px;
    /* 변경된 부분: 이미지 크기를 작게 조정 */
    height: 70px;
  }
}

.betweenBlock {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.betweenBlock-item {
  background-color: #f8f9fb;
  border-radius: 5px;
  width: 98%;
  height: 4px;
}

.not-logined-box {
  background-color: #aecbeb;
  border-radius: 10px;
  margin: 0 auto;
  padding-inline: 10%;
  font-size: 2rem;
  padding-block: 10px;
  font-weight: 500;
}

* {
  font-family: "jua";
}

.default_image_container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  /* 필요에 따라 조정 */
}

.default_image {
  margin: 7%;
  max-width: 100%;
  height: auto;
  /* 이미지 비율 유지 */
}
</style>

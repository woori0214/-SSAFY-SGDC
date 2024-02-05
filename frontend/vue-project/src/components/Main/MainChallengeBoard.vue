<template>
  <div class="challenge_board">
    <div class="challenge_board_head">현황게시판</div>

    <div class="betweenBlock">
      <div class="betweenBlock-item"></div>
    </div>

    <div class="current_board">
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
            <button
              ref="categori_btn_component"
              class="category_btn"
              v-for="category in categories"
              :key="category.id"
              :class="{ completed: category.isActive === 'ture' }"
              @click="proofSolo(category.id)"
            >
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
          <div class="carousel_container">
            <div class="carousel_slide" :style="slideStyle">
              <!-- Carousel 아이템 -->
              <div class="carousel_item" v-for="(item, index) in items" :key="index">
                <!--sender부분-->
                <div class="player1">
                  <img :src="item.imageUrl1" alt="..." class="player_img">
                  <p>{{ item.name1 }}</p>
                  <button>{{ item.challenge_status1 }}</button>
                  <!-- <img :src="item.sender_user_img" alt="sender image" class="player_img" />
                  <p>{{ item.sender_user_nickname }}</p>
                  <button
                    v-if="
                      item.sender_isCurrentUser && !item.sender_authenticated
                    "
                    @click="authenticate(item)"
                  >
                    인증하기
                  </button>
                  <div v-else-if="item.sender_authenticated">인증 완료</div>
                  <div v-else>진행중</div> -->
                </div>
                <div class =item_category_vs>
                  <h1 class="item_category">{{ item.category }}</h1>
                  <!-- <h1>{{ item.category_id }}</h1> -->
                  <h1 class="item_vs">vs</h1>
                </div>
                <!--receiver부분-->
                <div class="player2">
                  <!-- <img :src="item.receiver_user_img" alt="receiver image" class="player_img" />
                  <p>{{ item.receiver_user_nickname }}</p>
                  <button
                    v-if="
                      item.receiver_isCurrentUser &&
                      !item.receiver_authenticated
                    "
                    @click="authenticate(item)"
                  >
                    인증하기
                  </button>
                  <div v-else-if="item.receiver_authenticated">인증 완료</div>
                  <div v-else>진행중</div> -->
                  <img :src="item.imageUrl2" alt="..." class="player_img">
                  <p>{{ item.name2 }}</p>
                  <button>{{ item.challenge_status2 }}</button>
                </div>
              </div>
            </div>
            <!-- Carousel 컨트롤 -->
            <button @click="prev">＜</button>
            <button @click="next">＞</button>
            <!-- 인디케이터 -->
            <div class="indicators">
              <span
                v-for="(item, index) in items"
                :key="index"
                :class="{ active: index === currentIndex }"
                @click="goTo(index)"
              ></span>
            </div>
          </div>
        </div>
        <!-- 경쟁모드 아이템 end -->
      </div>
    </div>
    <PopUpProofPictureCompet
      v-if="showModal"
      :show="showModal"
      @uploadImage="handleUpload"
      @update:show="showModal = $event"
    />
    <PopUpProofPicture
      :show="isTestModalOpen"
      @update:show="closeTestModal"
      @uploadImage="handleUpload"
      :selectedCategory="selectedCategory"
      :isSoloMode="true"
    />
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

const userStorage = useUserStorageStore();
const competitionStore = useCompetionStore();
const soloStore = useSoloStore();
const loginStore = useLoginStore();

const todayChallenges = ref([]);
const userId = ref(null);

const competData = ref([]);
const currentIndex = ref(0);
const router = useRouter();
const chartRef = ref(null);
const isTestModalOpen = ref(false);
const showModal = ref(false);
const selectedCategory = ref(null);

const solo_progress = ref(35);

// const donut = document.querySelector(".donut");
// donut.data-percent = totalMinwon;
// donut.style.background = `conic-gradient(#3F8BC9 0% ${totalMinwon}%, #F2F2F2 ${totalMinwon}% 100%)`;
const categories = ref([
  { id: 1, name: "기상", isActive: false },
  { id: 2, name: "알고리즘", isActive: false },
  { id: 3, name: "운동", isActive: false },
  { id: 4, name: "식단", isActive: false },
  { id: 5, name: "스터디", isActive: false },
  { id: 6, name: "절제", isActive: false },
]);

const items = ref([
  {
    index: 1,
    category: "기상",
    name1: "화석",
    imageUrl1: "./src/assets/image1.png",
    challenge_status1: "진행중",
    name2: "지은",
    imageUrl2: "./src/assets/image2.png",
    challenge_status2: "인증 완료",
  },
  {
    index: 2,
    category: "알고리즘",
    name1: "화석",
    imageUrl1: "./src/assets/image1.png",
    challenge_status1: "진행중",
    name2: "태범",
    imageUrl2: "./src/assets/image2.png",
    challenge_status2: "진행중",
  },
]);

// 솔로모드 인증 바로가기
const proofSolo = function (categoryId) {
  userId.value = loginStore.loginUser;
  selectedCategory.value = categoryId;
  const challenge = { user_id: userId, category_id: categoryId };
  soloStore.soloChallenge(challenge);
  openTestModal();
};
// 인증 모달창
const openTestModal = () => {
  isTestModalOpen.value = true;
};
const closeTestModal = () => {
  isTestModalOpen.value = false;
};

// 인증 상태를 확인하는 함수
const isAuthenticated = (authImage) => {
  return authImage !== null && authImage !== "";
};

// 사용자가 sender인지 receiver인지 확인하는 함수
const isCurrentUser = (userId) => {
  return loggedInUserId === userId;
};

// 인증하기 버튼의 클릭 이벤트 핸들러
const authenticate = (item) => {
  showModal.value = true;
  selectedCompetItem.value = item;
};
const fetchCompetitionData = async () => {
  try {
    const response = await competitionStore.competitionProgressDetail(
      loggedInUserId
    );
    if (response.status === 200 && response.data.compet) {
      competData.value = response.data.compet.map((compet) => ({
        ...compet,
        sender_isCurrentUser: isCurrentUser(compet.sender_user_id),
        receiver_isCurrentUser: isCurrentUser(compet.receiver_user_id),
        sender_authenticated: isAuthenticated(compet.sender_auth_image),
        receiver_authenticated: isAuthenticated(compet.receiver_auth_image),
      }));
    }
  } catch (error) {
    console.error("Error fetching competition data:", error);
  }
};

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
    (currentIndex.value - 1 + items.value.length) % items.value.length;
};

const next = () => {
  currentIndex.value = (currentIndex.value + 1) % items.value.length;
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

function getCategoryNameById(id) {
  const categoryMap = {
    1: "기상",
    2: "알고리즘",
    3: "운동",
    4: "식단",
    5: "스터디",
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

onMounted(() => {
  fetchCompetitionData();
  const userInformation = userStorage.getUserInformation();
  const userId = ref(userInformation.user_id);
  if (userId) {
    soloStore
      .soloToday(userId)
      .then((response) => {
        todayChallenges.value = response.data["solo_id"].map((challenge) => ({
          category_id: challenge.category_id,
          category_name: getCategoryNameById(challenge.category_id),
          solo_result: challenge.solo_result,
        }));
      })
      .catch((error) => {
        console.error("Error fetching solo today data:", error);
      });
  } else {
    console.error("User ID not found");
  }

  const donut = document.querySelector(".donut");
  const totalMinwon = ref((4 / 6) * 100); //현재 진행도 값 << ref와 동기화 시켜줘야함
  if (donut) {
    solo_percent.value = 0;
    const donutAnimation = setInterval(() => {
      donut.style.background = `conic-gradient(#3f8bc9 0 ${solo_percent.value}%, #f8f9fb ${solo_percent.value}% 100% )`;
      solo_percent.value++;
      if (solo_percent.value >= totalMinwon.value)
        clearInterval(donutAnimation);
    }, 30);
  }

  setFontSize();
  // adjustDonutFontSize();
  window.addEventListener("resize", setFontSize);
  // window.addEventListener('resize', adjustDonutFontSize);

  const donut_parent = document.getElementsByClassName("donut_percent")[0];
  const donut_child = document.getElementsByClassName("donut_percent_text")[0];

  function adjustChildMargin() {
    const parentHeight = donut_parent.offsetHeight;
    donut_child.style.marginTop = (parentHeight / 3) * -2 + "px"; // 예시: 부모 높이의 절반을 마진 탑으로 설정
  }

  adjustChildMargin(); // 초기 마진 설정
  window.addEventListener("resize", adjustChildMargin); // 창 크기 조절 시 마진 재조정
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
  font-weight: 700;
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
  font-size: 22px;
  /* font-size: 2.0vw;
  font-size: 1.7vh; */
  font-weight: 600;
  text-align: center;
  transition: background-color 0.3s ease;
}
.category_btn:hover{
  background-color: #aecbeb;
  border: 3px solid #e1ecf7;
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
  height: 80%;
}

.carousel_container {
  position: relative;
  overflow: hidden;
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
  background-color: #4caf50;
  /* 도전 완료된 카테고리의 배경색 */
  color: white;
  /* 텍스트 색상 */
}

.player1, .player2 {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px; /* 요소들 사이의 간격을 조정합니다. */
}

.player_img {
  margin-top: 13px;
  width: 100px; /* 이미지의 너비 */
  height: 100px; /* 이미지의 높이, 너비와 동일하게 설정하여 정사각형 형태로 만듦 */
  border-radius: 50%; /* 모서리를 완전히 둥글게 만들어 원형 형태로 표시 */
  object-fit: cover; /* 이미지가 컨테이너를 꽉 채우도록 하면서 비율을 유지 */
  background-color: white;
  border: #2e2e2e;
}


.player1 p, .player2 p {
  margin: 0; /* 이름과 버튼 사이의 기본 마진을 제거 또는 조정 */
  font-size: 1rem; /* 필요에 따라 폰트 사이즈 조정 */
}

.player1 button, .player2 button {
  margin-top: 0px; /* 버튼 위의 간격을 조정 */
  padding: 4px 8px; /* 버튼 내부 패딩 조정 */
  font-size: 0.875rem; /* 버튼 폰트 사이즈 조정 */
}
.item_category{
  margin-bottom: 1px;
}
.item_vs{
  margin-top: 1px;
}
.item_category_vs {
  display: flex;         /* Flexbox 레이아웃 사용 */
  flex-direction: column; /* 자식 요소들을 세로 방향으로 쌓음 */
  align-items: center;    /* 가로 방향에서 가운데 정렬 */
  justify-content: center; /* 세로 방향에서 가운데 정렬 */
  height: 100%;            /* 필요한 경우, 높이 설정 */
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
</style>
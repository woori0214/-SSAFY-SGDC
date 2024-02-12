<template>
  <header class="header-wrapper">
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Protest+Riot&display=swap"
      rel="stylesheet"
    />

    <div class="header-wrapper-top" :style="web_width < 800 ? 'flex-direction: column; align-items: center;' : ''">
      <div class="header-music-box" v-show="!closeLogo && web_width >= 800">
        <BackGroundMusic class="header-music"></BackGroundMusic>
      </div>

      <div class="mainimage-box" :style="closeLogo ? 'margin-top: 40px' : ''">
        <div>
          <img
            src="@/assets/mainimage.gif"
            class="header-image"
            @mouseover="closeLogo = true"
            @mouseleave="closeLogo = false"
            ref="mainImage"
          />
          <div
            class="fade-out-up dialogue1"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 32%' : ''"
          >
            싸피에서!
          </div>
          <div
            class="fade-out-up dialogue2"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 39%' : ''"
          >
            강하다고 생각하는!
          </div>
          <div
            class="fade-out-up dialogue3"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 52%' : ''"
          >
            두 천재들의!
          </div>
          <div
            class="fade-out-up dialogue4"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 62%' : ''"
          >
            싸움!
          </div>
        </div>
      </div>

      <div class="header-links" v-if="!closeLogo">
        <button @click="popUpMailBox" class="notify-icon" v-if="isLogined_ref">
          <span class="material-symbols-outlined"> notifications </span>
        </button>
        <div class="profile-tmp">
          <!-- 로그아웃 상태 -->
          <div v-if="!isLogined_ref" class="login-signup-links">
            <RouterLink to="/login" class="nav-link">로그인</RouterLink>
            <RouterLink to="/signup" class="nav-link">회원가입</RouterLink>
          </div>
          <!-- 로그인 상태 -->
          <div v-else class="user-nav">
            {{ userNickname }}님
            <a @click="logout" class="nav-link logout"
              ><span class="material-symbols-outlined"> logout </span></a
            >
          </div>
        </div>
      </div>
      <PopUpMainMailbox :showModal="showMailBox" :close="closeMailBox" />
    </div>
    <div class="nav-wrapper">
      <nav v-if="!closeLogo">
        <RouterLink to="/">Main</RouterLink>
        <a href="#" @click="handleNavigation('/competition')">Competition</a>
        <a href="#" @click="handleNavigation('/solo')">Solo</a>
        <a href="#" @click="handleNavigation('/feed')">Feed</a>
        <a href="#" @click="handleNavigation('/MyPage')">MyPage</a>
        <div class="nav-searchUser">
          <SearchNickname></SearchNickname>
        </div>
      </nav>
      <div class="animationed-LED" v-if="closeLogo">
        <div class="LEDtrack">
          <div class="LEDcontent">
            &nbsp;싸강두천 - 천재들의 싸움은 외나무다리에서
            피어난다&nbsp;싸강두천 - 천재들의 싸움은 외나무다리에서
            피어난다&nbsp;싸강두천 - 천재들의 싸움은 외나무다리에서
            피어난다&nbsp;싸강두천 - 천재들의 싸움은 외나무다리에서 피어난다
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import router from "@/router";
import { useLoginStore } from "@/stores/login";
import { useUserStorageStore } from "@/stores/userStorage";
import { useUserStore } from "@/stores/user";
import BackGroundMusic from "./BackGroundMusic.vue";
import PopUpMainMailbox from "../PopUp/PopUpMainMailbox.vue";
import SearchNickname from "./SearchNickname.vue";

const userLoginStore = useLoginStore();
const userStore = useUserStore();
const userStorage = useUserStorageStore();

const showMailBox = ref(false);
const closeLogo = ref(false);

const isLogined_ref = ref(false);

const userNickname = ref("");

const web_width = ref(window.innerWidth);

onMounted(() => {
  window.addEventListener("resize", handleWidth);
});

const popUpMailBox = () => {
  showMailBox.value = true;
};

const closeMailBox = () => {
  showMailBox.value = false;
};

const logout = function () {
  userLoginStore.isLogout();
  isLogined_ref.value = false;
  router.push("/");
};

const handleNavigation = (to) => {
  const user_id = userStorage.getUserInformation().user_id;
  if (user_id != null) {
    if (to == "/MyPage") {
      router.push({ name: "MyPage", params: { userId: user_id } }); //나중에 user_id로 바꾸기
    } else {
      router.push(to);
    }
  } else {
    alert("다른 페이지에 접근하기 위해서는 로그인이 필요합니다 :)");
  }
};

setInterval(function () {
  //야매 로그인 확인 방법
  // 반복 실행할 코드
  if (isLogined_ref.value === false) {
    if (userStorage.getUserInformation().user_id != null) {
      isLogined_ref.value = true;
    }
  }
}, 1000);

onMounted(() => {
  userLoginStore.isLogined();

  const userId = ref(userStorage.getUserInformation().user_id);
  userStore
    .userData(userId.value)
    .then((res) => {
      userNickname.value = res.data.data.user_nickname;
    })
    .catch((err) => {
      console.log(err);
    });

  window.addEventListener("resize", handleWidth);
});
</script>

<style>
.notify-icon {
  border-radius: 100%;
  border: 0px;
  height: 38px;
  width: 38px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: background-color 0.3s ease;
}
.notify-icon:hover {
  background-color: #71a5de;
  color: #e1ecf7;
}

.fade-out-up {
  padding: 5px;
  background-color: #aecbeb;
  color: black;
  text-align: center;
  border-radius: 5px;
  position: absolute;
  font-size: 24px;
  font-weight: 600;
}

@keyframes fadeOutUp {
  0% {
    top: 30px;
    opacity: 1; /* 시작과 끝에서 투명도를 0으로 유지 */
  }
  50% {
    top: -10px;
    opacity: 0; /* 최고점에서 다시 투명해짐 */
  }
  100% {
    top: 30px;
    opacity: 0;
  }
}
.dialogue1 {
  position: absolute;
  /* left: 0%; */
  animation: fadeOutUp 4.5s linear infinite;
}

.dialogue2 {
  position: absolute;
  /* left: 50%; */
  animation: fadeOutUp 3s linear infinite;
}

.dialogue3 {
  position: absolute;
  /* left: 75%; */
  animation: fadeOutUp 4s linear infinite;
}

.dialogue4 {
  position: absolute;
  /* left: 100%; */
  animation: fadeOutUp 3.5s linear infinite;
}

.header-wrapper {
  /* border: 2px solid red; */
  display: flex;
  flex-direction: column;
  width: 100%;
}
.header-wrapper-top {
  display: flex;
  justify-content: space-between;
  align-items: end;
  width: 100%;
}
.header-links {
  /* border: 2px solid orange; */
  display: flex;
  flex-direction: column;
  align-items: end;
  justify-content: end;
  /* flex-grow: 1; */
  flex: 2;
  margin-bottom: 5px;
  gap: 8px;
}
.profile-tmp {
}
.login-signup-links {
  display: flex;
  gap: 15px;
}

.nav-link {
  border: 0px;
  border-radius: 5px;
  text-decoration: none;
  color: #131313;
  font-size: 16px;
  font-weight: 500;
  background-color: #aecbeb;
  padding-block: 5px;
  padding-inline: 8px;
  transition: color 0.3s ease;
}
.nav-link:hover {
  color: #ffffff;
}

.user-nav {
  display: flex;
  align-items: center;
  background-color: #e1ecf7;
  padding-left: 8px;
  border-radius: 5px;
  /* font-size: large; */
  font-size: 1.2em;
  font-weight: 600;
}

.logout {
  cursor: pointer;
  margin-left: 10px;
  color: #333;
}

.mainimage-box {
  /* border: 2px solid blue; */
  display: flex;
  justify-content: center;
  /* max-height: 90px; */
  min-height: 50px;
  flex: 5;
}
.header-image {
  /* border: 2px solid orange; */
  /* 높이를 자동으로 조절하여 비율 유지 */
  justify-content: center;
  height: 10vh;
  max-height: 90px;
  min-height: 50px;
  transition: all 0.5s ease;
}
.header-image:hover {
  height: 25vh;
  max-height: 25vh;
  /* transform: scale(1.2); */
}
.header-music-box {
  /* border: 2px solid orange; */
  flex: 2;
  display: flex;
  justify-content: left;
}
.header-music {
  /* border: 2px solid green; */
}

.nav-wrapper {
  background-color: #fff;
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

nav {
  /* border: 2px solid red; */
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

nav a {
  /* border: 2px solid green; */
  text-decoration: none;
  color: black;
  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
  margin-inline: 10px;
  &:hover {
    background-color: #83b0e1;
    color: #fff;
  }
}

.nav-searchUser {
  /* border: 1px solid red; */
  flex: 1;
  display: flex;
  justify-content: end;
  padding-right: 10px;
  height: 35px;
  align-items: center;
}

.animationed-LED {
  font-size: 35px;
  font-weight: 600;
  position: relative;
  width: 100%;
  max-width: 100%;
  height: 50px;
  /* padding: 60px 0; */
  overflow-x: hidden;
  overflow-y: hidden;
}
.animationed-LED .LEDtrack {
  position: absolute;
  white-space: nowrap;
  will-change: transform;
  animation: marquee 10s linear infinite;
}
@keyframes marquee {
  0% {
    transform: translateX(0);
    color: #71a5de;
  }
  20% {
    transform: translateX(-10%);
    color: #aecbeb;
  }
  40% {
    transform: translateX(-20%);
    color: #83b0e1;
  }
  60% {
    transform: translateX(-30%);
    color: #e1ecf7;
  }
  80% {
    transform: translateX(-40%);
    color: #83b0e1;
  }
  100% {
    transform: translateX(-50%);
    color: #71a5de;
  }
}

.protest-riot-regular {
  font-family: "Protest Riot", sans-serif;
  font-weight: 400;
  font-style: normal;
}
</style>  
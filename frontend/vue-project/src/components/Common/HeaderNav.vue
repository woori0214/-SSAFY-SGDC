<template>
  <header class="header-wrapper">
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <div class="header-wrapper-top">
      <div class="header-music-box" v-show="!closeLogo">
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
            안녕하세요!
          </div>
          <div
            class="fade-out-up dialogue2"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 42%' : ''"
          >
            반가워요!
          </div>
          <div
            class="fade-out-up dialogue3"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 52%' : ''"
          >
            싸피생 화이팅!
          </div>
          <div
            class="fade-out-up dialogue4"
            v-if="closeLogo"
            :style="closeLogo ? 'left: 62%' : ''"
          >
            싸강두천!
          </div>
        </div>
      </div>

      <div class="header-links" v-if="!closeLogo">
        <button @click="popUpMailBox">
          <span class="material-symbols-outlined"> notifications </span>
        </button>
        <div class="profile-tmp">
          <!-- 로그인 상태 -->
          <div v-if="!userLoginStore.loginUser" class="login-signup-links">
            <RouterLink to="/login" class="nav-link">로그인</RouterLink>
            <RouterLink to="/signup" class="nav-link">회원가입</RouterLink>
          </div>
          <!-- 로그아웃 상태 -->
          <div v-else class="user-nav">
            <span>{{ userLoginStore.userNickname }}님, 안녕하세요!</span>
            <a @click="logout" class="nav-link logout">로그아웃</a>
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
import { useLoginStore } from "@/stores/login";
import BackGroundMusic from "./BackGroundMusic.vue";
import PopUpMainMailbox from "../PopUp/PopUpMainMailbox.vue";

const showMailBox = ref(false);
const closeLogo = ref(false);

const popUpMailBox = () => {
  showMailBox.value = true;
};

const closeMailBox = () => {
  showMailBox.value = false;
};

const userLoginStore = useLoginStore();
const logout = function () {
  userLoginStore.isLogout();
};
</script>

<style>
.fade-out-up {
  padding: 5px;
  background-color: #aecbeb;
  color: black;
  text-align: center;
  border-radius: 5px;
  position: absolute;
  font-size: 18px;
  font-weight: 600;
}

@keyframes fadeOutUp {
  0%{
    top: 30px;
    opacity: 1; /* 시작과 끝에서 투명도를 0으로 유지 */
  }
  50% {
    top: -10px;
    opacity: 0; /* 최고점에서 다시 투명해짐 */
  }
  100%{
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
  overflow: auto;
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
  font-weight: 600;
  background-color: #aecbeb;
  padding-block: 5px;
  padding-inline: 8px;
}
.nav-link:hover {
  color: #ffffff;
}

.user-nav {
  display: flex;
  align-items: center;
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
  animation: marquee 5s linear infinite;
}
@keyframes marquee {
  0% {
    transform: translateX(0);
    color: red;
  }
  20% {
    transform: translateX(-10%);
    color: orange;
  }
  40% {
    transform: translateX(-20%);
    color: yellow;
  }
  60% {
    transform: translateX(-30%);
    color: green;
  }
  80% {
    transform: translateX(-40%);
    color: blue;
  }
  100% {
    transform: translateX(-50%);
    color: red;
  }
}
</style>  
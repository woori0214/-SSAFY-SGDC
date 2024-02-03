<template>
  <header class="header-wrapper">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <div class="header-music-box">
      <BackGroundMusic class="header-music"></BackGroundMusic>
    </div>
    <div class="mainimage-box">
      <img src="@/assets/mainimage.gif" class="header-image" />
    </div>
    <div class="header-links">
      <button @click="popUpMailBox"><span class="material-symbols-outlined">
notifications
</span></button>
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
    <PopUpMainMailbox :showModal="showMailBox" :close="closeMailBox"/>
  </header>
</template>

<script setup>
import { ref } from "vue";
import { useLoginStore } from "@/stores/login";
import BackGroundMusic from "./BackGroundMusic.vue";



import PopUpMainMailbox from "../PopUp/PopUpMainMailbox.vue";

const showMailBox = ref(false);

const popUpMailBox = () => {
  showMailBox.value = true;
}

const closeMailBox = () => {
  showMailBox.value = false;
}

const userLoginStore = useLoginStore();
const logout = function () {
  userLoginStore.isLogout();
};
</script>
  
<style scoped>
.header-wrapper {
  /* border: 2px solid red; */
  display: flex;
  justify-content: space-between;
  align-items: end;
  width: 100%;
}

.header-links {
  /* border: 2px solid orange; */
  display: flex;
  align-items: center;
  justify-content: end;
  /* flex-grow: 1; */
  flex: 2;
  margin-bottom: 5px;
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
.nav-link:hover{
    color:#ffffff
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
</style>  
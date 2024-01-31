<template>
  <div class="mother-body">
    <div class="back-ground-body">
      <!-- <img src="./assets/backimg1.png" class="back-main-item"> -->
      <div class="back-side-item" v-if="web_width > 1070">
        <img src="./assets/jitensya_kuma.png" alt="" class="bear" v-if="web_width > 1470">
        <img src="./assets/cloud_side_left.png" alt="" class="cloud_left">
        <img src="./assets/coster.png" alt="" class="coster">
      </div>
      <div class="back-side-item" v-if="web_width > 1070">
        <img src="./assets/pinokio.png" alt="" class="pinokio" v-if="web_width > 1470">
        <img src="./assets/cloud_side_right.png" alt="" class="cloud_right">
        <img src="./assets/FerrisWheel.png" alt="" class="ferris_wheel">
      </div>
    </div>
    <div id="main-body">
      <header>
        <div class="wrapper">
          <div class="headbar">
            
            <BackGroundMusic></BackGroundMusic>
            <HeaderNav></HeaderNav>
          </div>
          <div class="nav-wrapper">
            <nav>
              <RouterLink to="/">Main</RouterLink>
              <RouterLink to="/competition">Competition</RouterLink>
              <RouterLink to="/solo">Solo</RouterLink>
              <RouterLink to="/feed">Feed</RouterLink>
              <RouterLink to="/feeddetail">FeedDetail</RouterLink>
              <RouterLink :to="{ name: 'MyPage', params: { userId: user_id } }">MyPage</RouterLink>
              <RouterLink to="/login">Login</RouterLink>
              <RouterLink to="/signup">SignUp</RouterLink>
            </nav>
          </div>
        </div>
      </header>
      <br />
      <RouterView />
    </div>
    <div class="ServiceInformation">
      <div>싸강두천 - 천재들의 싸움은 외나무다리에서 피어난다</div>
      <div>Frontend : 오화석 김태범 홍지은</div>
      <div>Backend : 유수안 박현춘 전우리</div>
    </div>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import BackGroundMusic from "./components/Common/BackGroundMusic.vue";
import BackGroundImg from "./assets/pixil_background_winter.png";
import { ref, onMounted } from "vue";
import HeaderNav from "./components/Common/HeaderNav.vue";
import { useLoginStore } from "./stores/login";

//로그인된 유저 정보
const login = useLoginStore()

// const user_id = login.loginUser
const user_id = 1

const web_width = ref(window.innerWidth);

const handleWidth = () => {
  web_width.value = window.innerWidth;
};

onMounted(() => {
  window.addEventListener('resize', handleWidth);
})
</script>

<style scoped>
.coster {
  position: fixed;
  width: 400px;
  bottom: 132px;
  left: 0px;
  z-index: -2;
}

.ferris_wheel {
  position: fixed;
  width: 400px;
  bottom: 132px;
  right: 0px;
  z-index: -2;
}

.cloud_left {
  position: fixed;
  width: 350px;
  top: 0px;
  left: -20px;
  z-index: -3;
}

.cloud_right {
  position: fixed;
  width: 350px;
  top: 0px;
  right: 0px;
  z-index: -3;
}

.pinokio {
  position: fixed;
  width: 350px;
  bottom: 100px;
  right: -30px;
}

.bear {
  position: fixed;
  width: 200px;
  bottom: 100px;
  left: 0px;
  transform: scaleX(-1);
}

#main-body {

  z-index: 1;

  max-width: 1000px;
  height: calc(100vh - 139px);
  margin: auto;

  display: flex;
  flex-direction: column;

  overflow: auto;
}

#main-body::-webkit-scrollbar {
  display: none;
}

.back-main-item {
  position: fixed;
  width: 100vw;
  height: calc(100vh - 139px);
  ;

  z-index: -6;
}

.back-ground-body {
  position: fixed;
  width: 100vw;
  height: 100vh;

  background-color: #f8f9fb;
  background-image: url("./assets/pixil_background_winter.png");
  background-size: 220px;
  background-repeat: repeat-x;
  background-position: bottom;

  z-index: -2;
  display: flex;
  justify-content: space-between;
}

.nav-wrapper {
  background-color: #fff; 
  border-radius: 15px;
  overflow: hidden; 
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); 
}

nav {
  display: flex;
  justify-content: space-between; 
  align-items: center;
  margin: 20px 0;
}

nav a {
  text-decoration: none;
  color: black;
  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
  margin: 5px;

  &:hover {
    background-color: #83b0e1;
  }
}

.headbar {
  display: flex;
  flex-flow: wrap;
  gap: 20px;
  align-items: center;
}

.ServiceInformation{
  position: absolute;
  bottom: 0px;
  right: 0px;
  text-align: right;
  color: rgb(216, 164, 115);
  z-index: -1;
}
</style>

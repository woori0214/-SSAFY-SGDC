<template>
  <div class="mother-body">
    <div class="back-ground-body">
      <!-- <img src="./assets/backimg1.png" class="back-main-item"> -->
      <div class="back-side-item" v-if="web_width > 1070">
        <img
          src="./assets/jitensya_kuma.png"
          alt=""
          class="bear"
          v-if="web_width > 1470"
        />
        <img src="./assets/cloud_side_left.png" alt="" class="cloud_left" />
        <img src="./assets/coster.png" alt="" class="coster" />
      </div>
      <div class="back-side-item" v-if="web_width > 1070">
        <img
          src="./assets/pinokio.png"
          alt=""
          class="pinokio"
          v-if="web_width > 1470"
        />
        <img src="./assets/cloud_side_right.png" alt="" class="cloud_right" />
        <img src="./assets/FerrisWheel.png" alt="" class="ferris_wheel" />
      </div>
    </div>
    <div id="main-body">
      <header>
        <div class="wrapper">
          <div class="headbar">
            <HeaderNav></HeaderNav>
          </div>
        </div>
      </header>
      <br />
      <RouterView />
    </div>
    <!-- 개발용 라우터 바 -->
    <div class="developBar">
      <RouterLink to="/">Main</RouterLink>
      <RouterLink to="/competition">Competition</RouterLink>
      <RouterLink to="/solo">Solo</RouterLink>
      <RouterLink to="/feed">Feed</RouterLink>
      <RouterLink to="/feeddetail">FeedDetail</RouterLink>
      <RouterLink :to="{ name: 'MyPage', params: { userId: user_id } }"
        >MyPage</RouterLink
      >
      <RouterLink to="/login">Login</RouterLink>
      <RouterLink to="/signup">SignUp</RouterLink>
    </div>
    <!-- 개발용 라우터 바 End-->

    <div class="ServiceInformation">
      <div>싸강두천 - 천재들의 싸움은 외나무다리에서 피어난다</div>
      <div>Frontend : 오화석 김태범 홍지은</div>
      <div>Backend : 유수안 박현춘 전우리</div>
    </div>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import router from "@/router";
import BackGroundImg from "./assets/pixil_background_winter.png";
import { ref, onMounted } from "vue";
import HeaderNav from "./components/Common/HeaderNav.vue";
import { useLoginStore } from "./stores/login";
import { useUserStorageStore } from "./stores/userStorage";

//로그인된 유저 정보
const login = useLoginStore();

// const user_id = login.loginUser
const user_id = 1;

const web_width = ref(window.innerWidth);

const handleWidth = () => {
  web_width.value = window.innerWidth;
};

const tmp_flag = ref(false);

const handleNavigation = (to) => {
  const userStorage = useUserStorageStore();
  if (userStorage.getUserInformation().user_id.value != null) {
    router.push(to);
  } else {
    alert("다른 페이지에 접근하기 위해서는 로그인이 필요합니다 :)");
  }
};

onMounted(() => {
  window.addEventListener("resize", handleWidth);
});
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
.wrapper {
  margin-inline: 8px;
}

.headbar {
  display: flex;
  flex-flow: wrap;
  gap: 20px;
  align-items: center;
}

.developBar {
  position: absolute;
  top: 30px;
  left: 0px;
  display: flex;
  flex-direction: column;
  gap: 5px;
  padding: 5px;
  border-radius: 5px;
  background-color: #c1d8f0;
}
.developBar a {
  border-radius: 5px;
  background-color: #e1ecf7;
  color: black;
  text-align: center;
  text-decoration: none;
  padding-block: 3px;
}
.developBar a:hover {
  background-color: #83b0e1;
  color: #f8f9fb;
}
.ServiceInformation {
  position: absolute;
  bottom: 0px;
  right: 0px;
  text-align: right;
  color: rgb(216, 164, 115);
  z-index: -1;
}
</style>

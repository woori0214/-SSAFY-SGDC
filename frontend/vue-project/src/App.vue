<template>
  <div class="mother-body">
    <div class="back-ground-body">
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
            <h2>Router For Development</h2>
            <BackGroundMusic></BackGroundMusic>
          </div>
          <nav>
            <RouterLink to="/">Main</RouterLink>
            <RouterLink to="/competition">Competiton</RouterLink>
            <RouterLink to="/solo">Solo</RouterLink>
            <RouterLink to="/feed">Feed</RouterLink>
            <RouterLink to="/feeddetail">FeedDetail</RouterLink>
            <RouterLink to="/mypage">MyPage</RouterLink>
            <RouterLink to="/login">Login</RouterLink>
            <RouterLink to="/signup">SignUp</RouterLink>
          </nav>
        </div>
      </header>
      <br />
      <RouterView />
    </div>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import BackGroundMusic from "./components/Common/BackGroundMusic.vue";
import BackGroundImg from "./assets/pixil_background_winter.png";
import { ref, onMounted } from "vue";

const web_width = ref(window.innerWidth);

const handleWidth = () => {
  web_width.value = window.innerWidth;
};

onMounted(() => {
  window.addEventListener('resize', handleWidth);
})
</script>

<style scoped>
.coster{
  position: fixed;
  width: 400px;
  bottom: 132px;
  left: 0px;
  z-index: -2;
}
.ferris_wheel{
  position: fixed;
  width: 400px;
  bottom: 132px;
  right: 0px;
  z-index: -2;
}
.cloud_left{
  position: fixed;
  width: 350px;
  top: 0px;
  left: -20px;
  z-index: -3;
}
.cloud_right{
  position: fixed;
  width: 350px;
  top: 0px;
  right: 0px;
  z-index: -3;
}
.pinokio{
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
  /* border: 2px solid gainsboro; */
  /* background-color: rgba(255, 255, 255, 1.0); */
  z-index: 1;

  max-width: 1000px;
  height: calc(100vh - 139px);
  margin: auto;

  display: flex;
  flex-direction: column;

  overflow: auto;
}
#main-body::-webkit-scrollbar {
  display: none; /* 크롬, 사파리, 오페라, 엣지 */
}
.back-ground-body {
  position: fixed;
  width: 100vw;
  height: 100vh;
  z-index: -1;

  background-color: rgb(243, 238, 230);
  background-image: url("./assets/pixil_background_winter.png");
  background-size: 220px;
  background-repeat: repeat-x;
  background-position: bottom;

  display: flex;
  justify-content: space-between;
}
a {
  border: 1px solid green;
  margin: 5px;
  padding-inline: 10px;
}
nav {
  display: flex;
  margin-top: 10px;
  flex-wrap: wrap;
}
.headbar {
  display: flex;
  flex-flow: wrap;
  gap: 20px;
}
</style>

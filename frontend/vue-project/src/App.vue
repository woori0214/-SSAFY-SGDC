<template>
  <div class="mother-body">
    <div class="back-ground-body">
      <div class="back-side-item">
        <img src="./assets/jitensya_kuma.png" alt="" class="bear">
      </div>
      <div class="back-side-item">
        <img src="./assets/pinokio.png" alt="" class="pinokio">
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
import BackGroundImg from "./assets/pixil_background.png";
</script>

<style scoped>
.pinokio{
  position: fixed;
  width: 350px;
  bottom: 100px;
  right: -30px;
}
.bear{
  position: fixed;
  width: 200px;
  bottom: 100px;
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
.back-ground-body{
  position: fixed;
  width: 100vw;
  height: 100vh;
  z-index: -1;

  background-color: rgb(243, 238, 230);
  background-image: url("./assets/pixil_background.png");
  background-size: 700px;
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

<template>
  <div class="audio-body">
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <button class="audio-btn" @click="OnOffAudio">
      <span v-if="audioStatus" class="material-symbols-outlined audio-btn-icon">
        play_arrow
      </span>
      <span v-if="!audioStatus" class="material-symbols-outlined"> pause </span>
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import sunny1 from "../../assets/1SunnyWeather.mp3";

const audioStatus = ref(false);
const myAudio = new Audio(sunny1);

myAudio.loop = true;
// myAudio.autoplay = true;

const OnOffAudio = () => {
  // console.log("오디오 버튼 함수");
  // console.log(audioStatus);
  // console.log(myAudio);
  try {
    if (!audioStatus.value) {
      myAudio.play();
    } else {
      myAudio.pause();
    }
  } catch (error) {
    // console.log("오디오 버튼 오류");
    console.log(error);
  }
};

onMounted(() => {
  // console.log("온 마운트 오디오 객체 :");
  // console.log(myAudio);
  // 오디오가 재생될 때 호출되는 함수
  myAudio.addEventListener("play", () => {
    // console.log("오디오 재생");
    audioStatus.value = true;
  });

  // 오디오가 일시 중지될 때 호출되는 함수
  myAudio.addEventListener("pause", () => {
    // console.log("오디오 중지");
    audioStatus.value = false;
  });
});
</script>

<style>
.audio-btn {
  background-color: #e1ecf7;
  margin-bottom: 5px;
  border: 0px;
  border-radius: 100%;
  height: 40px;
  width: 40px;
  text-align: center;
  transition: background-color 0.3s ease;
}
.audio-btn:hover {
  background-color: #aecbeb;
}
</style>
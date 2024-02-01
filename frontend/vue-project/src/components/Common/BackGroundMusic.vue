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
    <!-- <div class="BGM-body">
      <button @click="SuffleBGM">음악 셔플</button>
      <button @click="PlayBGM">음악 재생</button>
      <button @click="PauseBGM">음악 정지</button>
      <div class="music_title">{{ musicTitle }}</div>
    </div> -->
    <!-- <audio
      src="src/components/Common/bgm/2RainyWeather.mp3"
      autoplay
      loop
      id="myAudio"
    ></audio> -->
    <button class="audio-btn" @click="OnOffAudio">
      <span v-if="audioStatus" class="material-symbols-outlined audio-btn-icon">
        play_arrow
      </span>
      <span v-if="!audioStatus" class="material-symbols-outlined"> pause </span>
    </button>
    <!-- <button @click="SuffleMyAudio">음악 바꾸기</button> -->
  </div>
</template>

<script setup>
import { ref } from "vue";
import sunny1 from "./bgm/1SunnyWeather.mp3";
import sunny2 from "./bgm/2SunnyWeather.mp3";
import sunny5 from "./bgm/5SunnyWeather.mp3";
import sunny9 from "./bgm/9SunnyWeather.mp3";
import rainy2 from "./bgm/2RainyWeather.mp3";

const bgm_lsit = ref([
  { source: sunny1, title: "Sunny Weather 1:00 a.m." },
  { source: sunny2, title: "Sunny Weather 2:00 a.m." },
  { source: sunny5, title: "Sunny Weather 5:00 a.m." },
  { source: sunny9, title: "Sunny Weather 9:00 a.m." },
  { source: rainy2, title: "Rainy Weather 2:00 a.m." },
]);

//const musicTitle = ref("___");
const nowMusicNum = ref(0);

// const SuffleMyAudio = () => {
//   const myAudio = document.getElementById("myAudio");
//   nowMusicNum.value = Math.floor(Math.random() * bgm_lsit.value.length);

//   myAudio.src = bgm_lsit.value[nowMusicNum.value].source;
// };

const audio = new Audio(sunny1);
audio.loop = true;
audio.autoplay = true;

const audioStatus = ref(true);

// 오디오가 재생될 때 호출되는 함수
audio.addEventListener('play', () => {
  audioStatus.value = true;
});

// 오디오가 일시 중지될 때 호출되는 함수
audio.addEventListener('pause', () => {
  audioStatus.value = false;
});

const OnOffAudio = () => {
  if(!audioStatus.value){
    audio.play();
  }else{
    audio.pause();
  }
};

// const audio = new Audio(sunny1);
// audio.loop = true;

// const SuffleBGM = () => {
//   nowMusicNum.value = Math.floor(Math.random() * bgm_lsit.value.length);
//   audio.src = bgm_lsit.value[nowMusicNum.value].source;

//   musicTitle.value = bgm_lsit.value[nowMusicNum.value].title;
//   audio.play();
// };

// const PlayBGM = () => {
//   musicTitle.value = bgm_lsit.value[nowMusicNum.value].title;
//   audio.play();
// };

// const PauseBGM = () => {
//   audio.pause();
//   musicTitle.value = "일시 정지 중";
// };
</script>

<style>
/* .BGM-body {
  display: flex;
  gap: 10px;
}
.audio-body {
  display: flex;
  align-items: center;
  gap: 10px;
}
#myAudio {
  height: 30px;
  width: 85px;
}
#myAudio::-webkit-media-controls-timeline {
  display: none !important;
}
#myAudio::-webkit-media-controls-mute-button {
  display: none;
}
#myAudio::-webkit-media-controls-current-time-display {
  display: none;
}
#myAudio::-webkit-media-controls-time-remaining-display {
  display: none;
}
#myAudio::-webkit-media-controls-panel {
  -webkit-justify-content: center;
} */

.audio-btn {
  background-color: #e1ecf7;
  margin-bottom: 5px;
  border: 0px;
  border-radius: 100%;
  height: 40px;
  width: 40px;
  text-align: center;
}
.audio-btn:hover{
  background-color: #aecbeb;
}
</style>
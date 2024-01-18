<template>
  <div class="main_box">
    <div>받은 도전장함  [ {{ mail_parameters.length }} / 20 ]</div>
    <div class="mail_box">
      <CompetitionMail
        v-for="(item, index) in mail_parameters"
        :key="index"
        :mail_text="item.sender_name"
        :mail_remain_time="item.remaind_time"
      ></CompetitionMail>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import CompetitionMail from "./CompetitionMailboxItem.vue";

const mail_parameters = ref([
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
  {
    sender_name: "김태범",
    remaind_time: "[12:40]",
  },
  {
    sender_name: "오화석",
    remaind_time: "[01:31]",
  },
  {
    sender_name: "홍지은",
    remaind_time: "[02:43]",
  },
  {
    sender_name: "박현춘",
    remaind_time: "[01:23]",
  },
]);
</script>

<style scoped>
.main_box {
  border: 2px solid blue;
  width: 95%;
  margin: auto;
  display: flex;
  flex-direction: column;
  height: 100%;
}
.mail_box {
  border: 2px solid skyblue;
  flex: 1;
  overflow: auto;
}
</style>
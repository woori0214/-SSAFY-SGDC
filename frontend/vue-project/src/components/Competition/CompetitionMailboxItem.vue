<template>
  <div class="mail">
    <div class="mail_content">
      <div class="mail_content_text">{{ mail_sender }}가 {{mail_category}}를 신청하였습니다.</div>
      <div class="mail_content_time">남은시간: {{ mail_remain_time }}</div>
    </div>
    <button @click="acceptChallenge">수락</button>
  </div>
</template>

<script>
export default {
  name: "mail-item",
  props: {
    mail_sender: {
      type: String,
      default: "XXX",
    },
    mail_category: {
      type: Number,
      default: "X",
    },
    mail_remain_time: {
      type: String,
      default: "[??:??]",
    },
  },
  methods: {
    acceptChallenge() {
      this.$emit("acceptChallenge");
    },
  },
};
</script>

<style>
.mail {
  display: flex;
  margin: 5px;
  gap: 10px;
}

.mail_content {
  flex: 1;
  display: flex;
  justify-content: space-between;
}

.mail_content_text {
}

.mail_content_time {
}

</style>
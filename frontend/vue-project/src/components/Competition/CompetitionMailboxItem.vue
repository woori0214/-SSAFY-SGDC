<template>
  <div class="mail">
    <div class="mail_content">
      <div class="mail_content_text">{{ mail_text }}가 XXXX를 신청하였습니다.</div>
      <div class="mail_content_time">{{ mail_remain_time }}</div>
    </div>
    <button class="mail_btn_accept">수락</button>
    <button class="mail_btn_refuse">거절</button>
  </div>
</template>

<script>
export default {
  name: "mail-item",
  props: {
    mail_text: {
      type: String,
      default: "XXX",
    },
    mail_remain_time: {
      type: String,
      default: "[??:??]",
    },
  },
};
</script>

<style>
.mail{
    /* border: 2px solid gray; */
    display: flex;
    margin: 5px;
    gap: 10px;
}
.mail_content{
    /* border: 2px solid skyblue; */
    flex: 1;
    display: flex;
    justify-content: space-between;
}
.mail_content_text{
    /* border: 2px solid blue; */
}
.mail_content_time{
    /* border: 2px solid blueviolet; */
}
.mail_btn_accept{
    /* border: 2px solid green; */
}
.mail_btn_refuse{
    /* border: 2px solid red; */
}
</style>
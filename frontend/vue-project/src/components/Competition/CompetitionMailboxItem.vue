<template>
  <div class="mail">
    <div class="mail_content">
      <div class="mail_content_text">{{ mail_sender }}가 {{mail_category}}를 신청하였습니다.</div>
      <div class="mail_content_time">{{ mail_remain_time }}</div>
    </div>
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
      default: "X"
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

</style>
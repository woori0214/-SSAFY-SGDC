<template>
  <div class="competmode_div">
    <h1 class="compet_title">경쟁모드</h1>
    <div class="select_body">
      <CompetitionSelect></CompetitionSelect>
    </div>
    <div class="mail_box_body">
      <CompetitionMailbox></CompetitionMailbox>
    </div>
  </div>
</template>

<script setup>
import CompetitionMailbox from "@/components/Competition/CompetitionMailbox.vue";
import CompetitionSelect from "@/components/Competition/CompetitionSelect.vue";
</script>

<style scoped>
.view_body {
  display: flex;
  flex-direction: column;
  gap: 10px;
  flex: 1;
}

.select_body[data-v-ccb05c14] {
    display: flex;
    justify-content: center;
}

.mail_box_body {
  max-height: 380px; /* 최대 높이를 380px로 설정 */
  height: auto; /* 높이를 내용물에 따라 자동으로 조절 */
  margin: 0;
  margin-bottom: 5%;
}

.compet_title {
  background-color: #83b0e1;
  border-radius: 10px;
  width: 60%;
  color: white;
  padding: 10px;
  display: flex;
  justify-content: center;
  margin: auto; 
  margin-top: 20px;
}

.competmode_div {
    display: flex;
    flex-direction: column;
    /* justify-content: center;
    align-items: center; */
    background-color: #e1ecf7;
    border-radius: 30px;
    text-align: center;
}


.main_box[data-v-c4d545a8] {
    width:calc(100% - 30px);
    display: flex;
    flex-direction: column;
    border: 2px solid #83b0e1;
    border-radius: 25px;
    margin: 30px 10px;
    background-color: #e1ecf7;
    padding: 0px;
}

</style>

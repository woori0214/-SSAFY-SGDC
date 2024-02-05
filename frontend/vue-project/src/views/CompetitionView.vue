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
  height: 380px; /* Set your desired fixed height */
  margin: 0;
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




</style>

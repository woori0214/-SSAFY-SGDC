<template>
  <div class="view_body">
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

.select_body {
  flex: 1; /* Fill the available space horizontally */
}

.mail_box_body {
  height: 380px; /* Set your desired fixed height */
  margin: 0;
  width: 100%;
}

.compet_title {
    background-color: #83b0e1;
    border-radius: 25px;
    width: 97%;
    color: white;
    padding: 10px;
}
</style>

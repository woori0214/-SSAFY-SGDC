<template>
  <div class="view_body">
    <h1>경쟁모드</h1>
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
  border: 2px solid red;
  display: flex;
  flex-direction: column;
  gap: 10px;
  flex: 1;
}

.select_body {
  border: 2px solid rgb(32, 29, 255);
}

.mail_box_body {
  border: 2px solid pink;
  height: 380px; /* Set your desired fixed height */
  /* overflow-y: auto; */
}
</style>

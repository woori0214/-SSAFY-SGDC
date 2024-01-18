<template>
    <div class="view_body">
        <h1>경쟁모드</h1>
        <CompetitionSelect class="select_body"></CompetitionSelect>
        <CompetitionMailbox class="mail_box_body"></CompetitionMailbox>
    </div>
</template>

<script setup>
import CompetitionMailbox from '@/components/Competition/CompetitionMailbox.vue';
import CompetitionSelect from '@/components/Competition/CompetitionSelect.vue';
</script>

<style scoped>
.view_body{
    border: 2px solid red;
    display: flex;
    flex-direction: column;
    gap: 10px;
    height: 700px;
}
.select_body{
    border: 2px solid rgb(32, 29, 255);
    flex: 1 1 auto;
}
.mail_box_body{
    border: 2px solid pink;
    flex: 1 1 auto;
    /* height: 550px; */
}
</style>
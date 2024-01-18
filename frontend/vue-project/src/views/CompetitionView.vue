<template>
    <div class="view-body">
        <h1>경쟁모드</h1>
        <CompetitionSelect></CompetitionSelect>
        <CompetitionMailbox class="mail_box_body"></CompetitionMailbox>
    </div>
</template>

<script setup>
import CompetitionMailbox from '@/components/Competition/CompetitionMailbox.vue';
import CompetitionSelect from '@/components/Competition/CompetitionSelect.vue';
</script>

<style scoped>
.view_body{
    display: flex;
    flex-direction: column;
    gap: 10px;
}
.mail_box_body{
    height: 550px;
}
</style>
<template>
    <div id="view-body">
        <h1>경쟁모드</h1>
        <CompetitionMailbox></CompetitionMailbox>
        <CompetitionSelect></CompetitionSelect>
    </div>
</template>

<script setup>
import CompetitionMailbox from '@/components/Competition/CompetitionMailbox.vue';
import CompetitionSelect from '@/components/Competition/CompetitionSelect.vue';
</script>

<style scoped>
#view-body{
    display: flex;
    flex-direction: column;
    gap: 10px;
}
</style>
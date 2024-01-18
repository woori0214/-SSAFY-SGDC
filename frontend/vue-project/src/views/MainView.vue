<template>
    <div>
        <h1>메인페이지</h1>
        <MainFeedList />
        <MainChallengeBoard />
        <MainRanking />
        <MainModeButton />

    </div>
</template>

<script setup>
import MainChallengeBoard from '@/components/Main/MainChallengeBoard.vue';
import MainFeedList from '@/components/Main/MainFeedList.vue';
import MainRanking from '@/components/Main/MainRanking.vue';
import MainModeButton from '@/components/Main/MainModeButton.vue';


</script>

<style scoped>

</style>
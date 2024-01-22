<template>
    <div class="main_view">
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
.main_view {
    /* border: 2px solid red; */
    display: flex;
    flex-direction: column;
    gap: 10px;

    /* test ccs */
    padding-bottom: 60px;
}

.main_view2 {
    /* border: 2px solid rgb(106, 238, 130); */
    display: flex;
    flex-direction: column;
    gap: 10px;
}
</style>
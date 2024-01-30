<template>
    <div class="solomode_div">
        <h1 class="solo_title">솔로모드</h1>
        <SoloSelectItem/>
    </div>
</template>

<script setup>
import SoloSelectItem from '@/components/Solo/SoloSelectItem.vue';
</script>

<style scoped>
.solomode_div {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #e1ecf7;
    border-radius: 30px;
    text-align: center;
}

.solo_title {
    background-color: #83b0e1;
    border-radius: 10px;
    width: 60%;
    color: white;
    padding: 10px;
}

</style>
<template>
    <div class="mode-button_container">
        <button @click="solo" class="mode_button solomode_btn">솔로 모드</button>
        <button @click="compet" class="mode_button competmode_btn">경쟁 모드</button>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

const solo = () => {
    router.push({ name: 'Solo' })
}

const compet = () => {
    router.push({ name: 'Competiton' })
}
</script>

<style scoped>

.mode-button_container {
    position: fixed;
    bottom: 0;
    width: 100%;
    max-width: 1000px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin: auto;
}

.mode_button {
    flex-grow: 1;
    margin: 0 3px;
    padding: 10px 0;
    height: 100px;
    color: #fff;
    border-radius: 5px;
    cursor: pointer;
}
.solomode_btn {
    background-color: #3498db;
}

.competmode_btn {
    background-color: #e74c3c;
}
</style>
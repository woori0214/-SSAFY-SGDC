<template>
    <div>
        <h1>알려드립니다</h1>
        <div id="feed_list">
            <div v-for="feed_item in feedlist" :key="feed_item.id" id="feed_item">
                <p>{{ feed_item.context }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const feedlist = ref([
    {id: 1, context: '파이어스톤 님이 기상챌린지 를 성공하셨습니다.'},
    {id: 2, context: '범쨩 님이 알고리즘챌린지 를 성공하셨습니다.'},
    {id: 3, context: '파이어스톤 님이 범쨩 님을 식단챌린지에서 이겼습니다.'},
    {id: 4, context: '춘식 님이 운동챌린지 를 성공하셨습니다.'},
    {id: 5, context: '화석 님이 스터디챌린지 를 성공하셨습니다.'},
    {id: 6, context: '우리 님이 절제챌린지 를 성공하셨습니다.'},
    {id: 7, context: '수안 님이 기상챌린지 를 성공하셨습니다.'},
    {id: 8, context: '춘 님이 알고리즘챌린지 를 성공하셨습니다.'},
    {id: 9, context: '곰돌이 님이 기상챌린지 를 성공하셨습니다.'},
    {id: 10, context: '파이어스톤 님이 운동챌린지 를 성공하셨습니다.'},
]);


</script>

<style scoped>
#feed_list {
    border: 2px black solid;
}

#feed_item {
    border: 1px gray solid;
}
</style>
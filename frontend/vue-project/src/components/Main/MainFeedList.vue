<template>
    <div class="feed_board">
        <div class="more_feed">
            <h1>알려드립니다</h1>
            <div>
                <button @click="more_feed">+더보기</button>
            </div>
        </div>
        <div class="feed_list">
            <div v-for="feed_item in feedlist" :key="feed_item.id" class="feed_item">
                <p class="context">{{ feed_item.context }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter()

const feedlist = ref([
    { id: 1, context: '파이어스톤 님이 기상챌린지 를 성공하셨습니다.' },
    { id: 2, context: '범쨩 님이 알고리즘챌린지 를 성공하셨습니다.' },
    { id: 3, context: '파이어스톤 님이 범쨩 님을 식단챌린지에서 이겼습니다.' },
    { id: 4, context: '춘식 님이 운동챌린지 를 성공하셨습니다.' },
    { id: 5, context: '화석 님이 스터디챌린지 를 성공하셨습니다.' },
    { id: 6, context: '우리 님이 절제챌린지 를 성공하셨습니다.' },
    { id: 7, context: '수안 님이 기상챌린지 를 성공하셨습니다.' },
    { id: 8, context: '춘 님이 알고리즘챌린지 를 성공하셨습니다.' },
    { id: 9, context: '곰돌이 님이 기상챌린지 를 성공하셨습니다.' },
    { id: 10, context: '파이어스톤 님이 운동챌린지 를 성공하셨습니다.' },
]);

const more_feed = () => {
    router.push({ name: 'Feed'})
}

</script>

<style scoped>
.more_feed {
    border: 1px rgb(193, 49, 90) solid;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.feed_board {
    border: 2px blue solid;
}

.feed_list {
    border: 2px rgb(234, 255, 0) solid;
    margin: 5px;
}

.feed_item {
    display: flex;
    border: 1px gray solid;
    background-color: aliceblue;
    border-radius: 30px;
    margin: 10px;
    padding: 10px;
}

.feed_content {
    flex: 1;
}

.context {
    margin: 0;
    font-size: 16px;
    color: #333;
}
</style>
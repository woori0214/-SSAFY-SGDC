<template>
    <div id="main-box">
        <div>매칭 시작하기</div>
        <div>
            <button id="categori-btn" v-for="categori in MyCategories" :key="categori">
                    {{ categori }}
             </button>
        </div>

        <div id="matching-btn-list">
            <button id="matching-btn">랜덤 매치</button>
            <button id="matching-btn">친구와 매치</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue";

const MyCategories = ref(["식단", "스터디", "알고리즘", "기상", "절제", "운동"]);
</script>

<style scoped>
#main-box{
    border: 2px solid greenyellow;
    width: 95%;
    
    margin: auto;
}

#categori-btn{
    margin-left: 8px;
}

#matching-btn-list{
    border: 2px solid orange;
    display: flex;
    justify-content: center;
    gap: 20px;
}

#matching-btn{
    width: fit-content;
    height: 50px;
}
</style>
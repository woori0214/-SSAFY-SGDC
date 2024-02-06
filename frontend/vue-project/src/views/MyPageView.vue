<template>
    <div class="mypageview_body">
        <div class="profile_body">
            <MyPageProfileBar :user-id="userId" :login-user="loginUser" />
        </div>
        <div class="compet_body">
            <MyPageCompetitionRecord :user-id="userId" :categories="categories" />
        </div>
        <div class="solo_body">
            <MyPageSoloRecord :user-id="userId" :categories="categories" />
        </div>
        <div class="analysis_body">
            <MyPageAnalysis :user-id="userId" :categories="categories" />
        </div>
        <div class="badge_body">
            <MyPageBadgeList :user-id="userId" />
        </div>
        <div v-if="isCurrentUser" class="challenge_body">
            <MyPageChallengeBoard />
        </div>
        <div v-if="isCurrentUser" class="ssallow_body">
            <MyPageSsallow :userId="userId" />
        </div>
    </div>
</template>

<script setup>
import MyPageAnalysis from '@/components/Mypage/MyPageAnalysis.vue';
import MyPageBadgeList from '@/components/Mypage/MyPageBadgeList.vue';
import MyPageChallengeBoard from '@/components/Mypage/MyPageChallengeBoard.vue';
import MyPageCompetitionRecord from '@/components/Mypage/MyPageCompetitionRecord.vue';
import MyPageProfileBar from '@/components/Mypage/MyPageProfileBar.vue';
import MyPageSoloRecord from '@/components/Mypage/MyPageSoloRecord.vue';
import MyPageSsallow from '@/components/Mypage/MyPageSsallow.vue';

import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useFollowStore } from '@/stores/follow';
import { useLoginStore } from '@/stores/login';
import { useUserStorageStore } from '@/stores/userStorage';
import { ref, onMounted, computed } from 'vue';

// 유저 이미지 가져오는 거 변경해야함
import userimg from '@/assets/image1.png';
import timerImage from '@/assets/wake.png';
import algoImage from '@/assets/algo.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';
import studyImage from '@/assets/study.png';
import healthImage from '@/assets/health.png';

const userStore = useUserStore();
const followStore = useFollowStore();
const loginStore = useLoginStore();
const route = useRoute();

const userId = ref(route.params.userId);  // userId를 저장할 ref 추가
const ssallowingData = ref([]);
const ssallowerData = ref([]);
const loginUser = ref(loginStore.loginUser);

const categories = ref([
    { id: 1, name: "기상", img: timerImage },
    { id: 2, name: '알고리즘', img: algoImage },
    { id: 3, name: '운동', img: healthImage },
    { id: 4, name: '스터디', img: studyImage },
    { id: 5, name: '식단', img: dietImage },
    { id: 6, name: '절제', img: fightingImage },
]);

// 로그인 유저와 마이페이지 유저가 같은지
const isCurrentUser = computed(() => loginUser.value === userId.value);

// 페이지 열었을 때, 사용자 정보 모드 받아와야함
// user 정보, 팔로우 정보는 props
onMounted(() => {
    userId.value = route.params.userId;

    
    // 쌀로우
    // followStore.ssallowing(userId.value)
    //     .then((res) => {
    //         ssallowingData.value = res.ssafy_friend
    //     })
    //     .catch((err) => {
    //         console.log(err)
    //     })
    followStore.ssallowing(101)
        .then((res) => {
            ssallowingData.value = res.data.data
            // console.log(ssallowingData.value)
        })
        .catch((err) => {
            console.log(err)
        })

    // 쌀로워
    // followStore.ssallower(userId.value)
    //     .then((res) => {
    //         ssallowerData.value = res.ssafy_friend
    //     })
    //     .catch((err) => {
    //         console.log(err)
    //     })
    followStore.ssallower(101)
        .then((res) => {
            ssallowerData.value = res.data.data
        })
        .catch((err) => {
            console.log(err)
        })

})

</script>

<style scoped>
.mypageview_body {
    display: flex;
    flex-direction: column;
    gap: 40px;
}

.profile_body {
    background: #e1ecf7;
    border-radius: 30px;
}

.compet_body {
    background: #e1ecf7;
    border-radius: 30px;
}

.solo_body {
    background: #e1ecf7;
    border-radius: 30px;
}

.analysis_body {
    background: #e1ecf7;
    border-radius: 30px;
}

.challenge_body {
    background: #e1ecf7;
    border-radius: 30px;
}

.badge_body {
    background: #e1ecf7;
    border-radius: 30px;
}

.ssallow_body {
    background: #e1ecf7;
    border-radius: 30px;
}
</style>
<template>
    <div class="mypageview_body">
        <div class="profile_body">
            <MyPageProfileBar :userData="userData" :ssallowingData="ssallowingData" :ssallowerData="ssallowerData"
                :loginUser="loginUser"
                @ssallowing-request="handleSsallowingRequest" />
        </div>
        <div class="compet_body">
            <MyPageCompetitionRecord :userId="userId" :categories="categories" />
        </div>
        <div class="solo_body">
            <MyPageSoloRecord :userId="userId" :categories="categories" />
        </div>
        <div class="analysis_body">
            <MyPageAnalysis :userId="userId" :categories="categories" />
        </div>
        <div class="badge_body">
            <MyPageBadgeList :userId="userId" />
        </div>
        <!-- <div v-if="isCurrentUser" class="challenge_body">
            <MyPageChallengeBoard />
        </div>
        <div v-if="isCurrentUser" class="ssallow_body">
            <MyPageSsallow :userId="userId" :ssallowingData="ssallowingData" :ssallowerData="ssallowerData" />
        </div> -->
        <div class="challenge_body">
            <MyPageChallengeBoard />
        </div>
        <div class="ssallow_body">
            <MyPageSsallow :userId="userId" :ssallowingData="ssallowingData" :ssallowerData="ssallowerData" />
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
import { ref, onMounted, computed } from 'vue';

// 유저 이미지 가져오는 거 변경해야함
import userimg from '@/assets/image1.png';
import timerImage from '@/assets/wake.png';
import algoImage from '@/assets/algo.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';
import studyImage from '@/assets/study.png';
import healthImage from '@/assets/health.png';

const user = useUserStore();
const follow = useFollowStore();
const login = useLoginStore();
const route = useRoute();

const userId = ref(1);  // userId를 저장할 ref 추가
// const userData = ref({});
// const ssallowingData = ref([]);
// const ssallowerData = ref([]);
// const loginUser = ref(login.loginUser);
const loginUser = ref(1);

const categories = ref([
    { id: 1, name: "기상", img: timerImage },
    { id: 2, name: '알고리즘', img: algoImage },
    { id: 3, name: '운동', img: healthImage },
    { id: 4, name: '스터디', img: studyImage },
    { id: 5, name: '식단', img: dietImage },
    { id: 6, name: '절제', img: fightingImage },
]);

const userData = {
    user_id: 1,
    login_id: "ssafy1234",
    user_ssafy_id: 1044422,
    user_email: "ssafy1234@gamil.com",
    user_nickname: "우리다",
    user_name: "우리",
    is_alert: "Y",
    user_img: userimg,
    created_at: "2024-01-12",
    updated_at: "2024-01-24",
    signout: "Y",
    badge_id: 1,
    user_phone: "010-5555-5555",
    challenge_cnt: 15,
    complain_cnt: 2
}

const ssallowingData = ref([
    {
        user_nickname: "지은",
        user_id: 2,
        user_img: userimg,
    },
    {
        user_nickname: "화석",
        user_id: 3,
        user_img: userimg,
    },
    {
        user_nickname: "태범",
        user_id: 4,
        user_img: userimg,
    },

])

const ssallowerData = ref([
    {
        user_nickname: "현춘",
        user_id: 5,
        user_img: userimg,
    },
    {
        user_nickname: "수안",
        user_id: 6,
        user_img: userimg,
    },
])

// 로그인 유저와 마이페이지 유저가 같은지
const isCurrentUser = computed(() => loginUser.value === userId.value);

const handleSsallowingRequest = (ssallowing) => {
    // MyPageProfileBar 컴포넌트에서 넘겨받은 데이터를 이용해 plusSsallowing 함수 호출
    console.log('팔로우 할게요')
    follow.plusSsallowing(ssallowing)
        .then((res) => {
            // 성공 시 처리
            console.log(res);
            console.log('추가되었습니다.')
        })
        .catch((err) => {
            // 실패 시 처리
            console.error(err);
        });
};

// 페이지 열었을 때, 사용자 정보 모드 받아와야함
// user 정보, 팔로우 정보는 props
onMounted(() => {
    userId.value = route.params.userId;
    console.log(userId.value)
    // user 정보
    user.userData(userId)
        .then((res) => {
            userData.value = res.user
        })
        .catch((err) => {
            console.log(err)
        })

    // 쌀로우
    follow.ssallowing(userId)
        .then((res) => {
            ssallowingData.value = res.ssafy_friend
        })
        .catch((err) => {
            console.log(err)
        })

    // 쌀로워
    follow.ssallower(userId)
        .then((res) => {
            ssallowerData.value = res.ssafy_friend
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
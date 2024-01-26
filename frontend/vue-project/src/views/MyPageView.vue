<template>
    <div class="mypageview_body">
        <h1>프로필페이지</h1>
        <div class="profile_body">
            <MyPageProfileBar :userData="userData" :ssallowingData="ssallowingData" :ssallowerData="ssallowerData"
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
import { ref, onMounted } from 'vue';
// 유저 이미지 가져오는 거 변경해야함
import userimg from '@/assets/image1.png';
import timerImage from '@/assets/timer.png';
import algoImage from '@/assets/algo.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';
import studyImage from '@/assets/study.png';
import healthImage from '@/assets/health.png';

const user = useUserStore()
const follow = useFollowStore()
const route = useRoute()

const userId = ref(1);  // userId를 저장할 ref 추가
// const userData = ref({})
// const ssallowingData = ref([])
// const ssallowerData = ref([])

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
    badge_id: 13,
    user_phone: "010-5555-5555",
    kakao_push: "Y",
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
    border: 2px solid red;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.profile_body {
    border: 1px solid rgb(51, 0, 255);

}

.compet_body {
    border: 1px solid rgb(255, 0, 132);
    ;
}

.solo_body {
    border: 1px solid rgb(0, 238, 255);

}

.analysis_body {
    border: 1px solid rgb(244, 156, 189);

}

.challenge_body {
    border: 1px solid rgb(247, 0, 255);

}

.badge_body {
    border: 1px solid rgb(210, 247, 0);

}

.ssallow_body {
    border: 1px solid rgb(0, 70, 22);

}
</style>
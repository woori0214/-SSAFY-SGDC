<template>
    <div>
        <div class="top">
            <h2> {{ user.user_nickname }} 님 프로필</h2>
            <div>
                <img v-for="complain in user.complain_cnt" :key="complain" src="@/assets/pinno.png" alt="pinno"
                    class="complain_img">
            </div>
        </div>
        <div class="middle">
            <div class="middle1">
                <div class="user_img_badge">
                    <img :src="user.user_img" alt="use_img" class="user_img">
                    <img :src="userBadgeImage" alt="badge_img" class="badge_img">
                </div>
                <div v-if="user.user_id != loginUser" class="myprofile_btns">
                    <button class="myprofile_btn">도전장 보내기</button>
                    <button class="myprofile_btn" @click="sendSsallowingRequest">쌀로우
                        신청</button>
                </div>
                <div v-if="user.user_id === loginUser" class="myprofile_btns">
                    <button class="myprofile_btn">도전장함</button>
                    <button class="myprofile_btn" @click="sendSsallowingRequest">마이페이지
                        수정</button>
                </div>
            </div>
            <div class="follow_cnt">
                <div class="follow_div">
                    <h1>{{ ssallower.length }}</h1>
                    <h3>쌀로워 수</h3>
                </div>
                <div class="follow_div">
                    <h1>{{ ssallowing.length }}</h1>
                    <h3>쌀로잉 수</h3>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useBadgeStore } from '@/stores/badge';

const user = ref([])

export default {
    props: ['userData', 'ssallowingData', 'ssallowerData', 'loginUser'],

    setup(props, { emit }) {
        const badgeStore = useBadgeStore();

        const user = ref(props.userData)
        const loginUser = ref(props.loginUser)
        const ssallowing = ref(props.ssallowingData)
        const ssallower = ref(props.ssallowerData)
        // console.log(user.value)


        const sendSsallowingRequest = () => {
            const ssallowing = { user_id: user.value.user_id, following_id: user.value.user_id }
            // 클릭 이벤트 발생 시 plusSsallowing 함수 호출
            emit('ssallowing-request', ssallowing);
        };

        // 사용자의 뱃지 이미지 찾기
        const userBadgeImage = computed(() => {
            const badge = badgeStore.badgeList.find(badge => badge.badge_id === user.value.badge_id);
            return badge ? badge.badge_img : ''; // 일치하는 뱃지가 있으면 이미지 반환, 없으면 빈 문자열 반환
        });


        return { user, ssallowing, ssallower, userBadgeImage, sendSsallowingRequest }
    }
}
</script>

<style scoped>
.top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 10px 50px;
}

.complain_img {
    border: 1px black solid;
    border-radius: 100px;
    background-color: aliceblue;
    width: 40px;
    height: 40px;
    margin: 3px;
}

.friend_btn {
    margin: 10px;
}

.user_img_badge {
    align-items: center;
    position: relative;
    display: inline-block;
}

.user_img {
    background: #f8f9fb;
    border-radius: 500px;
    width: 130px;
    height: 130px;
    margin: 10px;
}

.badge_img {
    position: absolute;
    /* 절대 위치 설정 */
    bottom: -0px;
    /* 사용자 이미지의 오른쪽 아래에 위치 */
    right: -15px;
    width: 70px;
    /* 뱃지 크기 */
    height: 70px;
}

.middle {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.middle1 {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-left: 50px;
}

.myprofile_btns {
    display: flex;
    flex-direction: column;
    margin: 50px;
}

.myprofile_btn {
    margin: 10px;
    background: #71a5de;
    border: #71a5de;
    border-radius: 30px;
    color: white;
    width: auto;
    height: 40px;
    
    /* 버튼 크기를 뷰포트 단위로 설정 */
    width: 15vw; 
    height: 8vh; 
    font-size: 1.5vw; 
}

.follow_cnt {
    display: flex;
    margin-right: 30px;
    text-align: center;
}

.follow_div {
    margin: 0 50px;
}

@media (max-width: 768px) {
    .top {
        flex-direction: column;
        align-items: center;
    }

    .complain_img {
        width: 20px;
        height: 20px;
        margin: 2px;
    }

    .middle {
        flex-direction: column;
        align-items: center;
    }

    .user_img {
        width: 100px;
        height: 100px;
        margin: 10px;
    }

    .follow_cnt {
        margin: 10px;
    }

    .user_img {
        width: 100px;
        /* 화면 크기가 작아졌을 때의 크기 */
        height: 100px;
    }

    .badge_img {
        bottom: 10px;
        /* 화면 크기가 작아졌을 때 뱃지 위치 조정 */
        right: 0px;
        width: 40px;
        /* 화면 크기가 작아졌을 때 뱃지 크기 조정 */
        height: 40px;
    }

    .myprofile_btn {
        width: 40vw; /* 너비를 화면 너비의 40%로 조정 */
        height: 8vh; /* 높이를 화면 높이의 8%로 조정 */
        font-size: 3.5vw; 
    }
}
</style>
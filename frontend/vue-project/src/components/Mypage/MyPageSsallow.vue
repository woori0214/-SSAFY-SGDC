<template>
    <div>
        <div class="accordion-header" @click="toggleAccordion">
            <h2>쌀로우</h2>
            <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
        </div>
        <div class="accordion-content-ssallow" :class="{ 'open': isOpen }">
            <div>
                <h2>쌀로잉</h2>
                <div v-for="ssallowing in ssallowings" :key="ssallowing.user_id" class="ssallow">
                    <div class="ssallow_info">
                        <img :src="ssallowing.user_img" alt="" class="ssallow_img">
                        <h2>{{ ssallowing.user_nickname }}</h2>
                    </div>
                    <div>
                        <button @click="gotoProfile(ssallowing.user_id)" class="ssallow_btn">프로필 페이지</button>
                        <!-- <button @click="isFollowing ? goUnssallow(ssallowing.user_id) : goSsallowing(ssallowing.user_id)"
                            class="ssallow_btn">{{ getButtonText() }}</button> -->
                        <button @click="toggleFollow(ssallowing)" class="ssallow_btn">{{
                            getButtonText(ssallowing.isFollowing) }}</button>

                    </div>
                </div>
            </div>
            <div>
                <h2>쌀로워</h2>
                <div v-for="ssallower in ssallowers" :key="ssallower.user_id" class="ssallow">
                    <div class="ssallow_info">
                        <img :src="ssallower.user_img" alt="" class="ssallow_img">
                        <h2>{{ ssallower.user_nickname }}</h2>
                    </div>
                    <div>
                        <button @click="gotoProfile(ssallower.user_id)" class="ssallow_btn">프로필 페이지</button>
                        <!-- <button @click="isFollowing ? goUnssallow(ssallowing.user_id) : goSsallowing(ssallowing.user_id)"
                            class="ssallow_btn">{{ getButtonText() }}</button> -->
                        <button @click="toggleFollow(ssallower)" class="ssallow_btn">{{ getButtonText(ssallower.isFollowing)
                        }}</button>

                    </div>
                </div>
            </div>

        </div>
    </div>
</template>
    
<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useFollowStore } from '@/stores/follow';



export default {
    props: ['userId', 'ssallowingData', 'ssallowerData'],
    
    setup(props) {
        const router = useRouter()
        const follow = useFollowStore()
        
        const userId = ref(props.userId);
        // const ssallowings = ref(props.ssallowingData);
        // const ssallowers = ref(props.ssallowerData);
        const isFollowing = ref(false); // 쌀로우 상태를 나타내는 변수
        const isUnfollowing = ref(false); // 언쌀로우 상태를 나타내는 변수
        
        const ssallowings = ref(props.ssallowingData.map(ssallowing => ({
            ...ssallowing,
            isFollowing: true, 
        })));
        
        const ssallowers = ref(props.ssallowerData.map(ssallower => ({
            ...ssallower,
            isFollowing: false, 
        })));
        // 사용자 프로필 페이지로 이동
        const gotoProfile = function (userId) {
            console.log(userId)
            router.push({ name: 'MyPage', params: { userId: userId } });
        }

        // // 언쌀로우 버튼 클릭 시 상태 변경 및 처리
        const goUnssallow = function (followingId) {
            const unssallow_info = { user_id: userId.value, following_id: followingId };
            follow.deleteSsallowing(unssallow_info)
                .then((res) => {
                    console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                })
        };

        // // 쌀로우 버튼 클릭 시 상태 변경 및 처리
        const goSsallowing = function (followId) {
            const ssallowing_info = { user_id: userId.value, following_id: followId }
            follow.plusSsallowing(ssallowing_info)
                .then((res) => {
                    console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                })
        }

        // 버튼 토글
        const toggleFollow = (ssallow) => {
            ssallow.isFollowing = !ssallow.isFollowing;
            if (ssallow.isFollowing) {
                goSsallowing(ssallow.user_id);
            } else {
                goUnssallow(ssallow.user_id);
            }
        };

        // 버튼 텍스트
        const getButtonText = (isFollowing) => {
            return isFollowing ? '언쌀로우' : '쌀로우';
        };
        
        // 아코디언 펼치기/접기
        const isOpen = ref(true);

        const toggleAccordion = () => {
            isOpen.value = !isOpen.value;
        };


        return {
            userId,
            ssallowings,
            ssallowers,
            follow,
            isOpen,
            gotoProfile,
            getButtonText,
            toggleAccordion,
            toggleFollow
        }
    }
};



</script>
    
<style scoped>
.ssallow {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.ssallow_info {
    display: flex;
    align-items: center;
}

.ssallow_btn {
    margin: 15px;
}

.ssallow_img {
    width: 50px;
    height: 50px;
    border-radius: 100px;
    margin: 20px;
    border: 1px black solid;
}

/* 아코디언 헤더 스타일링 */
.accordion-header {
    display: flex;
    align-items: center;
    background-color: #f1f1f1;
    padding: 10px;
    border: 1px solid #ccc;
    cursor: pointer;
}

.rotate-icon {
    transition: transform 0.3s ease;
    margin-left: auto;
    /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
}

.rotate-icon::before {
    content: '\25BC';
    /* 기본 화살표 아래 방향 유니코드 */
    display: inline-block;
}

.rotate-icon.rotate::before {
    content: '\25B2';
    /* 화살표 위 방향 유니코드 */
}

/* 아코디언 내용 스타일링 */
.accordion-content-ssallow {
    display: flex;
    flex-direction: column;
    padding: 10px;
    border: 1px solid #ccc;
    border-top: none;
    overflow: hidden;
    max-height: 0;
    transition: max-height 0.3s ease;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.accordion-content-ssallow.open {
    max-height: 1000px;
    transition: max-height 0.3s ease;
}
</style>
    
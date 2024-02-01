<template>
    <div>
        <div class="top">
            <h2> {{ mypageUser.user_nickname }} 님 프로필</h2>
            <div>
                <img v-for="complain in mypageUser.complain_cnt" :key="complain" src="@/assets/pinno.png" alt="pinno"
                    class="complain_img">
            </div>
        </div>
        <div class="middle">
            <!--사용자 이미지, 버튼-->
            <div class="middle1">
                <div class="user_img_badge">
                    <img :src="mypageUser.user_img" alt="use_img" class="user_img">
                    <img :src="userBadgeImage" alt="badge_img" class="badge_img">
                </div>
                <!-- <div v-if="mypageUser.user_id != loginUser" class="myprofile_btns"> -->
                <div class="myprofile_btns">
                    <button class="myprofile_btn" @click="openSendMsg">도전장 보내기</button>
                    <button @click="toggleFollow()" class="myprofile_btn">{{ getButtonText(isFollowing) }} </button>
                </div>
                <!-- </div> -->
                <div v-if="mypageUser.user_id === loginUser" class="myprofile_btns">
                    <button class="myprofile_btn" @click="openCompetMailbox">도전장함</button>
                    <button class="myprofile_btn" @click="">마이페이지 수정</button>
                </div>
            </div>
            <!--팔로우 수-->
            <div class="follow_cnt">
                <div class="follow_div">
                    <h1>{{ ssallower_cnt }}</h1>
                    <h3>쌀로워 수</h3>
                </div>
                <div class="follow_div">
                    <h1>{{ ssallowing_cnt }}</h1>
                    <h3>쌀로잉 수</h3>
                </div>
            </div>

            <!--도전장함-->
            <div v-if="isMailboxOpen" class="popup_mailbox">
                <div class="mailbox_content">
                    <CompetitionMailbox />
                    <button @click="isMailboxOpen = false" class="mailbox_close_btn">close</button>
                </div>
            </div>

            <!--도전장 보내기-->
            <div v-if="isopenSendMsg" class="popup_sendmsg">
                <div class="sendmsg_content">
                    <h3>카테고리를 선택해주세요</h3>
                    <div class="sendmsg_category">
                        <button v-for="category in categories" :key="category.id" @click="selectCategory(category.id)"
                            class="sendmsg_category_btn">
                            <h3>{{ category.name }}</h3>
                        </button>
                    </div>
                    <div class="sendmsg_btns">
                        <button @click="sendmsg()" class="sendmsg_btn">도전장 보내기</button>
                        <button @click="isopenSendMsg = false" class="sendmsg_btn">닫기</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useBadgeStore } from '@/stores/badge';
import { useCompetionStore } from '@/stores/competition';
import { useFollowStore } from '@/stores/follow';
import CompetitionMailbox from '@/components/Competition/CompetitionMailbox.vue';

// 카테고리 이미지
import timerImage from '@/assets/wake.png';
import algoImage from '@/assets/algo.png';
import healthImage from '@/assets/health.png';
import studyImage from '@/assets/study.png';
import dietImage from '@/assets/diet.png';
import fightingImage from '@/assets/fighting.png';

const mypageUser = ref([])

export default {
    props: ['userData', 'loginUser'],

    setup(props) {
        const categories = ref([
            {
                id: 1,
                name: '기상',
                contents: 'SSAFY 입실체크 빨리하기',
                img: timerImage
            },
            {
                id: 2,
                name: '알고리즘',
                contents: '알고리즘 1일 1문제 풀기',
                img: algoImage
            },
            {
                id: 3,
                name: '운동',
                contents: '운동 인증샷 찍기(헬스장, 공원 등)',
                img: healthImage
            },
            {
                id: 4,
                name: '스터디',
                contents: '하루 몇시간 공부했는지 인증하기',
                img: studyImage
            },
            {
                id: 5,
                name: '식단',
                contents: '10층 샐러드 빈그릇 인증샷 찍기',
                img: dietImage
            },
            {
                id: 6,
                name: '절제',
                contents: '오늘 나는 ㅇㅇㅇ을 참았다.',
                img: fightingImage
            },
        ]);

        const badgeStore = useBadgeStore();
        const competStore = useCompetionStore();
        const followStore = useFollowStore();

        const mypageUser = ref(props.userData);
        const loginUser = ref(props.loginUser);
        
        // 나중에 주석 풀기
        // const ssallowing_cnt = ref(null);
        // const ssallower_cnt = ref(null);
        const ssallowing_cnt = ref(3);
        const ssallower_cnt = ref(2);
        const selectedCategory = ref(null);

        const isMailboxOpen = ref(false);
        const isopenSendMsg = ref(false);
        const isFollowing = ref(false);

        // 사용자의 뱃지 이미지 찾기
        const userBadgeImage = computed(() => {
            const badge = badgeStore.badgeList.find(badge => badge.badge_id === mypageUser.value.badge_id);
            return badge ? badge.badge_img : ''; // 일치하는 뱃지가 있으면 이미지 반환, 없으면 빈 문자열 반환
        });

        // 도전장함
        const openCompetMailbox = function () {
            isMailboxOpen.value = true;
        }

        // 도전장보내기
        const openSendMsg = function () {
            isopenSendMsg.value = true;
        };
        const selectCategory = (categoryId) => {
            selectedCategory.value = categoryId;
            console.log(selectedCategory.value)
        };
        const sendmsg = function () {
            // selectedCategory의 현재 값으로 작업을 수행
            if (selectedCategory.value !== null) {
                const friendSendmsg = {
                    user_id: loginUser.value,
                    friend_id: mypageUser.value.user_id,
                    category_id: selectedCategory.value,
                }
                competStore.friendSend(friendSendmsg)
                    .then((res) => {
                        console.log('도전장 보내기 성공')
                    })
                    .catch((err) => {
                        console.log(err)
                    })
            } else {
                alert('"카테고리가 선택되지 않았습니다."')
            }

        };

        // 버튼 토글
        const toggleFollow = () => {
            isFollowing.value = !isFollowing.value;
            if (isFollowing.value) {
                goSsallowing();
            } else {
                goUnssallow();
            }
        };

        const goSsallowing = function () {
            const ssallowingData = { user_id: mypageUser.value.user_id, following_id: loginUser.value };
            followStore.plusSsallowing(ssallowingData)
                .then((res) => {
                    console.log(res);
                    console.log('팔로우됨');
                })
                .catch((err) => {
                    console.log(err);
                })
        };

        const goUnssallow = function () {
            const UnssallowingData = { user_id: mypageUser.value.user_id, following_id: loginUser.value };
            followStore.deleteSsallowing(UnssallowingData)
                .then((res) => {
                    console.log(res);
                    console.log('언팔함')
                })
                .catch((err) => {
                    console.log(err)
                })
        }
        // 버튼 텍스트
        const getButtonText = (isFollowing) => {
            return isFollowing ? '언쌀로우' : '쌀로우';
        };

        onMounted(() => {
            // 페이지 열었을 때 쌀로우 수
            followStore.getSsallowCount(mypageUser)
                .then((res) => {
                    ssallowing_cnt.value = res.data.followingCount
                    ssallower_cnt.value = res.data.followerCount
                })
                .catch((err) => {
                    console.log(err)
                })
            
            // 팔로우 했는지 안했는지 확인
            const checkusers = {user_id: mypageUser.value, following_id: loginUser.value}
            followStore.checkSsallowing(checkusers)
                .then((res) => {
                    isFollowing.value = res.success
                })
                .catch((err) => {
                    console.log(err)
                })

        });

        return {
            categories,
            mypageUser, loginUser,
            userBadgeImage,
            isopenSendMsg,
            ssallowing_cnt, ssallower_cnt,
            isMailboxOpen,
            isFollowing,
            toggleFollow, getButtonText,
            openSendMsg, selectCategory, sendmsg,
            openCompetMailbox,
        };
    },
    components: { CompetitionMailbox }
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
    margin-left: 30px;
}

.myprofile_btns {
    display: flex;
    flex-direction: column;
    margin: 30px;
}

.myprofile_btn {
    margin: 10px;
    background: #71a5de;
    border: #71a5de;
    border-radius: 30px;
    color: white;
    width: auto;
    height: 40px;
    font-size: medium;
    font-weight: bolder;
}

.follow_cnt {
    display: flex;
    margin-right: 30px;
    text-align: center;
}

.follow_div {
    margin: 0 50px;
}



/* 도전장함, 도전장 보내기 */
.popup_mailbox,
.popup_sendmsg {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
}

.mailbox_content,
.sendmsg_content {
    display: flex;
    flex-direction: column;
    background-color: #e1ecf7;
    border: #aecbeb 2px solid;
    padding: 50px;
    border-radius: 8px;
    text-align: center;
    width: 80vw;
}

.sendmsg_category {
    display: flex;
    justify-content: center;
}

.sendmsg_category_btn {
    cursor: pointer;
    width: 150px;
    padding: 10px;
    margin: 10px;

    border: 2px solid #83b0e1;
    border-radius: 25px;
    background-color: #83b0e1;

    color: white;
    font-size: larger;
    font-weight: bolder;
}

.sendmsg_category_btn.active {
    background-color: #83b0e1;
}

.mailbox_close_btn {
    width: 30%;
    height: 50px;
    margin: 10px auto;
    background-color: #71a5de;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    color: white;
    font-size: larger;
}

.sendmsg_btns {
    margin-top: 20px;
}

.sendmsg_btn {
    text-align: center;

    margin: 20px;
    width: 200px;
    height: 50px;

    background-color: #83b0e1;
    border: #83b0e1;
    border-radius: 25px;
    color: white;
    font-size: larger;
    font-weight: bolder;

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
        width: 40vw;
        /* 너비를 화면 너비의 40%로 조정 */
        height: 8vh;
        /* 높이를 화면 높이의 8%로 조정 */
        font-size: 3.5vw;
    }

    .mailbox_content {
        width: 90%;
        height: 90%;
        max-width: none;
        max-height: none;
    }
}

@media (max-width: 320px) {

    .popup_mailbox,
    .popup_sendmsg {}

    .mailbox_content,
    .sendmsg_content {
        /* 팝업 내용 스타일 추가 조정, 필요에 따라 */
    }
}
</style>
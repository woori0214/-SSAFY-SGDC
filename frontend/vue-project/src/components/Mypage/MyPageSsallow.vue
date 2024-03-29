<template>
    <div>
        <div class="ssallow_div">
            <h2>쌀로잉</h2>
            <div class="sallowing_body">
                <div v-for="ssallowing in ssallowings" :key="ssallowing.user_id" class="ssallow">
                    <div class="ssallow_info">
                        <img :src="ssallowing.userImg" alt="" class="ssallow_img">
                        {{ ssallowing.userNickname }}
                    </div>
                    <div class="ssallow_btns">
                        <button @click="gotoProfile(ssallowing.userId)" class="ssallow_btn">프로필 페이지</button>
                        <button v-if="mypageUser === loginUser" @click="toggleFollow(ssallowing)" class="ssallow_btn">{{
                            getButtonText(ssallowing.isFollowing) }}</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="ssallow_div">
            <h2>쌀로워</h2>
            <div class="ssallower_body">
                <div v-for="ssallower in  ssallowers " :key="ssallower.user_id" class="ssallow">
                    <div class="ssallow_info">
                        <img :src="ssallower.userImg" alt="" class="ssallow_img">
                        {{ ssallower.userNickname }}
                    </div>
                    <div class="ssallow_btns">
                        <button @click="gotoProfile(ssallower.userId)" class="ssallow_btn">프로필 페이지</button>
                        <button v-if="mypageUser === loginUser" @click="toggleFollow(ssallower)" class="ssallow_btn">{{
                            getButtonText(ssallower.isFollowing) }}</button>
                    </div>
                </div>
            </div>
        </div>
        <button @click="goback" class="backbtn">뒤로</button>
    </div>
</template>
  
<script>
import { RouterLink } from "vue-router";
import { ref, onMounted, watch } from "vue";
import router from "@/router";
import { useFollowStore } from "@/stores/follow";

export default {
    props: ['userId', 'loginUser'],

    setup(props) {
        const followStore = useFollowStore();

        const mypageUser = ref(props.userId);
        const loginuser = ref(props.loginUser);
        const isFollowing = ref(false);

        const ssallowings = ref([]);
        const ssallowers = ref([]);


        // 사용자 프로필 페이지로 이동
        const gotoProfile = (user_id) => {
            router.push({ name: 'Profile', params: { userId: user_id } })
        };

        // 언쌀로우 버튼 클릭 시 상태 변경 및 처리
        const goUnssallow = function (followingId) {
            // console.log(followingId)
            const unssallow_info = { to_user_id: followingId, from_user_id: mypageUser.value };
            followStore.deleteSsallowing(unssallow_info)
                .then((res) => {
                    // console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                })
        };

        // 쌀로우 버튼 클릭 시 상태 변경 및 처리
        const goSsallowing = function (followId) {
            const ssallowing_info = { to_user_id: followId, from_user_id: mypageUser.value }
            followStore.plusSsallowing(ssallowing_info)
                .then((res) => {
                    // console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                })
        }

        // 버튼 토글
        const toggleFollow = (ssallow) => {
            ssallow.isFollowing = !ssallow.isFollowing;
            if (ssallow.isFollowing) {
                goSsallowing(ssallow.userId);
            } else {
                goUnssallow(ssallow.userId);
            }
        };

        // 버튼 텍스트
        const getButtonText = (isFollowing) => {
            return isFollowing ? '언쌀로우' : '쌀로우';
        };


        const updateFollowStatus = () => {
            // ssallower에 대한 팔로우 상태 업데이트
            const ssallowerPromises = ssallowers.value.map(ssallower => {
                const checkData = { user_id: mypageUser.value, following_id: ssallower.user_id };
                return followStore.checkSsallowing(checkData).then(response => {
                    ssallower.isFollowing = response.data.isFollowing;
                }).catch(error => {
                    console.error("Error checking follow status for ssallower:", error);
                });
            });

            // ssallower의 팔로우 상태 확인 요청을 병렬로 처리
            Promise.all(ssallowerPromises).then(() => {
                // console.log("All ssallower follow statuses updated");
            }).catch(error => {
                console.error("Error updating ssallower follow statuses:", error);
            });
        };

        const goback = () => {
            router.go(-1)
        };

        onMounted(() => {

            // 쌀로우 (팔로잉) 데이터 처리
            followStore.ssallowing(mypageUser.value)
                .then(async (res) => {
                    const rawData = res.data.data;
                    const promises = rawData.map(ssallowing =>
                        followStore.checkSsallowing({ user_id: mypageUser.value, following_id: ssallowing.userId })
                    );

                    const results = await Promise.all(promises);
                    const updatedSsallowingData = rawData.map((ssallowing, index) => ({
                        ...ssallowing,
                        isFollowing: results[index].data.success,
                    }));

                    ssallowings.value = updatedSsallowingData;
                })
                .catch((err) => {
                    console.log(err);
                });

            // 쌀로워 (팔로워) 데이터 처리
            followStore.ssallower(mypageUser.value)
                .then(async (res) => {
                    const rawData = res.data.data;
                    const promises = rawData.map(ssallower =>
                        followStore.checkSsallowing({ user_id: mypageUser.value, following_id: ssallower.userId })
                    );

                    const results = await Promise.all(promises);
                    const updatedSsallowerData = rawData.map((ssallower, index) => ({
                        ...ssallower,
                        isFollowing: results[index].data.success,
                    }));

                    ssallowers.value = updatedSsallowerData;
                })
                .catch((err) => {
                    console.log(err);
                });

            updateFollowStatus();
        });


        // 아코디언 펼치기/접기
        const isOpen = ref(true);

        const toggleAccordion = () => {
            isOpen.value = !isOpen.value;
        };

        return {
            mypageUser,
            loginuser,
            ssallowings,
            ssallowers,
            followStore,
            isOpen,
            gotoProfile,
            getButtonText,
            toggleAccordion,
            toggleFollow,
            goback,
        }
    }
};


</script>
  
<style scoped>
.ssallow_div {
    background-color: #e1ecf7;
    border-radius: 20px;
    padding: 20px;
    margin: 10px;
}

.sallowing_body,
.ssallower_body {
    background: #f8f9fb;
    margin: 10px;
    border-radius: 30px;
    padding-left: 20px;
    padding-right: 20px;
    height: 300px;
    overflow: scroll;
}

.sallowing_body::-webkit-scrollbar,
.ssallower_body::-webkit-scrollbar {
    width: 5px;
}

.sallowing_body::-webkit-scrollbar-thumb,
.ssallower_body::-webkit-scrollbar-thumb {
    background-color: #71a5de;
    border-radius: 10px;
}


.ssallow {
    display: flex;
    align-items: center;
    justify-content: space-between;

    border-bottom: 1px solid rgba(0, 0, 0, 0.1);

}

.ssallow_info {
    display: flex;
    align-items: center;
    font-size: 22px;
    font-weight: bold;
}

.ssallow_btns {
    display: flex;
}

.ssallow_btn {
    margin: 10px;
    background: #e1ecf7;
    border: none;
    border-radius: 30px;
    color: black;

    width: 200px;
    height: 50px;
    font-size: 20px;
    font-weight: bold;
}

.ssallow_btn:hover {
    background: #83b0e1;
    color: white;
}

.ssallow_img {
    width: 50px;
    height: 50px;
    border-radius: 100px;
    margin: 20px;
    border: 3px #83b0e1 solid;
}

/* 아코디언 헤더 스타일링 */
.accordion-header {
    display: flex;
    align-items: center;
    background-color: #83b0e1;
    padding: 10px;
    cursor: pointer;
    border-radius: 15px;
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

.backbtn {
    margin: 10px;
    background: #e1ecf7;
    border: none;
    border-radius: 30px;
    color: black;

    width: 200px;
    height: 50px;
    font-size: 20px;
    font-weight: bold;
}

.backbtn:hover {
    background: #83b0e1;
    color: white;
}

@media screen and (max-width: 768px) {
    .ssallow_info {
        font-size: 20px;
    }

    .ssallow_btn {
        width: 150px;
        height: 50px;
        font-size: 17px;
    }
}

@media screen and (max-width: 450px) {
    .ssallow_info {
        font-size: 15px;
    }

    .ssallow_img {
        margin: 15px 5px;
    }

    .ssallow_btns {
        flex-direction: column;
    }

    .ssallow_btn {
        width: 130px;
        height: 30px;
        font-size: 15px;
        margin: 3px 10px;
    }

    @media screen and (max-width: 350px) {
        .ssallow {
            flex-direction: column;
        }

        .ssallow_btns {
            flex-direction: row;
        }

        .ssallow_btn {
            width: 110px;
            height: 30px;
            font-size: 15px;
            margin: 2px;
        }
    }
}
</style>
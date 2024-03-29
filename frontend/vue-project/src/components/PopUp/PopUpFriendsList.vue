<template>
    <div v-if="showSuccessModal" class="modal-tofriend">
        <div class="modal-tofriend-content">
            <h3>도전장 전송 성공!</h3>
            <p>{{ modalMessage }}</p>
            <button @click="closeSuccessModal">확인</button>
        </div>
    </div>

    <!-- 도전장 전송 실패 모달 -->
    <div v-if="showFailModal" class="modal-tofriend">
        <div class="modal-tofriend-content">
            <h3>도전장 전송 실패</h3>
            <p>{{ modalMessage }}</p>
            <button @click="closeFailModal">닫기</button>
        </div>
    </div>
    <div v-if="showModal" class="friendList">
        <div class="friendList_content">
            <div>
                <h2>선택한 카테고리 : {{ selectedCategoryName }}</h2>
            </div>
            <div class="find_friends">
                <input type="text" v-model="nickname" placeholder="친구 닉네임을 검색하세요 :)" @input="searchFriends"
                    class="find_friends_input">
                <button @click="searchFriends" class="find_friends_btn">검색</button>
            </div>
            <!-- 검색 창-->
            <div v-if="!searchPerformed" class="list_friends">
                <div v-for="ssallowing in ssallowings" :key="ssallowing.userId" class="popup_ssallowing">
                    <div class="popup_ssallowing_info">
                        <div class="popup_ssallowing_name">
                            <img :src="ssallowing.userImg" alt="" class="popup_ssallowing_img">
                            <h3>{{ ssallowing.userNickname }}</h3>
                        </div>
                    </div>
                    <button class="popup_ssallowing_btn" @click="sendRequest(ssallowing)">신청</button>
                </div>
            </div>
            <div v-else>
                <div v-if="searchResults.length > 0">
                    <div v-for="friend in searchResults" :key="friend.userId" class="popup_ssallowing">
                        <div class="popup_ssallowing_info">
                            <div class="popup_ssallowing_name">
                                <img :src="friend.userImg" alt="" class="popup_ssallowing_img">
                                <h3>{{ friend.userNickname }}</h3>
                            </div>
                        </div>
                        <button class="popup_ssallowing_btn" @click="sendRequest(friend)">신청</button>
                    </div>
                </div>
                <div v-else>
                    검색 결과 없습니다.
                </div>
            </div>
            <button @click="closeList" class="close_friendList">닫기</button>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useFollowStore } from '@/stores/follow';
import { useUserStore } from '@/stores/user';
import { useCompetionStore } from '@/stores/competition';

import userimg from '@/assets/image1.png';

export default {
    props: ['userId', 'showModal', 'Listclose', 'selectedCategory', 'selectedCategoryName'],


    setup(props, { emit }) {
        const followStore = useFollowStore();
        const userStore = useUserStore();
        const userCompetStore = useCompetionStore();

        const userId = ref(props.userId);
        // console.log(userId.value)
        const searchQuery = ref(''); // 검색어 바인딩을 위한 ref
        const searchResults = ref(null); // 검색 결과를 저장할 ref
        const searchPerformed = ref(false); // 검색 수행 여부 확인을 위한 ref

        const ssallowings = ref([]);

        const showSuccessModal = ref(false);
        const showFailModal = ref(false);
        const modalMessage = ref('');

        // 검색(new)
        const nickname = ref('');
        const searchFriends = async () => {
            const trimmedNickname = nickname.value.trim();
            if (trimmedNickname === '') {
                searchResults.value = null;
                searchPerformed.value = false;
                return;
            }
            try {
                const response = await userStore.findAllfriends(trimmedNickname);
                if (Array.isArray(response.data.data.content)) {
                    searchPerformed.value = true;
                    searchResults.value = response.data.data.content.map((user) => {
                        return {
                            userId: user.userId,
                            userNickname: user.userNickname,
                            userImg: user.userImg
                        };
                    });
                    // console.log(searchResults.value)
                } else {
                    searchPerformed.value = false;
                    console.error('올바르지 않은 API 응답 형식:', response);
                    searchResults.value = [];
                }
            } catch (error) {
                searchPerformed.value = false;
                console.error('검색 중 오류 발생:', error);
                searchResults.value = [];
            }
        };


        // 친구 선택 emit
        // PopUpFriendsList.vue 내부의 sendRequest 함수 수정
        const sendRequest = (friend) => {
            // console.log(friend)
            // emit 대신에 여기에서 friendSend 함수 호출
            const friendSendData = {
                userId: userId.value, // 현재 사용자의 ID
                friendId: friend.userId, // 선택된 친구의 ID
                categoryId: props.selectedCategory, // 선택된 카테고리 ID
            };
            // console.log("친구에게 도전장 보내기 요청 데이터:", friendSendData);

            // competitionStore의 friendSend 함수 호출
            userCompetStore.friendSend(friendSendData)
                .then(response => {
                    // console.log("친구에게 도전장 보내기 성공:", response);
                    // 성공적으로 도전장을 보냈다면, 사용자에게 알림을 표시하거나 다른 조치를 취할 수 있습니다.
                    // 예: 알림 표시, 모달 닫기 등

                    closeList(); // 요청 성공 후 친구 목록 모달 닫기
                    showSuccessModal.value = true;
                    modalMessage.value = `${friend.userNickname}님에게 도전장을 성공적으로 보냈습니다.`;
                })
                .catch(error => {
                    console.error("친구에게 도전장 보내기 실패:", error);
                    // 실패 시 사용자에게 오류 메시지를 표시할 수 있습니다.
                    showFailModal.value = true;
                    modalMessage.value = `도전장을 보내는 데 실패했습니다. 다시 시도해주세요.`;
                });
        };

        const closeSuccessModal = () => {
            showSuccessModal.value = false;
        };

        const closeFailModal = () => {
            showFailModal.value = false;
        };
        const closeList = () => {
            // console.log('닫아줘')
            searchPerformed.value = false;
            props.Listclose();
        }

        onMounted(() => {

            followStore.ssallowing(userId.value)
                .then((res) => {
                    ssallowings.value = res.data.data
                    // console.log(ssallowings.value)
                })
                .catch((err) => {
                    console.log(err)
                })

        })

        return {
            userId,
            ssallowings,
            searchQuery,
            searchResults,
            searchPerformed,
            nickname,
            searchFriends,
            sendRequest,
            closeList,
            closeFailModal,
            closeSuccessModal,
            showSuccessModal,
            showFailModal,
            modalMessage,
        }
    }
}
</script>

<style scoped>
.friendList {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
}

.friendList_content {
    background-color: white;
    width: calc(50% - 20px);
    height: calc(90% - 20px);
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);

}

.find_friends {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 5px;
}

.find_friends_input {
    border: #71a5de 2px solid;
    border-radius: 10px;
    width: 80%;
    height: 40px;
    margin: 10px;
    font-size: large;
}

.find_friends_btn {
    background: #aecbeb;
    border: #aecbeb;
    color: black;
    border-radius: 10px;

    width: 100px;
    height: 40px;

    min-width: 50px;
    margin: 10px;
    font-size: large;
    font-weight: bold;
}

.find_friends_btn:hover {
    background: #71a5de;
    border: #71a5de;
    color: white;
}

.list_friends {
    height: 60%;
    overflow: auto;
    margin: 10px;
    padding: 5px;

    border: #71a5de 2px solid;
    border-radius: 20px;
}

.list_friends::-webkit-scrollbar {
    width: 10px;
}

.list_friends::-webkit-scrollbar-thumb {
    background-color: #71a5de;
    border-radius: 10px;
    /* background-clip: padding-box; */
}

.popup_ssallowing {
    display: flex;
    justify-content: space-between;
    align-items: center;

    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.popup_ssallowing:last-child {
    border-bottom: none;
    /* 마지막 항목의 선 제거 */
}

.popup_ssallowing_info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;

    width: 85%;
}

.popup_ssallowing_name {
    display: flex;
    margin: 10px;
}

.popup_ssallowing_img {
    width: 50px;
    height: 50px;
    border: #71a5de solid 2px;
    border-radius: 100%;
    margin: 10px;
}

.popup_ssallowing_btn {
    background: #aecbeb;
    border: #aecbeb;
    color: black;
    border-radius: 10px;

    width: 100px;
    min-width: 50px;
    height: 40px;
    margin: 10px;
    font-size: medium;
    font-weight: bold;
}

.popup_ssallowing_btn:hover {
    background: #71a5de;
    border: #71a5de;
    color: white;
}

.close_friendList {
    min-width: 50%;
    height: 50px;
    padding: 8px 16px;

    background: #aecbeb;
    border: #aecbeb;
    color: black;

    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: larger;
    font-weight: bolder;
}

.close_friendList:hover {
    background-color: #71a5de;
    color: white;
}

.modal-tofriend {
    position: fixed;
    z-index: 100;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-tofriend-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
}

.modal-tofriend-content h3 {
    margin-top: 0;
}

.modal-tofriend-content p {
    margin: 20px 0;
}

.modal-tofriend-content button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.modal-tofriend-content button:hover {
    background-color: #0056b3;
}


@media screen and (max-width: 450px) {

    .friendList_content {
        width: calc(70% - 20px);
        height: calc(90% - 20px);
        overflow: scroll;
    }

    .popup_ssallowing {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;

        border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    }

    .popup_ssallowing_name {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
}
</style>
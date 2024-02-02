<template>
    <div v-if="showModal" class="friendList">
        <div class="friendList_content">
            <div>
                <h2>선택한 카테고리 : {{ selectedCategory }}</h2>
            </div>
            <div class="find_friends">
                <input type="text" v-model="searchQuery" placeholder="닉네임을 검색하세요 :)" class="find_friends_input">
                <button @click="searchFriends" class="find_friends_btn">검색</button>
            </div>
            <div v-if="!searchPerformed" class="list_friends">
                <div v-for="ssallowing in ssallowings" :key="ssallowing.userId" class="popup_ssallowing">
                    <div class="popup_ssallowing_info">
                        <div class="popup_ssallowing_name">
                            <img :src="ssallowing.userImg" alt="" class="popup_ssallowing_img">
                            <h3>{{ ssallowing.userNickname }}</h3>
                        </div>
                        <div>
                            <h3>전적</h3>
                        </div>
                    </div>
                    <button class="popup_ssallowing_btn" @click="sendRequest(ssallowing.userId)">신청</button>
                </div>
            </div>
            <div v-else>
                <div v-if="searchResults.length">
                    <div v-for="friend in searchResults" :key="friend.userId" class="popup_ssallowing">
                        <div class="popup_ssallowing_info">
                            <div class="popup_ssallowing_name">
                                <img :src="friend.userImg" alt="" class="popup_ssallowing_img">
                                <h3>{{ friend.userNickname }}</h3>
                            </div>
                            <div>
                                <h3>전적</h3>
                            </div>
                        </div>
                        <button class="popup_ssallowing_btn" @click="sendRequest(friend.userId)">신청</button>
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

import userimg from '@/assets/image1.png';

export default {
    props: ['userId', 'showModal', 'Listclose', 'selectedCategory',],

    setup(props, { emit }) {
        const followStore = useFollowStore();
        const userStore = useUserStore();

        const userId = props.userId;

        const searchQuery = ref(''); // 검색어 바인딩을 위한 ref
        const searchResults = ref([]); // 검색 결과를 저장할 ref
        const searchPerformed = ref(false); // 검색 수행 여부 확인을 위한 ref

        // const ssallowings = ref([]);
        const ssallowings = ref([
            {
                userId: 101,
                userNickname: '파이어스톤',
                userImg: userimg,
            },
            {
                userId: 102,
                userNickname: '춘춘',
                userImg: userimg,
            },
            {
                userId: 103,
                userNickname: '범스꿍스',
                userImg: userimg,
            },
            {
                userId: 104,
                userNickname: '우리언니',
                userImg: userimg,
            },
            {
                userId: 105,
                userNickname: '도파민중독',
                userImg: userimg,
            },
        ])

        // 검색 수행 함수
        const searchFriends = () => {
            if (searchQuery.value.trim()) {
                searchPerformed.value = true; // 검색 수행 표시
                userStore.findMyfriends(userId, { user_nickname: searchQuery.value }).then((res) => {
                    searchResults.value = res.data.length ? res.data : [];
                }).catch(() => {
                    searchResults.value = [];
                });
            } else {
                searchPerformed.value = false; // 검색어가 비어있으면 검색하지 않음
            }
        };

        // 친구 선택 emit
        const sendRequest = (friendId) => {
            emit('friend-selected', friendId); // 친구 선택 시 이벤트 발송
        };

        const closeList = () => {
            console.log('닫아줘')
            props.Listclose();
        }

        onMounted(() => {

            followStore.ssallowing(userId)
                .then((res) => {
                    ssallowings.value = res.data
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
            searchFriends,
            sendRequest,
            closeList,
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
    width: 50%;
    height: 70%;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);

}

.find_friends {
    display: flex;
    align-items: center;
}

.find_friends_input {
    border: #71a5de 2px solid;
    border-radius: 20px;
    width: 90%;
    height: 40px;
    font-size: large;
}

.find_friends_btn {
    background: #71a5de;
    border: #71a5de;
    color: white;
    border-radius: 10px;

    width: 5%;
    height: 40px;
    margin: 10px;
    font-size: large;
    font-weight: bold;
}

.list_friends {
    height: 60%;
    overflow: auto;
}

.popup_ssallowing {
    display: flex;
    align-items: center;
}

.popup_ssallowing_info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: center;

    width: 90%;
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
    background: #71a5de;
    border: #71a5de;
    color: white;
    border-radius: 10px;

    width: 5%;
    height: 30px;
    margin: 10px;
    font-size: medium;
    font-weight: bold;
}

.close_friendList {
    width: 200px;
    height: 50px;
    padding: 8px 16px;
    background-color: #71a5de;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: larger;
    font-weight: bolder;
}
</style>
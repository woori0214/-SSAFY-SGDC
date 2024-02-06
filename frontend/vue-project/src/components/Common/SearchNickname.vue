<template>
    <div>
        <input type="text" v-model="nickname" placeholder="친구 닉네임 검색..." @input="searchFriends">
        <div v-if="searchResults && searchResults.length > 0" class="search-results">
            <ul>
                <p v-for="result in searchResults" :key="result.userNickname">
                    {{ result.userNickname }}
                    <button @click="followUser(result.userId)" class="follow-button">팔로우</button>
                    <button @click="viewProfile(result.userId)" class="profile-button">프로필</button>
                </p>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from 'vue-router';

import { useUserStore } from "@/stores/user";
import { useFollowStore } from "@/stores/follow";
import { useUserStorageStore } from "@/stores/userStorage";
const userStore = useUserStore();
const userFollowStore = useFollowStore();
const useUserStorage = useUserStorageStore();
const userInformation = useUserStorage.getUserInformation();
const useridMe = userInformation.user_Id;
const nickname = ref('');
const searchResults = ref(null);
const router = useRouter();



const searchFriends = async () => {
    const trimmedNickname = nickname.value.trim();
    if (trimmedNickname === '') {
        searchResults.value = null;
        return;
    }
    try {
        const response = await userStore.findAllfriends(trimmedNickname); // `.value` 사용 제거
        console.log(response.data, "res1");
        console.log(response.data.data, "res2");
        console.log(response.data.data.content, "res3");

        if (Array.isArray(response.data.data.content)) {
            searchResults.value = response.data.data.content.map((user) => {
                //console.log(user.userId, user.userNickname, "잘들어옴!")
                return {
                    userId: user.userId,
                    userNickname: user.userNickname
                };
            });
            console.log(searchResults.value, "2222222");
        } else {
            console.error('올바르지 않은 API 응답 형식:', response);
            searchResults.value = [];
        }
    } catch (error) {
        console.error('검색 중 오류 발생:', error);
        searchResults.value = [];
    }
};

const followUser = (userId) => {
    return new Promise((resolve, reject) => {
        console.log(userId);
        console.log(useUserStorage.getUserInformation().user_id);
        const useridMe = useUserStorage.getUserInformation().user_id;
        // const ssallowingData = {
        //     toUserId: userId, 
        //     fromUserId: useridMe
        // };

        userFollowStore.plusSsallowing2(userId, useridMe)

            .then((response) => {
                console.log('통신은 연결됨');
                console.log(response, "팔로우 성공!");
                resolve(response);

            })
            .catch((error) => {
                console.error('팔로우 실패:', error);
                reject(error);

            });
    });
};

const viewProfile = (userId) => {
    // 'mypage/:userId' 경로로 라우팅합니다. userId를 동적 세그먼트로 사용합니다.
    router.push({ name: 'MyPage', params: { userId: userId } });
};

onMounted(() => {
    document.body.addEventListener('click', closeSearchResults);
    window.addEventListener('scroll', closeSearchResultsOnScroll);
});

onUnmounted(() => {
    document.body.removeEventListener('click', closeSearchResults);
    window.removeEventListener('scroll', closeSearchResultsOnScroll);
});

const closeSearchResults = () => {
    searchResults.value = null;
};

const closeSearchResultsOnScroll = () => {
    if (searchResults.value !== null) {
        searchResults.value = null;
    }
};
</script>

<style scoped>
.search-results {
    position: absolute;
    z-index: 1000;
    background-color: white;
    border: 1px solid #ccc;
    padding: 5px;
    list-style: none;
}

.search-results ul {
    margin: 0;
    padding: 0;
}

.search-results li {
    margin: 5px 0;
}

.follow-button,
.profile-button {
    /* 버튼에 대한 공통 스타일 */
    margin-left: 10px;
    padding: 5px 10px;
    border: 1px solid #ccc;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.follow-button:hover,
.profile-button:hover {
    background-color: #83b0e1;
    color: #fff;
}
</style>

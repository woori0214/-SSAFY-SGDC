<template>
    <div>
        <input type="text" v-model="nickname" placeholder="친구 닉네임 검색..." @input="searchFriends">
        <button @click="searchFriends">검색</button>
        <div v-if="searchResults && searchResults.length > 0" class="search-results">
            <ul>
                <p v-for="result in searchResults" :key="result.user_id">
                    {{ result.user_nickname }}
                    <button @click="followUser(result.user_id)" class="follow-button">팔로우</button>
                    <button @click="viewProfile(result.user_id)" class="profile-button">프로필</button>
                </p>
            </ul>
        </div>
    </div>
</template>
  
<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useUserStore } from "@/stores/user";

const nickname = ref('');
const searchResults = ref(null);
const userStore = useUserStore();

const dummyUsers = [
    { user_id: 1, user_nickname: "user1" },
    { user_id: 2, user_nickname: "user2" },
    { user_id: 3, user_nickname: "user3" },
];
const searchFriends = () => {
    if (nickname.value.trim() === '') {
        searchResults.value = null;
        return;
    }
    const filteredUsers = dummyUsers.filter(user => user.user_nickname.includes(nickname.value));
    searchResults.value = filteredUsers;
};
const followUser = (userId) => {
    // 팔로우 기능을 구현하는 코드 작성
    console.log(`팔로우 버튼이 클릭되었습니다. 사용자 ID: ${userId}`);
};

const viewProfile = (userId) => {
    // 프로필 보기 기능을 구현하는 코드 작성
    console.log(`프로필 버튼이 클릭되었습니다. 사용자 ID: ${userId}`);
};

// userStore.findAllfriends({ user_nickname: nickname.value })
//     .then(response => {
//         // API 응답이 어떤 형태인지 확인
//         console.log('API Response:', response);

//         // 데이터가 배열로 포함되어 있다면
//         if (response.status === "INT" && response.data && Array.isArray(response.data)) {
//             searchResults.value = response.data;
//         } else {
//             console.error('올바르지 않은 API 응답 형식:', response);
//             searchResults.value = null;
//         }
//     })
//     .catch(error => {
//         console.error('검색 중 오류 발생:', error);
//         searchResults.value = null;
//     });
// };
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
  
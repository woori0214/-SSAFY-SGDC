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
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useUserStore } from "@/stores/user";

const nickname = ref("");
const searchResults = ref(null);
const userStore = useUserStore();

const searchFriends = async() => {
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
            console.log(searchResults.value,"2222222");
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
  // 팔로우 기능을 구현하는 코드 작성
  console.log(`팔로우 버튼이 클릭되었습니다. 사용자 ID: ${userId}`);
};

const viewProfile = (userId) => {
  // 프로필 보기 기능을 구현하는 코드 작성
  console.log(`프로필 버튼이 클릭되었습니다. 사용자 ID: ${userId}`);
};

onMounted(() => {
  document.body.addEventListener("click", closeSearchResults);
  window.addEventListener("scroll", closeSearchResultsOnScroll);
});

onUnmounted(() => {
  document.body.removeEventListener("click", closeSearchResults);
  window.removeEventListener("scroll", closeSearchResultsOnScroll);
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
.main-search-borad {
  height: 100%;
  display: flex;
  align-items: center;
  gap: 5px;
}
.searchBar {
  height: calc(80% - 5px);
  border: 2px solid #83b0e1;
  border-radius: 3px;
  padding: none;
}
.searchBtn {
  height: 80%;
    background-color: #83b0e1;
    border: 0px;
    border-radius: 3px;
    transition: background-color 0.3s ease;
}
.searchBtn:hover{
    background-color: #e1ecf7;
    color: #83b0e1;
}
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
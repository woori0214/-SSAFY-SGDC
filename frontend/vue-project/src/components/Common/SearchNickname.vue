<template>
  <div>
    <input type="text" v-model="nickname" placeholder="친구 닉네임 검색..." @input="searchFriends">
    <div v-if="searchResults && searchResults.length > 0" class="search-results">
      <ul>
        <p v-for="result in enhancedSearchResults" :key="result.userId">
          {{ result.userNickname }}
          <button @click="() => result.isFollowing ? unfollowUser(result.userId) : followUser(result.userId)"
            class="follow-button">
            {{ result.isFollowing ? '언팔로우' : '팔로우' }}
          </button>
          <button @click="viewProfile(result.userId)" class="profile-button">프로필</button>
        </p>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, watchEffect } from "vue";
import router from "@/router";

import { useUserStore } from "@/stores/user";
import { useFollowStore } from "@/stores/follow";
import { useUserStorageStore } from "@/stores/userStorage";

const userStore = useUserStore();
const userFollowStore = useFollowStore();
const useUserStorage = useUserStorageStore();
const userInformation = useUserStorage.getUserInformation();
const nickname = ref('');
const searchResults = ref(null);
const isFollowingMap = ref(new Map());

const searchFriends = async () => {
  const trimmedNickname = nickname.value.trim();
  if (trimmedNickname === '') {
    searchResults.value = null;
    return;
  }
  try {
    const response = await userStore.findAllfriends(trimmedNickname);
    if (Array.isArray(response.data.data.content)) {
      searchResults.value = response.data.data.content.map((user) => {
        return {
          userId: user.userId,
          userNickname: user.userNickname
        };
      });
    } else {
      console.error('올바르지 않은 API 응답 형식:', response);
      searchResults.value = [];
    }
  } catch (error) {
    console.error('검색 중 오류 발생:', error);
    searchResults.value = [];
  }
};

const followUser = async (userId) => {
  const useridMe = useUserStorage.getUserInformation().user_id;
  try {
    await userFollowStore.plusSsallowing2(userId, useridMe);
    isFollowingMap.value.set(userId, true);
    console.log('팔로우 성공')
    console.log(isFollowingMap.value.get(userId));
    
  } catch (error) {
    console.error('팔로우 실패:', error);
    console.log(userId);
    console.log(useridMe);
    console.log(isFollowingMap.value.get(userId));

  }
};

const viewProfile = (userId) => {
  router.push({ name: 'MyPage', params: { userId: userId } });
};

const updateFollowingStatus = async () => {
  const users = searchResults.value;
  if (users) {
    for (const user of users) {
      try {
        const isFollowing = await checkFollowingStatus(user.userId);
        isFollowingMap.value.set(user.userId, isFollowing);
      } catch (error) {
        console.error('팔로잉 상태 업데이트 중 오류 발생:', error);
      }
    }
  }
};

watchEffect(() => {
  updateFollowingStatus();
});

const checkFollowingStatus = async (userId) => {
  console.log(userId)
  const useridMe = useUserStorage.getUserInformation().user_id;
  const checkusers = {
    user_id: useridMe,
    following_id: userId
  };

  try {
    const response = await userFollowStore.checkSsallowing(checkusers);
    console.log(response)
    return response.data.success;
  } catch (error) {
    console.error('팔로잉 상태 확인 중 오류 발생:', error);
    throw error;
  }
};

const unfollowUser = async (userId) => {
  const useridMe = useUserStorage.getUserInformation().user_id;
  const unSsallowingData = {
    user_id: userId,
    following_id: useridMe
  };

  try {
    await userFollowStore.deleteSsallowing(unSsallowingData);
    isFollowingMap.value.set(userId, false);

    console.log('언팔로우 성공');
    
    // console.log(isFollowingMap.get(userId));
  } catch (error) {
    console.error('언팔로우 실패:', error);
  }
};
const closeSearchResults = (event) => {
  if (!event.target.closest('.search-results')) {
    searchResults.value = null;
  }
};

// onMounted(() => {
//   document.addEventListener('click', closeSearchResults);
// });

// onUnmounted(() => {
//   document.removeEventListener('click', closeSearchResults);
// });
const enhancedSearchResults = computed(() => {
  // isFollowingMap의 반응성을 활성화하기 위해, 맵 전체 또는 특정 키에 접근
  // 여기서는 users 배열을 기반으로 각 userId에 대한 isFollowing 값을 참조
  const users = searchResults.value;
  if (users) {
    return users.map(user => ({
      ...user,
      // isFollowingMap에서 값을 직접 참조하여 반응성을 활성화
      isFollowing: isFollowingMap.value.get(user.userId) ?? false
    }));
  }
  return [];
});
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

.searchBtn:hover {
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
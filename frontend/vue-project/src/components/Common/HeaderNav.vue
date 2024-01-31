<template>
    <header>
        <div class="header-wrapper">
            <div class="header-links">
                <div v-if="!userLoginStore.loginUser" class="login-signup-links">
                    <RouterLink to="/login" class="nav-link">로그인</RouterLink>
                    <RouterLink to="/signup" class="nav-link">회원가입</RouterLink>
                </div>
                <div v-else class="user-nav">
                    <span>{{ userLoginStore.userNickname }}님, 안녕하세요!</span>
                    <a @click="logout" class="nav-link logout">로그아웃</a>
                </div>
            </div>
        </div>
        <div class="mainimage">
            <img src="@/assets/mainimage.gif" class="header-image">

        </div>
    </header>
</template>
  
<style scoped>
.header-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
}

.header-links {
    display: flex;
    align-items: center;
    justify-content: flex-end;

    flex-grow: 1;

}

.login-signup-links {
    margin-right: 10px;
}

.nav-link {
    text-decoration: none;
    color: #333;
    margin-right: 10px;
}

.user-nav {
    display: flex;
    align-items: center;
}

.logout {
    cursor: pointer;
    margin-left: 10px;
    color: #333;
}

.header-image {
    width: 30%;
    /* 이미지의 너비를 부모 요소 너비의 50%로 설정 */
    height: auto;
    /* 높이를 자동으로 조절하여 비율 유지 */
    justify-content: center;
}
.mainimage{
    display: flex;
    justify-content: center;
}
</style>
  
<script setup>
import { useLoginStore } from '@/stores/login';

const userLoginStore = useLoginStore();
const logout = function () {
    userLoginStore.isLogout();
};
</script>
  
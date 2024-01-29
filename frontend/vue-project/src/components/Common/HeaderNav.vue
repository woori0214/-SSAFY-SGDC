<template>
    <header>
        <div class="header-wrapper">

            <b-navbar-nav v-if="!userLoginStore.loginUser" class="navbar-login">
                <b-nav-item>
                    <RouterLink to="/login" tag="span" class="nav-link">로그인</RouterLink>
                </b-nav-item>
                <b-nav-item>
                    <RouterLink to="/signup" tag="span" class="nav-link">회원가입</RouterLink>
                </b-nav-item>
            </b-navbar-nav>
            <div v-else>
            <b-navbar-nav class="user-nav" right>
                <span v-if="userLoginStore.loginUser">{{ userLoginStore.userNickname }}님, 안녕하세요!</span>
                <span @click="logout" class="nav-link logout">로그아웃</span>
            </b-navbar-nav>
            </div>
        </div>
    </header>
</template>


<script setup>
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useLoginStore } from '@/stores/login';

const userLoginStore = useLoginStore();
const logout = function() {
    useLoginStore.isLogout();
}
</script>

<style scoped></style>
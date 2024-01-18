<template>
    <div>
        <h1>로그인</h1>
        <form @submit.prevent="login">
            <div>
                <label for="userId">아이디</label>
                <input type="text" v-model="userId" id="userId" />
            </div>
            <div>
                <label for="password">비밀번호</label>
                <input type="password" v-model="password" id="password" />
            </div>
            <button type="submit">로그인</button>
        </form>
    </div>
</template>
  
<script>
import { ref, computed } from 'vue';

import { useLoginStore } from "@/stores/login";
export default {
    setup() {
        const userId = ref('');
        const password = ref('');
        const loginUser = useLoginStore();
        const login = () => {
            const User = {
                loginId: userId.value,
                userPassword: password.value
            };

            loginUser.isLogin(User)
                .then(() => {
                    console.log('로그인 성공');
                    router.push('/');
                })
                .catch(() => {
                    console.log('로그인 실패');
                });
        };

        return {
            userId,
            password,
            login,
            loginUser,
        };
    }
}
</script>
  
<style scoped>
form {
    max-width: 300px;
    margin: auto;
}

label {
    display: block;
    margin-bottom: 8px;
}
</style>

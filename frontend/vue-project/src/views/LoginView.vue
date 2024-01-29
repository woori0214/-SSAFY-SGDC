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
      <button type="submit" :disabled="isSubmitButtonDisabled">로그인</button>
    </form>
  </div>
</template>
  
<script>
import { ref, computed } from "vue";
import router from "@/router";
import { useLoginStore } from "@/stores/login";


export default {
  setup() {
    const userId = ref("");
    const password = ref("");
    const isSubmitButtonDisabled = ref(false);
    const loginUser = useLoginStore();

    const login = () => {
      if (userId.value == "" || password.value == "") {
        alert("아이디 혹은 비밀번호를 입력하세요.");
        console.log("아이디 혹은 비밀번호를 입력하세요.");
        return;
      }
      isSubmitButtonDisabled.value = true;

      const User = ref({
        loginId: userId.value,
        userPassword: password.value,
      });

      loginUser
        .isLogin(User.value)
        .then(() => {
          console.log("로그인 성공,vue");
          router.push("/");
        })
        .catch((e) => {
          console.log("로그인 실패");
          console.log(e);
        })
        .finally(() => {
          isSubmitButtonDisabled.value = false;
          console.log(userId.value);
          console.log(password.value);
        });
    };

    return {
      userId,
      password,
      login,
      loginUser,
      isSubmitButtonDisabled,
    };
  },
};
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

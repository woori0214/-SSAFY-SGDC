<template>
  <div class="login-body">
    <head>
      <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
        />
      </head>
    <div class="login-title">로그인</div>
    <form @submit.prevent="login" class="login-form">
      <div class="login-form-item">
        <label for="userId" class="login-form-item-label">아이디</label>
        <input
          type="text"
          class="login-form-item-input"
          v-model="userId"
          id="userId"
        />
      </div>
      <div class="login-form-item">
        <label for="password" class="login-form-item-label">비밀번호</label>
        <input
          type="password"
          class="login-form-item-input"
          v-model="password"
          id="password"
        />
      </div>
      <button
        type="submit"
        class="login-form-btn"
        :disabled="isSubmitButtonDisabled"
      >
        <span class="material-symbols-outlined"> login </span>
        로그인
      </button>
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
.login-body {
  /* border: 2px solid red; */
  width: 80%;
  height: 85%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.login-title {
  /* border: 2px solid orange; */

  font-size: 45px;
  font-weight: 700;
  margin-bottom: 20px;
  color: #2196f3;
}
.login-form {
  /* border: 2px solid orange; */
  background-color: #e1ecf7;
  border-radius: 25px;
  padding: 15%;
  display: flex;
  flex-direction: column;
  gap: 30px;
}
.login-form-item {
  /* border: 2px solid yellow; */

  display: flex;
  flex-direction: column;
  gap: 10px;
}
.login-form-item-label {
  /* border: 2px solid green; */

  font-size: 30px;
  font-weight: 600;
}
.login-form-item-input {
  border: 2px solid #e1ecf7;
  border-radius: 8px;

  height: 40px;
  font-size: 24px;
  padding-inline: 10px;
}
.login-form-btn {
  border: 2px solid #aecbeb;
  border-radius: 8px;
  background-color: #aecbeb;
  margin: 0 auto;
  width: 40%;
  min-width: 130px;
  height: 50px;
  font-size: 25px;
  font-weight: 600;
}
</style>

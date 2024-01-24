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

// import { useUserStorageStore } from "@/stores/userStorage";
import { useLoginStore } from "@/stores/login";


export default {
  setup() {
    const userId = ref("");
    const password = ref("");
    const isSubmitButtonDisabled = ref(false);
    const loginUser = useLoginStore();
    // const userStorage = useUserStorageStore();

    const login = () => {
      if (userId.value == "" || password.value == "") {
        alert("아이디 혹은 비밀번호를 입력하세요.");
        console.log("아이디 혹은 비밀번호를 입력하세요.");
        return;
      }
      isSubmitButtonDisabled.value = true;

      const User = {
        loginId: userId.value,
        userPassword: password.value,
      };

      loginUser
        .isLogin(User)
        .then(() => {
          // userStorage.setUserInformation(User);

          console.log("로그인 성공");
          router.push("/");
        })
        .catch(() => {
          console.log("로그인 실패");
        })
        .finally(() => {
          console.log("Hello");
          isSubmitButtonDisabled.value = false;
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

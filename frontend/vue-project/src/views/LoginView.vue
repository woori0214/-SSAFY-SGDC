<template>
  <div class="login-body">
    <head>
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
      />
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
      />
    </head>
    <div class="login-title slideDown">로그인</div>
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
        <span>로그인</span>
      </button>

      <!-- <button class="go-signup-btn" @click="">
        <span class="material-symbols-outlined"> person_add </span>
        회원가입
      </button> -->
      <RouterLink to="/signup" class="go-signup-btn"
        ><span class="material-symbols-outlined"> person_add </span
        >회원가입</RouterLink
      >
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
  height: 70%;
  margin: 0 auto;
  display: flex;
  flex-grow: 1;
  padding-bottom: 50px;
  flex-direction: column;
  /* justify-content: center; << 얘가 높이를 좁게하면 타이틀을 네비하고 겹치게하는 주범이였음 */
  gap: 20px;
}
.login-title {
  /* border: 2px solid orange; */

  font-size: 45px;
  font-weight: 700;
  /* margin-bottom: 20px; */
  color: #2196f3;
}
.login-form {
  /* border: 2px solid orange; */
  background-color: #e1ecf7;
  border-radius: 25px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
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
.go-signup-btn {
  border: 2px solid #aecbeb;
  border-radius: 8px;
  background-color: #f8f9fb;
  color: black;
  margin: 0 auto;
  width: 40%;
  min-width: 130px;
  height: 30px;
  font-size: 20px;
  font-weight: 600;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: -15px;
  text-decoration: none;
}

.slideDown {
  animation-name: slideDown;
  -webkit-animation-name: slideDown;

  animation-duration: 1s;
  -webkit-animation-duration: 1s;

  animation-timing-function: ease;
  -webkit-animation-timing-function: ease;

  visibility: visible !important;
}

@keyframes slideDown {
  0% {
    transform: translateY(-100%);
  }
  50% {
    transform: translateY(8%);
  }
  65% {
    transform: translateY(-4%);
  }
  80% {
    transform: translateY(4%);
  }
  95% {
    transform: translateY(-2%);
  }
  100% {
    transform: translateY(0%);
  }
}

@-webkit-keyframes slideDown {
  0% {
    -webkit-transform: translateY(-100%);
  }
  50% {
    -webkit-transform: translateY(8%);
  }
  65% {
    -webkit-transform: translateY(-4%);
  }
  80% {
    -webkit-transform: translateY(4%);
  }
  95% {
    -webkit-transform: translateY(-2%);
  }
  100% {
    -webkit-transform: translateY(0%);
  }
}
</style>

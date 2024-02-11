

<template>
  <div class="signup-main-body">
    <link rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <link rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <div class="signup-title slideDown">회원가입</div>
    <div class="signup-question-box">
      <button class="signup-question-before-btn" @click="signupSlideprev">
<<<<<<< HEAD
        <
=======

>>>>>>> 00fb29c3f3ec920a152bf29f4ec1e2e010e308bb
      </button>
      <div class="signup-question-slide" :style="signupSlideStyle">
        <!-- 프로필 사진 -->
        <!-- <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-userImg">
              <label for="customFileInput" class="signup-userImg-upload"
                :style="userImg ? `background-image: url(${userImg})` : ``"></label>
               -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-userImg">
              <!-- 이미지 미리보기 -->
              <img v-if="previewUrl" :src="previewUrl" alt="Profile preview" class="thumbnail" />
              <div v-else class="signup-userImg-upload" @click="clickFileInput">
                <!-- <div class="signup-userImg-context">프로필을<br />업로드 하세요.</div> -->
              </div>

              <!-- 실제 파일 입력 -->
              <input type="file" accept="image/*" id="customFileInput" ref="fileInputRef" @change="handleFileUpload" />

              <div class="signup-userImg-context">
                프로필을<br />업로드 하세요.
              </div>
            </div>

            <button id="signup-clear-btn" class="expandUp" @click="signupSlidenext" v-if="!fileUploadError && userImg">
              다음!
            </button>
          </div>
        </div>

        <!-- 아이디 -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-question-context-box">
              <label for="id" class="signup-question-context-what">아이디</label>
            </div>

            <div class="signup-question-context-box">
              <input type="text" v-model="id" @input="idCheck" placeholder="아이디를 입력하세요"
                class="signup-question-context-input" />
            </div>

            <div class="signup-question-context-box">
              <button @click="checkIdAvailability" class="signup-question-context-check">
                중복확인
              </button>
            </div>

            <div class="error-box">
              <p v-if="idError" class="input_error">
                아이디는 4자 이상 20자 이하의 대소문자로 시작하는 조합으로
                입력해야 합니다.
              </p>
              <p v-show="idExists && !idAvailable" class="input_error">
                중복된 아이디가 있습니다.
              </p>
              <p v-show="!idExists && idAvailable" class="success_message">
                사용 가능한 아이디입니다.
              </p>
            </div>
            <button id="signup-clear-btn" class="expandUp" @click="signupSlidenext"
              v-if="!idError && !idExists && idAvailable">
              다음!
            </button>
          </div>
        </div>

        <!-- 싸피 프로젝트 -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-question-context-box">
              <label for="email" class="signup-question-context-what">이메일</label>

              <input type="email" v-model="email" placeholder="싸피프로젝트 사이트 이메일을 입력하세요."
                class="signup-question-context-input" />
            </div>

            <div class="signup-question-context-box">
              <label for="password" class="signup-question-context-what">비밀번호</label>
              <input type="password" v-model="password" placeholder="싸피프로젝트 사이트 비밀번호를 입력하세요."
                class="signup-question-context-input" :style="password != '' ? 'font-family: sans-serif' : ''" />
            </div>

            <div class="signup-question-context-box">
              <button @click="submitForm" class="signup-question-context-check">
                본인확인
              </button>
            </div>

            <div class="error-box">
              <div v-if="submitted" class="confirmation_message">
                <p v-if="isVerificationSuccess" class="success_message">
                  본인확인이 완료되었습니다.
                </p>
                <p v-else class="input_error">본인확인에 실패했습니다.</p>
              </div>
            </div>

            <button id="signup-clear-btn" class="expandUp" @click="signupSlidenext" v-if="isVerificationSuccess">
              다음!
            </button>
          </div>
        </div>

        <!-- 학번 -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-question-context-box">
              <label for="ssafyid" class="signup-question-context-what">ssafy학번</label>
            </div>

            <div class="signup-question-context-box">
              <input type="text" v-model="ssafyid" placeholder="ssafy학번을 입력하세요" class="signup-question-context-input" />
            </div>

            <div class="signup-question-context-box">
              <button @click="checkStudentnumAvailability" class="signup-question-context-check">
                중복확인
              </button>
            </div>

            <div class="error-box">
              <p v-show="ssafyidExists" class="input_error">
                중복된 학번입니다.
              </p>
              <p v-show="ssafyidAvailable" class="success_message">
                학번이 인증되었습니다.
              </p>
            </div>

            <button id="signup-clear-btn" class="expandUp" @click="signupSlidenext"
              v-if="!ssafyidExists && ssafyidAvailable">
              다음!
            </button>
          </div>
        </div>

        <!-- 사용자 이름 -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-question-context-box">
              <label for="name" class="signup-question-context-what">이름</label>
            </div>

            <div class="signup-question-context-box">
              <input type="text" v-model="name" @input="nameCheck" placeholder="이름을 입력하세요"
                class="signup-question-context-input" />
            </div>

            <div class="error-box">
              <p v-if="nameError" class="input_error">
                이름은 2자 이상, 10자 이하의 한글이어야 합니다.
              </p>
            </div>

            <button id="signup-clear-btn" class="expandUp" @click="signupSlidenext" v-if="!nameError && name != ''">
              다음!
            </button>
          </div>
        </div>

        <!-- 닉네임 -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-question-context-box">
              <label for="nickname" class="signup-question-context-what">닉네임</label>
            </div>

            <div class="signup-question-context-box">
              <input type="text" v-model="nickname" @input="nicknameCheck" placeholder="닉네임을 입력하세요"
                class="signup-question-context-input" />
            </div>

            <div class="signup-question-context-box">
              <button @click="checkNickAvailability" class="signup-question-context-check">
                중복확인
              </button>
            </div>

            <div class="error-box">
              <p v-if="nicknameError" class="input_error">
                닉네임은 2자 이상, 10자 이하의 한글이어야 합니다.
              </p>
              <p v-show="nicknameExists" class="input_error">
                중복된 닉네임이 있습니다.
              </p>
              <p v-show="nicknameAvailable" class="success_message">
                사용 가능한 닉네임입니다.
              </p>
            </div>

            <button id="signup-clear-btn" class="expandUp" @click="signupSlidenext"
              v-if="!nicknameError && !nicknameExists && nicknameAvailable">
              다음!
            </button>
          </div>
        </div>

        <!-- 전화번호 -->
        <div class="signup-question-item">
          <div class="signup-question-context">
            <div class="signup-question-context-box">
              <label for="phonenumber" class="signup-question-context-what">전화번호</label>
            </div>

            <div class="signup-question-context-box">
              <input type="text" v-model="phonenumber" @input="phonenumberCheck" placeholder="-를 제외하고 입력해주세요."
                class="signup-question-context-input" />
            </div>

            <div class="signup-question-context-box">
              <button @click="checkPhoneNumAvailability" class="signup-question-context-check">
                중복확인
              </button>
            </div>

            <div class="error-box">
              <p v-if="phonenumberError" class="input_error">
                유효한 전화번호를 입력하세요.
              </p>
              <p v-show="phonenumberExists && !phonenumberAvailable" class="input_error">
                중복된 전화번호가 있습니다.
              </p>
              <p v-show="!phonenumberExists && phonenumberAvailable" class="success_message">
                사용 가능한 번호입니다.
              </p>
            </div>
          </div>
        </div>
        <!-- <div>
      <label for="alert">랜덤 도전장 수신 여부</label>
      <div class="toggle_switch">
        <input
          type="checkbox"
          id="alert_toggle"
          v-model="isAlert"
          @input="toggleAlert"
        />
        <label for="alert_toggle"></label>
      </div>
    </div> -->
      </div>
      <button class="signup-question-next-btn" @click="signupSlidenext">
        >
      </button>
    </div>
    <!-- 회원가입 제출 -->
    <button class="submitButton" @click="submitForm2" :disabled="!hasAnyError">
      회원가입
    </button>

    <div class="signup-history-body">
      <div class="signup-question-history">
        <div class="history-item" :style="{ backgroundColor: previewUrl ? '#aecbeb' : '#f8f9fb' }">
          <div class="history-item-isclear" :style="previewUrl ? 'background-color: #2196f3' : ''"></div>
          프로필 사진
        </div>
        <div class="history-item" :style="!idError && !idExists && idAvailable
          ? ''
          : 'background-color: #f8f9fb'
          ">
          <div class="history-item-isclear" :style="!idError && !idExists && idAvailable
            ? 'background-color: #2196f3'
            : ''
            "></div>
          아이디 설정
        </div>
        <div class="history-item" :style="isVerificationSuccess ? '' : 'background-color: #f8f9fb'">
          <div class="history-item-isclear" :style="isVerificationSuccess ? 'background-color: #2196f3' : ''"></div>
          싸피 프로젝트 본인 인증
        </div>
        <div class="history-item" :style="!ssafyidExists && ssafyidAvailable
          ? ''
          : 'background-color: #f8f9fb'
          ">
          <div class="history-item-isclear" :style="!ssafyidExists && ssafyidAvailable
            ? 'background-color: #2196f3'
            : ''
            "></div>
          싸피 학번 입력
        </div>
        <div class="history-item" :style="!nameError && name != '' ? '' : 'background-color: #f8f9fb'">
          <div class="history-item-isclear" :style="!nameError && name != '' ? 'background-color: #2196f3' : ''"></div>
          이름 입력
        </div>
        <div class="history-item" :style="!nicknameError && !nicknameExists && nicknameAvailable
          ? ''
          : 'background-color: #f8f9fb'
          ">
          <div class="history-item-isclear" :style="!nicknameError && !nicknameExists && nicknameAvailable
            ? 'background-color: #2196f3'
            : ''
            "></div>
          닉네임 설정
        </div>
        <div class="history-item" :style="!phonenumberError && !phonenumberExists && phonenumberAvailable
          ? ''
          : 'background-color: #f8f9fb'
          ">
          <div class="history-item-isclear" :style="!phonenumberError && !phonenumberExists && phonenumberAvailable
            ? 'background-color: #2196f3'
            : ''
            "></div>
          전화번호 입력
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

import { useSignupStore } from "@/stores/signup";
import { useLoginStore } from "@/stores/login";

export default {
  setup() {
    const router = useRouter();

    const id = ref("");
    const userImg = ref("");
    const fileInputRef = ref(null);
    const fileUploadError = ref(null);
    const email = ref("");
    const password = ref("");
    const submitted = ref(false);
    const ssafyid = ref("");
    const name = ref("");
    const nickname = ref("");

    const phonenumber = ref("");
    const phonenumberExists = ref(false);
    const phonenumberAvailable = ref(false);

    const isAlert = ref(false);

    const idExists = ref(false);
    const idAvailable = ref(false);

    const nicknameExists = ref(false);
    const nicknameAvailable = ref(false);

    const nameExists = ref(false);
    const nameAvailable = ref(false);

    const ssafyidExists = ref(false);
    const ssafyidAvailable = ref(false);

    const isVerificationSuccess = ref(false);

    const signUp = useSignupStore();

    const nameError = ref(false);
    const nicknameError = ref(false);
    const idError = ref(false);
    const phonenumberError = ref(false);

    const userImageDefault = ref("@/assets/camera2.png");
    // 모든 정보가 입력되었는지 검사하는 함수
    const hasAnyError = computed(() => {
      // console.log('idError: ' + !idError.value);
      // console.log('nameError: ' +!nameError.value);
      // console.log('nicknameError: ' + !nicknameError.value);
      // console.log('phonenumberError: ' + !phonenumberError.value);
      // console.log('idExists: ' + !idExists.value);
      // console.log('nicknameExists: ' + !nicknameExists.value);
      // console.log('idAvailable: ' + idAvailable.value);
      // console.log('nicknameAvailable: ' + nicknameAvailable.value);
      // console.log('ssafyidExists: ' + !ssafyidExists.value);
      // console.log('ssafyidAvailable: ' + ssafyidAvailable.value);
      // console.log('phonenumberExists: ' + !phonenumberExists.value);
      // console.log('phonenumberAvailable: ' + phonenumberAvailable.value);
      // console.log('submitted: ' + submitted.value);
      // console.log('End');

      return (
        !idError.value &&
        !nameError.value &&
        !nicknameError.value &&
        !phonenumberError.value &&
        !idExists.value &&
        !nicknameExists.value &&
        idAvailable.value &&
        nicknameAvailable.value &&
        !ssafyidExists.value &&
        ssafyidAvailable.value &&
        !phonenumberExists.value &&
        phonenumberAvailable.value &&
        submitted.value
      );
    });

    const currentIndex = ref(0);
    const totalQuestionLength = ref(7);

    const signupSlideprev = () => {
      currentIndex.value =
        (currentIndex.value - 1 + totalQuestionLength.value) %
        totalQuestionLength.value;
    };

    const signupSlidenext = () => {
      currentIndex.value = (currentIndex.value + 1) % totalQuestionLength.value;
    };

    const signupSlidegoTo = (index) => {
      currentIndex.value = index;
    };

    const signupSlideStyle = computed(() => {
      return {
        transform: `translateX(-${currentIndex.value * 100}%)`,
      };
      // transform: `translateX(-${currentIndex.value * 100}%)`,
    });

    // 프로젝트 싸피 회원 인증하는 함수
    const submitForm = () => {
      const ssafy = { usernameOrEmail: email.value, password: password.value };

      signUp
        .isssafy(ssafy)
        .then(() => {
          console.log("인증 완료");
          submitted.value = true;
          isVerificationSuccess.value = true;
        })
        .catch(() => {
          console.log("싸피생이 아닙니다");

          submitted.value = true;
          isVerificationSuccess.value = false;
        });
    };

    //아이디 중복 확인
    const checkIdAvailability = () => {
      console.log("아이디 중복 확인 함수 실행" + id.value);
      signUp
        .isid(id.value)
        .then((response) => {
          console.log(response);
          if (!(response.data.data.result == "true")) {
            idExists.value = true;
            idAvailable.value = false;
          } else {
            idExists.value = false;
            idAvailable.value = true;
          }
        })
        .catch(() => {
          console.log("error");
        });
    };

    // 닉네임 중복 확인
    const checkNickAvailability = () => {
      signUp
        .isnickname(nickname.value)
        .then((response) => {
          if (!(response.data.data.result == "true")) {
            nicknameExists.value = true;
            nicknameAvailable.value = false;
          } else {
            nicknameExists.value = false;
            nicknameAvailable.value = true;
          }
        })
        .catch(() => {
          console.log("error");
        });
    };

    // 싸피 학번 중복 확인
    const checkStudentnumAvailability = () => {
      signUp
        .isstudentnum(ssafyid.value)
        .then((response) => {
          if (!(response.data.data.result == "true")) {
            ssafyidExists.value = true;
            ssafyidAvailable.value = false;
          } else {
            ssafyidExists.value = false;
            ssafyidAvailable.value = true;
          }
        })
        .catch(() => {
          console.log("error");
        });
    };

    // 전화번호 중복 확인
    const checkPhoneNumAvailability = () => {
      console.log("전화번호 인증: " + phonenumber.value);
      signUp
        .authphone(phonenumber.value)
        .then((response) => {
          if (!(response.data.data.result == "true")) {
            phonenumberExists.value = true;
            phonenumberAvailable.value = false;
          } else {
            phonenumberExists.value = false;
            phonenumberAvailable.value = true;
          }
        })
        .catch(() => {
          console.log("error~~");
        });
    };

    // 이름 규칙 검사
    const nameCheck = function () {
      if (!/^[가-힣]{2,10}$/.test(name.value)) {
        nameError.value = true;
      } else {
        nameError.value = false;
      }
    };

    // 닉네임 규칙 검사
    const nicknameCheck = function () {
      if (!/^[가-힣]{2,10}$/.test(nickname.value)) {
        nicknameError.value = true;
      } else {
        nicknameError.value = false;
      }
    };

    //아이디 규칙 검사
    const idCheck = function () {
      if (!/^[A-Za-z][A-Za-z0-9]{3,19}$/.test(id.value)) {
        idError.value = true;
      } else {
        idError.value = false;
      }
    };

    // 전화번호 규칙 검사
    const phonenumberCheck = function () {
      if (!/^\d{11}$/.test(phonenumber.value)) {
        phonenumberError.value = true;
      } else {
        phonenumberError.value = false;
      }
    };

    // 랜덤 도전장 수신 여부 토글 동작 함수
    const toggleAlert = () => {
      isAlert.value = !isAlert.value;
    };

    // 회원가입 성공시 가입 정보로 자동 로그인하는 함수
    const autoLoginForSignup = (signupInformation) => {
      const User = {
        loginId: signupInformation.loginId,
        userPassword: signupInformation.userPassword,
      };

      loginUser
        .isLogin(User)
        .then(() => {
          // userStorage.setUserInformation(User);

          console.log("로그인 성공");
          try {
            router.push({ name: "Main" });
          } catch (error) {
            alert("라우팅 에러발생함" + error);
          }
        })
        .catch(() => {
          console.log("로그인 실패");
        })
        .finally(() => {
          console.log("Hello");
          isSubmitButtonDisabled.value = false;
        });
    };
    const profile = ref(null);
    // 회원가입 최종 제출 검사 및 회원가입
    const submitForm2 = () => {
      console.log(hasAnyError.value);
      console.log(hasAnyError);
      console.log(ssafyidExists.value);
      if (!hasAnyError.value && ssafyidExists.value) {
        alert("양식을 올바르게 입력해주세요.");
        return;
      }
      // const newUser = ref({
      //   loginId: id.value,
      //   userEmail: email.value,
      //   userSsafyId: ssafyid.value,
      //   userNickname: nickname.value,
      //   userName: name.value,
      //   userPassword: password.value,
      //   userImg: userImg.value,
      //   userPhone: phonenumber.value,
      //   // isAlert: isAlert.value ? "활성" : "비활성",
      //   kakaoPush: false,
      // });
      // const formData = new FormData();
      // // 텍스트 필드 데이터 추가
      // formData.append('loginId', id.value);
      // formData.append('userEmail', email.value);
      // formData.append('userSsafyId', ssafyid.value);
      // formData.append('userNickname', nickname.value);
      // formData.append('userName', name.value);
      // formData.append('userPassword', password.value);
      // formData.append('userPhone', phonenumber.value);
      // 선택된 파일이 있다면 파일 데이터 추가
      // 파일 저장을 위한 ref 변수
      const userSignUp = ref({
        loginId: id.value,
        userEmail: email.value,
        userSsafyId: ssafyid.value,
        userNickname: nickname.value,
        userName: name.value,
        userPassword: password.value,
        userImg: '',
        userPhone: phonenumber.value,
        kakaoPush: true // 이 부분은 실제 사용하는 변수에 따라 조정하세요.
      });
      // function onFileChange(fileInputRef) {
      //   profile.value = fileInputRef.target.files[0];

      // }
      // const jsonBlob = new Blob([JSON.stringify(userData)], { type: "application/json" });
      // if (selectedFile.value) {
      //   formData.append('userImg', selectedFile.value);
      // }
      // formData.append('kakaoPush', true);
      // signUp.submitNewUser(formData)
      //   .then((response) => {
      //     console.log("회원가입 완료", response);
      //     router.push({ name: "Main" }).catch((error) => {
      //       alert("라우팅 에러발생함" + error);
      //     });
      //   })
      //   .catch((error) => {
      //     console.log("회원가입 실패", error);
      //   });
      const formData = new FormData();
      formData.append('userSignUpJson', JSON.stringify(userSignUp.value));
      formData.append('profileImage', profile.value);
      signUp.submitNewUser(formData)
        .then((response) => {
          console.log("회원가입 완료", response);
          // 성공 후 처리, 예를 들어 페이지 이동
          router.push({ name: "Main" }).catch((error) => {
            alert("라우팅 에러 발생: " + error);
          });
        })
        .catch((error) => {
          console.log("회원가입 실패", error);
          // 실패 처리 로직
        });

    }
    // if (fileInputRef.value && fileInputRef.value.files[0]) {
    //   formData.append("userImg", fileInputRef.value.files[0]);
    // }

    // Axios, Fetch API 등을 사용하여 서버로 데이터 전송



    const previewUrl = ref(""); // 이미지 미리보기 URL을 저장할 상태
    // 유저 프로필 사진 업로드
    const selectedFile = ref(null); // 선택된 파일을 저장할 상태


    const handleFileUpload = (fileInputRef) => {
      const file = fileInputRef.target.files[0];
      profile.value = file;
      if (profile) {
        const reader = new FileReader();
        reader.onload = (e) => {
          previewUrl.value = e.target.result; // 파일 리더가 읽은 데이터로 미리보기 URL 업데이트
        };
        reader.readAsDataURL(file); // 파일 내용을 읽어 데이터 URL로 변환
      }
    };

    // 레이블 클릭 시 실제 파일 입력을 트리거하는 함수
    const clickFileInput = () => {
      fileInputRef.value.click();
    };


    return {
      id,
      email,
      password,
      submitted,
      submitForm,
      ssafyid,
      name,
      nickname,
      phonenumber,
      // isAlert,
      checkIdAvailability,
      checkNickAvailability,
      checkStudentnumAvailability,

      idExists,
      idAvailable,
      nicknameExists,
      nicknameAvailable,
      nameExists,
      nameAvailable,
      signUp,
      nameCheck,
      nicknameCheck,
      idCheck,
      phonenumberCheck,
      nameError,
      nicknameError,
      idError,
      phonenumberError,
      toggleAlert,
      submitForm2,
      hasAnyError,
      ssafyidExists,
      ssafyidAvailable,
      userImg,
      fileInputRef,
      handleFileUpload,
      fileUploadError,
      isVerificationSuccess,
      autoLoginForSignup,
      phonenumberExists,
      phonenumberAvailable,
      checkPhoneNumAvailability,
      signupSlideStyle,
      signupSlideprev,
      signupSlidenext,
      signupSlidegoTo,
      userImageDefault,
      selectedFile,
      previewUrl,
      clickFileInput,

    };
  },
};
</script>
  
<style scoped>
#signup-clear-btn {
  position: absolute;
  bottom: -50px;
  width: 150px;
  font-size: 25px;
  font-weight: 600;
  border: 3px solid #2196f3;
  border-radius: 10px;
  background-color: #f8f9fb;
  color: #202020;
}

.signup-main-body {
  /* border: 2px solid red; */
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 85%;
  justify-content: space-between;
}

.signup-title {
  /* border: 2px solid orange; */

  color: #2196f3;
  width: 70%;
  font-size: 45px;
  font-weight: 700;
}

.signup-question-box {
  /* border: 2px solid orange; */

  width: 100%;
  height: 70%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.signup-question-before-btn {
  border: 2px solid #71a5de;
  border-radius: 5px;
  background-color: #71a5de;
  color: #f8f9fb;

  z-index: 2;
  height: 40%;
  width: 20px;
  font-size: 10px;
  text-align: center;
}

.signup-question-next-btn {
  border: 2px solid #71a5de;
  border-radius: 5px;
  background-color: #71a5de;
  color: #f8f9fb;

  z-index: 2;
  height: 40%;
  width: 20px;
  font-size: 10px;
  text-align: center;
}

.signup-question-slide {
  /* border: 2px solid yellow; */

  width: 90%;
  height: 85%;
  display: flex;
  transition: transform 0.3s ease;
}

.signup-question-item {
  /* border: 2px solid green; */

  flex: 0 0 auto;
  width: 100%;
  display: flex;
  align-content: center;
}

.signup-question-context {
  /* border: 2px solid green; */
  background-color: #e1ecf7;
  border-radius: 25px;

  width: 80%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.signup-question-context-what {
  /* border: 2px solid purple; */

  width: 40%;
  margin-left: 0;
  margin-right: auto;
  font-size: 30px;
  font-weight: 700;
  color: #71a5de;
}

.signup-question-context-input {
  border: 2px solid #f8f9fb;
  border-radius: 5px;

  width: calc(100% - 20px);
  height: 40px;
  margin: 0 auto;
  font-size: 22px;
  padding-inline: 10px;
  padding-block: 5px;
}

.signup-question-context-check {
  border: 2px solid #aecbeb;
  border-radius: 5px;
  background-color: #aecbeb;
  color: black;

  font-size: 18px;
  font-weight: 600;
  padding-inline: 5px;
  margin-left: auto;
  margin-right: 0;
}

.signup-question-context-item {
  border: 2px solid purple;

  width: 100%;
  margin: 0 auto;
}

.signup-question-context-box {
  /* border: 2px solid blue; */
  width: 80%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

/* Signup Question Styles */
#customFileInput {
  display: none;
}

.signup-userImg {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.signup-userImg-upload {
  border: 3px solid #71a5de;
  background-color: #fff;
  border-radius: 100%;
  background-image: url("@/assets/camera2.png");
  background-size: contain;
  /* 이미지가 컴포넌트를 완전히 채우도록 설정 */
  background-position: center;
  /* 이미지 중앙 정렬 */
  background-repeat: no-repeat;

  width: 30%;
  height: 0;
  padding-bottom: 30%;
}

.signup-userImg-context {
  /* border: 1px solid red; */

  font-size: 35px;
  font-weight: 700;
}

/* Signup Question Styles End */

.signup-history-body {
  width: 80%;
  position: relative;
}

.signup-question-history {
  /* border: 2px solid orange; */

  display: flex;
  justify-content: left;
  overflow: auto;
  gap: 15px;
  padding-inline: 30px;
}

.signup-history-body::before,
.signup-history-body::after {
  content: "";
  position: absolute;
  top: 0;
  bottom: 0;
  width: 6%;
  /* 흐릿한 효과의 너비 */
  z-index: 2;
  pointer-events: none;
  /* 마우스 이벤트 방지 */
}

.signup-history-body::before {
  left: 0;
  background: linear-gradient(to right, #f8f9fb, rgba(255, 255, 255, 0));
}

.signup-history-body::after {
  right: 0;
  background: linear-gradient(to left, #f8f9fb, rgba(255, 255, 255, 0));
}

.signup-question-history::-webkit-scrollbar {
  height: 5px;
  border: 1px solid black;
}

.signup-question-history::-webkit-scrollbar-thumb {
  background-color: #71a5de;
  background-clip: padding-box;
  /* border: 2px solid transparent; */
}

.signup-question-history::-webkit-scrollbar-track {
  background-color: rgb(255, 255, 255);
  /* box-shadow: inset 0px 0px 5px white; */
}

.history-item {
  border: 2px solid #aecbeb;
  border-radius: 20px;
  background-color: #aecbeb;
  font-size: 20px;
  font-weight: 600;
  white-space: nowrap;
  display: flex;
  align-items: center;
  gap: 5px;
  padding-inline: 5px;
  transition: background-color 0.3s ease;
}

.history-item-isclear {
  border: 3px solid #2196f3;
  border-radius: 100%;
  /* 임시 크기 */
  width: 10px;
  height: 10px;
  /* 임시 크기 */
}

.submitButton {
  font-size: 22px;
  font-weight: 600;
  border: 3px solid #2196f3;
  border-radius: 8px;
  padding-inline: 10px;
  background-color: #f8f9fb;
  margin-right: 15%;
  margin-left: auto;
}

.error-box {
  /* border: 2px solid red; */
  position: absolute;
  width: 80%;
  bottom: 0px;
  text-align: center;
}

.error-item {}

/* 수신 여부 토글 CSS --------------------------------------- */
.toggle_switch {
  position: relative;
  display: inline-block;
  width: 30px;
  height: 15px;
}

.toggle_switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.toggle_switch label {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  border-radius: 15px;
  cursor: pointer;
}

.toggle_switch label:after {
  content: "";
  position: absolute;
  top: 2px;
  left: 2px;
  width: 13px;
  height: 13px;
  background-color: #fff;
  border-radius: 50%;
  transition: 0.3s;
}

.toggle_switch input:checked+label {
  background-color: #2196f3;
}

.toggle_switch input:checked+label:after {
  transform: translateX(15px);
}

.thumbnail {
  max-width: 200px;
  /* 원하는 크기로 조절하세요 */
  max-height: 200px;
  margin-top: 5px;
  /* 이미지와 다른 입력란 간격 조절 */
}

@media only screen and (max-width: 600px) {
  .toggle_switch {
    width: 20px;
    height: 10px;
  }

  .toggle_switch label:after {
    width: 9px;
    height: 9px;
  }

  .thumbnail {
    max-width: 30px;
    max-height: 30px;
  }

  h1 {
    font-size: 1.5em;
    /* Smaller font size for smaller screens */
  }

  input[type="text"],
  input[type="email"],
  input[type="password"],
  button {
    width: 100%;
    /* Full width on small screens */
    margin: 0;
    /* Remove margins */
    box-sizing: border-box;
    /* Include padding in width calculation */
    font-family: "jua";
  }

  .input_error,
  .success_message,
  .confirmation_message {
    font-size: 0.8em;
    /* Smaller font size for error messages */
  }

  /* Adjust padding and margin for smaller screens */
  div {
    padding: 5px;
  }
}

/* You can add more media queries for different breakpoints */

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

.expandUp {
  animation-name: expandUp;
  -webkit-animation-name: expandUp;

  animation-duration: 0.7s;
  -webkit-animation-duration: 0.7s;

  animation-timing-function: ease;
  -webkit-animation-timing-function: ease;

  visibility: visible !important;
}

@keyframes expandUp {
  0% {
    transform: translateY(100%) scale(0.6) scaleY(0.5);
  }

  60% {
    transform: translateY(-7%) scaleY(1.12);
  }

  75% {
    transform: translateY(3%);
  }

  100% {
    transform: translateY(0%) scale(1) scaleY(1);
  }
}

@-webkit-keyframes expandUp {
  0% {
    -webkit-transform: translateY(100%) scale(0.6) scaleY(0.5);
  }

  60% {
    -webkit-transform: translateY(-7%) scaleY(1.12);
  }

  75% {
    -webkit-transform: translateY(3%);
  }

  100% {
    -webkit-transform: translateY(0%) scale(1) scaleY(1);
  }
}
</style>

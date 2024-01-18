

<template>
    <div>

        <h1>회원가입</h1>
        <div>
            <label for="userImg">프로필 사진</label>
            <input type="file" accept="image/*" @change="handleFileUpload" id="userImg" ref="fileInputRef" />
            <p v-if="fileUploadError" class="input_error">{{ fileUploadError }}</p>

            <img v-if="userImg" :src="userImg" alt="프로필 사진" class="thumbnail" />
        </div>

        <div>
            <label for="id">아이디</label>
            <input type="text" v-model="id" @input="idCheck" placeholder="아이디를 입력하세요" />
            <p v-if="idError" class="input_error">
                아이디는 4자 이상 20자 이하의 대소문자로 시작하는 조합으로 입력해야 합니다.
            </p>
            <button @click="checkIdAvailability">중복확인</button>
            <p v-show="idExists && !idAvailable" class="input_error">중복된 아이디가 있습니다.</p>
            <p v-show="!idExists && idAvailable" class="success_message">사용 가능한 아이디입니다.</p>
        </div>
        <div>
            <label for="email">이메일</label>
            <input type="email" v-model="email" placeholder="싸피프로젝트 사이트 이메일을 입력하세요." />
        </div>
        <div>
            <label for="password">비밀번호</label>
            <input type="password" v-model="password" placeholder="싸피프로젝트 사이트 비밀번호를 입력하세요." />
        </div>
        <div>
            <label for="ssafyid">ssafy학번</label>
            <input type="text" v-model="ssafyid" placeholder="ssafy학번을 입력하세요" />
            <button @click="checkStudentnumAvailability">중복확인</button>
            <p v-show="ssafyidExists" class="input_error">중복된 학번입니다.</p>
            <p v-show="ssafyidAvailable" class="success_message">학번이 인증되었습니다.</p>
        </div>
        <button @click="submitForm">본인확인</button>
        <div v-if="submitted" class="confirmation_message">
            <p v-if="isVerificationSuccess" class="success_message">본인확인이 완료되었습니다.</p>
            <p v-else class="input_error">본인확인에 실패했습니다.</p>
        </div>

        <div>
            <label for="name">이름</label>
            <input type="text" v-model="name" @input="nameCheck" placeholder="이름을 입력하세요" />
            <p v-if="nameError" class="input_error">이름은 2자 이상, 10자 이하의 한글이어야 합니다.</p>
        </div>

        <div>
            <label for="nickname">닉네임</label>
            <input type="text" v-model="nickname" @input="nicknameCheck" placeholder="닉네임을 입력하세요" />
            <p v-if="nicknameError" class="input_error">닉네임은 2자 이상, 10자 이하의 한글이어야 합니다.</p>
            <button @click="checkNickAvailability">중복확인</button>
            <p v-show="nicknameExists" class="input_error">중복된 닉네임이 있습니다.</p>
            <p v-show="nicknameAvailable" class="success_message">사용 가능한 닉네임입니다.</p>
        </div>

        <div>
            <label for="phonenumber">전화번호</label>
            <input type="text" v-model="phonenumber" @input="phonenumberCheck" placeholder="-를 제외하고 입력해주세요." />
            <p v-if="phonenumberError" class="input_error">유효한 전화번호를 입력하세요.</p>
        </div>
        <div>
            <label for="alert">랜덤 도전장 수신 여부</label>
            <div class="toggle_switch">
                <input type="checkbox" id="alert_toggle" v-model="isAlert" @input="toggleAlert" />
                <label for="alert_toggle"></label>
            </div>

        </div>
        <button @click="submitForm2" :disabled="hasAnyError">회원가입</button>
    </div>
</template>
  
<script>
import { ref, computed } from 'vue';

import { useSignupStore } from "@/stores/signup";





export default {
    setup() {
        const id = ref('');
        const userImg = ref('');
        const fileInputRef = ref(null);
        const fileUploadError = ref(null);
        const email = ref('');
        const password = ref('');
        const submitted = ref(false);
        const ssafyid = ref('');
        const name = ref('');
        const nickname = ref('');
        const phonenumber = ref('');
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
        const hasAnyError = computed(() => {
            return (
                idError.value ||
                nameError.value ||
                nicknameError.value ||
                phonenumberError.value ||
                idExists.value ||
                nicknameExists.value ||
                !idAvailable.value ||
                !nicknameAvailable.value ||
                ssafyidExists.value ||
                !ssafyidAvailable.value ||
                submitted.value
            );
        });
        const submitForm = () => {
            const ssafy = { usernameOrEmail: email.value, password: password.value };

            signUp.isssafy(ssafy)
                .then(() => {
                    console.log('인증 완료');
                    submitted.value = true;
                    isVerificationSuccess.value = true;
                })
                .catch(() => {
                    console.log('싸피생이 아닙니다')
                    
                    submitted.value = true;
                    isVerificationSuccess.value = false;
                })
        }
        const checkIdAvailability = () => {
            signUp.isid(id.value)
                .then((response) => {
                    if (response.status === 200) {
                        idExists.value = true;
                        idAvailable.value = false;
                    } else {
                        idExists.value = false;
                        idAvailable.value = true;
                    }
                })
                .catch(() => {
                    console.log('error');
                });
        };

        const checkNickAvailability = () => {
            signUp.isnickname(nickname.value)
                .then((response) => {
                    if (response.status === 200) {

                        nicknameExists.value = true;
                        nicknameAvailable.value = false;
                    } else {

                        nicknameExists.value = false;
                        nicknameAvailable.value = true;
                    }
                })
                .catch(() => {
                    console.log('error');
                });
        };

        const checkStudentnumAvailability = () => {
            signUp.isstudentnum(ssafyid.value)
                .then((response) => {
                    if (response.status === 200) {
                        ssafyidExists.value = true;
                        ssafyidAvailable.value = false;
                    } else {
                        ssafyidExists.value = false;
                        ssafyidAvailable.value = true;
                    }
                })
                .catch(() => {
                    console.log('error');
                });
        };
        const nameCheck = function () {
            if (!/^[가-힣]{2,10}$/.test(name.value)) {
                nameError.value = true;
            } else {
                nameError.value = false;
            }
        }

        const nicknameCheck = function () {
            if (!/^[가-힣]{2,10}$/.test(nickname.value)) {
                nicknameError.value = true;
            } else {
                nicknameError.value = false;
            }
        }
        const idCheck = function () {
            if (!/^[A-Za-z][A-Za-z0-9]{3,19}$/.test(id.value)) {
                idError.value = true;
            } else {
                idError.value = false;
            }
        };
        const phonenumberCheck = function () {
            if (!/^\d{11}$/.test(phonenumber.value)) {
                phonenumberError.value = true;
            } else {
                phonenumberError.value = false;
            }
        };
        const toggleAlert = () => {
            isAlert.value = !isAlert.value;
        };

        const submitForm2 = () => {
            if (hasAnyError.value || ssafyidExists.value) {
                alert('양식을 올바르게 입력해주세요.');
                return;
            }
            const newUser = {
                loginId: id.value,
                userEmail: email.value,
                userNickname: nickname.value,
                userPassword: password.value,
                userSsafyId: ssafyid.value,
                userPhone: phonenumber.value,
                userName: name.value,
                isAlert: isAlert.value ? '활성' : '비활성',
                userImg: userImg.value,
            };

            signUp.submitNewUser(newUser)
                .then(() => {
                    console.log('회원가입 완료');
                    submitted.value = true;
                    router.push('/');
                })
                .catch(() => {
                    console.log('회원가입 실패');

                });
        };
        const handleFileUpload = () => {
            const file = fileInputRef.value.files[0];
            if (file) {
                userImg.value = URL.createObjectURL(file);
                fileUploadError.value = null;
            }
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
            isAlert,
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





        };
    },
};
</script>
  
<style scoped>
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
    content: '';
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
    background-color: #2196F3;

}

.toggle_switch input:checked+label:after {
    transform: translateX(15px);
}
.thumbnail {
    max-width: 50px; /* 원하는 크기로 조절하세요 */
    max-height: 50px;
    margin-top: 5px; /* 이미지와 다른 입력란 간격 조절 */
}
</style>

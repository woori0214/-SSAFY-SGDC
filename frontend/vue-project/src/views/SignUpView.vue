
<template>
    <div>

        <h1>회원가입</h1>
        <div>
            <label for="id">아이디</label>
            <input type="id" v-model="id" placeholder="아이디를 입력하세요" />
            <button @click="checkIdAvailability">중복확인</button>
            <p v-show="idExists" class="input-error">중복된 아이디가 있습니다.</p>
            <p v-show="idAvailable" class="success-message">사용 가능한 아이디입니다.</p>
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
            <input type="ssafyid" v-model="ssafyid" placeholder="ssafy학번을 입력하세요" />
            <button @click="checkStudentnumAvailability">중복확인</button>
            <p v-show="idExists" class="input-error">중복된 학번입니다.</p>
            <p v-show="idAvailable" class="success-message">학번이 인증되었습니다.</p>
        </div>
        <button @click="submitForm">본인확인</button>

        <div>
            <label for="name">이름</label>
            <input type="name" v-model="name" placeholder="이름을 입력하세요" />
        </div>
        <div>
            <label for="nickname">닉네임</label>
            <input type="nickname" v-model="nickname" placeholder="닉네임을 입력하세요" />
            <button @click="checkNickAvailability">중복확인</button>
            <p v-show="idExists" class="input-error">중복된 닉네임이 있습니다.</p>
            <p v-show="idAvailable" class="success-message">사용 가능한 닉네임입니다.</p>
        </div>
        <div>
            <label for="phonenumber">전화번호</label>
            <input type="phonenumber" v-model="phonenumber" placeholder="휴대전화번호를 입력하세요" />
           
        </div>
        <div>
            <label for="alert">랜덤 도전장 수신 여부</label>
            <div class="toggle-switch">
                <input type="checkbox" id="alert-toggle" v-model="isAlert" />
                <label for="alert-toggle"></label>
            </div>
        </div>



    </div>
</template>
  
<script>
import { ref } from 'vue';

import { useSignupStore } from "@/stores/signup";


export default {
    setup() {
        const signUp = useSignupStore();
        const id = ref('');
        const email = ref('');
        const password = ref('');
        const submitted = ref(false);
        const ssafyid = ref('');
        const name = ref('');
        const nickname = ref('');
        const phonenumber = ref('');
        const isAlert = ref('');
        const idExists = ref(false);
        const idAvailable = ref(false);

        const submitForm = () => {
            const ssafy = { usernameOrEmail: email.value, password: password.value };

            signUp.isssafy(ssafy)
                .then(() => {
                    console.log('인증 완료');
                    submitted.value = true;
                    
                })
                .catch(() => {
                    console.log('싸피생이 아닙니다')
                    alert('싸피생만 가입할 수 있습니다')
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
        const checkStudentnumAvailability = () => {
            signUp.isstudentnum(ssafyid.value)
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
            signUp,



        };
    },
};
</script>
  
<style scoped>
.toggle-switch {
    position: relative;
    display: inline-block;
    width: 30px;
    height: 15px;
}

.toggle-switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.toggle-switch label {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #ccc;
    border-radius: 15px;
    cursor: pointer;
}

.toggle-switch label:after {
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

.toggle-switch input:checked+label {
    background-color: #2196F3;
    /* Toggle color when checked */
}

.toggle-switch input:checked+label:after {
    transform: translateX(15px);
}
</style>
  
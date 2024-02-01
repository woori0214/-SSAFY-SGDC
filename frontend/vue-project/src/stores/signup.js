
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { serverURL, v1_URL } from '@/main.js';


export const useSignupStore = defineStore('signup', () => {

    const ssafyurl = 'https://project.ssafy.com/ssafy/api/auth/signin';
    const URL = serverURL + 'user/signup';
    // 사용자 프로젝트 싸피 인증

    const isssafy = function (ssafy) {
        console.log('isssafy 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(ssafyurl, ssafy)
                .then((response) => {

                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    // 아이디 중복 확인
    const isid = function (loginId) {
        console.log('isid 되고있나')

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-id/${loginId}`)
                .then((response) => {
                    console.log('아이디 중복 확인');
                    resolve(response);
                })
                .catch((e) => {
                    console.log('아이디 중복 에러');
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    // 닉네임 중복 확인
    const isnickname = function (NicknameData) {
        console.log('isnickname 되고있나')

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-nick/${NicknameData}`)
                .then((response) => {

                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    // 싸피 학번 중복 확인
    const isstudentnum = function (StudentnumData) {
        console.log('isstudentnum 되고있나')

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-student-num/${StudentnumData}`)
                .then((response) => {

                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    const submitNewUser = function (newUser) {
        console.log('submitNewUser 되고있나');
        console.log(newUser);

        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/`, newUser)
                .then(response => {

                    resolve(response);
                })
                .catch((e) => {

                    reject(e);
                });
        })
    }

    // 전화번호 인증(방법 미정) << 임시로 중복확인만
    const authphone = function (userPhone) {
        console.log('authphone 되고있나' + userPhone);

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-phoneNum/${userPhone}`)
                .then(response => {
                    console.log(response);
                    resolve(response);
                })
                .catch((e) => {
                    console.log(response);
                    reject(e);
                });
        })
    }

    return {
        isid,
        isnickname,
        isssafy,
        isstudentnum,
        authphone,
        submitNewUser,
    }
})




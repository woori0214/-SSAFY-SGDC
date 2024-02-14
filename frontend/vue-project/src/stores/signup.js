
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { serverURL, v1_URL } from './config';


export const useSignupStore = defineStore('signup', () => {

    const ssafyurl = 'https://project.ssafy.com/ssafy/api/auth/signin';
    const URL = serverURL + v1_URL + 'user/signup';
    // 사용자 프로젝트 싸피 인증

    //싸피 이메일 중복 체크
    const isssafyEmail = function (ssafy) {
        // console.log('isssafyEmail 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-student-email/${ssafy.usernameOrEmail}`)
                .then((response) => {
                    // console.log('싸피 이메일 중복 체크');
                    // console.log(response);
                    // console.log(response.data.data.result);

                    if(response.data.data.result == 'true'){
                        resolve(response);
                    }else{
                        alert('중복된 이메일 입니다.');
                        reject('중복된 이메일 입니다.');
                    }
                })
                .catch((e) => {
                    console.log(e);
                    reject(e);
                });
        }

        );
    }

    const isssafy = function (ssafy) {
        // console.log('isssafy 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(ssafyurl, ssafy)
                .then((response) => {
                    // console.log('싸피 인증 통과 메세지');
                    // console.log(response);
                    // console.log(response.response.data.message);
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e);
                    console.log(e.response.data.message);
                    // reject(e);
                    if(e.response.data.message == '아이디가 존재하지 않습니다.'){
                        reject(e.response.data.message);
                    }else{
                        resolve(e.response.data.message);
                    }
                });
        }

        );
    }

    // 아이디 중복 확인
    const isid = function (loginId) {
        // console.log('isid 되고있나')

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-id/${loginId}`)
                .then((response) => {
                    // console.log('아이디 중복 확인');
                    resolve(response);
                })
                .catch((e) => {
                    // console.log('아이디 중복 에러');
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    // 닉네임 중복 확인
    const isnickname = function (NicknameData) {
        // console.log('isnickname 되고있나')

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
        // console.log('isstudentnum 되고있나')

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
        // console.log('submitNewUser 되고있나');
        // console.log(newUser);

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
        // console.log('authphone 되고있나' + userPhone);

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-phoneNum/${userPhone}`)
                .then(response => {
                    // console.log(response);
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e);
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
        isssafyEmail,
    }
})




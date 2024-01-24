
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';



export const useSignupStore = defineStore('signup', () => {

    const ssafyurl = 'https://project.ssafy.com/ssafy/api/auth/signin';
    const URL = 'http://localhost:8080/user/signup';
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
    const isid = function (IdData) {
        console.log('isid 되고있나')

        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-id/${IdData}`)
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

    // 전화번호 인증(방법 미정)
    const authphone = function (phoneNum) {

    }

    return {
        isid, 
        isnickname, 
        isssafy, 
        isstudentnum,
        authphone,
    }
})




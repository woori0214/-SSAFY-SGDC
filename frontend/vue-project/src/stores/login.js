
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import { useLoginStore } from "@/stores/login";
import axios from 'axios';

export const useLoginStore = defineStore('login', () => {
    const URL = 'http://localhost:8080/user';
    const userStorage = useUserStorageStore();

    // 로그인 함수
    const isLogin = function (login_id) {
        console.log('isLogin 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/login`, login_id)
                .then((response) => {
                    // 스토리지 로그인 정보 등록
                    //        로그인 정보를 등록하기 전에 전에 남아있는 정보가 있나 확인 해야되나..?
                    userStorage.setUserInformation(response);
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    // 로그아웃 함수
    const isLogout = function (token) {
        console.log('isLogout 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/logout`, token)
                .then((response) => {
                    // 스토리지 로그인 정보 전부 삭제
                    userStorage.deleteAllStorage();
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    return {
        isLogin,
        isLogout,
    }
})
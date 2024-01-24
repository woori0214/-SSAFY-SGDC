
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';

export const useLoginStore = defineStore('login', ()=>{
    const URL = 'http://localhost:8080/user/login';
    
    const loginUser = ref();
    const isLogin = function (login) {
        console.log('되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(URL, login)
                .then((response) => {

                    resolve(response);
                    loginUser = response.data.user;
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }
    const getLoginUserInfo = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${USER}/user-info/${userId}`)
                .then((response) => {
                    resolve(response.data.user);
                })
                .catch((error) => {
                    console.error(error);
                    reject(error);
                });
        });
    };
    return {isLogin, loginUser,getLoginUserInfo}
})
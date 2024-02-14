import { ref } from 'vue';
import { defineStore } from 'pinia';
import { useUserStorageStore } from './userStorage';
import axios from 'axios';
import { serverURL, v1_URL } from './config';

const URL = serverURL + v1_URL + 'user/re-auth/';
const userAuthToken = ref(null);
// userStorage.getUserInformation().token


export let authorizationAPI = axios.create({
    headers: {
        'Authorization': `Bearer ${userAuthToken.value}`, // 인증 토큰을 헤더에 추가
        'Content-Type': 'application/json',
    }
});

export const updateAuthToken = (updateToken) => {
    userAuthToken.value = updateToken;
    authorizationAPI = axios.create({
        headers: {
            'Authorization': `Bearer ${userAuthToken.value}`, // 인증 토큰을 헤더에 추가
            'Content-Type': 'application/json',
        }
    });

    // console.log('인증 토큰 업데이트');
    // console.log(userAuthToken.value);
    // console.log('=================================');
    // console.log(authorizationAPI.defaults.headers['Authorization']);
    // console.log('=================================');
};



// 인터셉터 수정
authorizationAPI.interceptors.response.use(response => response, function (error) {
    if (error.response && error.response.data && error.response.data.status === 401) {
        console.error('API Response ERROR 401');
        reciveRefreshToken();
    }
    return Promise.reject(error);
});

const reciveRefreshToken = function () {
    // console.log('reciveRefreshToken 실행 중');

    return new Promise((resolve, reject) => {
        authorizationAPI
            .post(`${URL}`, {})
            .then((response) => {
                // console.log(response);
                useUserStorageStore().setStorage("token", response.data.data.accessToken); // response.data.data를 response.data로 수정, 구조에 따라 다를 수 있음
                updateAuthToken(response.data.data.accessToken);

                resolve(response);
            })
            .catch((e) => {
                console.log(e);
                if (e.response && e.response.data.data && e.response.data.data.status === 401) return;
                reject(e);
            });
    });
}
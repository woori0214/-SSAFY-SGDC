
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { serverURL, v1_URL } from '@/main.js';


export const useFollowStore = defineStore('follow', () => {
    const URL = serverURL + v1_URL + 'follow';
    
    // 쌀로잉 조회
    const ssallowing = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/following/${userId.value}`)
                .then((res) => {
                    console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 쌀로워 조회
    const ssallower = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/follower/${userId.value}`)
                .then((res) => {
                    console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 쌀로잉 추가
    const plusSsallowing = function (ssallowingData) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/following/${ssallowingData.user_id}`, ssallowingData)
                .then((res) => {
                    console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }
    // 쌀로잉 삭제
    const deleteSsallowing = function (ssallowing) {
        return new Promise((resolve, reject) => {
            axios
                .delete(`${URL}/${ssallowing.user_id}/${ssallowing.following_id}`)
                .then((res) => {
                    console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }
    return {ssallowing, ssallower, plusSsallowing, deleteSsallowing}
})




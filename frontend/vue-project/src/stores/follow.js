
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';



export const useFollowStore = defineStore('follow', () => {
    const URL = 'http://localhost:8080/follow';
    
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

        const ssallowing = {
                user_id: ssallowingData.user_id,
                following_id: ssallowingData.following_id
        }

        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/following/${ssallowing.user_id}`, ssallowing)
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
                .delete(`${URL}/${ssallowing.value.user_id}/${ssallowing.value.following_id}`)
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




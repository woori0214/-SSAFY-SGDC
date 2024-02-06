
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { authorizationAPI } from './authAPI';
import { serverURL, v1_URL } from './config';


export const useFollowStore = defineStore('follow', () => {
    const URL = serverURL + v1_URL + 'follow';
    
    // 쌀로우 수 조회
    const getSsallowCount = function (userId) {

        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/follow-count/${userId.value}`)
                .then((res) => {
                    // console.log(res);
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                })
        })
    }
    // 쌀로잉 조회
    const ssallowing = function (userId) {
        // console.log(userId)
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/following/${userId.value}`)
                .then((res) => {
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
            authorizationAPI
                .get(`${URL}/follower/${userId.value}`)
                .then((res) => {
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
            authorizationAPI
            .post(`${URL}/${ssallowingData.user_id}/${ssallowingData.following_id}`, ssallowingData)
            .then((res) => {
                // console.log(res);
                resolve(res);
            })
            .catch((err) => {
                console.log(err);
                reject(err);
            });
        });
    }
    // 쌀로잉 삭제
    const deleteSsallowing = function (unSsallowingData) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .delete(`${URL}/${unSsallowingData.user_id}/${unSsallowingData.following_id}`)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    // 사용자가 팔로잉했는지
    const checkSsallowing = function(checkusers) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/follow-check/${checkusers.user_id}/${checkusers.following_id}`)
                .then((res) => {
                    // console.log(res)
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                })
        })
    }
    return {
        getSsallowCount,
        ssallowing, 
        ssallower, 
        plusSsallowing, 
        deleteSsallowing,
        checkSsallowing
    }
})




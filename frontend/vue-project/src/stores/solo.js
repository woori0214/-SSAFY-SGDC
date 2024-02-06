
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { serverURL, v1_URL } from './config';


export const useSoloStore = defineStore('solo', () => {
    const URL = serverURL + v1_URL + 'solo';
    // 솔로 모드 내역(오늘) 데이터 저장
    // api 연결되면 지우자.
    const soloTodayData = ref([
        {
            solo_status: 1,
            solo_result: 1,
            category_id: 1,
        },
        {
            solo_status: 0,
            solo_result: 1,
            category_id: 2,
        },
        {
            solo_status: 1,
            solo_result: 1,
            category_id: 3,
        },
        {
            solo_status: 1,
            solo_result: 1,
            category_id: 4,
        },
        {
            solo_status: 0,
            solo_result: 1,
            category_id: 5,
        },
        {
            solo_status: 0,
            solo_result: 1,
            category_id: 6,
        },
    ])

    // 솔로 모드 내역 표시(오늘) 함수
    // 한번 테스트 해봐야함
    const soloToday = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/today-list/${userId}`)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    //솔로모드 도전
    const soloChallenge = function (challenge) {
        const category = { user_id: challenge.user_id, category_id: challenge.category_id }
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/${category.category_id}`, category)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err)
                })
        })
    }

    //솔로모드 인증
    const soloAuth = function (challengeData) {

        const solo_auth = { user_id: challengeData.user_id, category_id: challengeData.category_id, solo_img: challengeData.uploadedImage }

        return new Promise((resolve, reject) => {
            axios
                .patch(`${URL}/solo/${solo_auth.user_id}/${solo_auth.category_id}/challenge-auth`, solo_auth.solo_img)
                .then((res) => {
                    resolve(res);
                    console.log('업로드 완료')
                })
                .catch((err) => {
                    reject(err)
                })
        })
    }

    //솔로모드 내역(전체 리스트)
    const soloList = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/challenge-list/${userId}`)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }
    return { soloTodayData, soloToday, soloChallenge, soloAuth, soloList }
})




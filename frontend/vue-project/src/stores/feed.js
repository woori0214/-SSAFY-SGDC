import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
// import { serverURL, v1_URL } from '@/main.js';
import { serverURL, v1_URL } from './config';
import { authorizationAPI } from './authAPI';
import { useUserStorageStore } from './userStorage';


export const useFeedStore = defineStore('feed', () => {
    const URL = serverURL + v1_URL + 'feed';
    const URL2 = serverURL + v1_URL;

    const userStorage = useUserStorageStore();

    //게시물 한 개
    const getFeed = function (feedId, userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/feed-info/${feedId}/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //게시물 조회수 업데이트
    const updateFeedView = function (feedId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .patch(`${URL}/feed-views/${feedId}`, {})
                .then((res) => {
                    resolve(res)
                    // console.log('조회수 +1')
                })
                .catch((err) => {
                    reject(err)
                })
        })
    };

    //게시물 리스트 조회
    const getFeedList = function () {
        // console.log('Start Get Feed List ::');
        // console.log(authorizationAPI.defaults.headers['Authorization']);
        // console.log('----------------------------------------------');
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/feed-list`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //게시물 리스트(페이지 당 10개씩)
    const getFeedListPage = function (last_feed_id, list_size) {
        // console.log('getFeedListPage 실행...');
        // console.log('인증 토큰 확인 ::');
        // console.log(authorizationAPI.defaults.headers['Authorization']);
        // console.log('----------------------------------------------');
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/feed-list/pages`, {
                    params: { userId: (userStorage.getUserInformation().user_id != null ? userStorage.getUserInformation().user_id : 0), feedId: last_feed_id, page: 0, size: list_size }
                })
                .then((response) => {
                    // console.log('get List : ');
                    // console.log(response);
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //피드 좋아요 업데이트 <<< 안씀
    const updateFeedLike = function (feedId) {


        return new Promise((resolve, reject) => {
            authorizationAPI
                .patch(`${URL}/feed-like/${feedId}`, {})
                .then((res) => {
                    resolve(res)
                    // console.log('좋아요 +1')
                })
                .catch((err) => {
                    reject(err)
                })
        })
    };

    //피드좋아요한 유저 추가
    const addfeedLikeUser = function (feedId, userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .post(`${URL}/feed-like/${feedId}/${userId}`, {})
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //피드 좋아요했다가 취소한 유저 삭제
    const deleteFeedLikeUser = function (feedId, userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .post(`${URL}/feed-like/${feedId}/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };


    //신고하기
    const reportUser = function (feedId, userId, reportUserId, reportDetail) {

        return new Promise((resolve, reject) => {
            authorizationAPI
                .post(`${URL2}feed-report/report?userId=${userId}&feedId=${feedId}&reportUserId=${reportUserId}&reportDetail=${reportDetail}`, {
                    // params: {
                    //     userId: userId,
                    //     feedId: feedId,
                    //     reportUserId: reportUserId,
                    //     reportDetail: reportDetail
                    // }
                })
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };


    return {
        getFeed,
        updateFeedLike,
        deleteFeedLikeUser,
        addfeedLikeUser,
        getFeedListPage,
        updateFeedView,
        getFeedList,
        reportUser,
    }
})
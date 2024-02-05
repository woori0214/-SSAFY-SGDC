import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { serverURL, v1_URL } from '@/main.js';


export const useFeedStore = defineStore('feed', () => {
    const URL = serverURL + v1_URL + 'feed';

    //게시물 한 개
    const getFeed = function(feedId){
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/feed-info/${feedId}`)
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
            axios
                .patch(`${URL}/feed-views/${feedId}`,{})
                .then((res) => {
                    resolve(res)
                    console.log('조회수 +1')
                })
                .catch((err) => {
                    reject(err)
                })
        })
    };

    //게시물 리스트 조회
    const getFeedList = function(){
        return new Promise((resolve, reject) => {
            axios
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
    const getFeedListPage = function(page){
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/feed-list/pages`, page)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //피드 좋아요 업데이트
    const updateFeedLike = function (feedId) {


        return new Promise((resolve, reject) => {
            axios
                .patch(`${URL}/feed-like/${feedId}`, {})
                .then((res) => {
                    resolve(res)
                    console.log('좋아요 +1')
                })
                .catch((err) => {
                    reject(err)
                })
        })
    };

    //피드좋아요한 유저 추가
    const addfeedLikeUser = function (feedId, userId) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/feed-like/${feedId}/${userId}`,{} )
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
            axios
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


    return {
        getFeed,
        updateFeedLike,
        deleteFeedLikeUser,
        addfeedLikeUser,
        getFeedListPage,
        updateFeedView,
        getFeedList,

        






    }
  })
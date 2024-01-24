import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useFeedStore = defineStore('feed', () => {
    const URL = 'http://localhost:8080/feed';

    // 피드 상세페이지 표시
    const getFeedDetail = function (feed_id) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/feed-info/${feed_id}`)
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

    // 피드 게시물 리스트 표시(최신 n개) << n = 10
    const getFeedList = function () {
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
        }

        );
    }

    // //다음 피드 리스트 표시
    // const getNextFeedList = function() {
    //     return new Promise((resolve, reject) => {
    //         axios
    //             .get(`${URL}/feed-list/`)
    //             .then((response) => {

    //                 resolve(response);
    //             })
    //             .catch((e) => {
    //                 console.log(e)
    //                 reject(e);

    //             });
    //     }

    //     );
    // }

    // 피드 좋아요 추가

    const patchFeedLike = function (feed_id) {
        return new Promise((resolve, reject) => {
            axios
                .patch(`${URL}/feed/feed-like/${feed_id}`, {})
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

    // 피드 좋아요 유저 추가
    const patchFeedLikeUser = function (feed_id, user_id) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/feed/feed-like/${feed_id}/${user_id}`, {})
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

    // 피드 좋아요 유저 삭제
    const patchFeedLikeUserDelete = function (feed_id, user_id) {
        return new Promise((resolve, reject) => {
            axios
                .delete(`${URL}/feed/feed-like/${feed_id}/${user_id}`)
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

    // 피드 조회수 추가
    const patchFeedViews = function (feed_id) {
        return new Promise((resolve, reject) => {
            axios
                .patch(`${URL}/feed/feed-views/${feed_id}`, {})
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

    return {
        getFeedDetail,
        getFeedList,
        patchFeedLike,
        patchFeedLikeUser,
        patchFeedLikeUserDelete,
        patchFeedViews,
    }
})
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { authorizationAPI } from './authAPI';
import { serverURL, v1_URL } from './config';


export const useCompetionStore = defineStore('competition', () => {
    const URL = serverURL + v1_URL + 'compet';
    const URL2 = serverURL + v1_URL;


    //랜덤도전장보내기
    const randomSend = function (randomSend) {
        return new Promise((resolve, reject) => {
            console.log(randomSend);
            authorizationAPI
                .post(`${URL}/random-send`, randomSend)
                .then((response) => {
                    resolve(response);
                    console.log('여긴왔나');
                })
                .catch((e) => {
                    console.log(e)

                    reject(e);
                });
        })
    };


    // //랜덤도전장수락하기
    // const randomAccept = function (randomAccept) {
    //     return new Promise((resolve, reject) => {
    //         axios
    //             .post(`${URL}/random-accept`, randomAccept)
    //             .then((response) => {
    //                 resolve(response);
    //             })
    //             .catch((e) => {
    //                 console.log(e)
    //                 reject(e);
    //             });
    //     })
    // };
    //친구에게 도전장 보내기
    const friendSend = function (friendSend) {
        console.log(friendSend)
        return new Promise((resolve, reject) => {
            authorizationAPI
                .post(`${URL}/friend-send`, friendSend)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    //친구도전장 수락하기
    // const friendAccept = function (friendAccept) {
    //     return new Promise((resolve, reject) => {
    //         axios
    //             .post(`${URL}/friend-send`, friendAccept)
    //             .then((response) => {
    //                 resolve(response);
    //             })
    //             .catch((e) => {
    //                 console.log(e)
    //                 reject(e);
    //             });
    //     })
    // };
    //친구,랜덤도전장 수락하기
    const bothAccept = function (matchingId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .post(`${URL}/accept/${matchingId}`, {})
                .then((response) => {
                    resolve(response);
                    console.log('수락이 완료됐습니다.')
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                    console.log('수락이 실패됐습니다.')
                });
        })
    };
    //도전장함
    const competitionMailbox = function (userId) {
        return new Promise((resolve, reject) => {
            console.log('axios');
            console.log(userId);
            authorizationAPI
                .get(`${URL}/receive-list/${userId}`)
                .then((response) => {
                    resolve(response);
                    console.log('도전장을 잘 갖고왔습니다.');
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //경쟁인증

    const competitionImage = function (formData) {
        console.log('axios');
        console.log(`Request URL: ${URL2}image-auth/upload`);
        for (let [key, value] of formData.entries()) {
            console.log(`${key}: ${value}`);
        }
        return new Promise((resolve, reject) => {
            authorizationAPI.request({
                method: 'post',
                url: `${URL2}image-auth/upload`,
                data: formData,
                headers: { 'Content-Type': 'multipart/form-data' }
            })
                .then(res => {
                    resolve(res);
                    console.log('업로드 완료');
                })
                .catch(err => {
                    reject(err);
                    console.log('업로드 실패');
                    for (let [key, value] of formData.entries()) {
                        console.log(`${key}: ${value}`);
                    }
                });
        });
    }

    //종료 경쟁 목록 조회
    const competitionFinish = function (userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/finish-compet-list/${userId}`)
                .then((res) => {
                    resolve(res);
                    console.log('통신잘되나');
                })
                .catch((err) => {
                    console.log(err)
                    reject(err);
                });
        })
    };

    //종료 경쟁 정보 상세 조회(개별)
    const competitionFinishDetail = function (userId, cometId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/finish-compet-detail/${userId}/${cometId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //경쟁모드인증현황
    const competitionProgressDetail = function (userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/progress-compet-detail/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };


    //친구 리스트 조회
    const competitionFriendList = function (userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL2}/friends/list/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    //사용자경쟁모드 분석
    const competitionAnalysis = function (userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/analysis/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const competitionAnalysisCategory = function (userId, userCategory) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/analysis/${userId}/${userCategory}`)
                .then((response) => {
                    resolve(response);
                    console.log('카테고리별유저상태잘 받아옴');
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    return {
        randomSend,
        // randomAccept,
        friendSend,
        // friendAccept,
        bothAccept,
        competitionMailbox,
        competitionImage,
        competitionFinish,
        competitionFinishDetail,
        competitionProgressDetail,
        competitionFriendList,
        competitionAnalysis,
        competitionAnalysisCategory,
    };
})
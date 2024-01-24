import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { compileTemplate } from 'vue/compiler-sfc';

export const useCompetionStore = defineStore('competition', () => {
    const URL = 'http://localhost:8080/compet';
    const URL2 = 'http://localhost:8080';

    const randomSend = function (randomSend) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/random-send`, randomSend)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };

    const randomAccept = function (randomAccept) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/random-accept`, randomAccept)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const friendSend = function (friendSend) {
        return new Promise((resolve, reject) => {
            axios
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
    const friendAccept = function (friendSend) {
        return new Promise((resolve, reject) => {
            axios
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
    const competitionMailbox = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/list/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const competitionImage = function (image) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/image-auth`, image)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const competitionFinishDetail = function (userId, cometId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/finish-compet-datil/${userId}/${cometId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const competitionProgressDetail = function (userId, cometId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/progress-compet-detail/${userId}/${cometId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const competitionFriendList = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/friend/list/${userId}`)
                .then((response) => {
                    resolve(response);
                })
                .catch((e) => {
                    console.log(e)
                    reject(e);
                });
        })
    };
    const competitionAnalysis = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/${userId}`)
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
        randomSend,
        randomAccept,
        friendSend,
        friendAccept,
        competitionMailbox,
        competitionImage,
        competitionFinishDetail,
        competitionProgressDetail,
        competitionFriendList,
        competitionAnalysis,
    };
})
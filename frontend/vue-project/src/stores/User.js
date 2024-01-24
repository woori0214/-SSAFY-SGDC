import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';

export const useUserStore = defineStroe('user', () => {
    const URL = 'http://localhost:8080/user';
    const getLoginUserInfo = function (userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/user-info/${userId}`)
                .then((response) => {
                    resolve(response.data.user);
                })
                .catch((error) => {
                    console.error(error);
                    reject(error);
                });
        });
    };
    return(getLoginUserInfo)

})

import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';

export const useLoginStore = defineStore('login', () => {
    const URL = 'http://localhost:8080/user/login';
    const isLogin = function (login) {
        console.log('되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(URL, login)
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
    
    return { isLogin }
})
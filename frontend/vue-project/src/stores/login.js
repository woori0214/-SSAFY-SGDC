
import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import { useUserStorageStore } from "@/stores/userStorage";
// import { useLoginStore } from "@/stores/login";
import axios from 'axios';

export const useLoginStore = defineStore('login', () => {
    const URL = 'http://localhost:8080/user';
    const userStorage = useUserStorageStore();
    const userInfo = userStorage.getUserInformation();
    const loginUser = ref(null);
    const userNickname = ref(null);


    // 로그인 함수
    const isLogin = function (loginData) {
        console.log('isLogin 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/login`, loginData)
                .then((response) => {
                    // 스토리지 로그인 정보 등록
                    //        로그인 정보를 등록하기 전에 전에 남아있는 정보가 있나 확인 해야되나..?
                    // console.log('isLogin response :');

                    userStorage.setUserInformation({
                        user_id: response.data.user_id,
                        user_name: response.data.user_name,
                        user_nickname: response.data.user_nickname,
                        // token: response.data.token,
                    });
                    loginUser.value = userInfo.user_id;
                    userNickname.value = userInfo.user_nickname;

                    
                    resolve(response);
                    // router.push({ name: "Main" });


                    // console.log(userNickname.value);

                })
                .catch((e) => {
                    console.log(e)
                    reject(e);

                });
        }

        );
    }

    //로그아웃 함수
    const isLogout = function () {
        console.log('isLogout 되고있나');
        userStorage.deleteAllStorage();
        loginUser.value = null; 
        userNickname.value = null;
        // router.push({ name: "Main" });

        // return new Promise((resolve, reject) => {
        //     axios
        //         .post(`${URL}/logout`, token)
        //         .then((response) => {
        //             //스토리지 로그인 정보 전부 삭제
        //             userStorage.deleteAllStorage();
        //             resolve(response);
        //         })
        //         .catch((e) => {
        //             console.log(e)
        //             reject(e);

        //         });
        // }

        // );
    }

    // const isLogout = function () {
    //     console.log('isLogout 되고있나');

    //     return new Promise((resolve, reject) => {
    //         axios
    //             .post(`${URL}/logout`, {})
    //             .then((response) => {
    //                 // 스토리지 로그인 정보 전부 삭제
    //                 userStorage.deleteAllStorage();
    //                 resolve(response);
    //             })
    //             .catch((e) => {
    //                 console.log(e)
    //                 reject(e);

    //             });
    //     }

    //     );
    // }

    return {
        isLogin,
        isLogout,
        userInfo,
        loginUser,
        userNickname,


    }
})
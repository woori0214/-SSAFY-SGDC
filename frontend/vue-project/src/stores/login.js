
import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import { useUserStorageStore } from "@/stores/userStorage";
// import { useLoginStore } from "@/stores/login";
import axios from 'axios';
import { authorizationAPI, updateAuthToken } from './authAPI';
import { serverURL, v1_URL } from './config';

export const useLoginStore = defineStore('login', () => {
    const URL = serverURL + v1_URL + 'user';

    const userStorage = useUserStorageStore();
    const userInfo = userStorage.getUserInformation();
    const loginUser = ref(null);
    const userNickname = ref(null);


    // 로그인 함수
    const isLogin = function (loginData) {
        console.log('isLogin 되고있나');

        return new Promise((resolve, reject) => {
            axios
                .post(`${URL}/login/`, loginData)
                .then((response) => {
                    // 스토리지 로그인 정보 등록
                    //        로그인 정보를 등록하기 전에 전에 남아있는 정보가 있나 확인 해야되나..?
                    // console.log('isLogin response :');
                    console.log('Login test');
                    console.log(response);
                    console.log('Login response');
                    userStorage.setUserInformation({
                        user_id: response.data.data.user_id,
                        user_name: response.data.data.user_name,
                        user_nickname: response.data.data.user_nickname,
                        token: response.data.data.accessToken,
                    });
                    loginUser.value = userInfo.user_id;
                    userNickname.value = userInfo.user_nickname;

                    updateAuthToken(response.data.data.accessToken);

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


    const isLogined = function () {
        console.log('isLogined 되고있나');

        if(userStorage.getUserInformation().user_id != null){
            loginUser.value = userStorage.getUserInformation().user_id;
            userNickname.value = userStorage.getUserInformation().user_nickname;
            updateAuthToken(userStorage.getUserInformation().token);
        }
        //저장된 토큰의 유효기간을 검사해서 로그인을 막기도 하기....? 그럼 페이지 넘어가다가 자동으로 로그아웃 되기도 하는 거 아닌가?
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
        isLogined,

    }
})
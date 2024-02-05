import { ref, computed, reactive } from 'vue'
import { useUserStorageStore } from "@/stores/userStorage";
import axios from 'axios';
import { serverURL, v1_URL } from '@/main.js';

const URL = serverURL + v1_URL + 'user/re-auth/';
const userStorage = useUserStorageStore();

export const authorizationAPI = axios.create({
    baseURL: 'https://your-api-endpoint.com',
    headers: {
        'Authorization': `Bearer YOUR_ACCESS_TOKEN`, // 인증 토큰을 헤더에 추가
        'Content-Type': 'application/json'
    }
});

api.interceptors.response.use(
    function (error) {
        // 요청 실패 시 실행될 코드 (HTTP 상태 코드가 200 범위를 벗어날 경우)
        if (error.response && error.response.data.data.status === 401) {
            // 404 오류에 대한 특정 처리를 여기에 작성
            console.error('API Response ERROR 401');
            // 오류를 처리하고, 필요하다면 사용자에게 알림 등의 추가 동작을 수행
            reciveRefreshToken();
        }
        // 오류를 다시 throw하여 이후 처리에서도 오류를 캐치할 수 있게 함
        return Promise.reject(error);
    }
);

const reciveRefreshToken = function () {
    console.log('reciveRefreshToken 되고있나');

    return new Promise((resolve, reject) => {
        authorizationAPI
            .post(`${URL}`, {})
            .then((response) => {
                console.log(response);
                userStorage.setStorage("token", response.data.data.accessToken);

                resolve(response);
            })
            .catch((e) => {
                console.log(e)
                if (error.response && error.response.data.data.status === 401) return;
                reject(e);
            });
    }

    );
}
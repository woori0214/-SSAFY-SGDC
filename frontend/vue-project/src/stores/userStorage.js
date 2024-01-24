import { defineStore } from 'pinia';

export const useUserStorageStore = defineStore('userStorage', () => {

  const setStorage = function (item, data) {
    localStorage.setItem(item, data);
  }

  const getStorage = function (item) {
    return localStorage.getItem(item);
  }

  const deleteAllStorage = function () {
    localStorage.clear();
  }

  const deleteItemStorage = function (item) {
    localStorage.removeItem(item);
  }

  const setUserInformation = function (data) {
    localStorage.setItem("user_id", data.user_id);
    localStorage.setItem("user_name", data.user_name);
    localStorage.setItem("token", data.token);
  }

  const getUserInformation = function () {
    const user_id = localStorage.getItem("user_id");
    const user_name = localStorage.getItem("user_name");
    const token = localStorage.getItem("token");

    return {
      user_id: user_id || null,
      user_name: user_name || null,
      token: token || null,
    }
  }

  const userAuthentication = function () {
    const user_id = localStorage.getItem("user_id");
    const token = localStorage.getItem("token");

    // 유저 로그인 토큰 인증 API 들어갈 자리
    // API에 user_id와 token를 같이 보내고 
    // 해당 user_id의 현재 로그인 token이 일치하는지 확인
    // 해당 정보가 인증되면 return true
    // 아니면 return false
  }

  return {
    setStorage,
    deleteAllStorage,
    deleteItemStorage,
    getUserInformation,
    setUserInformation,
    getStorage,
  }
})
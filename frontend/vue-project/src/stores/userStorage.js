import { defineStore } from 'pinia';

export const useUserStorageStore = defineStore('userStorage', () => {

  const setStorage = function (item, data) {
    localStorage.setItem(item, data);
  }

  const setUserInformation = function (data) {
    localStorage.setItem("user_id", data.user_id);
    localStorage.setItem("user_name", data.user_name);
    localStorage.setItem("token", data.token);
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

  return {
    setStorage,
    deleteAllStorage,
    deleteItemStorage,
    getUserInformation,
    setUserInformation,
    getStorage,
  }
})
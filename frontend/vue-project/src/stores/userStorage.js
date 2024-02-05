import { defineStore } from 'pinia';

export const useUserStorageStore = defineStore('userStorage', () => {

  // item항목에 data 저장 (ex: user_name << "김태범")
  const setStorage = function (item, data) {
    localStorage.setItem(item, data);
  }

  // item항목에 저장된 값 가져오기
  const getStorage = function (item) {
    return localStorage.getItem(item);
  }

  // 스토리지에 저장된 데이터 전부 삭제
  const deleteAllStorage = function () {
    localStorage.clear();
  }

  // 스토리지에 저장된 특정 item항목 데이터 삭제
  const deleteItemStorage = function (item) {
    localStorage.removeItem(item);
  }

  // 유저 로그인 정보 등록하기 
  // { user_id, user_name, token }
  const setUserInformation = function (data) {
    // console.log('data:');
    // console.log(data);
    // console.log('data.user_id:');
    // console.log(data.user_id);
    localStorage.setItem("user_id", data.user_id);
    localStorage.setItem("user_name", data.user_name);
    localStorage.setItem("user_nickname", data.user_nickname);
    localStorage.setItem("token", data.token);
  }

  // 유저 로그인 정보 가져오기
  // { user_id, user_name, token }
  const getUserInformation = function () {
    const user_id = localStorage.getItem("user_id");
    const user_name = localStorage.getItem("user_name");
    const user_nickname = localStorage.getItem("user_nickname");
    const token = localStorage.getItem("token");

    return {
      user_id: user_id || null,
      user_name: user_name || null,
      user_nickname: user_nickname || null,
      token: token || null,
    }
  }

  // 유저 로그인 정보를 사용하는
  // API 인증 함수
  //  해당 함수는 실제로 API 연결들을 완료하고 나서 추가로 선택하여 넣어야할 것 같습니다. 보안을 위한 것이기 때문에
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
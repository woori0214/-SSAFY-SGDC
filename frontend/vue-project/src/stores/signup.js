
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';



export const useSignupStore = defineStore('signup', () => {

    const ssafyurl = 'https://project.ssafy.com/ssafy/api/auth/signin';
    const URL = 'http://localhost:8080/user/signup';
    const isssafy = function(ssafy) {
        console.log('되고있나');
    
    
        return new Promise((resolve, reject) => {
            axios
                .post(ssafyurl, ssafy)
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
    const isid = function(IdData) {
        console.log('되고있나')
    
    
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-id/${IdData}`)
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
    const isnickname = function(NicknameData) {
        console.log('되고있나')
    
    
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-nick/${NicknameData}`)
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
    const isstudentnum = function(StudentnumData) {
        console.log('되고있나')
    
    
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/check-student-num/${StudentnumData}`)
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
    const submitNewUser = function(NewUser){
        console.log('회원가입완료')
        return new Promise((resolve, reject) =>{
            axios
                .post(`${URL}`)
                .then((response)=>{
                    resolve(response);
                })
                .catch((e)=>{
                    console.log(e)
                    reject(e);
                })
        })
    }
    return {isid, isnickname, isssafy, isstudentnum, submitNewUser}
})




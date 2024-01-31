import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { serverURL } from '@/main.js';


export const useBadgeStore = defineStore('badge', () => {
    const URL = serverURL + 'badge';

    const badgeList = ref([
        {
            badge_name: "기린이",
            badge_detail: "기상 챌린지",
            badge_img: "@/assets/badge.png"
          },
          {
            badge_name: "따뜻한 로봇",
            badge_detail: "알고리즘 챌린지",
            badge_img: "@/assets/badge.png"
          },
          {
            badge_name: "코끼리",
            badge_detail: "식단 챌린지",
            badge_img: "@/assets/badge.png"
          },
          {
            badge_name: "짐종국",
            badge_detail: "운동 챌린지",
            badge_img: "@/assets/badge.png"
          },
          {
            badge_name: "스터디윗미",
            badge_detail: "스터디 챌린지",
            badge_img: "@/assets/badge.png"
          },
          {
            badge_name: "참자",
            badge_detail: "절제 챌린지",
            badge_img: "@/assets/badge.png"
          },
    ])
    
    // 전체뱃지 리스트 조회
    const getBadgeList = function() {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/list`)
                .then((res) => {
                    badgeList.value = res.badge_List
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err)
                    reject(err);
                });
        })
    }

    // 유저뱃지 리스트 조회
    const getUserBadgeList = function(userId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URL}/list/${userId}`)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err)
                    reject(err);
                });
        })
    }

    return {
        badgeList,
        getBadgeList,
        getUserBadgeList
    }
  })
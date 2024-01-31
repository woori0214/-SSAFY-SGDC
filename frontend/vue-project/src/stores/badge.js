import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';

import wakebadge from '@/assets/badges/wake1.png';
import algobadge from '@/assets/badges/algo1.png';
import healthbadge from '@/assets/badges/health1.png';
import studybadge from '@/assets/badges/study1.png';
import dietbadge from '@/assets/badges/diet1.png';
import fightingbadge from '@/assets/badges/wake1.png';

export const useBadgeStore = defineStore('badge', () => {

    const URI = 'http://localhost8080/badge';

    const badgeList = ref([
        {
            badge_id: 1,
            badge_name: "기린이",
            badge_detail: "기상 챌린지",
            badge_img: wakebadge
          },
          {
            badge_id: 2,
            badge_name: "따뜻한 로봇",
            badge_detail: "알고리즘 챌린지",
            badge_img: algobadge
          },
          {
            badge_id: 3,
            badge_name: "짐종국",
            badge_detail: "운동 챌린지",
            badge_img: healthbadge
          },
          {
            badge_id: 4,
            badge_name: "스터디윗미",
            badge_detail: "스터디 챌린지",
            badge_img: studybadge
          },
          {
            badge_id: 5,
            badge_name: "코끼리",
            badge_detail: "식단 챌린지",
            badge_img: dietbadge
          },
          {
            badge_id: 6,
            badge_name: "참자",
            badge_detail: "절제 챌린지",
            badge_img: fightingbadge
          },
    ])
    
    // 전체뱃지 리스트 조회
    const getBadgeList = function() {
        return new Promise((resolve, reject) => {
            axios
                .get(`${URI}/list`)
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
                .get(`${URI}/list/${userId}`)
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
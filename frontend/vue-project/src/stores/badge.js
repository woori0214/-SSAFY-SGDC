import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { authorizationAPI } from './authAPI';
import { serverURL, v1_URL } from './config';


import wakebadge from '@/assets/badges/wake1.png';
import algobadge from '@/assets/badges/algo1.png';
import healthbadge from '@/assets/badges/health1.png';
import studybadge from '@/assets/badges/study1.png';
import dietbadge from '@/assets/badges/diet1.png';
import fightingbadge from '@/assets/badges/wake1.png';

export const useBadgeStore = defineStore('badge', () => {
    const URL = serverURL + v1_URL + 'badge';

    const badgeList = ref([
        {
            badgeId: 1,
            badgeName: "기린이",
            badgeDetail: "기상 챌린지",
            badgeImg: wakebadge
          },
          {
            badgeId: 2,
            badgeName: "따뜻한 로봇",
            badgeDetail: "알고리즘 챌린지",
            badgeImg: algobadge
          },
          {
            badgeId: 3,
            badgeName: "짐종국",
            badgeDetail: "운동 챌린지",
            badgeImg: healthbadge
          },
          {
            badgeId: 4,
            badgeName: "스터디윗미",
            badgeDetail: "스터디 챌린지",
            badgeImg: studybadge
          },
          {
            badgeId: 5,
            badgeName: "코끼리",
            badgeDetail: "식단 챌린지",
            badgeImg: dietbadge
          },
          {
            badgeId: 6,
            badgeName: "참자",
            badgeDetail: "절제 챌린지",
            badgeImg: fightingbadge
          },
    ])
    

    // 유저뱃지 리스트 조회
    const getUserBadgeList = function(userId) {
      console.log(userId)
        return new Promise((resolve, reject) => {
          authorizationAPI
                .get(`${URL}/${userId}`)
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
        getUserBadgeList
    }
  })
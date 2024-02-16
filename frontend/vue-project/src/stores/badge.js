import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { authorizationAPI } from './authAPI';
import { serverURL, v1_URL } from './config';


import girinibadge from '@/assets/badges/girini.png';
import amtarkbadge from '@/assets/badges/amtark.png';
import diechickenbadge from '@/assets/badges/diechicken.png';
import momstouchbadge from '@/assets/badges/momstouch.png';
import gaddarobadge from '@/assets/badges/gaddaro.png';
import kkangtongbadge from '@/assets/badges/kkangtong.png';
import triceratopsbadge from '@/assets/badges/triceratops.png';
import elephantbadge from '@/assets/badges/elephant.png';
import Cervus_nipponbadge from '@/assets/badges/Cervus nippon.png';
import fairybadge from '@/assets/badges/fairy.png';
import ricetheifbadge from '@/assets/badges/ricetheif.png';
import underarmorbadge from '@/assets/badges/underarmor.png';
import kimbadge from '@/assets/badges/kim.png';
import breathbadge from '@/assets/badges/breath.png';
import failbadge from '@/assets/badges/fail.png';
import studybadge from '@/assets/badges/1t.png';
import pinokiobadge from '@/assets/badges/pinokio.png';
import ssafypolicebadge from '@/assets/badges/police.png'
import fighting1badge from '@/assets/badges/fighting1.png';


export const useBadgeStore = defineStore('badge', () => {
    const URL = serverURL + v1_URL + 'badge';

    const badgeList = ref([
        {
            badgeId: 1,
            badgeName: "기린이",
            badgeDetail: "기상 경쟁 승리 횟수 5회 이상 - 열심히 일어나셨네요",
            badgeImg: girinibadge
          },
        {
            badgeId: 2,
            badgeName: "싸피를 나온 암탉",
            badgeDetail: "기상 경쟁 승리 횟수 10회 이상 - 이젠 싸피 학생들을 깨워주는 닭...",
            badgeImg: amtarkbadge
          },
        {
            badgeId: 3,
            badgeName: "굶어 죽은 새",
            badgeDetail: "기상 경쟁 패배 횟수 5회 이상 - 일찍 일어난 새가 벌레를 잡아요",
            badgeImg: diechickenbadge
          },
        {
            badgeId: 4,
            badgeName: "맘스터치",
            badgeDetail: "기상 경쟁 패배 횟수 10회 이상 - 으이구 일어나!! 해가 중천이야!!",
            badgeImg: momstouchbadge
          },
        {
            badgeId: 5,
            badgeName: "가장 따뜻한 로봇",
            badgeDetail: "알고리즘 경쟁 승리 횟수 5회 이상 - 혹시 당신은 알고리즘 푸는 로봇?",
            badgeImg: gaddarobadge
          },
        {
            badgeId: 6,
            badgeName: "깡통 로봇",
            badgeDetail: "알고리즘 경쟁 패배 횟수 5회 이상 - 이 쓸모없는 깡통 녀석",
            badgeImg: kkangtongbadge
          },
        {
            badgeId: 7,
            badgeName: "트리케라톱스",
            badgeDetail: "식단 경쟁 승리 3회 이상 - 트리케라톱스는 초식 공룡입니다",
            badgeImg: triceratopsbadge
          },
        {
            badgeId: 8,
            badgeName: "코끼리",
            badgeDetail: "식단 경쟁 승리 5회 이상 - 코끼리는 초식 동물입니다",
            badgeImg: elephantbadge
          },
        {
            badgeId: 9,
            badgeName: "꽃사슴",
            badgeDetail: "식단 경쟁 승리 7회 이상 - 꽃사슴은 초식 동물입니다.",
            badgeImg: Cervus_nipponbadge
          },
        {
            badgeId: 10,
            badgeName: "요정",
            badgeDetail: "식단 경쟁 승리 10회 이상 - 이슬만 먹고 사시는군요",
            badgeImg: fairybadge
          },
        {
            badgeId: 11,
            badgeName: "밥도둑",
            badgeDetail: "식단 경쟁 패배 5회 이상 - 내 밥 가져와!",
            badgeImg: ricetheifbadge
          },
        {
            badgeId: 12,
            badgeName: "언더아머 단속반",
            badgeDetail: "운동 경쟁 승리 5회 이상 - 500이하 언더아머 금지",
            badgeImg: underarmorbadge
          },
        {
            badgeId: 13,
            badgeName: "김종꾹",
            badgeDetail: "운동 경쟁 승리 10회 이상 - 본업은 가수에요",
            badgeImg: kimbadge
          },
        {
            badgeId: 14,
            badgeName: "숨쉬기 운동 금지",
            badgeDetail: "운동 경쟁 패배 5회 이상 - 숨은 잘 쉬어요",
            badgeImg: breathbadge
          },
        {
            badgeId: 15,
            badgeName: "운동 전문 실패자",
            badgeDetail: "운동 경쟁 패배 10회 이상 - 하면 잘 하는 사람이...",
            badgeImg: failbadge
          },
        {
            badgeId: 16,
            badgeName: "엉덩이 1톤",
            badgeDetail: "스터디 경쟁 승리 5회 이상 - 엉덩이가 진짜 무거우시네요.",
            badgeImg: studybadge
          },
        {
            badgeId: 17,
            badgeName: "피노키오",
            badgeDetail: "거짓 인증 - 우리한테 왜 그랬어요...",
            badgeImg: pinokiobadge
          },
        {
            badgeId: 18,
            badgeName: "싸피 보안관",
            badgeDetail: "거짓 인증 신고하기 5회 - 거짓말쟁이 피노키오 발견! 탕!",
            badgeImg: ssafypolicebadge
          },
          
    ])
    

    // 유저뱃지 리스트 조회
    const getUserBadgeList = function(userId) {
      // console.log(userId)
        return new Promise((resolve, reject) => {
          authorizationAPI
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
        getUserBadgeList
    }
  })
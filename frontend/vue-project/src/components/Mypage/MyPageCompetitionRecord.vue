<template>
    <div>
        <div class="accordion-header" @click="toggleAccordion">
            <h2>싸강두천 전적</h2>
            <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
        </div>
        <div class="com_accordion-content" :class="{ 'open': isOpen }">
            <!--승패에 따라 이미지 다르게 보여주기-->
            <div v-for="competdata in competList" :key="competdata.compet_id" class="result_div">
                <div class="result_img_div">
                    <img :src="competdata.compet_result === 1 ? winimg : loseimg" alt="" class="result_img"
                        @click="openPopup(competdata)">
                </div>
            </div>

            <!-- 경기 상세 정보-->
            <div v-if="showPopup" class="popup">
                <div class="popup-content">
                    <!-- Time -->
                    <div class="time">{{ formatTime(selectedMatchingData[0].compet_expiration_time) }}</div>

                    <!-- Sender and Receiver with Images -->
                    <div class="match-details">
                        <div class="sender">
                            <img :src="getAuthImage(popupdata.sender_id)" alt="Sender Image">
                            <span>{{ popupdata.sender_nickname }}</span>
                        </div>
                        <div class="category">
                            <span>{{ getCategoryName(popupdata.category_id) }}</span>
                        </div>
                        <div class="receiver">
                            <img :src="getAuthImage(popupdata.receiver_id)" alt="Receiver Image">
                            <span>{{ popupdata.receiver_nickname }}</span>
                        </div>
                    </div>

                    <!-- Result -->
                    <div class="result">{{ popupdata.compet_result === 1 ? '승리' : '패배' }}</div>

                    <!-- Close Button -->
                    <button @click="showPopup = false">닫기</button>
                </div>
            </div>

        </div>
    </div>
</template>
  
<script>
import { ref, computed } from 'vue';
import { useCompetionStore } from '@/stores/competition';

import winimg from '@/assets/pose_win_boy.png';
import loseimg from '@/assets/pose_lose_boy.png';
import authimg from '@/assets/camera.png'

export default {
    props: ['userId', 'categories'],

    setup(props) {
        const userId = ref(props.userId);
        const categories = ref(props.categories);
        const compet = useCompetionStore()
        const showPopup = ref(false);
        const popupdata = ref(null)
        const selectedMatchingData = ref([
            {
                "matcing_id": 1,
                "send_id": 1,
                "category_id": 1,
                "compet_kind": "친구",
                "is_sender": "Y",
                "compet_expiration_time": "2024-01-25T12:00:00"
            },
            {
                "matcing_id": 1,
                "send_id": 2,
                "category_id": 1,
                "compet_kind": "친구",
                "is_sender": "N",
                "compet_expiration_time": "2024-01-25T12:00:00"
            },
        ]);

        const selectedAuthData = ref([
            {
                "auth_id": 1,
                "auth_img": authimg,
                "create_at": "2024-01-20T15:00:00",
                "matcing_id": 1,
                "compet_id": 1
            },
            {
                "auth_id": 2,
                "auth_img": authimg,
                "create_at": "2024-01-20T15:00:00",
                "matcing_id": 1,
                "compet_id": 1
            },
        ]);

        const competList = ref([
            {
                compet_id: 1, compet_result_id: 1, compet_result: 1, matching_id: 1,
                sender_id: 1, sender_nickname: '우리다', receiver_id: 2, receiver_nickname: '화석', tooltip_info: '경기1'
            },
            {
                compet_id: 2, compet_result_id: 2, compet_result: 0, matching_id: 2,
                sender_id: 1, sender_nickname: '우리다', receiver_id: 3, receiver_nickname: '태범', tooltip_info: '경기2'
            },
            {
                compet_id: 3, compet_result_id: 3, compet_result: 1, matching_id: 3,
                sender_id: 1, sender_nickname: '우리다', receiver_id: 4, receiver_nickname: '지은', tooltip_info: '경기3'
            },
            {
                compet_id: 4, compet_result_id: 4, compet_result: 1, matching_id: 4,
                sender_id: 1, sender_nickname: '우리다', receiver_id: 5, receiver_nickname: '현춘', tooltip_info: '경기4'
            },
            {
                compet_id: 5, compet_result_id: 5, compet_result: 0, matching_id: 5,
                sender_id: 1, sender_nickname: '우리다', receiver_id: 6, receiver_nickname: '수안', tooltip_info: '경기5'
            },
            {
                compet_id: 6, compet_result_id: 6, compet_result: 1, matching_id: 6,
                sender_id: 1, sender_nickname: '우리다', receiver_id: 7, receiver_nickname: '우리', tooltip_info: '경기6'
            },

        ])

        // 팝업창
        const openPopup = (competdata) => {
            // compet.competitionFinishDetail(userId, competdata.compet_id)
            //     .then((res) => {
            //         console.log(res)
            //         selectedMatchingData.value = res.matchings;
            //         selectedAuthData.value = res.image_auths;
            //         showPopup.value = true;
            //     })
            //     .catch((err) => {
            //         console.log(err)
            //     })
            popupdata.value = competdata
            showPopup.value = true;
        };

        // 시간 포맷 변경
        const formatTime = (timeString) => {
            console.log(timeString)
            const date = new Date(timeString);
            // 유효한 날짜인지 확인
            if (!isNaN(date.getTime())) {
                // 날짜가 유효한 경우, 원하는 형식으로 변환하여 반환
                return date.toLocaleDateString();
            } else {
                // 날짜가 유효하지 않은 경우, 에러 메시지 반환
                return "Invalid time";
            }
        };

        // 인증 이미지 가져오기
        const getAuthImage = (authId) => {
            const auth = selectedAuthData.value.find(auth => auth.auth_id === authId);
            return auth ? auth.auth_img : '';
        };

        // 카테고리 이름 가져오기
        const getCategoryName = (categoryId) => {
            const category = categories.value.find(cat => cat.id === categoryId);
            return category ? categories.name : '';
        };

        // 페이지 열었을 때 정보 가져오기
        // onMounted(() => {
        //   compet.competitionFinish(userId)
        //     .then((res) => {
        //       console.log(res)
        //       competList.value = res.compet
        //     })
        //     .catch((err) => {
        //       console.log(err)
        //     });
        // });

        // 아코디언
        const isOpen = ref(true);

        const toggleAccordion = () => {
            isOpen.value = !isOpen.value;
        };

        return {
            userId,
            categories,
            popupdata,
            winimg, loseimg,
            compet,
            competList,
            isOpen,
            selectedMatchingData,
            selectedAuthData,
            showPopup,
            formatTime,
            getAuthImage,
            getCategoryName,
            toggleAccordion,
            openPopup
        }
    }

}
</script>
  
<style scoped>
/* 아코디언 헤더 스타일링 */.accordion-header {
    display: flex;
    align-items: center;
    background-color: #83b0e1;
    padding: 10px;
    cursor: pointer;
    border-radius: 15px;
  }

.rotate-icon {
    transition: transform 0.3s ease;
    margin-left: auto;
    /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
}

.rotate-icon::before {
    content: '\25BC';
    /* 기본 화살표 아래 방향 유니코드 */
    display: inline-block;
}

.rotate-icon.rotate::before {
    content: '\25B2';
    /* 화살표 위 방향 유니코드 */
}

/* 아코디언 내용 스타일링 */
.com_accordion-content {
    display: flex;
    padding: 10px;
    border-top: none;
    overflow: auto;
    max-height: 0;
    transition: max-height 0.3s ease;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.com_accordion-content.open {
    max-height: 1000px;
    /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
    transition: max-height 0.3s ease;
}

.result_div {
    display: flex;
    flex-direction: row;
}

.result_img_div {
    margin: 10px;
    text-align: center;
    position: relative;
}

.result_img {
    width: 100px;
}

.win_img {
    width: 100px;
}

.lose_img {
    width: 100px;
}

.popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.popup-content {
    background-color: aliceblue;
    width: 80%;
    /* 변경 */
    max-width: 80%;
    /* 추가 */
    max-height: 80%;
    /* 추가 */
    overflow: auto;
    /* 추가 */
    text-align: center;
}

.time,
.match-details,
.result {
    margin-bottom: 20px;
}

.match-details {
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.sender,
.receiver {
    display: flex;
    flex-direction: column;
}

.match-details img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    object-fit: cover;
}

.result {
    font-size: 1.5em;
    color: rgb(255, 0, 0);
    /* Or red based on result */
}</style>
  
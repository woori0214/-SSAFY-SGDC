<template>
    <div>
        <div class="accordion-header" @click="toggleAccordion">
            <h2>싸강두천 전적</h2>
            <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
        </div>
        <transition>
            <div class="com_accordion-content" :class="{ 'open': isOpen }" v-show="isOpen">
                <!--승패에 따라 이미지 다르게 보여주기-->
                <div v-for="competdata in competList" :key="competdata.compet_id" class="result_div">
                    <div class="result_img_div">
                        <img :src="competdata.compet_result === 'SEND_WIN' || competdata.compet_result === 'BOTH_WIN' ? winimg : loseimg"
                            alt="" class="result_img" @click="openPopup(competdata)">
                    </div>
                </div>

                <!-- 경기 상세 정보-->
                <div v-if="showPopup" class="popup">
                    <div class="popup-content">
                        <!-- Time -->
                        <div class="time">
                            {{ formatTime(selectedMatchingData.done_at) }}
                        </div>

                        <!-- Sender and Receiver with Images -->
                        <div class="match-details">
                            <div class="sender">
                                <img :src=selectedMatchingData.user_image_auth alt="Sender Image">
                                <h3>{{ userNickname }}</h3>
                            </div>
                            <div class="result_category">
                                <span>{{ selectedMatchingData.category_id }}</span>
                                <h2>vs</h2>
                            </div>
                            <div class="receiver">
                                <img :src=selectedMatchingData.other_image_auth alt="Receiver Image">
                                <h3>{{ selectedMatchingData.other_nickname }}</h3>
                            </div>
                        </div>

                        <!-- Result -->
                        <div class="result">{{ selectedMatchingData.compet_result }}</div>
                        <!-- Close Button -->
                        <button @click="showPopup = false" class="popup_close_btn">닫기</button>
                    </div>
                </div>

            </div>
        </transition>
    </div>
</template>
  
<script>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router'; 
import { useUserStore } from '@/stores/user';
import { useCompetionStore } from '@/stores/competition';

import winimg from '@/assets/pose_win_boy.png';
import loseimg from '@/assets/pose_lose_boy.png';
import authimg from '@/assets/camera.png'

export default {
    props: ['userId', 'categories'],

    setup(props) {

        const route = useUserStore();
        const userStore = useUserStore();
        const competStore = useCompetionStore();

        const userId = ref(props.userId);
        const categories = ref(props.categories);
        const userNickname = ref(null);

        const competList = ref([]);
        // 선택한 싸강두천 전적
        const selectedMatchingData = ref([]);

        const showPopup = ref(false);
        const popupdata = ref(null);

        // 팝업창
        const openPopup = (competdata) => {
            // console.log(competdata)
            competStore.competitionFinishDetail(userId.value, competdata.compet_id)
                .then((res) => {
                    selectedMatchingData.value = res.data.competition;
                    showPopup.value = true;
                })
                .catch((err) => {
                    console.log(err)
                })
        };

        // 시간 포맷 변경
        const formatTime = (timeString) => {
            // console.log(selectedMatchingData)
            // console.log(timeString)
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

        // 카테고리 이름 가져오기
        const getCategoryName = (categoryId) => {
            const category = categories.value.find(cat => cat.id === categoryId);
            return category ? category.name : '';
        };

        // 페이지 열었을 때 정보 가져오기
        onMounted(() => {
            competStore.competitionFinish(userId.value)
                .then((res) => {
                    // console.log('싸강두천 전적 가져옴')
                    competList.value = res.data.competitions
                })
                .catch((err) => {
                    // console.log('싸강두천 전적 못가져옴')
                    console.log(err)
                });

            // 마이페이지 사용자 닉네임 가져오기
            userStore.userData(userId.value)
                .then((res) => {
                    userNickname.value = res.data.data.user_nickname
                })
                .catch((err) => {
                    console.log(err)
                })

        });

        // 아코디언
        const isOpen = ref(true);

        const toggleAccordion = () => {
            isOpen.value = !isOpen.value;
        };

        return {
            userId, userNickname,
            categories,
            winimg, loseimg,
            userStore, competStore,
            competList,
            isOpen,
            selectedMatchingData,
            showPopup,
            formatTime,
            getCategoryName,
            toggleAccordion,
            openPopup
        }
    }

}
</script>
  
<style scoped> /* 아코디언 헤더 스타일링 */
 .accordion-header {
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
     font-family: sans-serif;
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
     overflow: hidden;
     max-height: 0;
     transition: max-height 0.3s ease;
 }

 .com_accordion-content::-webkit-scrollbar {
     height: 5px;
 }

 .com_accordion-content::-webkit-scrollbar-thumb {
     background-color: #71a5de;
     border-radius: 10px;
 }

 /* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
 .com_accordion-content.open {
     max-height: 1000px;
     transition: max-height 0.3s ease;
     overflow: scroll;
 }

 .result_div {
     display: flex;
     flex-direction: row;
     padding: 10px;
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
     background: rgba(0, 0, 0, 0.5);
 }

 .popup-content {
     display: flex;
     flex-direction: column;
     background-color: #e1ecf7;
     border: #aecbeb 5px solid;
     border-radius: 50px;
     width: 50%;
     height: calc(70% - 20px);
     max-width: 80%;
     max-height: 70%;
     overflow: auto;
     text-align: center;
     justify-content: center;
 }

 .time {
     margin-top: 10px;
     margin-bottom: 20px;
     font-size: 30px;
     font-weight: bold;
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
     width: 150px;
     height: 150px;
     border-radius: 30px;
     object-fit: cover;
 }

 .result_category {
     display: flex;
     flex-direction: column;
     text-align: center;
     justify-content: center;
 }

 .result {
     font-size: 2.0em;
     font-weight: bolder;
     color: rgb(255, 0, 0);
 }

 .popup_close_btn {
     width: 30%;
     height: 40px;
     margin: 0 auto;
     background-color: #71a5de;
     border: none;
     border-radius: 20px;
     cursor: pointer;
     color: white;
 }


 .time,
 .match-details,
 .result {
     margin-bottom: 20px;
 }


 .sender,
 .receiver {
     display: flex;
     flex-direction: column;
 }

 .match-details img {
     width: 150px;
     height: 150px;
     border-radius: 30px;
     object-fit: cover;
 }

 .result {
     font-size: 2.0em;
     font-weight: bolder;
     color: rgb(255, 0, 0);
 }

 .popup_close_btn {
     width: 30%;
     height: 40px;
     margin: 0 auto;
     background-color: #71a5de;
     border: none;
     border-radius: 20px;
     cursor: pointer;
     color: white;
 }

 @media screen and (max-width: 768px) {
     .popup-content {
         width: 60%;
         height: calc(70% - 10px);
     }

     .time {
         font-size: 25px;
     }

 }

 @media screen and (max-width: 570px) {
     .popup-content {
         width: 90%;
     }

     .match-details img {
         width: 100px;
         height: 100px;
         border-radius: 30px;
         object-fit: cover;
     }

     .result {
         font-size: 1.0em;
         font-weight: bolder;
         color: rgb(255, 0, 0);
     }
 }
</style>
  
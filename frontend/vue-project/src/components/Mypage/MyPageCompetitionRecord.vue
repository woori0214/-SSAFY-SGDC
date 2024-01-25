<template>
    <div>
        <div class="accordion-header" @click="toggleAccordion">
            <h2>싸강두천 전적</h2>
            <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
        </div>
        <div class="com_accordion-content" :class="{ 'open': isOpen }">
            <div v-for="competdata in competList" :key="competdata.compet_id" class="result_div">
                <div class="result_img_div">
                    <PopUpCompetTooltip :imageSrc="competdata.compet_result === 1 ? winimg : loseimg"
                        :competdata="competdata" :userId="userId" />
                    <p>{{ competdata.sender_nickname }} vs {{ competdata.receiver_nickname }}</p>
                </div>
                <!-- <div v-if="competdata.compet_result === 1" class="result_img_div">
                    <img src="@/assets/pose_win_boy.png" alt="" class="win_img">
                    <p>{{ competdata.user_nickname }} vs {{ competdata.matching_nickname }}</p>
                </div>
                <div v-else-if="competdata.compet_result === 0" class="result_img_div">
                    <img src="@/assets/pose_lose_boy.png" alt="" class="lose_img">
                    <p>{{ competdata.user_nickname }} vs {{ competdata.matching_nickname }}</p>
                </div> -->
            </div>
        </div>
    </div>
</template>
  
<script>
import { ref } from 'vue';
import { useCompetionStore } from '@/stores/competition';
import PopUpCompetTooltip from '@/components/PopUp/PopUpCompetTooltip.vue';


import winimg from '@/assets/pose_win_boy.png';
import loseimg from '@/assets/pose_lose_boy.png';

export default {
    props: ['userId',],
    components: {
        PopUpCompetTooltip,
    },
    setup(props) {
        const userId = ref(props.userId)
        const compet = useCompetionStore()

        const competList = ref([
            { compet_id: 1, compet_result_id: 1, compet_result: 1, matching_id: 1, sender_id: 1, sender_nickname: '우리다', receiver_id:2, receiver_nickname: '화석' },
            { compet_id: 2, compet_result_id: 2, compet_result: 0, matching_id: 2, sender_id: 1, sender_nickname: '우리다', receiver_id:3, receiver_nickname: '태범' },
            { compet_id: 3, compet_result_id: 3, compet_result: 1, matching_id: 3, sender_id: 1, sender_nickname: '우리다', receiver_id:4, receiver_nickname: '지은' },
            { compet_id: 4, compet_result_id: 4, compet_result: 1, matching_id: 4, sender_id: 1, sender_nickname: '우리다', receiver_id:5, receiver_nickname: '현춘' },
            { compet_id: 5, compet_result_id: 5, compet_result: 0, matching_id: 5, sender_id: 1, sender_nickname: '우리다', receiver_id:6, receiver_nickname: '수안' },
            { compet_id: 6, compet_result_id: 6, compet_result: 1, matching_id: 6, sender_id: 1, sender_nickname: '우리다', receiver_id:7, receiver_nickname: '우리' },
        ])

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

        return { userId, compet, winimg, loseimg, competList, isOpen, toggleAccordion }
    }

}
</script>
  
<style scoped>
/* 아코디언 헤더 스타일링 */
.accordion-header {
    display: flex;
    align-items: center;
    background-color: #f1f1f1;
    padding: 10px;
    border: 1px solid #ccc;
    cursor: pointer;
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
    border: 1px solid #ccc;
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
}

.win_img {
    width: 100px;
}

.lose_img {
    width: 100px;
}
</style>
  
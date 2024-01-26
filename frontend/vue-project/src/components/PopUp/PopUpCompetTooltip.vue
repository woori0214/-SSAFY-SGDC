<!-- PopUpCompetTooltip.vue -->
<template>
    <div class="tooltip" @mouseover="showCompetInfo(competId)" @mouseleave="hideTooltip">
        <img :src="imageSrc" :alt="competId" class="result-img" />
        <div class="tooltiptext" :class="{ visible: showTooltip }">
            <p>나와</p>
        </div>
    </div>
</template>
  
<script>
import { ref } from 'vue';
import { useCompetionStore } from '@/stores/competition';

export default {
    props: ['competdata', 'imageSrc', 'userId'],
    
    setup(props) {
        const compet = useCompetionStore();
        const userId = ref(props.userId)
        const competId = ref(props.competdata.compet_id)
        const showTooltip = ref(false);
        const matchingInfo = ref([
            {
                matcing_id: 1,
                send_id: 1,
                category_id: 1,
                compet_kind: "친구",
                is_sender: "Y",
                compet_expiration_time: "2024-01-25T12:00:00"
            },
            {
                matcing_id: 2,
                send_id: 2,
                category_id: 1,
                compet_kind: "친구",
                is_sender: "N",
                compet_expiration_time: "2024-01-25T12:00:00"
            },
        ]);

        const imageAuth = ref([
            {
                auth_id: 1,
                auth_img: "auth_image_url_1.jpg",
                create_at: "2024-01-20T15:00:00",
                matcing_id: 1,
                compet_id: 1
            },
            {
                auth_id: 2,
                auth_img: "auth_image_url_2.jpg",
                create_at: "2024-01-20T15:00:00",
                matcing_id: 2,
                compet_id: 1
            },
           
        ])

        const showCompetInfo = (competId) => {
            
            showTooltip.value = true;
            console.log('나와')
            // compet.competitionFinishDetail(userId.value, competId)
            //     .then((res) => {
            //         matchingInfo.value = res.matchings;
            //         imageAuth.value = res.image_auths;
            //     })
        };

        const hideTooltip = () => {
            // Tooltip 숨기기
            showTooltip.value = false;
        };

        return { 
            compet, 
            userId, 
            competId, 
            showTooltip, 
            matchingInfo, 
            imageAuth, 
            showCompetInfo, 
            hideTooltip };
    },
};
</script>
  
<style scoped>
.tooltip {
    position: relative;
    display: inline-block;
    cursor: pointer;
}

.tooltip .tooltiptext {
    visibility: hidden;
    width: 200px;
    background-color: #333;
    color: #000000;
    text-align: center;
    padding: 5px;
    border-radius: 5px;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -100px;
    opacity: 0;
    transition: opacity 0.3s;
}

.tooltip .tooltiptext.visible {
    opacity: 1;
}

.result-img {
    width: 100px;
}
</style>
  
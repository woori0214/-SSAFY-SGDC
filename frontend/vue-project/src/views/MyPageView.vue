<template>
    <div class="mypageview_body">
        <h1>프로필페이지</h1>
        <div class="profile_body"> 
            <MyPageProfileBar />
        </div>
        <div class="compet_body">
            <MyPageCompetitionRecord />
        </div>
        <div class="solo_body">
            <MyPageSoloRecord />
        </div>
        <div class="analysis_body">
            <MyPageAnalysis />
        </div>
        <div class="challenge_body">
            <MyPageChallengeBoard />
        </div>
        <div class="badge_body">
            <MyPageBadgeList />
        </div>
        <div class="ssallow_body">
            <MyPageSsallow />
        </div>
    </div>
</template>

<script setup>
import MyPageAnalysis from '@/components/Mypage/MyPageAnalysis.vue';
import MyPageBadgeList from '@/components/Mypage/MyPageBadgeList.vue';
import MyPageChallengeBoard from '@/components/Mypage/MyPageChallengeBoard.vue';
import MyPageCompetitionRecord from '@/components/Mypage/MyPageCompetitionRecord.vue';
import MyPageProfileBar from '@/components/Mypage/MyPageProfileBar.vue';
import MyPageSoloRecord from '@/components/Mypage/MyPageSoloRecord.vue';
import MyPageSsallow from '@/components/Mypage/MyPageSsallow.vue';


</script>

<style scoped>
.mypageview_body {
    border: 2px solid red;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.profile_body {
    border: 1px solid rgb(51, 0, 255);
    
}

.compet_body {
    border: 1px solid rgb(255, 0, 132);
    ;
}

.solo_body {
    border: 1px solid rgb(0, 238, 255);
    
}

.analysis_body {
    border: 1px solid rgb(244, 156, 189);
    
}

.challenge_body {
    border: 1px solid rgb(247, 0, 255);
    
}

.badge_body {
    border: 1px solid rgb(210, 247, 0);
    
}

.ssallow_body {
    border: 1px solid rgb(0, 70, 22);
    
}



</style>
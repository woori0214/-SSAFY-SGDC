<template>
    <div>
        <MyPageSsallow 
        :user-id="userId"
        :login-user="loginUser"
        />
    </div>
</template>

<script setup>
import MyPageSsallow from '@/components/Mypage/MyPageSsallow.vue';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStorageStore } from '@/stores/userStorage';

const route = useRoute();
const userstorageStore = useUserStorageStore();

const userId = ref(route.params.userId);
const loginUser = ref(userstorageStore.getUserInformation().user_id);

</script>

<style scoped>

</style>
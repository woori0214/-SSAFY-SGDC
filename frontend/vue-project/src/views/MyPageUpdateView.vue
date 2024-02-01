<template>
    <div class="profile-edit">
        <!-- 닉네임 수정 필드 -->
        <div class="profile-field">
            <label for="nickname">현재 닉네임:</label>
            <span>{{ nickname }}</span>
        </div>
        <div class="profile-field">
            <label for="editNickname">수정 닉네임:</label>
            <input id="editNickname" type="text" v-model="editNickname" @input="nicknameCheck = false">
            <button @click="checkNickname">중복 확인</button>
        </div>
        <p v-if="nicknameCheck && !nicknameValid" style="color: red;">중복된 닉네임이 있습니다.</p>
        <button v-if="nicknameValid" @click="updateNickname">저장하기</button>


        <div class="profile-field">
            <label for="badge">현재 대표뱃지:</label>
            <span>{{ selectedBadge }}</span>
        </div>
        <!-- 뱃지 ID 수정 필드 -->
        <div class="profile-field">
            <label for="badge">뱃지 선택:</label>
            <select v-model="selectedBadge">
                <option v-for="badge in badgeList" :key="badge.badge_id" :value="badge.badge_id">
                    {{ badge.badge_name }}
                </option>
            </select>
            <button @click="updateBadge">저장하기</button>
        </div>

        <!-- 전화번호 수정 필드 -->
        <div class="profile-field">
            <label for="phone">현재 전화번호:</label>
            <span>{{ phoneNumber }}</span>
        </div>
        <div class="profile-field">
            <label for="editPhone">전화번호:</label>
            <input id="editPhone" type="text" v-model="editPhoneNumber" />
            <button @click="updatePhoneNumber">저장하기</button>
        </div>

        <!-- 프로필 이미지 수정 필드 -->
        <div class="profile-field">
            <label for="profileImage">프로필 이미지:</label>
            <!-- 현재 프로필 이미지 표시 -->
            <img :src="profileImageUrl" alt="프로필 이미지" class="current-profile-image" />
            <input type="file" id="profileImage" @change="handleImageUpload" />
            <button @click="updateProfileImage">저장하기</button>
        </div>
    </div>
</template>
  

<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import { useUserStorageStore } from '@/stores/userStorage';
import { useBadgeStore } from '@/stores/badge';
import axios from 'axios';
import { useSignupStore } from '@/stores/signup';
const userStore = useUserStore();
const useUserStorage = useUserStorageStore();
const userBadgeStore = useBadgeStore();
const userSignupStore = useSignupStore();
const userId = ref('');
const badgeList = ref([]);
const updatedUser = ref(null);
const nickname = ref('');
const editNickname = ref('');
const nicknameCheck = ref(false); // 닉네임 중복 확인 여부
const nicknameValid = ref(false); // 닉네임 중복 확인 결과 (true: 사용 가능, false: 중복)
const selectedBadge = ref(null);

const phoneNumber = ref('');
const profileImageUrl = ref(''); // 서버로부터 받은 프로필 이미지 URL 저장
const profileImageFile = ref(null); // 사용자가 업로드한 새 이미지 파일 저장

const loadBadgeList = () => {
    userBadgeStore.getUserBadgeList(userId.value).then(res => {

        badgeList.value = res.data.badge_List.map(badge => ({
            badge_id: badge.badge_id,
            badge_name: badge.badge_name
        }));
    }).catch(error => {
        console.error('리스트 못 갖고왔음:', error);
    });
};
const checkNickname = () => {
    if (!editNickname.value) {
        alert('닉네임을 입력해주세요.');
        return;
    }
    useSignupStore().isnickname(editNickname.value).then(response => {
        if (response.data.status === 200) {
            nicknameValid.value = true;
            // alert('사용 가능한 닉네임입니다.');
        } else {
            nicknameValid.value = false;
        }
        nicknameCheck.value = true;
    }).catch(error => {
        console.error('닉네임 중복 확인 중 오류 발생:', error);
    });
};

onMounted(() => {
    const userInformation = useUserStorage.getUserInformation();
    userId.value = userInformation.user_id;

    userStore.userData(userId.value).then(res => {
        nickname.value = res.data.user.user_nickname;
        selectedBadge.value = res.data.badge_id;
        phoneNumber.value = res.data.user_phonenumber;
        profileImageUrl.value = res.data.user_img;
    }).catch(error => {
        console.error('Error fetching user data:', error);
    });
    loadBadgeList();
});
const handleImageUpload = (event) => {
    profileImageFile.value = event.target.files[0];
    // 선택된 이미지 미리보기를 위해 URL 생성
    profileImageUrl.value = URL.createObjectURL(event.target.files[0]);
};
const updateProfileImage = () => {
    const formData = new FormData();
    if (profileImageFile.value) {
        formData.append('profileImage', profileImageFile.value);
    }
    // 프로필 이미지 업데이트 로직 구현
    // 예: axios.post('/api/profile/update', formData, { headers: { 'Content-Type': 'multipart/form-data' } });
};
const updateNickname = () => {
    const validateNickname = /^[A-Za-z가-힣]{1,20}$/;
    if (!validateNickname.test(updatedUser.value.nickname) || !updatedUser.value.nickname) {
        alert('닉네임이 올바르지 않습니다.');
        updatedUser.value.nickname = '';
        return;
    }

    updateUser();
};

const updateBadge = () => {
    // 뱃지 ID 업데이트 로직 구현
};

const updatePhoneNumber = () => {
    // 전화번호 업데이트 로직 구현
};


</script>


<style></style>
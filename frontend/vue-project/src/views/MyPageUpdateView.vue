<template>
    <div class="profile-edit">
        <!-- 닉네임 수정 필드 -->

        <div class="profile-field">
            <label for="nickname">현재 닉네임:</label>
            <span>{{ nickname }}</span>
        </div>
        <div class="profile-field">
            <label for="editNickname">수정 닉네임:</label>
            <input id="editNickname" type="text" v-model="editNickname" @input="validateNickname">
            <button @click="checkNickname">중복 확인</button>
        </div>
        <p v-if="nicknameCheck && !nicknameValid" style="color: red;">중복된 닉네임이 있습니다.</p>
        <p v-if="!isNicknameFormatValid" style="color: red;">닉네임은 2~10자 한글이어야 합니다.</p>
        <button @click="updateNickname" :disabled="!nicknameValid || !isNicknameFormatValid">저장하기</button>

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
const editPhoneNumber = ref('');
const nicknameCheck = ref(false); // 닉네임 중복 확인 여부
const nicknameValid = ref(false); // 닉네임 중복 확인 결과 (true: 사용 가능, false: 중복)
const selectedBadge = ref(null);
const phoneNumber = ref('');
const profileImageUrl = ref(''); // 서버로부터 받은 프로필 이미지 URL 저장
const profileImageFile = ref(null);
const isNicknameFormatValid = ref(true);

const validateNickname = () => {
    isNicknameFormatValid.value = /^[가-힣]{2,10}$/.test(editNickname.value);
};

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
    userSignupStore().isnickname(editNickname.value).then(response => {
        if (response.data.status === 200) {
            nicknameValid.value = true;
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
        phoneNumber.value = res.data.user_phone;
        profileImageUrl.value = res.data.user_img;
    }).catch(error => {
        console.error('Error fetching user data:', error);
    });
    loadBadgeList();
});

const handleImageUpload = (event) => {
    profileImageFile.value = event.target.files[0];
    profileImageUrl.value = URL.createObjectURL(event.target.files[0]);
};

const updateProfileImage = () => {
    const formData = new FormData();
    if (profileImageFile.value) {
        formData.append('profileImage', profileImageFile.value);
        useUserStore.userUpdate(userId.value, updateData());
    }
    
};

const updateNickname = () => {
    if (isNicknameFormatValid.value && nicknameValid.value) {
        useUserStore.userUpdate(userId.value, updateData());
        // 업데이트 로직 구현
    }
};

const updateBadge = () => {
    if (selectedBadge.value !== null) {
        // 뱃지를 수정한 경우
        useUserStore.userUpdate(userId.value, updateData());
    } else {
        // 뱃지를 수정하지 않은 경우
        const selectedBadgeId = badgeList.find(badge => badge.badge_name === selectedBadge).badge_id;
        useUserStore.userUpdate(userId.value, {
            userId: userId.value,
            updateData: {
                badge_id: selectedBadgeId
            }
        });
    }
};

const updatePhoneNumber = () => {
    useUserStore.userUpdate(userId.value, updateData());
};

const updateData = function () {
    return {
        userId: userId.value,
        updateData: {
            user_nickname: editNickname.value || nickname.value,
            user_phone: editPhoneNumber.value || phoneNumber.value,
            user_img: profileImageUrl.value,  // 이미지 업로드 로직에 따라 수정
            badge_id: badge.badge_id || selectedBadge.value
        }
    };
};

</script>

<style scoped>
.profile-edit {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.profile-field {
    margin-bottom: 20px;
}

.profile-field label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

.profile-field input[type="text"],
.profile-field select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

.profile-field input[type="file"] {
    border: none;
    margin-top: 10px;
}

button {
    background-color: #e1ecf7;
    /* Green */
    color: black;
    padding: 10px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #83b0e1 ;
    color: white;
}

button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.current-profile-image {
    max-width: 100%;
    height: auto;
    margin-top: 10px;
    border-radius: 4px;
}

p {
    margin: 5px 0;
}
</style>

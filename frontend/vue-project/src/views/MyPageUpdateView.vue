<template>
    <div class="profile-edit">
        <!-- 닉네임 수정 필드 -->

        <div class="profile-field">
            <label for="nickname">현재 닉네임: {{ nickname }}</label>
        </div>
        <div class="profile-field">
            <label for="editNickname">수정 닉네임:</label>
            <input id="editNickname" type="text" v-model="editNickname" @input="validateNickname">
            <div class="nickname_btn">
                <button @click="checkNickname" class="updatebtn">중복 확인</button>
                <button @click="updateNickname" :disabled="!nicknameValid || !isNicknameFormatValid"
                    class="updatebtn">저장하기</button>
            </div>
        </div>
        <p v-if="nicknameCheck && !nicknameValid" style="color: red;">중복된 닉네임이 있습니다.</p>
        <p v-if="!isNicknameFormatValid" style="color: red;">닉네임은 2~10자 한글이어야 합니다.</p>

        <div class="profile-field">
            <label for="badge">현재 대표뱃지:</label>
            <img :src=userBadgeImg alt="현재 대표뱃지" class="nowbadge" v-if="userBadgeImg">
        </div>
        <!-- 뱃지 ID 수정 필드 -->
        <div class="profile-field">
            <label for="badge">뱃지 선택:</label>
            <div class="custom-dropdown">
                <div class="dropdown-selected" @click="toggleDropdown">
                    <div v-if="selectedBadge" class="dropdown-item">
                        <img :src="selectedBadgeImage" alt="Selected Badge" class="badge_icon">
                        <span>{{ selectedBadgeName }}</span>
                    </div>
                    <div v-if="!selectedBadge" class="select_badge_list">누르세요</div>
                </div>
                <div class="dropdown-content" v-if="showDropdown">
                    <div class="dropdown-item" v-for="badge in badgeList" :key="badge.badge_id" @click="selectBadge(badge)">
                        <img :src="badge.badge_img" alt="Badge Image" class="badge_icon">
                        <span>{{ badge.badge_name }}</span>
                    </div>
                </div>
            </div>
            <div class="btn_position">
                <button @click="updateBadge" class="updatebtn">저장하기</button>
            </div>
        </div>

        <!-- 전화번호 수정 필드 -->
        <div class="profile-field">
            <label for="phone">현재 전화번호: {{ phoneNumber }}</label>
        </div>
        <div class="profile-field">
            <label for="editPhone">전화번호:</label>
            <input id="editPhone" type="text" v-model="editPhoneNumber" />
            <div class="btn_position">
                <button @click="updatePhoneNumber" class="updatebtn">저장하기</button>
            </div>
        </div>

        <!-- 프로필 이미지 수정 필드 -->
        <div class="profile-field">
            <label for="profileImage">프로필 이미지:</label>
            <!-- 현재 프로필 이미지 표시 -->
            <img :src="profileImageUrl" alt="프로필 이미지" class="current-profile-image" />
            <input type="file" id="profileImage" @change="handleImageUpload" />
            <button @click="updateProfileImage" class="updatebtn">저장하기</button>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useUserStore } from '@/stores/user';
import { useUserStorageStore } from '@/stores/userStorage';
import { useBadgeStore } from '@/stores/badge';
import axios from 'axios';
import { useSignupStore } from '@/stores/signup';

import userbadge from '@/assets/badges/wake1.png'
import wakebadge from '@/assets/badges/wake1.png';
import algobadge from '@/assets/badges/algo1.png';
import healthbadge from '@/assets/badges/health1.png';
import studybadge from '@/assets/badges/study1.png';
import dietbadge from '@/assets/badges/diet1.png';
import fightingbadge from '@/assets/badges/fighting1.png';

const userStore = useUserStore();
const useUserStorage = useUserStorageStore();
const userBadgeStore = useBadgeStore();
const userSignupStore = useSignupStore();

// 현재 사용자 정보
const userId = ref('');
const nickname = ref('');
// const badgeList = ref([]);
const badgeId = ref(null);
const userBadgeImg = ref(''); // 주석풀기
// const userBadgeImg = userbadge;
const phoneNumber = ref('');
const profileImageUrl = ref(''); // 서버로부터 받은 프로필 이미지 URL 저장

// 업데이트 된 사용자 정보
const editNickname = ref('');
const nicknameCheck = ref(false); // 닉네임 중복 확인 여부
const nicknameValid = ref(false); // 닉네임 중복 확인 결과 (true: 사용 가능, false: 중복)
const selectedBadge = ref(null);
const editPhoneNumber = ref('');
const profileImageFile = ref(null);
const isNicknameFormatValid = ref(true);
const updatedUser = ref(null);


// 뱃지리스트(더미)
const badgeList = ref([
    {
        badge_id: 1,
        badge_name: "기린이",
        badge_detail: "기상 챌린지",
        badge_img: wakebadge
    },
    {
        badge_id: 2,
        badge_name: "따뜻한 로봇",
        badge_detail: "알고리즘 챌린지",
        badge_img: algobadge
    },
    {
        badge_id: 3,
        badge_name: "짐종국",
        badge_detail: "운동 챌린지",
        badge_img: healthbadge
    },
    {
        badge_id: 4,
        badge_name: "스터디윗미",
        badge_detail: "스터디 챌린지",
        badge_img: studybadge
    },
    {
        badge_id: 5,
        badge_name: "코끼리",
        badge_detail: "식단 챌린지",
        badge_img: dietbadge
    },
    {
        badge_id: 6,
        badge_name: "참자",
        badge_detail: "절제 챌린지",
        badge_img: fightingbadge
    },
])

// 닉네임
const validateNickname = () => {
    isNicknameFormatValid.value = /^[가-힣]{2,10}$/.test(editNickname.value);
};

const checkNickname = () => {
    if (!editNickname.value) {
        alert('닉네임을 입력해주세요.');
        return;
    }
    console.log(editNickname)
    userSignupStore.isnickname(editNickname.value)
        .then(response => {
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

const updateNickname = () => {
    if (isNicknameFormatValid.value && nicknameValid.value) {
        const update_data = updateData()
        userStore.userUpdate(userId.value, update_data);
        // 업데이트 로직 구현
    }
};

// 뱃지
// 뱃지 드랍박스
const showDropdown = ref(false);
const toggleDropdown = () => {
    showDropdown.value = !showDropdown.value;
};

const selectBadge = (badge) => {
    selectedBadge.value = badge;
    console.log(selectedBadge.value)
    showDropdown.value = false;
};

// 선택된 뱃지의 이름과 이미지 URL을 계산된 속성으로 반환
const selectedBadgeName = computed(() => selectedBadge.value ? selectedBadge.value.badge_name : '뱃지 선택');
const selectedBadgeImage = computed(() => selectedBadge.value ? selectedBadge.value.badge_img : 'default-badge.png');

// 리스트 받아오기
const loadBadgeList = () => {
    userBadgeStore.getUserBadgeList(userId.value).then(res => {
        badgeList.value = res.data.badge_List.map(badge => ({
            badge_id: badge.badge_id,
            badge_name: badge.badge_name,
            badge_detail: badge.badge_detail,
            badge_img: badge.badge_img,
        }));
    }).catch(error => {
        console.error('리스트 못 갖고왔음:', error);
    });
};

const updateBadge = () => {
    if (selectedBadge.value !== null) {
        // 뱃지를 수정한 경우
        const update_data = updateData()
        userStore.userUpdate(userId.value, update_data);
    }
};


const handleImageUpload = (event) => {
    profileImageFile.value = event.target.files[0];
    profileImageUrl.value = URL.createObjectURL(event.target.files[0]);
};

const updateProfileImage = () => {
    const formData = new FormData();
    if (profileImageFile.value) {
        formData.append('profileImage', profileImageFile.value);
        userStore.userUpdate(userId.value, updateData());
    }

};


const updatePhoneNumber = () => {
    const update_data = updateData()
    userStore.userUpdate(userId.value, update_data);
};

const updateData = function () {
    return {
        user_nickname: editNickname.value || nickname.value,
        user_phone: editPhoneNumber.value || phoneNumber.value,
        user_img: profileImageUrl.value,  // 이미지 업로드 로직에 따라 수정
        badge_id: selectedBadge.value.badge_id || badgeId.value
    }
};

onMounted(() => {
    // 사용자 정보 가져오기
    const userInformation = useUserStorage.getUserInformation();
    userId.value = userInformation.user_id;
    userStore.userData(userId.value)
        .then(res => {
            nickname.value = res.data.data.user_nickname;
            badgeId.value = res.data.data.badge_id;
            phoneNumber.value = res.data.data.user_phone;
            profileImageUrl.value = res.data.data.user_img;
        }).catch(error => {
            console.error('Error fetching user data:', error);
        });
    loadBadgeList();
    const currentBadge = badgeList.value.find(badge => badge.badge_id === badgeId.value);
    if (currentBadge) {
        userBadgeImg.value = currentBadge.badge_img; // ref를 사용한 경우 .value로 접근
    } else {
        // userBadgeImg.value = 'badge_lock.png'; // 일치하는 뱃지가 없는 경우 기본 이미지 설정
    }
});
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

.nickname_btn {
    display: flex;
    justify-content: space-between;
}

.btn_position {
    display: flex;
    justify-content: right;
}
.updatebtn {
    background-color: #e1ecf7;
    /* Green */
    color: black;
    padding: 10px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.updatebtn:hover {
    background-color: #83b0e1;
    color: white;
}

.updatebtn:disabled {
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

.nowbadge {
    width: 100px;
    height: 100px;
}

.badge_icon {
    width: 70px;
    height: 70px;
}

.select_badge_list {
    background-color: white;
    height: 30px;
    border: rgb(150, 150, 150) 2px solid;
    border-radius: 5px;
}

.dropdown-content {
    height: 200px;
    overflow: scroll;

    background-color: #e1ecf7;
    border-radius: 20px;
}

.dropdown-item {
    display: flex;
    /* justify-content: center; */
    align-items: center;
}
</style>

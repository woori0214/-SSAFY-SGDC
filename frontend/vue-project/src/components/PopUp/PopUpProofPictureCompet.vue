<template>
    <transition name="modal">
        <div class="modal_mask" v-if="show" @click="close">
            <div class="modal_wrapper">
                <div class="modal_container" @click.stop>
                    <h1>경쟁모드 인증</h1>

                    <!-- 이미지와 파일 업로드를 위한 input 요소 감싼 레이블 -->
                    <label for="fileInput">
                        <div class="image-container">
                            <img :src="currentImageSrc" alt="이미지" class="modal-image" />
                        </div>
                    </label>

                    <input id="fileInput" type="file" @change="handleFileChange" style="display: none;" />

                    <!-- 업로드 버튼과 닫기 버튼 컨테이너 -->
                    <div class="button-container">
                        <button class="upload-button" @click="competAuthImage">업로드</button>
                        <button class="close-button" @click="close">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>


<script setup>
import { ref } from 'vue';

import defaultImage from '@/assets/camera.png'; // 기본 이미지 경로 확인 필요

// const props = defineProps({
//     userId: Number,
//     competId: Number,
//     show: Boolean
// });
const props = defineProps(['show', 'userId', 'competId']);
const emit = defineEmits(['update:show', 'competAuthImage']);

const currentImageSrc = ref(defaultImage);
const profile = ref(null);

function close() {
    currentImageSrc.value = defaultImage;
    emit('update:show', false);
}

const handleFileChange = event => {
    const file = event.target.files[0];
    profile.value = file; // 파일 객체를 profile ref에 저장
    if (file) {
        profile.value = file; // 파일 저장
        const reader = new FileReader();
        reader.onload = e => currentImageSrc.value = e.target.result;
        reader.readAsDataURL(file); // 파일 읽기 및 미리보기 생성
    } else {
        alert('유효하지 않은 파일 형식입니다. 이미지를 선택하세요.');
    }
};

function competAuthImage() {
    if (profile.value) {
        emit('competAuthImage', profile.value); // profile에 저장된 파일 객체를 부모 컴포넌트로 전달
        close();
    } else {
        alert('파일을 선택해주세요.');
    }
}


</script>



<style scoped>
.modal_mask {
    position: fixed;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
}

.modal_wrapper {
    display: flex;
    padding: 20px;
    justify-content: center;
}

.modal_container {
    background: #e1ecf7;
    border-radius: 30px;
    border: #e1ecf7 1px solid;
    padding: 20px;
    width: 80%;
    max-width: 600px;
    height: auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    border: 1px solid #e1ecf7;
}

/* 파일 입력을 위한 스타일 */
input[type="file"] {
    margin-top: 10px;
}

/* 업로드 버튼과 닫기 버튼 컨테이너 스타일 */
.button-container {
    display: flex;
    justify-content: center;
    width: 100%;
    margin-top: 10px;
}

/* 업로드 버튼 스타일 */
.upload-button,
.close-button {
    width: 200px;
    height: 50px;
    padding: 8px 16px;
    background-color: #71a5de;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: larger;
    font-weight: bolder;
}

.upload-button {
    margin-right: 10px;
}

/* 이미지 스타일 */
.image-container {
    text-align: center;
    width: 100%;
    height: auto;
    /* overflow: hidden; */
}

.modal-image {
    width: 100%;
    height: auto;
}

/* 모바일 화면용 스타일 */
@media (max-width: 600px) {
    .modal_container {
        width: 80%;
    }
}
</style>


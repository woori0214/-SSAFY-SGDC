<template>
  <transition name="modal">
      <div class="modal_mask" v-if="show" @click="close">
          <div class="modal_wrapper">
              <div class="modal_container" @click.stop>
                  <h1>인증</h1>

                  <!-- 이미지와 파일 업로드를 위한 input 요소 감싼 레이블 -->
                  <label for="fileInput">
                      <div class="image-container">
                          <img :src="currentImageSrc" alt="이미지" class="modal-image" />
                      </div>
                  </label>

                  <input id="fileInput" type="file" @change="handleFileChange" style="display: none;"/>

                  <!-- 업로드 버튼과 닫기 버튼 컨테이너 -->
                  <div class="button-container">
                      <button class="upload-button" @click="uploadImage">업로드</button>
                      <button class="close-button" @click="close">닫기</button>
                  </div>
              </div>
          </div>
      </div>
  </transition>
</template>

<script>
import defaultImage from '@/assets/camera.png';

export default {
  props: ['show', 'selectedCategory', 'isSoloMode',],
  emits: ['update:show', 'uploadImage'],
  data() {
      return {
          currentImageSrc: defaultImage,
      };
  },
  methods: {
      close() {
          // console.log('모달을 닫고 상태를 초기화합니다.');
          this.currentImageSrc = defaultImage;
          this.$emit('update:show', false);
      },
      handleFileChange(event) {
          // console.log('파일이 선택되었습니다:', event.target.files[0]);
          const file = event.target.files[0];
          if (file) {
              if (file.type.startsWith('image/')) {
                  const reader = new FileReader();
                  reader.onload = (e) => {
                      // console.log('FileReader가 로딩되었습니다:', e.target.result);
                      this.currentImageSrc = e.target.result;
                  };
                  reader.readAsDataURL(file);
              } else {
                  // console.log('유효하지 않은 파일 형식입니다. 이미지를 선택하세요.');
              }
          }
      },
      uploadImage() {
          // console.log('이미지 업로드 로직을 실행합니다.');
          // 여기에 이미지 업로드 로직을 추가하세요.
          this.$emit('uploadImage', this.currentImageSrc);

      },
  },
};
</script>

<style scoped>
/* 업데이트된 스타일링 */
.modal_mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal_wrapper {
  padding: 20px;
}

.modal_container {
  background: #e1ecf7;
  border-radius: 30px;
  border: #e1ecf7 1px solid;
  padding: 20px;
  width: 80%; /* 변경된 너비 */
  max-width: 600px; /* 최대 너비 */
  height: auto; /* 자동 높이 조정 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #e1ecf7;
}

/* 파일 입력을 위한 스타일 */
input[type="file"] {
  margin-top: 10px; /* 간격 조정 */
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
  padding: 8px 16px;
  background-color: #71a5de;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.upload-button {
  margin-right: 10px; /* 간격 조정 */
}

/* 이미지 스타일 */
.image-container {
  text-align: center;
  width: 100%;
  height: auto;
  overflow: hidden;
}

.modal-image {
  width: 100%;
  height: auto;
}

/* 모바일 화면용 스타일 */
@media (max-width: 600px) {
  .modal_container {
      width: 90%;
  }
}
</style>


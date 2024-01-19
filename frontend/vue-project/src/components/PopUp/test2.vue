<!-- test2.vue -->
<template>
    <transition name="modal">
      <div class="modal_mask" v-if="show" @click="close">
        <div class="modal_wrapper">
          <div class="modal_container" @click.stop>
            <!-- 파일 업로드를 위한 input 요소 추가 -->
            <input type="file" @change="handleFileChange" />
            <slot></slot>
            <footer>
              <button @click="close">닫기</button>
            </footer>
          </div>
        </div>
      </div>
    </transition>
  </template>
  
  <script>
  export default {
    props: ['show'],
    emits: ['update:show'],
    methods: {
      close() {
        this.$emit('update:show', false);
      },
      handleFileChange(event) {
        // 파일이 변경되었을 때의 로직을 여기에 추가
        const file = event.target.files[0];
        console.log('Selected file:', file);
        // 파일 처리 로직을 추가하세요.
      },
    },
  };
  </script>
  
  <style>
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
    background: white;
    padding: 20px;
    border-radius: 10px;
    width: 800px;
    /* Example width */
    height: 500px;
    /* Example height */
  }
  
  .modal-enter-active,
  .modal-leave-active {
    transition: opacity 0.5s;
  }
  
  .modal-enter,
  .modal-leave-to {
    opacity: 0;
  }
  </style>
  
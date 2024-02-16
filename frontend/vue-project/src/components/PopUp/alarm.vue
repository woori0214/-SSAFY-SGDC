<!-- ModalComponent.vue -->
<template>
    <transition name="modal">
      <div class="modal-mask" v-if="show" @click="close">
        <div class="modal-wrapper">
          <div class="modal-container" @click.stop>
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
      }
    }
  };
  </script>
  
  <style>
  .modal-mask {
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
  
  .modal-wrapper {
    padding: 20px;
  }
  
  .modal-container {
    background: white;
    padding: 20px;
    border-radius: 10px;
  }
  
  .modal-enter-active, .modal-leave-active {
    transition: opacity 0.5s;
  }
  
  .modal-enter, .modal-leave-to {
    opacity: 0;
  }
  </style>
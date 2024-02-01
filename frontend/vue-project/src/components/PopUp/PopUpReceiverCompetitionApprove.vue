<template>
  <div v-if="showModal" class="modal">
    <button @click="closeModal">닫기</button>
  </div>
</template>

<script setup>
import { defineProps, ref, onMounted } from "vue";

const props = defineProps({
  showModal: Boolean,
  close: Function,
});

const closeModal = () => {
  props.close();
};

console.log("수락 팝업창 뜸");
onMounted(() => {
  console.log("수락 팝업창 뜸");
  console.log(props.showModal);
});
</script>

<style>
.modal {
  z-index: 10;
}
</style>
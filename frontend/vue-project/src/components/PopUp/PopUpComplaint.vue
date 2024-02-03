<template>
  <div v-if="showModal" class="complaintBox-wrap">
    <div class="complaintBox-body">
        <div class="complaintBox-title">신고하기</div>
      <textarea type="text" v-model="complaintText" class="complaintBox-content" />

      <div class="complaintBox-Btns">
        <button @click="close_complaintBox" class="complaintBox-complaintBtn">
          신고하기
        </button>
        <button @click="close_complaintBox" class="complaintBox-closeBtn">
          닫기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

const props = defineProps({
  showModal: Boolean,
  close: Function,
});

const close_complaintBox = () => {
  props.close();
};

const complaintText = ref("");
</script>

<style>
.complaintBox-wrap {
  /* border: 2px solid red; */

  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 12;
}
.complaintBox-body {
  /* border: 2px solid blue; */
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 40%;
  max-height: 40%;
  background: #fff;
  border-radius: 10px;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  gap: 10px;
}
.complaintBox-title{
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
    background-color: #aecbeb;
    padding-inline: 10px;
    padding-block: 10px;
    font-size: 24px;
}
.complaintBox-content{
    margin: 20px;
    font-size: 15px;
    height: 80px;
    overflow: auto;
    word-break:break-all;
}
.complaintBox-Btns{
    margin-bottom: 10px;
    margin-inline: 15px;
    display: flex;
    justify-content: space-between;
}
.complaintBox-complaintBtn{

}
.complaintBox-closeBtn{

}
</style>
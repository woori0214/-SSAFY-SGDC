<template>
  <div v-if="showModal" class="complaintBox-wrap">
    <div class="complaintBox-body">
      <div class="complaintBox-title">신고하기</div>

      <div class="pinokio_select_btn">
        <label
          class="pinokio_user_label"
          :style="pinokio_userId == props.user1_id ? 'color: #71a5de' : ''"
          ><input
            type="radio"
            class="pinokio_user_radio"
            v-bind:value="props.user1_id"
            v-model="pinokio_userId"
          />{{ props.user1_nickname }}</label
        >
        <label
          class="pinokio_user_label"
          :style="pinokio_userId == props.user2_id ? 'color: #71a5de' : ''"
          ><input
            type="radio"
            class="pinokio_user_radio"
            v-bind:value="props.user2_id"
            v-model="pinokio_userId"
          />{{ props.user2_nickname }}</label
        >
      </div>

      <textarea
        type="text"
        style="resize: none"
        v-model="complaintText"
        class="complaintBox-content"
      ></textarea>

      <div class="complaintBox-Btns">
        <button @click="reportFeedUser" class="complaintBox-complaintBtn">
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
import { useFeedStore } from "@/stores/feed";
import { useUserStorageStore } from "@/stores/userStorage";

const userStorage = useUserStorageStore();
const useFeedS = useFeedStore();

const complaintText = ref("");

const pinokio_userId = ref();

const props = defineProps({
  showModal: Boolean,
  close: Function,
  feedId: Number,
  user1_id: Number,
  user1_nickname: String,
  user2_id: Number,
  user2_nickname: String,
});

const close_complaintBox = () => {
  props.close();
};

const reportFeedUser = () => {
  const user_id_data = Number(userStorage.getUserInformation().user_id);
  useFeedS
    .reportUser(
      props.feedId,
      user_id_data,
      pinokio_userId.value,
      complaintText.value
    )
    .then((res) => {
      
    })
    .catch((err) => {
      console.log(err);
    });

  props.close();
};
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
.complaintBox-title {
  border-top-right-radius: 10px;
  border-top-left-radius: 10px;
  background-color: #aecbeb;
  padding-inline: 10px;
  padding-block: 10px;
  font-size: 24px;
}
.complaintBox-content {
  margin-bottom: 20px;
  margin-inline: auto;
  font-size: 15px;
  height: 80px;
  width: 90%;
  overflow: auto;
  word-break: break-all;
  border: 2px solid #aecbeb;
  border-radius: 5px;
  padding: 5px;
}
.complaintBox-content:focus {
  outline: none;
}

.complaintBox-content::-webkit-scrollbar {
  width: 5px;
}
.complaintBox-content::-webkit-scrollbar-thumb {
  background-color: #71a5de;
  border-radius: 5px;
  background-clip: padding-box;
}
.complaintBox-content::-webkit-scrollbar-track {
  background-color: rgba(255, 255, 255, 0);
  border-radius: 5px;
}

.complaintBox-Btns {
  margin-bottom: 10px;
  margin-inline: 15px;
  display: flex;
  justify-content: space-between;
}
.complaintBox-complaintBtn {
}
.complaintBox-closeBtn {
}

.pinokio_select_btn {
  /* border: 1px solid red; */
  display: flex;
  justify-content: space-around;
}
.pinokio_user_label {
  /* border: 1px solid orange; */
  font-size: 1.4rem;
  color: rgb(201, 201, 201);
}
.pinokio_user_radio {
  appearance: none;
}
</style>
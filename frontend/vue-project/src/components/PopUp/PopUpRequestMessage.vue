<!-- PopUpRequestMessage.vue -->
<template>
    <div v-if="showModal" class="modal">
        <div class="modal-content">
            <!-- 모달 내용 -->
            <template v-if="modalType === 'randomMatching'">
                <h1>랜덤의 상대에게 도전장을 보냈습니다</h1>
                <!-- Display additional random matching modal content -->
            </template>
            <template v-else-if="modalType === 'friendMatching'">
                <h1>친구리스트</h1>
                <h3>선택한 카테고리: {{ category_id }}</h3>
                <h3>현재 사용자 ID: {{ user_id }}</h3>
                
                <ul>
                    <li v-for="friend in friendList" :key="friend.followingId">
                        {{ friend.followingId.userNickname }}
                    </li>
                    <span v-for="record in friendRecord" :key="record.userCategoryId">
                        <span v-if="record.userId === friend.followingId">
                            전적: {{ record.categoryWinCount / (record.categoryWinCount + record.categoryFailCount) }}
                        </span>
                    </span>
                    <button @click="submitSendToFriend(friend.followingId, category_id)">신청</button>
                </ul>

            </template>

            <button @click="closeModal">닫기</button>
        </div>
    </div>
    <div v-if="showResponseModal" class="modal">
        <div class="modal-content">
            <h1>도전장 전송 완료</h1>
            <p>{{ responseMessage }}</p>
            <button @click="closeResponseModal">닫기</button>
        </div>
    </div>
</template>
  
<script setup>
import { defineProps, ref, onMounted } from 'vue';
import { useCompetionStore } from '@/stores/competition';

const props = defineProps({
    showModal: Boolean,
    close: Function,
    modalType: String,
    category_id: Number,
    user_id: String
});
const closeModal = () => {
    props.close();
};
const competion = useCompetionStore();
const friendList = ref([]);
const friendRecord = ref([]);
const showResponseModal = ref(false);
const responseMessage = ref("");
onMounted(() => {

    competion
        .competitionFriendList(props.user_id)
        .then((response) => {
            friendList.value = response.data;
        })
        .catch((error) => {
            console.error('Error fetching friend list:', error);
        });

    competion
        .competitionAnalysis(props.user_id)
        .then((response) => {
            friendRecord.value = response.data;

        })
        .catch((error) => {
            console.error('Error competition list:', error);
        })


});

const submitSendToFriend = async (friendId, categoryId) => {
    try {
        // API 요청을 위한 데이터 객체를 생성합니다.
        const sendFriendData = {
            userId: props.user_id,
            friendId: friendId,
            categoryId: categoryId
        };

        // competion 스토어의 friendSend 액션을 호출하여 API 요청을 보냅니다.
        await competion.friendSend(sendFriendData);

        // 요청이 성공하면 콘솔에 성공 메시지를 표시합니다.
        console.log('Friend send request sent successfully');
        const response = await competion.friendSend(sendFriendData);
        responseMessage.value = `${response.data.matching.userNickname} 님에게 도전장을 보냈습니다`;
        showResponseModal.value = true;  // 새 모달 창을 표시합니다.
        // 여기에 성공 시의 추가적인 로직을 구현할 수 있습니다.
        // 예를 들어, 모달을 닫거나 사용자에게 알림을 제공할 수 있습니다.
        props.close(); // 모달을 닫는 예시입니다.

    } catch (error) {
        // 요청이 실패하면 콘솔에 오류 메시지를 표시합니다.
        console.error('Error sending friend send request:', error);

        // 여기에 실패 시의 추가적인 로직을 구현할 수 있습니다.
        // 예를 들어, 사용자에게 오류 메시지를 표시할 수 있습니다.
    }
};
const closeResponseModal = () => {
    showResponseModal.value = false;
};
</script>
  
<style>
.modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

/* Additional styling for specific modal content */
.modal-content h1 {
    color: #333;
    font-size: 24px;
    margin-bottom: 10px;
}

.modal-content h3 {
    color: #666;
    font-size: 18px;
    margin-bottom: 8px;
}

button {
    background-color: #3498db;
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

button:hover {
    background-color: #2980b9;
}
</style>
  
  
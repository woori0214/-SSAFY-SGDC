<template>
    <div class="challenge_board">
        <h1>현황게시판</h1>
        <div class="current_board">
            <div class="solo">
                <h2>솔로 모드 현황</h2>
                <div class="solo_board">
                    <div class="success_graph" ref="chartContainer">
                        <canvas ref="myChart" width="150" height="150"></canvas>
                    </div>
                    <div class="categories">
                        <button class="category_btn" v-for="category in categories" :key="category.id"
                            :class="{ 'active': category.isActive }" @click="navigateToPage(category)">
                            {{ category.name }}
                        </button>
                    </div>
                </div>
            </div>
            <div class="compet">
                <h2>경쟁 모드 현황</h2>
                <div class="compet_board">
                    <div class="carousel_container">
                        <div class="carousel_slide" :style="slideStyle">
                            <!-- Carousel 아이템 -->
                            <div class="carousel_item" v-for="(item, index) in competData" :key="index">
                                <!--sender부분-->
                                <div class="player1">
                                    <img :src="item.sender_user_img" alt="sender image" class="player_img">
                                    <p>{{ item.sender_user_nickname }}</p>
                                    <button v-if="item.sender_isCurrentUser && !item.sender_authenticated"
                                        @click="authenticate('sender', item)">인증하기</button>
                                    <div v-else-if="item.sender_authenticated">인증 완료</div>
                                    <div v-else>진행중</div>
                                </div>
                                <div>
                                    <h1>{{ item.category_id }}</h1>
                                    <h1>vs</h1>
                                </div>
                                <!--receiver부분-->
                                <div class="player2">
                                    <img :src="item.receiver_user_img" alt="receiver image" class="player_img">
                                    <p>{{ item.receiver_user_nickname }}</p>
                                    <button v-if="item.receiver_isCurrentUser && !item.receiver_authenticated"
                                        @click="authenticate('receiver', item)">인증하기</button>
                                    <div v-else-if="item.receiver_authenticated">인증 완료</div>
                                    <div v-else>진행중</div>
                                </div>
                            </div>
                        </div>
                        <!-- Carousel 컨트롤 -->
                        <button @click="prev">＜</button>
                        <button @click="next">＞</button>
                        <!-- 인디케이터 -->
                        <div class="indicators">
                            <span v-for="(item, index) in items" :key="index" :class="{ active: index === currentIndex }"
                                @click="goTo(index)"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useCompetionStore } from '@/stores/competition';
import { useLoginStore } from '@/stores/login';
import axios from 'axios';
import Chart from 'chart.js/auto';
const loginStore = useLoginStore();
const loggedInUserId = loginStore.userId; // 로그인한 사용자의 ID를 가져옴
const competitionStore = useCompetionStore();
const categories = ref([
    { id: 1, name: '기상', isActive: false },
    { id: 2, name: '알고리즘', isActive: false },
    { id: 3, name: '운동', isActive: false },
    { id: 4, name: '식단', isActive: false },
    { id: 5, name: '스터디', isActive: false },
    { id: 6, name: '절제', isActive: false },
]);

const items = ref([
    {
        index: 1, category: '기상', name1: '화석', imageUrl1: './src/assets/image1.png', challenge_status1: '진행중',
        name2: '지은', imageUrl2: './src/assets/image2.png', challenge_status2: '인증 완료',
    },
    {
        index: 2, category: '알고리즘', name1: '화석', imageUrl1: './src/assets/image1.png', challenge_status1: '진행중',
        name2: '태범', imageUrl2: './src/assets/image2.png', challenge_status2: '진행중',
    },
]);
const competData = ref([]);
const currentIndex = ref(0);
const router = useRouter();
const chartRef = ref(null);
// 인증 상태를 확인하는 함수
const isAuthenticated = (authImage) => {
    return authImage !== null && authImage !== '';
};

// 사용자가 sender인지 receiver인지 확인하는 함수
const isCurrentUser = (userId) => {
    return loggedInUserId === userId;
};

// 인증하기 버튼의 클릭 이벤트 핸들러
const authenticate = (role, item) => {
    // 인증 프로세스를 구현...
};
const fetchCompetitionData = async () => {
    try {
        const response = await competitionStore.competitionProgressDetail(loggedInUserId);
        if (response.status === 200 && response.data.compet) {
            competData.value = response.data.compet.map(compet => ({
                ...compet,
                sender_isCurrentUser: isCurrentUser(compet.sender_user_id),
                receiver_isCurrentUser: isCurrentUser(compet.receiver_user_id),
                sender_authenticated: isAuthenticated(compet.sender_auth_image),
                receiver_authenticated: isAuthenticated(compet.receiver_auth_image),
            }));
        }
    } catch (error) {
        console.error('Error fetching competition data:', error);
    }
};

const fetchData = async () => {
    // Fetch your data here and update categories if needed
    // 예를 들어:
    // const response = await axios.get('/your-endpoint');
    // categories.value = response.data;
};

const navigateToPage = (category) => {
    if (category.isActive == false) {
        // Navigate to the desired route
        // 예를 들어:
        // router.push({ name: 'CategoryPage', params: { id: category.id } });
        router.push({ name: 'Competiton' })
    }
};

const prev = () => {
    currentIndex.value = (currentIndex.value - 1 + items.value.length) % items.value.length;
};

const next = () => {
    currentIndex.value = (currentIndex.value + 1) % items.value.length;
};

const goTo = (index) => {
    currentIndex.value = index;
};

const slideStyle = computed(() => {
    return {
        transform: `translateX(-${currentIndex.value * 100}%)`
    };
});
onMounted(() => {
    fetchCompetitionData();
    fetchData();
    if (chartRef.value) {
        const ctx = chartRef.value.getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Category 1'],
                datasets: [
                    {
                        data: [60, 40],
                        backgroundColor: ['lightblue', 'transparent'],
                    },
                ],
            },
            options: {
                cutout: '60%',
                responsive: true,
                maintainAspectRatio: false,
            },
        });
    }
});


</script>
  

  
<style>
.challenge_board {
    border: 2px rgb(71, 48, 248) solid;
}

.current_board {
    display: flex;
    border: 1px green solid;
}

.solo {
    border: 1px rgb(255, 53, 188) solid;
    width: 50%;
    margin: 5px;
    text-align: center;
}

.compet {
    border: 1px rgb(50, 248, 255) solid;
    width: 50%;
    margin: 5px;
    text-align: center;
}

.solo_board {
    border: 1px rgb(224, 76, 100) solid;
    padding: 5px;
    display: flex;
}

.success_graph {
    border: 1px black solid;
    border-radius: 100%;
    width: 150px;
    height: auto;
    text-align: center;
}

.categories {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.category_btn {
    width: 100px;
    height: 50px;
    border-radius: 20px;
    margin: 3px;
    padding: 5px;
    cursor: pointer;
    flex-basis: calc(33.33% - 10px);
}

.active {
    background-color: rgb(66, 66, 250);
    color: #ababab;
}

.player_img {
    width: 100px;
    height: 100px;
}

.carousel_item {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    text-align: center;
}

.compet_board {
    /* 기존 스타일 유지하고 overflow 추가 */
    overflow: hidden;
    /* carousel 이 해당 영역을 넘어가지 않도록 */
}

.carousel_container {
    position: relative;
    overflow: hidden;
    /* 필요에 따라 여기도 추가할 수 있습니다 */
    width: 100%;
    /* 혹은 필요한 너비로 조정하세요 */
    margin: auto;
    /* 가운데 정렬 */
}

.carousel_slide {
    display: flex;
    transition: transform 0.3s ease;
}

.carousel_item {
    flex: 0 0 auto;
    /* 각 아이템의 너비를 자동으로 설정 */
    width: 100%;
    /* 부모 컨테이너의 100% 너비를 가짐 */
}

@media (max-width: 768px) {
    .current_board {
        flex-direction: column;
    }

    .solo,
    .compet {
        width: 100%;
    }

    .carousel_item {
        flex-direction: row;
        align-items: center;
        text-align: center;
    }

    .player1,
    .player2 {
        margin-bottom: 10px;
    }

    .player_img {
        width: 70px;
        /* 변경된 부분: 이미지 크기를 작게 조정 */
        height: 70px;
    }
}
</style>
  
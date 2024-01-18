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
                        <button v-for="category in categories" :key="category.id" :class="{ 'active': category.isActive }"
                            @click="navigateToPage(category)">
                            <p>{{ category.name }}</p>
                        </button>
                    </div>
                </div>
            </div>
            <div class="compet">
                <h2>경쟁 모드 현황</h2>
                <div class="compet_board">
                    <div class="carousel-container">
                        <div class="carousel-slide" :style="slideStyle">
                            <!-- Carousel 아이템 -->
                            <div class="carousel-item" v-for="(item, index) in items" :key="index">
                                <div class="player1">
                                    <img :src="item.imageUrl1" alt="...">
                                    <p>{{ item.name1 }}</p>
                                    <button>{{ item.challenge_status }}</button>
                                </div>
                                <h1>vs</h1>
                                <div class="player2">
                                    <img :src="item.imageUrl2" alt="...">
                                    <p>{{ item.name2 }}</p>
                                    <button>{{ item.challenge_status }}</button>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="player1">
                                    <img :src="items[currentIndex].imageUrl1" alt="...">
                                    <p>{{ items[currentIndex].name1 }}</p>
                                    <button>{{ items[currentIndex].challenge_status }}</button>
                                </div>
                                <h1>vs</h1>
                                <div class="player2">
                                    <img :src="items[currentIndex].imageUrl2" alt="...">
                                    <p>{{ items[currentIndex].name2 }}</p>
                                    <button>{{ items[currentIndex].challenge_status }}</button>
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
  
<script>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Chart from 'chart.js/auto';

export default {
    setup() {
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
                index: 1, name1: '화석', imageUrl1: './src/assets/image1.png', challenge_status: '진행중',
                name2: '지은', imageUrl2: './src/assets/image2.png', challenge_status: '인증 완료',
            },
            {
                index: 2, name1: '화석', imageUrl1: './src/assets/image1.png', challenge_status: '진행중',
                name2: '태범', imageUrl2: './src/assets/image2.png', challenge_status: '진행중',
            },
        ]);

        const currentIndex = ref(0);
        const router = useRouter();
        const chartRef = ref(null);


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

        return {
            categories,
            items,
            currentIndex,
            slideStyle,
            chartRef,
            navigateToPage,
            prev,
            next,
            goTo
        };

    }
};
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

button {
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

img {
    width: 50px;
    height: 50px;
}

.carousel-item {
    display: flex;
    justify-content: space-between;
}

.compet_board {
    /* 기존 스타일 유지하고 overflow 추가 */
    overflow: hidden;
    /* carousel 이 해당 영역을 넘어가지 않도록 */
}

.carousel-container {
    position: relative;
    overflow: hidden;
    /* 필요에 따라 여기도 추가할 수 있습니다 */
    width: 100%;
    /* 혹은 필요한 너비로 조정하세요 */
    margin: auto;
    /* 가운데 정렬 */
}

.carousel-slide {
    display: flex;
    transition: transform 0.3s ease;
}

.carousel-item {
    flex: 0 0 auto;
    /* 각 아이템의 너비를 자동으로 설정 */
    width: 100%;
    /* 부모 컨테이너의 100% 너비를 가짐 */
}
</style>
  
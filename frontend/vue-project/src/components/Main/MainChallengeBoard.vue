<template>
    <div>
        <div id="challenge_board">
            <h1>현황게시판</h1>
            <div id="current_board">
                <div id="solo">
                    <h2>솔로 모드 현황</h2>
                    <div id="solo_board">
                        <div id="success_graph">
                            <div>
                                <canvas ref="myChart"></canvas>
                            </div>
                        </div>

                        <div id="categories">
                            <button v-for="category in categories" :key="category.id"
                                :class="{ 'active': category.isActive }" @click="navigateToPage(category)">
                                {{ category.name }}
                            </button>
                        </div>
                    </div>
                </div>
                <div id="compet">
                    <h2>경쟁 모드 현황</h2>
                    <div id="compet_board">


                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>

import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Chart from 'chart.js/auto';

const categories = ref([
    { id: 1, name: '기상', isActive: false },
    { id: 2, name: '알고리즘', isActive: false },
    { id: 3, name: '운동', isActive: false },
    { id: 4, name: '식단', isActive: false },
    { id: 5, name: '스터디', isActive: false },
    { id: 6, name: '절제', isActive: false },
]);

const router = useRouter();

const fetchData = () => {
    //   return axios.get('YOUR_API_ENDPOINT')
    //     .then(response => {

    //       const activationStatuses = response.data;

    //       categories.value.forEach((category, index) => {
    //         category.isActive = activationStatuses[index];
    //       });
    //     })
    //     .catch(error => {
    //       console.error('Error fetching data:', error);
    //     });
};

const navigateToPage = (category) => {
    if (category.isActive) {
        router.push();
    }
};

onMounted(() => {
    fetchData();
});

const chartRef = ref(null);

onMounted(() => {
  if (chartRef.value) {
    const ctx = chartRef.value.getContext('2d');
    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Category 1'],
        datasets: [
          {
            data: [75], // 데이터에 따라 원형 그래프의 비율을 조절하세요.
            backgroundColor: ['lightblue'],
          },
        ],
      },
    });
  }
});

</script>

<style>
#challenge_board {
    border: 1px black solid;
}

#current_board {
    display: flex;
}

#solo {
    border: 1px black solid;
    width: 50%;
    margin: 10px;
    text-align: center;
}

#compet {
    border: 1px black solid;
    width: 50%;
    margin: 10px;
}

#solo_board {
    display: flex;
}


#success_graph {
    border: 1px black solid;
    border-radius: 100%;
    width: 100px;
    height: 100px;
    text-align: center;
}

#category {
    margin: 3px;
}

#categories {
    display: flex;
}

button {
    margin: 3px;
    padding: 5px 10px;
    cursor: pointer;
}

.active {
    background-color: rgb(66, 66, 250);
    /* 변경하고 싶은 활성화된 상태의 배경색을 지정하세요 */
    color: #ababab;
    /* 변경하고 싶은 활성화된 상태의 텍스트색을 지정하세요 */
}
</style>
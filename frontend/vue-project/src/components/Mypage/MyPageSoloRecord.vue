<template>
  <div>
    <div class="accordion-header" @click="toggleAccordion">
      <h2>솔로모드 내역</h2>
      <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
    </div>
    <transition>
      <div class="solo_accordion-content" :class="{ 'open': isOpen }" v-show="isOpen">
        <div v-for="solodata in soloListData" :key="solodata.category_id" class="solo_record">
          <div v-if="solodata.solo_status === 'DONE' && solodata.solo_result === 'COMPLETE'" class="solo_img_div">
            <img :src="getImageUrl(solodata.category_id)" alt="" class="soloimg" />
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>
  
<script>
import { ref, onMounted } from 'vue';
import { useSoloStore } from '@/stores/solo';

export default {
  props: ['userId', 'categories'],

  setup(props) {
    const soloStore = useSoloStore();

    const userId = ref(props.userId);
    const categories = ref(props.categories);
    const soloListData = ref([]);

    // 페이지 열었을 때 정보 가져오기
    onMounted(() => {
      soloStore.soloList(userId.value)
        .then((res) => {
          console.log(res)
          soloListData.value = res.data.solos
        })
        .catch((error) => {
          console.log(error)
        });
    });

    // 이미지 매칭
    const getImageUrl = (categoryId) => {
      const category = categories.value.find(c => c.id === categoryId);
      return category ? category.img : null;
    };

    // 아코디언 펼치기/접기
    const isOpen = ref(true);

    const toggleAccordion = () => {
      isOpen.value = !isOpen.value;
    };



    return { userId, soloListData, categories, isOpen, toggleAccordion, getImageUrl }
  }
};



</script>
  
<style scoped>
/* 아코디언 헤더 스타일링 */
.accordion-header {
  display: flex;
  align-items: center;
  background-color: #83b0e1;
  padding: 10px;
  cursor: pointer;
  border-radius: 15px;
}

.rotate-icon {
  transition: transform 0.3s ease;
  margin-left: auto;
  /* 화살표를 헤더의 오른쪽에 위치시키기 위한 스타일 */
}

.rotate-icon::before {
  content: '\25BC';
  /* 기본 화살표 아래 방향 유니코드 */
  display: inline-block;
}

.rotate-icon.rotate::before {
  content: '\25B2';
  /* 화살표 위 방향 유니코드 */
}

/* 아코디언 내용 스타일링 */
.solo_accordion-content {
  padding: 10px;
  border-top: none;
  overflow: hidden;
  max-height: 0;
  transition: max-height 0.3s ease;
  display: flex;
  flex-direction: row;

}

.solo_accordion-content::-webkit-scrollbar {
  height: 5px;
}

.solo_accordion-content::-webkit-scrollbar-thumb {
  background-color: #71a5de;
  border-radius: 10px;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.solo_accordion-content.open {
  max-height: 1000px;
  /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
  transition: max-height 0.3s ease;
  overflow: scroll;
}

.solo_img_div {
  margin: 10px;
}

.soloimg {
  background: #f8f9fb;
  border-radius: 100%;
  width: 110px;
  margin: 5px;

}
</style>
  
<template>
  <div>
    <div class="accordion-header" @click="toggleAccordion">
      <h2>분석</h2>
      <span :class="{ 'rotate-icon': true, 'rotate': isOpen }"></span>
    </div>
    <transition>
      <div class="accordion-content" :class="{ open: isOpen }" v-show="isOpen">
        <div class="analysis">
          <!-- 솔로 -->
          <div class="solo_analysis" ref="compet_analysis_component">
            <div class="analysis_head">
              <div class="analysis_head_item">솔로모드</div>
            </div>
            <div class="solo_analysis_contents">
              <div class="solo_analysis_cnt">
                <h1>{{ success_solo }} 회</h1>
                <h3>성공한 솔로 도전 횟수</h3>
              </div>
              <div class="solo_analysis_category">
                <div class="many_solo_img_div">
                  <img :src="many_solo_challenge_image" alt="Category Image" class="many_solo_img"
                    v-if="many_solo_challenge" />
                </div>
                <h3>많이 도전한 카테고리</h3>
              </div>
            </div>
          </div>

          <!-- 경쟁 -->
          <div class="compet_analysis" ref="compet_analysis_component">
            <div class="compet_analysis_head">
              <div class="compet_analysis_head_item">경쟁모드</div>
            </div>
            <div class="compet_analysis_contents">
              <h1>{{ total_analysis }}%</h1>
              <h3>
                {{ all_wincount }}승 {{ all_failcount }}패 {{ all_count }}전
              </h3>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>
  
<script>
import { ref, onMounted, computed } from "vue";
import { useCompetionStore } from "@/stores/competition";
import { useSoloStore } from "@/stores/solo";

export default {
  props: ["userId", "categories"],

  setup(props) {
    const userId = ref(props.userId);
    const categories = ref(props.categories);
    const competStore = useCompetionStore();
    const soloStore = useSoloStore();

    let maxCount = 0;
    let mostFrequentId = null;

    const compet_analysis = ref([]);

    const soloListData = ref([]);

    const all_wincount = computed(() => {
      // compet_analysis가 정의되어 있고, 배열이 비어 있지 않은지 확인
      if (compet_analysis.value && compet_analysis.value.length > 0) {
        return compet_analysis.value.reduce(
          (total, item) => total + item.category_win_count,
          0
        );
      }
      return 0; // 조건에 맞지 않으면 0 반환
    });

    const all_failcount = computed(() => {
      // compet_analysis가 정의되어 있고, 배열이 비어 있지 않은지 확인
      if (compet_analysis.value && compet_analysis.value.length > 0) {
        return compet_analysis.value.reduce(
          (total, item) => total + item.category_fail_count,
          0
        );
      }
      return 0; // 조건에 맞지 않으면 0 반환
    });

    // 총 횟수 계산
    const all_count = computed(() => {
      return all_wincount.value + all_failcount.value;
    });

    // 전적 승률 계산
    // 전적 승률 계산
    const total_analysis = computed(() => {
      if (all_count.value === 0) {
        return 0; // 분모가 0일 때 0 반환
      }
      const ans = (all_wincount.value / all_count.value) * 100;
      return ans.toFixed(0);
    });
    // 솔로모드 많이 시도한 카테고리 계산
    const many_solo_challenge = computed(() => {
      if (soloListData.value && soloListData.value.length > 0) {
        const countMap = soloListData.value.reduce((acc, cur) => {
          acc[cur.category_id] = (acc[cur.category_id] || 0) + 1;
          return acc;
        }, {});

        let maxCount = 0;
        let mostFrequentId = null;

        for (const id in countMap) {
          if (countMap[id] > maxCount) {
            maxCount = countMap[id];
            mostFrequentId = id;
          }
        }

        return mostFrequentId;
      }
      return null; // 솔로 데이터가 없으면 null 반환
    });


    // 솔로모드 챌린지 성공 횟수
    const success_solo = computed(() => {
      return soloListData.value.filter((item) => item.solo_result === 'COMPLETE').length;
    });

    // 솔로모드 많이 한 카테고리 이미지 매칭
    const many_solo_challenge_image = computed(() => {
      const categoryId = many_solo_challenge.value;
      const category = categories.value.find(
        (cat) => cat.id.toString() === categoryId
      );
      return category ? category.img : "";
    });

    // 페이지 열었을 때 정보 가져오기(경쟁 리스트, 솔로 리스트)
    onMounted(async () => {
      try {
        const compRes = await competStore.competitionAnalysis(userId.value);
        console.log('경쟁분석 가져옴');
        compet_analysis.value = compRes.data.userCategories;

        const soloRes = await soloStore.soloList(userId.value);
        console.log('솔로분석 가져옴');
        soloListData.value = soloRes.data.solos;
      } catch (err) {
        console.error(err);
      }
    });

    // 아코디언 펼치기/접기
    const isOpen = ref(true);

    const toggleAccordion = () => {
      isOpen.value = !isOpen.value;
    };

    // 경쟁모드 높이 솔로모드에 맞추기
    const solo_analysis_component = ref(null);
    const compet_analysis_component = ref(null);

    // 사이즈 조정 함수
    function setCompetHeight() {
      if (solo_analysis_component.value && compet_analysis_component.value) {
        const soloComponentHeight = solo_analysis_component.value.offsetHeight;
        compet_analysis_component.value.style.height = soloComponentHeight + "px";
      }
    }

    setCompetHeight();
    window.addEventListener("resize", setCompetHeight);

    return {
      userId,
      compet_analysis,
      categories,
      soloListData,
      isOpen,
      all_wincount,
      all_failcount,
      all_count,
      total_analysis,
      many_solo_challenge,
      success_solo,
      many_solo_challenge_image,
      toggleAccordion,
      setCompetHeight,
    };
  },
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
  content: "\25BC";
  /* 기본 화살표 아래 방향 유니코드 */
  display: inline-block;
}

.rotate-icon.rotate::before {
  content: "\25B2";
  /* 화살표 위 방향 유니코드 */
}

/* 아코디언 내용 스타일링 */
.accordion-content {
  display: flex;
  padding: 10px;
  border-top: none;
  overflow: hidden;
  max-height: 0;
  transition: max-height 0.3s ease;
  overflow: hidden;
}

/* 내용이 펼쳐진 경우에만 보여지도록 스타일 지정 */
.accordion-content.open {
  max-height: 1000px;
  /* 충분한 크기로 조절하거나, 실제 내용의 높이에 따라 조절해주세요. */
  transition: max-height 0.3s ease;
}

.analysis {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-inline: 20px;
  /* padding-inline: 10px; */
  gap: 20px;
}

.solo_analysis {
  /* width: 50%; */
  flex: 2;
  text-align: center;
}

.analysis_head {
  display: flex;
  justify-content: left;
  margin-left: 18px;
}

.analysis_head_item {
  border-inline: #3f8bc9 solid 2px;
  border-top: #3f8bc9 solid 2px;
  background-color: #f8f9fb;
  border-top-right-radius: 15px;
  border-top-left-radius: 15px;
  padding-inline: 8px;
  padding-top: 8px;
  padding-bottom: 5px;

  font-size: 22px;
  font-weight: 600;
}

.solo_analysis_contents {
  background-color: #aecbeb;
  border-radius: 20px;
  /* padding: 15px; */
  display: flex;
  justify-content: space-around;
  align-items: center;
  min-height: 190px;
}

.solo_analysis_cnt {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 10px;
}

.solo_analysis_category {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 10px;
}

.many_solo_img_div {
  width: 70px;
  height: 70px;
}

.many_solo_img {
  width: 70px;
  margin: 10px;
}

.compet_analysis {
  /* width: 50%; */
  flex: 1;
  text-align: center;
}

.compet_analysis_head {
  display: flex;
  justify-content: left;
  margin-left: 18px;
}

.compet_analysis_head_item {
  border-inline: #3f8bc9 solid 2px;
  border-top: #3f8bc9 solid 2px;
  background-color: #f8f9fb;
  border-top-right-radius: 15px;
  border-top-left-radius: 15px;
  padding-inline: 8px;
  padding-top: 8px;
  padding-bottom: 5px;

  font-size: 22px;
  font-weight: 600;
}

.compet_analysis_contents {
  background-color: #aecbeb;
  border-radius: 20px;
  /* padding: 15px; */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 190px;
}
</style>
  
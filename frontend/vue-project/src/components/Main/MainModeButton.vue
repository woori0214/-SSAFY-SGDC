<template>
  <div class="mode-button_container">
    <button
      @click="solo"
      class="mode_button solomode_btn"
    >
      솔로 모드
    </button>
    <button
      @click="compet"
      class="mode_button competmode_btn"
    >
      경쟁 모드
    </button>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const solo = () => {
  router.push({ name: "Solo" });
};

const compet = () => {
  router.push({ name: "Competiton" });
};
</script>

<style scoped>
.mode-button_container {
  position: fixed;
  bottom: 0;
  width: 100%;
  max-width: 1000px;
  display: flex;
  gap: 20px;
  justify-content: space-around;
  align-items: center;
  margin-bottom: 10px;
}

.mode_button {
  /* flex-grow: 1;

    padding: 10px 0;
    height: 50px;
    color: #fff;
    border-radius: 5px;
    cursor: pointer; */
  flex: 1 1 auto;
  margin: 10px;
  padding: 10px;
  height: 70px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  /* text-shadow: 0px 0px 10px rgba(0,0,0,0.2);*/
  box-shadow: 0 0 20px #eee;
  border-radius: 10px;
  font-size: 30px;
  font-weight: 600;
}

.mode_button:hover {
  background-position: right center;
}

.solomode_btn {
  background-image: linear-gradient(
    to right,
    #25aae1,
    #4481eb,
    #04befe,
    #3f86ed
  );
  box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.75);
}

.competmode_btn {
  background-image: linear-gradient(
    to right,
    #eb3941,
    #f15e64,
    #e14e53,
    #e2373f
  );
  box-shadow: 0 5px 15px rgba(242, 97, 103, 0.4);
}
</style>
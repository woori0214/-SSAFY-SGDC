<template>
    <div class="feed_board">
      <div class="more_feed">
        <h1>알려드립니다</h1>
        <div>
          <button @click="more_feed">+더보기</button>
        </div>
      </div>
      <div class="feed_list">
        <div v-for="feed_item in feedlist" :key="feed_item.feed_id" class="feed_item">
          <p class="context">{{ feed_item.feed_content }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRouter } from 'vue-router';
  import { useFeedStore } from '@/stores/feed';
  
  const router = useRouter();
  const feedInfo = useFeedStore();
  const feedlist = ref([]);
  
  onMounted(async () => {
      try {
          const response = await feedInfo.getFeedList(); 
          if (response.message === 'success') {
              
              const sortedFeed = response.feed.sort((a, b) => new Date(b.update_at) - new Date(a.update_at));
             
              feedlist.value = sortedFeed.slice(0, 10);
          } else {
              console.error('피드 목록을 가져오지 못했습니다:', response.message);
          }
      } catch (error) {
          console.error('피드 목록을 가져오는 중 오류가 발생했습니다:', error);
      }
  });
  
  const more_feed = () => {
      router.push({ name: 'Feed' });
  };
  </script>
  
<template>
  <div id="main-body">
    <header>
      <div class="wrapper">
        <h2>Router For Development</h2>
        <nav>
          <RouterLink to="/">Main</RouterLink>
          <RouterLink to="/competition">Competiton</RouterLink>
          <RouterLink to="/solo">Solo</RouterLink>
          <RouterLink to="/feed">Feed</RouterLink>
          <RouterLink to="/feeddetail">FeedDetail</RouterLink>
          <RouterLink to="/mypage">MyPage</RouterLink>
          <RouterLink to="/login">Login</RouterLink>
          <RouterLink to="/signup">SignUp</RouterLink>
        </nav>
      </div>
    </header>
  <br>
    <RouterView />
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>

<style scoped>
#main-body{
  border: 2px solid gainsboro;

  max-width: 1000px;
  margin: auto;

  display: flex;
  flex-direction: column;
}

a {
  border: 1px solid green;
  margin: 5px;
  padding-inline: 10px;
}
nav {
  display: flex;
  margin-top: 10px;
  flex-wrap: wrap;
}
</style>
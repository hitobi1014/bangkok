<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <main>
    <RouterView />
  </main>
</template>

<style lang="scss">
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);
@import '@/assets/css/main-layout';
* {
  font-family: 'Noto Sans KR', sans-serif;
  box-sizing: content-box;
}
</style>

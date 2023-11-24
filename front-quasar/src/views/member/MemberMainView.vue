<script setup>

import DefaultHeader from "@/components/DefaultHeader.vue";
import AppFooter from "@/components/AppFooter.vue";
</script>

<template>
  <default-header />
<!--  <router-view name="mainContent"/>-->
  <router-view/>
  <app-footer/>
</template>

<style scoped lang="scss">

</style>
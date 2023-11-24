<script setup>
defineProps({
  btnName: {
    type: String,
    required: true
  }
})
</script>
<template>
  <q-btn color="secondary" :label="btnName" />
</template>

<style scoped lang="scss"></style>

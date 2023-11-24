<script setup>
import { ref } from "vue";

const tab = ref('')
</script>

<template>
  <div class="nav-header">
    <div>
      <div class="ul-box">
        <ul class="row">
          <li><router-link :to="{name:'memberMyInfo'}">내정보</router-link></li>
          <li><router-link :to="{name:'memberModify'}">정보수정</router-link></li>
          <li><router-link :to="{name:'memberLike'}">관심매물</router-link></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/css/reuse-var";

@include a-deco-none;

a {
  color : #717171;
}

.route-active {
  color: #7022e7;
  font-weight: 700;
}

li {
  list-style-type: none;
  margin-right: 15px;
  align-items: center;
}

.nav-header {
  background-color: #e0f1f1;
  height: 50px;
  > div {
    width: 1000px;
    margin: 0 auto;
  }
}

.ul-box {
  height: 50px;
  display: flex;
  align-items: center;
}
</style>
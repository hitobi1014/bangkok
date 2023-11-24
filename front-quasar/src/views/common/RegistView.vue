<script setup></script>

<template>
  <div class="bk-container row justify-center">
    <div class="regist-box self-center row">
      <div class="col column justify-center items-center">
        <router-link :to="{ name: 'memberRegist' }">
          <div>
            <q-icon name="person_outline" />
            <p>일반 회원</p>
          </div>
        </router-link>
      </div>
      <div class="col column justify-center items-center">
        <router-link :to="{ name: 'agentRegist' }">
          <div>
            <q-icon name="real_estate_agent" />
            <p>사업자</p>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

@include a-deco-none;
a {
  font-size: 68px;
  color: #595959;
  @include full-w-h;
  @include display-flex-center;

  p {
    text-align: center;
  }
}

i {
  font-size: 320px;
}

.regist-box {
  border: 2px solid $border-color;
  width: 1100px;
  height: 650px;

  > div:nth-child(1) {
    border-right: 2px solid $border-color;
  }
}
</style>

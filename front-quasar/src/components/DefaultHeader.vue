<script setup>
import { useMemberStore } from '@/stores/memberStore'
import { onMounted, ref } from 'vue'
import { storeToRefs } from 'pinia'

const memberStore = useMemberStore()
const { loginInfo } = storeToRefs(memberStore)

let name = loginInfo?.value?.name
</script>

<template>
  <!--  <q-layout view="hHh lpR fFf">-->
  <q-layout style="min-height: 50px">
    <!--    <q-header elevated class="bg-primary text-white" >-->
    <q-header elevated class="bg-primary text-white" height-hint="98">
      <q-toolbar>
        <div class="col-12 col-md-10">
          <q-tabs align="left">
            <!--       navbar 아파트, 원룸 등-->
            <q-route-tab :to="{name:'marketPrice'}" label="실거래가 조회" />
            <q-route-tab :to="{ name: 'estateSale' }" label="매물" />
            <q-route-tab :to="{ name: 'memberBoard' }" label="게시판" />
          </q-tabs>
        </div>
        <div class="col-12 col-md-2 bk-menu-box">
          <div>
            <p>
              <router-link
                v-if="name == null || name === ''"
                :to="{ name: 'login' }"
                class="text-weight-medium text-white"
              >
                로그인
              </router-link>
              <router-link v-else :to="{ name: 'mypage' }" class="text-weight-medium text-white">
                마이페이지 {{ name }}
              </router-link>
            </p>
          </div>
        </div>
      </q-toolbar>
    </q-header>

    <!--    <q-page-container>-->
    <!--      <router-view />-->
    <!--    </q-page-container>-->
  </q-layout>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

@include a-deco-none;

.q-toolbar {
  height: 50px;
}

.bk-menu-box {
  height: 100% !important; // 나중에 수정
  //background-color: white;
  display: flex;
  justify-content: end;

  div {
    height: 100%;
    width: 88px;
    display: flex;
    align-items: center;

    &:hover {
    }

    p {
      font-size: 16px;
      width: 100%;
      margin: 0;
    }
  }
}

.bg-primary {
  background-color: $primary-color !important;
}
</style>

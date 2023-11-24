<script setup>
import { useMemberStore } from '@/stores/memberStore'
import { storeToRefs } from 'pinia'

const memberStore = useMemberStore()
const { loginInfo } = storeToRefs(memberStore)

let name = loginInfo?.value?.name
</script>

<template>
  <q-layout style="min-height: 50px">
    <q-header elevated class="bg-primary text-white" height-hint="98">
      <q-toolbar>
        <div class="col-12 col-md-10">
          <q-tabs align="left">
            <q-route-tab :to="{ name: 'estateBoard' }" label="매물 관리" />
            <q-route-tab :to="{ name: 'agentSchedule' }" label="일정 관리" />
          </q-tabs>
        </div>
        <div class="col-12 col-md-2 bk-menu-box">
          <div>
            <p></p>
          </div>
        </div>
      </q-toolbar>
    </q-header>
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

<script setup>
import AgentHeader from '@/components/AgentHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import agentAPI from '@/api/agent'
import { ref, provide, readonly } from 'vue'
import { RouterView } from 'vue-router'

const refresh = ref(0)
const changeRefresh = () => {
  refresh.value = new Date().getTime()
}

const agent = ref({})
const getAgent = () => {
  agentAPI.getAgent(
    1, // 나중에 로그인한 Agetn의 ID로 변경
    (data) => {
      agent.value = data.data
    },
    () => {
      console.log('매물 등록을 위한 중개사 조회에 실패하였습니다.')
    }
  )
}

getAgent()
provide('agent', agent)
provide('res', { refresh: readonly(refresh) })
provide('service', { changeRefresh })
</script>

<template>
  <!-- <default-header /> -->
  <agent-header />
  <router-view />
  <app-footer />
</template>

<style scoped lang="scss"></style>

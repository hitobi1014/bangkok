<script setup>
import AppButton from '@/components/AppButton.vue'
import { reactive, ref } from 'vue'
import AppLogo from '@/components/AppLogo.vue'
import memberAPI from '@/api/member'
import { useRouter } from 'vue-router'
import { useMemberStore } from '@/stores/memberStore'
import { storeToRefs } from 'pinia'

const router = useRouter()
const memberStore = useMemberStore()
const isPwd = ref(true)

const tab = ref('member')
const member = reactive({
  email: 'ssafy',
  emailDomain: 'naver.com',
  password: '1234'
})

const agent = reactive({
  email: '',
  emailDomain: '',
  password: ''
})

const login = () => {
  memberAPI.memberLogin(
    member,
    ({ data }) => {
      console.log(`로그인 성공 로그인멤버 : ${data.email}`)
      // 로그인 성공시 피니아에 현재 로그인 정보 저장
      memberStore.setMemberInfo(data)
      router.push({ name: 'memberMain' })
    },
    () => {
      console.log('로그인 실패')
    }
  )
}
</script>

<template>
  <!--  <div class="container">-->
  <div class="row justify-center bk-container">
    <div class="login-box self-center">
      <app-logo />
      <div class="row">
        <div class="col-2" />
        <div class="col-8 column">
          <div class="col-9 column">
            <div class="col-11 login-info column">
              <q-tabs
                v-model="tab"
                indicator-color="transparent"
                active-color="white"
                class="bg-teal text-grey-5 shadow-2 col-3"
              >
                <q-tab name="member" icon="account_circle" label="일반회원" />
                <q-tab name="agent" icon="real_estate_agent" label="사업자" />
              </q-tabs>
              <div class="col-9">
                <!--                탭 컴포넌트로 분리하기-->
                <q-tab-panels v-model="tab" animated class="login-panel">
                  <q-tab-panel name="member" class="column justify-evenly">
                    <div>
                      <q-input outlined v-model="member.email" label="이메일" />
                    </div>
                    <!--    패스워드-->
                    <div>
                      <q-input
                          outlined
                          v-model="member.password"
                          :type="isPwd ? 'password' : 'text'"
                          label="비밀번호"
                      >
                        <template v-slot:append>
                          <q-icon
                              :name="isPwd ? 'visibility_off' : 'visibility'"
                              class="cursor-pointer"
                              @click="isPwd = !isPwd"
                          />
                        </template>
                      </q-input>
                    </div>
<!--                    <div>-->
<!--                      <q-input outlined v-model="member.password" label="비밀번호" />-->
<!--                    </div>-->
                  </q-tab-panel>

                  <q-tab-panel name="agent" class="column justify-evenly">
                    <div class="">
                      <q-input outlined v-model="agent.email" label="이메일" />
                    </div>
                    <div class="">
                      <q-input outlined v-model="agent.password" label="비밀번호" />
                    </div>
                  </q-tab-panel>
                </q-tab-panels>
              </div>
            </div>
            <div class="col-1 row">
              <p class="col">
                <router-link to="" class="text-weight-medium text-black"
                  >아이디/비밀번호 찾기
                </router-link>
              </p>
              <p class="col text-right">
                <router-link :to="{ name: 'regist' }" class="text-weight-medium text-black">회원가입
                </router-link>
              </p>
            </div>
          </div>
          <div class="col-3 login-button-box">
            <AppButton btn-name="로그인" @click="login" />
          </div>
        </div>
        <div class="col-2" />
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

@include a-deco-none;

.q-tab-panel > div {
  margin-bottom: 8px;
}

.login-panel {
  height: 100%;
}

.login-box {
  height: 540px;
  width: 580px;
  border: 2px solid $border-color;
  border-radius: 8px;

  > div:nth-child(1) {
    height: calc(100% - 80%);
  }

  > div:nth-child(2) {
    height: 80%;
  }

  button {
    width: 200px;
  }
}

.login-info {
  border: 1px solid $border-color;
}

.login-button-box {
  display: flex;
  align-items: center;

  button {
    width: 100%;
  }
}
</style>
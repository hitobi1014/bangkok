<script setup>
import MemberLikeHomes from "@/components/member/mypage/MemberLikeHomes.vue";
import * as commonUtil from '@/assets/js/commonUtils'
import {useMemberStore} from "@/stores/memberStore";
import {storeToRefs} from "pinia";
import {ref} from "vue";

//피니아 회원정보
const memberStore = useMemberStore()
const {loginInfo} = storeToRefs(memberStore)
const mobile = commonUtil.formatMobile(loginInfo.value?.mobile)
const cnt = ref()

const getSalesCount = (count) => {
  cnt.value = count;
}

const loadMemberInfo = () => {
  loginInfo.value.fileDto = null ?? commonUtil.setDefaultImage('어피치.png')
}

loadMemberInfo()
</script>

<template>
  <div class="container">
    <div class="myinfo-top">
      <!--■■■■■ left start ■■■■■-->
      <div class="row">
        <!--이미지-->
        <div class="img-box">
          <div><img :src="loginInfo.fileDto"></div>
        </div>
        <!--회원정보-->
        <div class="q-ml-lg q-mt-lg">
          <h4>{{ loginInfo?.name }}님</h4>
          <p style="margin-top: 24px;">{{ loginInfo?.email }}@{{ loginInfo?.emailDomain }}</p>
          <p style="margin-top: 8px;">{{ mobile }}</p>
        </div>
      </div>
      <!--■■■■■ left end ■■■■■-->

      <!--■■■■■ right start ■■■■■-->
      <div>
        <!--관심매물-->
        <div class="right-box">
          <div>
            <p>{{ cnt }}건</p>
            <p>관심매물</p>
          </div>
        </div>
      </div>
      <!--■■■■■ right end ■■■■■-->
    </div>
  </div>
  <member-like-homes @salesCount="getSalesCount"/>
</template>

<style scoped lang="scss">
@import "@/assets/css/reuse-var";

p, h4 {
  margin: 0;
}

h4 {
  font-weight: 500;
}


.container {
  height: 200px;
  //padding: 40px 0;
  background: #fafafa;
  line-height: 1;
  padding-top: 24px;

  .myinfo-top {
    width: 1000px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    height: 100%;
  }
}

.img-box {
  display: flex;
  flex-direction: column;

  > div {
    width: 150px;
    height: 150px;
    border-radius: 70%;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  img {
    width: 80%;
  }
}

.right-box {
  p {
    font-size: 20px;
    font-weight: 500;
  }

  div {
    width: 159px;
    height: 144px;
    border: 1px solid $border-color;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

}
</style>
import { defineStore } from "pinia";
import { reactive, ref } from "vue";

export const useMemberStore  = defineStore('member', () => {
  const loginInfo = ref(null)

  function setMemberInfo(data) {
    console.log(`setMemberInfo`)
    loginInfo.value = data;
    console.log(`피니아 로그인 정보 : ${loginInfo.value}`);
  }

  return {loginInfo, setMemberInfo}
})
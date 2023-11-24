<script setup>
import {reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import {storeToRefs} from "pinia";
import {useMemberStore} from "@/stores/memberStore";

const memberStore = useMemberStore()
const {loginInfo} = storeToRefs(memberStore)

const router = useRouter()

const isPwd = ref(true)

const memberInfo = ref()
const emailWithDomain = ref()

// const registInfo = reactive({
//   emailWithDomain: '',
//   password: '',
//   name: '',
//   mobile: '',
//   img: ''
// })

const submitForm = () => {
  console.log('submitForm')
  // memberAPI.joinMember(
  //   registInfo,
  //   () => {
  //     console.log('가입 성공')
  //     router.push({ name: 'memberMain' })
  //   },
  //   () => {
  //     console.log('가입 실패')
  //   }
  // )
}

const cancel = () => {
  router.push({name: ''}) // 마이페이지 메인으로 이동하기
}

const fileSelect = async (files) => {
  console.log(files);
  const fileName = files[0]
  await base64(fileName)
}


const base64 = (file) => {
  return new Promise((resolve) => {
    let a = new FileReader()
    a.onload = (e) => {
      resolve(e.target.result)
      const previewImage = document.getElementById('preview')
      previewImage.src = e.target.result
    }

    a.readAsDataURL(file)
  })
}


const imagePath = ref(); // 회원이미지
const loadMemberData = () => {
  memberInfo.value = loginInfo?.value
  if (loginInfo?.value) {
    emailWithDomain.value = `${loginInfo.value.email}@${loginInfo.value.emailDomain}`
  }
  console.log(`회원 정보 : ${JSON.stringify(memberInfo.value)}`)

  imagePath.value = null ?? `basicImg.svg`
}



const setDefaultImage = (name) => {
  console.log(`setDefaultImage name => ${name}`)
  const path = `/src/assets/image/${name}`
  console.log(path)
  return new URL(path, import.meta.url).href;
}

loadMemberData()
</script>

<template>
  <div class="modify-container">
    <div class="text-center">
      <span>정보 수정</span>
    </div>
    <form @submit.prevent="submitForm" class="row justify-center">
      <div class="info-div">
        <!--이미지-->
        <div class="row justify-evenly">
          <div class="img-box">
            <img :src="setDefaultImage(imagePath)" id="preview" />
          </div>
          <div class="column justify-center">
            <div>
              <input
                  type="file"
                  id="upload-image"
                  hidden
                  @change="fileSelect($event.target.files)"
              />
              <label for="upload-image" class="upload-img"> 사진선택 </label>
            </div>
            <div>
              <span style="font-size: 12px">사이즈: 145x145px / 파일크기: 1MB 이하</span>
            </div>
          </div>
        </div>
        <!-- ■■■■■ input ■■■■■-->
        <!--    이메일-->
        <div>
          <q-input outlined v-model="emailWithDomain" readonly label="이메일"/>
        </div>

        <!--    닉네임-->
        <div>
          <q-input outlined v-model="memberInfo.name" label="닉네임" type="text"/>
        </div>
        <!--   휴대전화 -->
        <div>
          <q-input outlined v-model="memberInfo.mobile" label="휴대전화" type="tel"/>
        </div>
        <!--      input 종료  -->
        <!-- 버튼 box -->
      </div>
    </form>
    <div class="row justify-center button-box">
      <q-btn color="white" label="취소" @click="cancel" class="text-weight-medium text-black"/>
      <q-btn color="secondary" label="수정" type="submit" class="text-weight-medium"/>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

.modify-container {
  min-height: 600px;
  width: 650px;
  margin: 0 auto;
  padding: 40px 0 60px;

  > div:nth-child(1) {
    font-size: 32px;
    margin-bottom: 20px;
  }
}

.q-input {
  margin-bottom: 12px;
}

.info-div {
  width: 65%;

  > div:nth-child(1) {
    margin-bottom: 10px;
  }
}

.upload-img {
  &:hover {
    cursor: pointer;
  }

  background: #fff;
  border-radius: 7px;
  border: 1px solid;
  padding: 4px 16px;
  min-height: 2.572em;
  color: #636363;
  font-weight: 600;
}

.button-box {
  @include border-solid-gray;
  padding: 40px 30px 40px;
  border-top: 0px;
}

.img-box {
  width: 150px;
  height: 150px;
}

.img-button {
  width: 90px;
  height: 32px;
}

img {
  width: 145px;
  height: 145px;
}

form {
  border: 1px solid $border-color;
  padding: 40px 30px 40px;
  //border-radius: 8px;
}

div > button {
  margin-right: 15px;
  width: 120px;
  height: 45px;
}
</style>
<script setup>
import AppLogo from '@/components/AppLogo.vue'
import {reactive, ref} from 'vue'
import memberAPI from '@/api/member'
import {useRouter} from 'vue-router'

const isPwd = ref(true)
const registInfo = reactive({
  email: '',
  emailDomain: '',
  password: '',
  name: '',
  mobile: '',
  file: ''
})

const router = useRouter()

const submitForm = () => {
  const formData = new FormData();
  const memberInfo = {
    email:registInfo.email,
    emailDomain:registInfo.emailDomain,
    password:registInfo.password,
    name:registInfo.name,
    mobile:registInfo.mobile
  }

  memberAPI.joinMember(
      dataConvert(),
      () => {
        console.log('가입 성공')
        router.push({name: 'memberMain'})
      },
      () => {
        console.log('가입 실패')
      }
  )
}

const dataConvert = () => {
  const formData = new FormData();
  const memberInfo = {
    email:registInfo.email,
    emailDomain:registInfo.emailDomain,
    password:registInfo.password,
    name:registInfo.name,
    mobile:registInfo.mobile
  }

  const json = JSON.stringify(memberInfo)
  const blob = new Blob([json], {type: "application/json"});

  // console.log(`받은 blob : ${blob}`)
  formData.append('member', blob)
  formData.append('file', registInfo.file)

  return formData
}

// 이미지 업로드
const fileSelect = async (files) => {
  // console.log(files);
  const file = files[0]
  registInfo.file = file
  // console.log(`fileName : ${file.name}`)
  await base64(file)
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

</script>

<template>
  <div class="bk-container row justify-center">
    <div class="registinfo-box">
      <div>
        <app-logo/>
        <p class="text-weight-medium text-center">회원가입</p>
      </div>
      <div class="column items-center">
        <form @submit.prevent="submitForm" enctype="multipart/form-data">
          <div class="info-div">
            <!--      input 시작  -->
            <!--    이메일-->
            <!--#01이미지-->
            <div class="img-content">
              <div class="img-box">
                <img src="" id="preview" alt="이미지를 선택해주세요"/>
              </div>
              <div class="column justify-center">
                <div>
                  <span>{{registInfo.file}}</span>
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
            <div>
              <q-input outlined v-model="registInfo.email" label="이메일"/>
              <q-input outlined v-model="registInfo.emailDomain" label="이메일도메인"/>
            </div>
            <!--    패스워드-->
            <div>
              <q-input
                  outlined
                  v-model="registInfo.password"
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
              <!--            <q-input outlined v-model="registInfo.password" label="비밀번호" />-->
            </div>
            <!--    닉네임-->
            <div>
              <q-input outlined v-model="registInfo.name" label="닉네임"/>
            </div>
            <!--   휴대전화 -->
            <div>
              <q-input outlined v-model="registInfo.mobile" label="휴대전화"/>
            </div>

            <!--      input 종료  -->
            <q-btn color="secondary" label="가입하기" type="submit"/>
          </div>
        </form>
      </div>
    </div>
  </div>

</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

.registinfo-box {
  width: 880px;
  margin-top: 88px;

  > div > p {
    font-size: 36px;
  }
}

.info-div {
  width: 380px;

  .q-input {
    margin-bottom: 12px;
  }
}

// 사진 input
.img-content {
  width: 100%;
  height: 150px;
  display: flex;
  margin-bottom: 10px;

  .img-box {
    @include border-solid-gray;
    margin-right: 10px;

    > img {
      width: 145px;
      height: 145px;
    }
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
</style>

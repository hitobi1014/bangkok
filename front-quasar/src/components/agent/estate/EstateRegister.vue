<script setup>
import { reactive, inject } from 'vue'
import { useRouter } from 'vue-router'
import estateAPI from '@/api/estate'
import EstateNameSearch from '@/components/agent/estate/EstateNameSearch.vue'

const router = useRouter()
const { changeRefresh } = inject('service')
const agent = inject('agent')
const estate = reactive({
  title: '',
  estateSalesType: '',
  content: '',
  salesPrice: '',
  maintenanceCost: '',
  houseInfo: '',
  img: '',
  agent: ''
})
const options = [
  { label: '원룸', value: 'ONE' },
  { label: '투룸', value: 'TWO' },
  { label: '아파트', value: 'APT' },
  { label: '빌라', value: 'VILLA' },
  { label: '주택', value: 'HOUSING' },
  { label: '상가', value: 'SHOPPING_MALL' }
]

const resultSelected = (selectedResult) => {
  estate.houseInfo = selectedResult
}

const submit = () => {
  estateAPI.registEstate(
    dataConvert(),
    () => {
      router.push('/agent/estate-board').then(() => changeRefresh())
      changeRefresh('register')
    },
    () => {
      console.log('등록에 실패하였습니다.')
    }
  )
}

const reset = () => {
  router.push('/agent/estate-board').then(() => changeRefresh())
}

// 데이터 변환
const dataConvert = () => {
  const formData = new FormData()
  const estateInfo = {
    title: estate.title,
    estateSalesType: estate.estateSalesType.value,
    content: estate.content,
    salesPrice: estate.salesPrice,
    maintenanceCost: estate.maintenanceCost,
    houseInfo: estate.houseInfo,
    agent: agent.value
  }

  const json = JSON.stringify(estateInfo)
  const blob = new Blob([json], { type: 'application/json' })

  formData.append('estate', blob)
  formData.append('file', estate.img)

  return formData
}

// 이미지 업로드
const fileSelect = async (files) => {
  const file = files[0]
  estate.img = file
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
  <div class="registinfo-box">
    <q-form @submit.prevent>
      <q-card>
        <q-card-section class="custom-card-section">
          <h2 class="text-h6 text-center ">매물 등록</h2>
        </q-card-section>
        <q-card-section>
          <q-input outlined v-model.lazy="estate.title" label="제목" dense clearable />
        </q-card-section>
        
        <q-card-section>
          <estate-name-search @resultSelected="resultSelected" />
        </q-card-section>

        <q-card-section>
          <q-select
            outlined
            v-model="estate.estateSalesType"
            label="매물유형"
            dense
            clearable
            :options="options"
            option-label="label"
            option-value="value"
          />
        </q-card-section>

        <q-card-section>
          <div class="img-content">
            <div class="img-box">
              <img src="" id="preview" alt="이미지를 선택해주세요" />
            </div>
            <div class="column justify-center">
              <div>
                <span>{{ estate.img }}</span>
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
        </q-card-section>

        <q-card-section>
          <q-input outlined v-model.lazy="estate.salesPrice" label="매매가" dense clearable />
        </q-card-section>

        <q-card-section>
          <q-input outlined v-model.lazy="estate.maintenanceCost" label="관리비" dense clearable />
        </q-card-section>

        <q-card-section>
          <q-input outlined v-model.lazy="estate.content" label="메모" dense clearable />
        </q-card-section>

        <q-card-section style="display: flex; justify-content: space-between;">
          <q-btn type="submit" style="background-color: #027979; color: white" label="매물 등록" class="q-mr-md" @click="submit" />
          <q-btn type="reset" style="background-color: #027979; color: white" label="등록 취소" class="q-ml-md" @click="reset" />
        </q-card-section>
      </q-card>
    </q-form>
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';
.registinfo-box {
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
}

.custom-card-section {
  width: 350px;
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

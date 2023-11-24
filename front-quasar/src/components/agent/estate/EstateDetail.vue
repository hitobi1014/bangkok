<script setup>
import { ref, watch, inject, watchEffect } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import estateAPI from '@/api/estate'

const router = useRouter()
const route = useRoute()
const { changeRefresh } = inject('service')
const estate = ref({})
const src = ref('')
const options = [
  { label: '원룸', value: 'ONE' },
  { label: '투룸', value: 'TWO' },
  { label: '아파트', value: 'APT' },
  { label: '빌라', value: 'VILLA' },
  { label: '주택', value: 'HOUSING' },
  { label: '상가', value: 'SHOPPING_MALL' }
]

const selectedSalesTypeLabel = ref('')

watchEffect(() => {
  const selectedOption = options.find(option => option.value === estate.value.estateSalesType)
  selectedSalesTypeLabel.value = selectedOption ? selectedOption.label : ''
})

const getEstate = () => {
  estateAPI.getEstate(
    route.params.id,
    (data) => {
      estate.value = data.data
      src.value = 'http://localhost:8080/' + estate.value.file.realPath+ '/' + estate.value.file.saveFileName
      console.log(src.value)
    },
    () => {
      console.log('매물 조회에 실패하였습니다.')
    }
  )
}

const modify = () => {
  estateAPI.modifyEstate(
    estate.value,
    () => {
      router.push('/agent/estate-board').then(() => changeRefresh())
    },
    () => {
      console.log('매물 수정에 실패하였습니다.')
    }
  )
}

const remove = () => {
  estateAPI.removeEstate(
    estate.value.id,
    () => {
      router.push('/agent/estate-board').then(() => changeRefresh())
    },
    () => {
      console.log('매물 삭제에 실패하였습니다.')
    }
  )
}

watch(
  () => route.params.id,
  () => getEstate()
)

getEstate()
</script>

<template>
  <div class="registinfo-box">
    <q-form @submit="modify" @reset="remove">
      <q-card>
        <q-card-section class="custom-card-section">
          <h2 class="text-h6 text-center ">매물 조회</h2>
        </q-card-section>

        <q-card-section>
          <q-input outlined v-model.lazy="estate.title" label="제목" dense clearable />
        </q-card-section>

        <q-card-section>
          <q-input outlined v-model.lazy="selectedSalesTypeLabel" label="매물 유형" dense clearable />
        </q-card-section>

        <q-card-section>
          <div class="img-content">
            <div class="img-box">
              <img :src="src" id="preview" alt="이미지를 선택해주세요" />
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
          <q-btn type="modify" style="background-color: #027979; color: white" label="내용 수정" class="q-mr-md" />
          <q-btn type="reset" style="background-color: #027979; color: white" label="매물 삭제" class="q-ml-md" />
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

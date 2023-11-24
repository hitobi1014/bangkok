<script setup>
import { ref, watch, inject } from 'vue'
import estateAPI from '@/api/estate'
import { useRouter } from 'vue-router'

const router = useRouter()
const search = ref('')
const estates = ref([])
const selectedCondition = ref('제목+내용')
const { refresh } = inject('res')

const pickEstate = (id) => {
  router.push(`/agent/detail/${id}`)
}

const submit = () => {
  router.push(`/agent/register/`)
}

const getEstates = () => {
  estateAPI.getEstates(
    (data) => {
      console.log(data.data)
      estates.value = data.data.filter((estate) => {
        switch (selectedCondition.value) {
          case '제목+내용':
            return (
              !search.value ||
              estate.title.includes(search.value) ||
              estate.content.includes(search.value)
            )
          case '동':
            return !search.value || estate.houseInfo.dong.includes(search.value)
          case '아파트명':
            return !search.value || estate.houseInfo.apartmentName.includes(search.value)
          default:
            return true
        }
      })
    },
    () => {
      console.log('매물 목록 조회에 실패하였습니다.')
    }
  )
}
getEstates()

watch([search, refresh], () => {
  getEstates()
})

watch(selectedCondition, () => {
  search.value = ''
})

const areas = [15, 15, 20, 12, 18, 20, 24, 25, 32, 48]

const columns = [
  { name: 'dong', label: '동', field: (row) => row.houseInfo.dong, align: 'center', dense: true },
  {
    name: 'address',
    label: '주소',
    field: (row) =>
      row.houseInfo.dongSearch.sidoName +
      ' ' +
      row.houseInfo.dongSearch.gugunName +
      ' ' +
      row.houseInfo.roadName,
    align: 'center',
    dense: true
  },
  {
    name: 'estateSalesType',
    label: '매물유형',
    field: (row) => mapEstateSalesType(row.estateSalesType),
    align: 'center',
    dense: true
  },
  {
    label: '면적',
    field: (row, index) => {
      const areaIndex = index < areas.length ? index : 0
      const area = areas[areaIndex]
      return `${area} 평`
    },
    align: 'center',
    dense: true
  },
  { name: 'agent', label: '담당자', field: (row) => row.agent.name, align: 'center', dense: true },
  { name: 'title', label: '제목', field: 'title', align: 'center', dense: true },
  { name: 'content', label: '메모', field: 'content', align: 'center', dense: true },
  {
    name: 'salesPrice',
    label: '가격',
    field: (row) => `${row.salesPrice} 만원`,
    align: 'center',
    dense: true,
    sortable: true
  },
  {
    name: 'maintenanceCost',
    label: '관리비',
    field: (row) => `${row.maintenanceCost} 만원`,
    align: 'center',
    dense: true,
    sortable: true
  },
  {
    name: 'apartmentValue',
    label: '아파트명',
    field: (row) => row.houseInfo.apartmentName,
    align: 'center',
    dense: true
  }
]

const mapEstateSalesType = (type) => {
  switch (type) {
    case 'ONE':
      return '원룸'
    case 'TWO':
      return '투룸'
    case 'APT':
      return '아파트'
    case 'VILLA':
      return '빌라'
    case 'HOUSING':
      return '주택'
    case 'SHOPPING_MALL':
      return '상가'
    default:
      return ''
  }
}
const separator = ref('cell')
</script>

<template>
  <div class="map-container absolute-top-left">
    <div class="relative-position full-height">
      <!--### 검색바 ###-->
      <div class="map-top absolute-top-left">
        <div class="row top-content">
          <!--#01 SEARCH-->
          <div class="row full-height">
            <!--#01-01 INPUT-->
            <div class="row position-height-100">
              <q-select
                v-model="selectedCondition"
                :options="['제목+내용', '동', '아파트명']"
                label="검색 조건"
                outlined
                dense
                style="width: 200px"
              />
            </div>
            <div class="row q-ml-xl select-list">
              <q-input
                v-model="search"
                label="검색"
                lazy-rules
                :rules="[(val) => !!val || '검색어를 입력하세요.']"
                outlined
                dense
              >
                <template v-slot:prepend>
                  <q-icon name="search" />
                </template>
              </q-input>
            </div>
          </div>
          <div class="row q-mr-xl submit-btn">
            <q-btn
              type="submit"
              style="background-color: #027979; color: white"
              label="신규 매물 추가"
              class="q-mr-md"
              @click="submit()"
            />
          </div>
        </div>
        <div>
          <q-table
            flat
            bordered
            :title="'등록 매물'"
            :rows="estates"
            :columns="columns"
            row-key="(row) => row.id"
            :pagination="true"
            @row-click="(event, item, index) => pickEstate(item.id)"
            :separator="separator"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import '@/assets/css/reuse-var';

@include a-deco-none;
@include list-none;

$main-header-height: 50px;
$left-basic-width: 490px;
$map-top-height: 60px;

.position-height-100 {
  position: relative;
  height: 100%;
}

.submit-btn {
  height: 100%;
  display: flex;
  align-items: center;
}

.map-container {
  padding-top: 50px;
  @include full-w-h;
}

.top-content {
  @include full-w-h;
  display: flex;
  justify-content: space-between;
  padding: 12px 30px 12px 20px;
}

.select-list {
  height: 100%;

  &:not(:first-child) label {
    margin-left: 6px;
  }
  > label {
    &:nth-child(1),
    &:nth-child(2) {
      width: 120px;
    }
    &:nth-child(3),
    &:nth-child(4) {
      width: 90px;
    }
    &:last-child {
      width: 150px;
    }
  }
}
// 검색창
.map-top {
  position: absolute;
  z-index: 1;
  width: 100%;
  height: 60px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
</style>

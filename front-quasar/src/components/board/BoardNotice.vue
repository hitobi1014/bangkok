<script setup>
import { computed, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import boardAPI from '@/api/board'

const route = useRoute()

// const tableTitle = ref(route.params.cateName)
// const path = ref(route.params.catePath)

//### axios ###
// TODO: 게시판 server 데이터 연동하기
const getBoardList = (path) => {
  console.log(`getBoard Path : ${path.toUpperCase()}`)
  boardAPI.getBoardList(
    path.toUpperCase(),
    ({ data }) => {
      rows.value = data
      console.log(`성공 데이터 : ${JSON.stringify(data)}`)
    },
    () => {
      console.log(`목록 가져오기 실패`)
    }
  )
}

const his = history.state
const tableTitle = ref(his.name)
const path = ref(his.path)
console.log(`title: ${tableTitle.value} \t path : ${path.value}`)
getBoardList(path.value)

watch(route, () => {
  // tableTitle.value = route.params.cateName
  // console.log(`이름 : ${route.params.cateName} 경로 : ${route.params.catePath}`);
  console.log(`받은 : his ${history.state.name} path : ${history.state.path}`)
  tableTitle.value = history.state.name
  getBoardList(history.state.path)
})

const columns = [
  {
    name: 'id',
    required: true,
    label: '글 번호',
    align: 'center',
    field: (row) => row.id,
    sortable: true
  },
  { name: 'title', label: '제목', field: 'title', align: 'left' },
  { name: 'memberName', label: '작성자', field: 'memberName', align: 'center' },
  { name: 'hit', label: '조회수', field: 'hit', align: 'center' }
]

const arrayCnt = 33
// const rows = Array(arrayCnt).fill()
//   .map((_,i) => ({
//     boardId: String(i+1),
//     title: `글 제목${i+1}`,
//     memberName: `작성자${i+1}`,
//     hit: String(i+1)
//   }));

const rows = ref()

const pagination = ref({
  sortBy: 'desc',
  descending: false,
  page: 1,
  rowsPerPage: 10
  // rowsNumber: xx if getting data from a server
})

const pagesNumber = computed(() => Math.ceil(rows.length / pagination.value.rowsPerPage))
const current = ref(3)
</script>

<template>
  <div class="q-pa-none q-mt-xl">
    <q-table
      flat
      bordered
      :title="tableTitle"
      :rows="rows"
      :columns="columns"
      :row-key="(row) => row.id"
      v-model:pagination="pagination"
      hide-pagination
    />

    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="pagination.page"
        :max="pagesNumber"
        direction-links
        boundary-links
        icon-first="skip_previous"
        icon-last="skip_next"
        icon-prev="fast_rewind"
        icon-next="fast_forward"
      />
    </div>
  </div>
  <!--          v-model="current"-->
  <!--          :max="5"-->

  <!--      <div class="row justify-center q-mt-md">-->
  <!--        <q-pagination-->
  <!--          v-model="pagination.page"-->
  <!--          color="grey-8"-->
  <!--          :max="pagesNumber"-->
  <!--          size="sm"-->
  <!--        />-->
  <!--      </div>-->
</template>

<style scoped lang="scss">
.q-pa-md {
  margin-top: 40px;
}
</style>

<script setup>
import { ref, defineEmits } from 'vue'
import { QDialog } from 'quasar'
import estateAPI from '@/api/estate'

const search = ref('')
const open = ref(false)
const searchResults = ref([])

const emits = defineEmits()

const handleSearch = () => {
  if (search.value !== '') {
    estateAPI.getEstatesWithName(
      search.value,
      (data) => {
        searchResults.value = data.data
        open.value = true
      },
      () => {
        console.log('이름 조회에 실패하였습니다.')
      }
    )
  } else {
    searchResults.value = []
  }
}

const selectResult = (result) => {
  emits('resultSelected', result)
  search.value = result.apartmentName
  open.value = false
}
</script>

<template>
  <div>
    <q-input outlined dense v-model="search" label="동이나 아파트명을 입력하세요" lazy-rules>
      <template v-slot:append>
        <q-icon name="search" @click="handleSearch" />
      </template>
    </q-input>


    <q-dialog v-model="open">
      <q-card>
        <q-card-section>
          <div class="text-h6 text-center">검색 결과</div>
        </q-card-section>

        <q-card-section v-if="searchResults.length > 0">
          <q-list dense padding style="width: 300px; max-height: 200px; overflow-y: auto;">
            <q-item v-for="(result, index) in searchResults" :key="result.id">
              <q-item-label @click="selectResult(result)"> {{ result.dong }} {{ result.apartmentName }} </q-item-label>
            </q-item>
          </q-list>
        </q-card-section>

        <q-card-section v-else>
          <div class="text-h6 text-center">검색 결과가 없습니다.</div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn label="닫기" color="primary" @click="open=false" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<style scoped></style>

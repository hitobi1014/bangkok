<script setup>
import {reactive, ref} from "vue";
import {useMemberStore} from "@/stores/memberStore";
import {storeToRefs} from "pinia";
import memberAPI from "@/api/member"
import * as commonUtil from '@/assets/js/commonUtils'

const memberStore = useMemberStore()
const {loginInfo} = storeToRefs(memberStore)

const emits = defineEmits()

const estateSales = reactive({
  cost: '매매 2억 1500만',
  type: '투룸',
  address: '테헤란로 12길 5-3',
  title: '급하게 나온 매물!',
  hashtag: [
    {text: '#역세권'},
    {text: '#좋아요'}
  ]
})


const memberInfo = ref()
const loadMemberData = () => {
  memberInfo.value = loginInfo?.value
  if (memberInfo.value != null) {
    getLikeHomes(memberInfo.value)
  }
}

const imagePath = ref()
const setDefaultImage = (name) => {
  console.log(`setDefaultImage name => ${name}`)
  let path = ''
  if (name == null) {
    path = `/src/assets/image/${name}`
  } else {
    path = `http://localhost:8080/${name}`
  }

  console.log(`이미지 풀 경로 => :${path}`)
  // imagePath.value = 'http://localhost:8080/' + estate.value.file.realPath+ '/' + estate.value.file.saveFileName
  return new URL(path, import.meta.url).href;
}

// ■■■■■ axios start ■■■■■
const likeHomes = ref()
const getLikeHomes = (member) => {
  memberAPI.getLikeHomes(member,
      ({data}) => {
        likeHomes.value = data
        likeHomes.value.data.forEach(item => {
          item.estateSalesType = commonUtil.convertHouseType(item.estateSalesType)
          item.salesPrice = commonUtil.compactToMoneyUnit(item.salesPrice)
          // item.img = null ?? commonUtil.setDefaultImage('apt2.jpg')
          item.imagePath = `${item.fileDto.realPath}/${item.fileDto.saveFileName}`
        })
        console.log(`getLikeHomes 성공 ${JSON.stringify(data)}`)

        emits('salesCount', likeHomes.value.count)
      },
      (error) => {
        console.error(`에러발생 :${error}`)
      }
  )
}
// ■■■■■ axios end ■■■■■

// setup
loadMemberData()
</script>

<template>
  <div class="sale-list-container">
    <div>
      <div>
        <h4>관심매물</h4>
      </div>
      <div>
        <hr>
      </div>
    </div>
    <div class="row estate-container">
      <!--  매물리스트 -->
      <template v-for="homes in likeHomes?.data" :key="homes.id">
        <router-link :to="{name:'estateSaleDetail', params:{estateno:homes.esateSalesId}}">
          <div class="estate-box">
            <!--### 이미지 ###-->
            <div class="img-box">
              <img :src="setDefaultImage(homes.imagePath)" id="preview" />
<!--              <img :src="homes.img">-->
            </div>
            <!--### content ###-->
            <div class="content-box">
              <!--가격-->
              <div>
                <p class="estate-cost text-weight-medium">{{ homes.salesPrice }}</p>
              </div>
              <!--부동산타입 ex) 원룸, 투룸, 아파트, 단독-->
              <div>
                <p class="estate-type text-weight-medium">{{ homes.estateSalesType }}</p>
              </div>
              <!--위치정보-->
              <div>
                <p class="text-weight-medium">{{ homes.houseInfo.roadName }} <br> {{ homes.houseInfo.apartmentName }}
                </p>
              </div>
              <!--제목-->
              <div>
                <p class="text-weight-medium">{{ homes.title }}</p>
              </div>
              <!--해시태그-->
              <div class="q-my-md">
          <span v-for="tag in estateSales.hashtag" :key="tag.text" class="estate-hashtag text-weight-medium">
            {{ tag.text }}
          </span>
              </div>
            </div>
          </div>
        </router-link>
      </template>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "@/assets/css/reuse-var";

@include a-deco-none;
a {
  color: $grey-8;
}

p {
  margin: 0;
}

h4{
  margin: 0;
  font-size: 1.55rem;
}

.estate-container {
  column-gap: 80px;
  row-gap: 15px;
}

.estate-cost {
  font-size: 28px;
  color: #027979;
}

.estate-type {
  color: #027979b5
}

.estate-hashtag {
  display: inline-block;
  height: 24px;
  padding: 5px 6px 0;
  margin-left: 4px;
  background: #eeeeee;
  border-radius: 12px;
  color: #888;
  font-size: 13px;
  line-height: 13px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: center;
  letter-spacing: 0;
}

.sale-list-container {
  @include width-1000-layout
}

.estate-box {
  width: 280px;
  border: 1px solid $border-color;
}

.img-box {
  width: 100%;
  height: 200px;

  img {
    width: 100%;
    height: 100%;
  }
}

.content-box {
  padding: 20px 20px 0;

  > div {
    margin-bottom: 4px;
  }
}

</style>
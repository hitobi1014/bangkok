<script setup>
import {onBeforeUnmount, onMounted, reactive, ref, toRaw} from 'vue'
import {chevronLeft, chevronRight} from '@/assets/svg/svg-list'
import estateAPI from '@/api/estate'
import memberAPI from '@/api/member'
import * as commonUtils from '@/assets/js/commonUtils'
import {useMemberStore} from "@/stores/memberStore";
import {storeToRefs} from "pinia";

const memberStore = useMemberStore()
const {loginInfo} = storeToRefs(memberStore)

const key = import.meta.env.VITE_KAKAO_API_KEY
const kakaoMapURL = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${key}&libraries=services,clusterer,drawing`
const script = document.createElement('script')
const map = ref(null)

// 최초 로딩시 위도 경도 설정
const lat = ref(37.500613)
const lng = ref(127.036431)
const mapCenter = reactive({
  centerLat: 0,
  centerLng: 0
})

// svg
const arrowSvg = ref(chevronLeft)

// 카카오맵 시작###
const loadScript = () => {
  script.src = kakaoMapURL
  script.onload = () => kakao.maps.load(() => loadMap(lat.value, lng.value))
  document.head.appendChild(script)
}

const loadMap = (getLat, getLng) => {
  const container = document.getElementById('map')
  const options = {
    center: new kakao.maps.LatLng(getLat, getLng),
    level: 3
  }
  map.value = new kakao.maps.Map(container, options)

  kakao.maps.event.addListener(toRaw(map.value) , 'dragend', function() {
    console.log(`kakao.maps.event.addListener`)
    changeModel()
  });
}

// 카카오맵 끝###

const toggle = ref(false)
const hashtags = ref(['역세권', '로얄층', '풀옵션', '붕세권', '테스트33333', '테스트33'])

// ### 이벤트 핸들러 ###

const toggleMapLeft = () => {
  getMapInfo()
  const mapContent = document.querySelector('.map-content')
  const arrow = document.querySelector('#arrow-svg')

  if (!toggle.value) {
    arrowSvg.value = chevronRight
    mapContent.classList.toggle('close')
  } else {
    arrowSvg.value = chevronLeft
    mapContent.classList.remove('close')
  }

  toggle.value = !toggle.value
  loadMap(mapCenter.centerLat, mapCenter.centerLng)
}

// 카카오맵 정보
// const swNeLatLng = ref() //남서, 북동 좌표
let paramSwLatLng = []
let paramNeLatLng = []
const getMapInfo = () => {
  // 지도의 현재 중심좌표를 얻어옵니다
  let center = map.value.getCenter()
  mapCenter.centerLat = center.getLat()
  mapCenter.centerLng = center.getLng()
  // 지도의 현재 영역을 얻어옵니다
  let bounds = map.value.getBounds();
  // 영역의 남서쪽 좌표를 얻어옵니다
  let swLatLng = bounds.getSouthWest();
  // 영역의 북동쪽 좌표를 얻어옵니다
  let neLatLng = bounds.getNorthEast();

  paramSwLatLng = [swLatLng.getLat(), swLatLng.getLng()]
  paramNeLatLng = [neLatLng.getLat(), neLatLng.getLng()]
  console.log(`lat => ${swLatLng.getLat()} \t lng => ${swLatLng.getLng()}`)
  // 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
}

//TODO:map-left 목록 새로 불러올때마다 해시태그 설정 수행
const hashTagMoreSet = () => {
  // 모든 li_build 클래스 요소 중 sa 클래스를 가지지 않은 요소에 대해 반복
  document.querySelectorAll('.li_build:not(.sa)').forEach(function (element) {
    let _width = 0
    // 각 요소 내의 hashtag 클래스 내 span 요소에 대해 반복
    element.querySelectorAll('.hashtag span').forEach(function (span) {
      _width += span.offsetWidth // 각 span 요소의 너비를 더함
      if (span.parentElement.offsetWidth - 155 < _width) {
        if (span.parentElement.querySelector('.more')) {
          span.remove()
        } else {
          // span 요소에 more 클래스 추가하고 내용을 '…'로 변경
          span.classList.add('more')
          span.textContent = '…'
        }
      }
    })
  })
  // 모든 li_build 클래스 요소에 sa 클래스 추가
  document.querySelectorAll('.li_build').forEach(function (element) {
    element.classList.add('sa')
  })
}

// #01 SEARCH
// #01-01 INPUT 검색
const searchKeyword = ref(null)

// #01-02 SELECT | 01:매물종류 02:거래종류 03:가격 04:면적 05:사용승인연도
const options01 = ref([
  {label: '전체', value: 'ALL'},
  {label: '아파트', value: 'APT'},
  {label: '주택', value: 'HOUSING'},
  {label: '원룸', value: 'ONE'},
  {label: '투룸', value: 'TWO'},
])
const options02 = ref([
  {label: '전체', value: 'ALL'},
  {label: '월세', value: 'RENT'},
  {label: '전세', value: 'LEASE'},
  {label: '매매', value: 'SALE'},
])
const options03 = ref([
  {label: '~1천', value: 0.1},
  {label: '1천', value: 1},
  {label: '2천', value: 2},
  {label: '3천', value: 3},
  {label: '4천', value: 4},
])
const options04 = ref([
  {label: '~33m²', value: '0'},
  {label: '33m²', value: '33'},
  {label: '66m²', value: '66'},
  {label: '99m²', value: '99'},
  {label: '126m²', value: '126'},
])
const options05 = ref([
  {label: '전체', value: 'ALL'},
  {label: '1년이하', value: '1'},
  {label: '3년이하', value: '3'},
  {label: '5년이하', value: '5'},
  {label: '10년이하', value: '10'},
  {label: '15년이하', value: '15'},
  {label: '20년이하', value: '20'},
])

const selectModel01 = ref(null)
const selectModel02 = ref(null)
const selectModel03 = ref(null)
const selectModel04 = ref(null)
const selectModel05 = ref(null)

const dense = ref(true)

// 해당
onMounted(() => {
  console.log(`onMouned`)

  if (window.kakao && window.kakao.maps) {
    loadMap(lat.value, lng.value)
  } else {
    loadScript()
  }
  document.querySelector('.ww').classList.toggle('open')

  // hashTagMoreSet()
})

// ■■■■■ function start ■■■■■
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

// ■■■■■ function end ■■■■■


// ■■■■■ event start ■■■■■
const selectKeyword = ref()
const changeModel = (data, label) => {
  console.log(`changeModel data => ${JSON.stringify(data)} label => ${label}`)

  getMapInfo()

  selectKeyword.value = {
    estateSalesType: selectModel01.value?.value,
    transactionType: selectModel02.value?.value,
    salesPrice: selectModel03.value?.value,
    area: selectModel04.value?.value,
    year: selectModel05.value?.value,
    swLatLng: paramSwLatLng,
    neLatLng: paramNeLatLng
  }

  searchSelect(selectKeyword.value)
}
// ■■■■■ event end ■■■■■

//TODO:데이터 연결 및 axios 처리

// ■■■■■ axios start ■■■■■
/**
 * select 검색종류 별 axios
 */
const searchSelect = (param) => {
  estateAPI.getSelectSearch(
      param,
      ({data}) => {
        console.log(`받아온 데이터 => ${JSON.stringify(data)}`)
        estateSales.value = data
        estateSales.value.forEach(item => {
          item.transactionType = commonUtils.convertHouseTranscationType(item.transactionType)
          item.estateSalesType = commonUtils.convertHouseType(item.estateSalesType)
          item.salesPrice = commonUtils.compactToMoneyUnit(item.salesPrice)
          item.imagePath = `${item.file.realPath}/${item.file.saveFileName}`
          // imagePath.value = `${item.file.realPath}/${item.file.saveFileName}`
        })
      },
      (error) => {
        console.error(`에러발생 ${error}`)
      }
  )

}

const estateSales = ref()
const getEstates = () => {
  estateAPI.getEstates(1,
      ({data}) => {
        estateSales.value = data
        estateSales.value.forEach(item => {
          item.transactionType = commonUtils.convertHouseTranscationType(item.transactionType)
          item.estateSalesType = commonUtils.convertHouseType(item.estateSalesType)
          item.salesPrice = commonUtils.compactToMoneyUnit(item.salesPrice)
          item.imagePath = `${item.file.realPath}/${item.file.saveFileName}`
        })
      },
      (error) => {
        console.error(`에러발생 ${error}`)
      }
  )
}

const likeCnt = ref()
const getLikeHomes = () => {
  console.log(`getLikeHomes`)
  if (loginInfo.value == null) return

  const memberInfo = loginInfo.value
  console.log(`실행`)
  memberAPI.getLikeHomes(
      memberInfo,
      ({data}) => {
        likeCnt.value = data.count
      },
      (error) => {
        console.error(error)
      }
  )
}
// ■■■■■ axios end ■■■■■

// setup
const initMethod = () => {
  getEstates()
  getLikeHomes()
  hashTagMoreSet()
}

initMethod()
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
              <q-input
                  outlined
                  square
                  v-model="searchKeyword"
                  placeholder="지역/지하철/매물번호"
                  class="search position-height-100"
                  :dense="dense"
              >
                <template v-slot:prepend>
                  <q-icon name="search"/>
                </template>
              </q-input>
            </div>
            <!--#01-02 select-->
            <div class="row q-ml-xl select-list">
              <!--TODO:### 나중에 q-select 컴포넌트 만들어서 리팩토링-->
              <q-select
                  square
                  outlined
                  v-model="selectModel01"
                  :options="options01"
                  label="매물종류"
                  :dense="dense"
                  @update:model-value="changeModel(selectModel01, '매물종류')"
              />
              <q-select
                  square
                  outlined
                  v-model="selectModel02"
                  :options="options02"
                  label="거래종류"
                  :dense="dense"
                  @update:model-value="changeModel(selectModel02, '거래종류')"
              />
              <q-select
                  square
                  outlined
                  v-model="selectModel03"
                  :options="options03"
                  label="가격"
                  :dense="dense"
                  @update:model-value="changeModel(selectModel03, '가격')"
              />
              <q-select
                  square
                  outlined
                  v-model="selectModel04"
                  :options="options04"
                  label="면적"
                  :dense="dense"
                  @update:model-value="changeModel(selectModel04, '면적')"
              />
              <q-select
                  square
                  outlined
                  v-model="selectModel05"
                  :options="options05"
                  label="사용승인연도"
                  :dense="dense"
                  @update:model-value="changeModel(selectModel05, '사용승인연도')"
              />
            </div>
          </div>
          <!--#02 초기화버튼-->
          <div class="reset-box">
            <a href="" class="reset-btn">
              <span>
                <q-icon name="restart_alt"/>
              </span>
              <span>초기화</span>
            </a>
          </div>
        </div>
      </div>
      <div class="map-view row">
        <div class="map-content position-height-100 full-width">
          <!--### 컨텐츠 좌측 영역 ###-->
          <div class="map-left">
            <div class="map-left-content position-height-100">
              <div class="list-area">
                <!--list 순서 기능-->
                <div class="list-sorting row items-center">
                  <!--순서 종류 표시-->
                  <div class="col-11 sorting-txt">
                    <!--TODO:링크 처리 하기-->
                    <router-link to="">최신순</router-link>
                    <router-link to="">가격순</router-link>
                    <router-link to="">면적순</router-link>
                  </div>
                  <!--관심매물 등록수 표시-->
                  <div class="col-1">
                    <!--하트 아이콘-->
                    <!--관심매물 수 조회 하기-->
                    <template v-if="likeCnt === undefined || likeCnt === 0">
                      <q-icon name="favorite_border" style="color: red"/>
                      0
                    </template>
                    <template v-else>
                    <span>
                      <q-icon name="favorite" style="color: red"/>{{ likeCnt }}
                    </span>
                    </template>
                    <!--                    <a href="#" style="color: red">3</a>-->
                  </div>
                </div>
                <!--매물 목록 출력-->
                <ul class="ul-list">
                  <li v-for="sales in estateSales" :key="sales.id" class="li_build">
                    <router-link :to="{name:'estateSaleDetail', params:{estateno:sales.id}}" class="sale-link row">
                      <!--이미지-->
                      <div class="img-area relative-position">
                        <div class="img-thumb">
                          <img :src="setDefaultImage(sales.imagePath)" id="preview" />
<!--                          <img src="@/assets/image/apt.jpg" alt="이미지없음"/>-->
                        </div>
                        <!--사진 장수알려주기-->
                        <div class="img_count">
                          <!--icon과 장수-->
                          <span>
                            <q-icon name="photo_camera"/>
                            3
                          </span>
                        </div>
                      </div>
                      <!--정보 박스-->
                      <div class="info-area">
                        <!--메인정보-->
                        <div>
                          <!--가격-->
                          <h3 class="no-margin">
                            <span>{{ sales.transactionType }} {{ sales.salesPrice }}</span>
                          </h3>
                          <!--아파트정보-->
                          <div class="text-overflow">
                            <span class="estate-type">{{ sales.estateSalesType }}</span>
                            <span class="estate-loc">{{
                                sales.houseInfo.dongSearch.sidoName
                              }} {{ sales.houseInfo.dongSearch.gugunName }} {{ sales.houseInfo.roadName }}</span>
                          </div>
                          <div>
                            <span class="txt01">86.47/60.38m²</span>
                            <span class="txt01">고/19층</span>
                            <span class="txt01">방3/욕실1</span>
                          </div>
                          <div>
                            <span class="txt02">{{ sales.title }}</span>
                          </div>
                        </div>
                        <!--해시태그들-->
                        <div class="hashtag absolute-bottom">
                          <span v-for="(tag, index) in hashtags" :key="index">
                            {{ tag }}
                          </span>
                        </div>
                      </div>
                    </router-link>
                  </li>
                </ul>
              </div>
            </div>
            <!--화살표 누르면 map-left on/off -->
            <a href="#" class="list-btn-switch" id="a-click" @click="toggleMapLeft">
              <div class="bw clfix">
                <div class="wa abc">
                  <div class="ww row justify-center items-center">
                    <span id="arrow-svg" v-html="arrowSvg"></span>
                  </div>
                </div>
              </div>
            </a>
          </div>
          <!--###컨텐츠 우측 영역###-->
          <div class="map-right absolute-top-right">
            <div id="map"/>
          </div>
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

.more {
  width: 28px;
}

@mixin ease-i-o {
  transition: 0.3s ease-in-out;
}

@mixin full-w-h {
  width: 100%;
  height: 100%;
}

.search {
  width: 200px;

  div {
    position: relative;
    height: 100%;
  }
}

.reset-box {
  padding: 0 14px;
  border: 1px solid rgba(0, 0, 0, 0.24);

  .reset-btn {
    height: 100%;
    display: flex;
    align-items: center;

    > span {
      height: 100%;

      &:nth-child(1) {
        font-size: 18px;
      }

      &:nth-child(2) {
        display: grid;
        place-items: center;
        color: #222222;
      }
    }
  }
}

// 공통적으로 사용
// 텍스트가 줄넘어가면 overflow 처리 , 말줄임 사용
.text-overflow {
  @include text-over-ellipsis;
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

.map-content {
  &.close {
    .map-left {
      transform: translate(-100%, 0);
    }

    .list-btn-switch {
      width: 34px;
      right: -34px;
    }

    .map-right {
      width: 100%;
    }

    #arrow-svg {
      transform: scale(-1, 1);
      animation-name: switch_ico;
      animation-duration: 0.4s;
      animation-iteration-count: infinite;
      animation-direction: alternate;
    }
  }

  .map-left {
    width: $left-basic-width;
    height: 100%;
    position: absolute;
    z-index: 2;
    background: #fff;
    @include ease-i-o;
  }

  .map-right {
    width: calc(100% - $left-basic-width);
    height: 100%;

    #map {
      @include full-w-h;
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

// 맵 전체적인 view
.map-view {
  padding-top: 60px;
  position: relative;
  height: 100%;
  z-index: 0;
}

// sorting
.list-sorting {
  height: 40px;

  .sorting-txt {
    padding-left: 24px;
    display: flex;
    height: 100%;

    > a {
      color: $text-gray;
      padding: 0 11px 0 10px;
      position: relative;
      display: inline-flex;
      align-items: center;

      &:not(:first-child)::before {
        content: '|';
        display: block;
        position: absolute;
        width: 1px;
        height: 12px;
        top: 50%;
        left: 0px;
        margin-top: -11px;
        background: #eee;
      }
    }
  }
}

.ul-list {
  height: calc(100% - 40px);
  overflow-y: scroll;
  padding: 0;
  margin: 0;
}

// 매물목록
.list-area {
  @include full-w-h;
  background: #eeeeee;

  ul {
    background: white;
  }

  li {
    border-bottom: 1px solid #eee;
  }
}

.sale-link {
  padding: 15px 0 15px 15px;
}

.img-area {
  width: 140px;
  height: 140px;

  .img-thumb {
    @include full-w-h;
    position: relative;
    overflow: hidden;
    display: flex;
  }
}

.img_count {
  width: 100%;
  height: 25px;
  display: flex;
  align-items: center;
  padding-left: 10px;
  position: absolute;
  bottom: 0;
  left: 0;

  &::before {
    @include full-w-h;
    content: '';
    position: absolute;
    top: 0px;
    left: 0px;
    background: #24303a;
    opacity: 0.7;
  }

  > span {
    position: relative;
    height: 100%;
    color: #ffffff;

    > i {
      height: 100%;
    }
  }
}

// 정보
.info-area {
  width: 285px;
  margin: 5px 0 0 15px;
  line-height: normal;
  position: relative;
  color: $text-basic-color;

  h3 {
    font-size: 100%;
    line-height: 16px;

    > span {
      font-size: 20px;
      font-weight: 700;
      color: $primary-color;
    }
  }

  .estate-type {
    color: $primary-color;
    font-size: 14px;
  }

  .estate-loc {
    font-size: 14px;
    @include text-over-ellipsis;

    &::before {
      content: 'ㆍ';
    }
  }

  // TODO: not 연산 찾기
  .txt01 {
    &:not(:first-child)::before {
      content: 'ㆍ';
    }
  }

  .txt02 {
    color: $text-gray;
  }
}

// info- 해시태그
.hashtag {
  @include text-over-ellipsis;
  font-size: 0px;
  width: 439px;
  left: -155px;
  padding-left: 155px;

  > span {
    @include text-over-ellipsis;
    display: inline-block;
    height: 24px;
    padding: 5px 6px 0;
    background: #eeeeee;
    border-radius: 12px;
    color: #888;
    font-size: 13px;
    line-height: 13px;
    text-align: center;
    letter-spacing: 0;

    &::before {
      //content: '#';
    }

    &:not(:first-child) {
      margin-left: 4px;
    }
  }
}

.list-btn-switch {
  display: block;
  overflow: hidden;
  position: absolute;
  top: 50%;
  right: -20px;
  width: 20px;
  height: 52px;
  margin-top: -26px;
  background: #fff;
  z-index: 100;
  transition: 0.3s ease-in-out;
  border: 1px solid rgba(0, 0, 0, 0.13);
  border-radius: 0 6px 6px 0;

  &:focus,
  &:visited {
    color: #222;
    text-decoration: none;
  }

  &:hover {
    cursor: pointer;
  }

  .bw {
    @include full-w-h;
    position: relative;
    font-size: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .wa {
    height: 100%;
    transition: 0.3s ease-in-out;
  }

  .ww {
    position: relative;
    z-index: 1;
    @include full-w-h;
  }
}

// 애니메이션
@keyframes switch_ico {
  0% {
    transform: translate(-2px, 0);
  }
  100% {
    transform: translate(2px, 0);
  }
}
</style>

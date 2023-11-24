<script setup>
/** global kakao **/

import {useRoute} from "vue-router";
import {computed, onBeforeMount, onBeforeUnmount, onMounted, reactive, ref, toRaw, watch} from "vue";
import estateAPI from '@/api/estate'

const route = useRoute();
const estateno = ref();

// 해시태그 더미 데이터 생성
const hashtags = ref([
  '#다가구', '#제2종근린생활시설', '#역세권', '#로얄층', '#교육환경우수', '#동향', '#올수리'
])


// carosel
const slide = ref(1)
const autoplay = ref(true)

//부동산 정보
const estateSaleInfo = ref({})
//면적
const area = reactive({
  exclusiveArea: '',
  exclusiveAreaCalc: '',
  supplyArea: '',
  supplyAreaCalc: ''
})

const dummyArea = () => {
  area.exclusiveArea = 144.22
  area.exclusiveAreaCalc = areaToP(144.22)
  area.supplyArea = 49.59
  area.supplyAreaCalc = areaToP(49.59)
}
const areaToP = (area) => {
  return Math.round((area * 0.3025) * 100) / 100
}

// ■■■■■ axios start ■■■■■
/**
 * 상세 매물 조회
 */
let formattedText = null
const getEstate = () => {
  // estateno.value = null ?? 1
  const no = route.params.estateno

  console.log(`getEstate 받은 번호 : ${no}`)
  estateAPI.getEstate(
      no,
      ({data}) => {
        estateSaleInfo.value = data
        viewAmount.value = compactToMoneyUnit(estateSaleInfo.value.salesPrice)
        convertTranscationType()
        format()
        dummyArea()

        // 위도,경도 설정
        lat.value = estateSaleInfo.value.houseInfo.lat
        lng.value = estateSaleInfo.value.houseInfo.lng

        console.log(`상세매물조회 성공`)
      },
      (error) => {
        console.error(`에러 : ${error}`)
      }
  )
}

const format = () => {
  formattedText = computed(() => {
    // 줄바꿈 문자를 <br> 태그로 대체하여 HTML로 해석되도록 함
    return estateSaleInfo.value.content.replace(/\n/g, '<br>');
  });
}

// ■■■■■ axios end ■■■■■

// ■■■■■ function start ■■■■■
// 카카오 제공 카테고리
//   TODO: 아이콘 설정하기 material icon

const categoryId = ref('MT1')
const kakaocategories = ref([
  {id: 'MT1', name: '대형마트', icon: 'train'},
  {id: 'CS2', name: '편의점', icon: 'train'},
  {id: 'PS3', name: '어린이집', icon: 'train'},
  {id: 'SC4', name: '학교', icon: 'train'},
  {id: 'AC5', name: '학원', icon: 'train'},
  {id: 'PK6', name: '주차장', icon: 'train'},
  {id: 'OL7', name: '주유소', icon: 'train'},
  {id: 'SW8', name: '지하철역', icon: 'train'},
  {id: 'BK9', name: '은행', icon: 'train'},
  {id: 'CT1', name: '문화시설', icon: 'train'},
  {id: 'AG2', name: '중개업소', icon: 'train'},
  {id: 'PO3', name: '공공기관', icon: 'train'},
  {id: 'AT4', name: '관광명소', icon: 'train'},
  {id: 'AD5', name: '숙박', icon: 'train'},
  {id: 'FD6', name: '음식점', icon: 'train'},
  {id: 'CE7', name: '카페', icon: 'train'},
  {id: 'HP8', name: '병원', icon: 'train'},
  {id: 'PM9', name: '약국', icon: 'train'}
])


const tab = ref('map') // 지도/로드뷰 switch
const viewTransactionType = ref()
const convertTranscationType = () => {
  // SALE,LEASE,RENT
  switch (estateSaleInfo.value.transactionType) {
    case 'SALE':
      viewTransactionType.value = '매매'
      break;
    case 'LEASE':
      viewTransactionType.value = '전세'
      break;
    case 'RENT':
      viewTransactionType.value = '월세'
      break;
  }

}

const viewAmount = ref()
/**
 * 금액단위를 3자리씩 끊어 간략하게 표현
 */
const compactToMoneyUnit = (money) => {
  return new Intl.NumberFormat('ko-KR', {notation: 'compact', maximunSignificantDigits: 3})
      .format(money)
}

watch(route.params.estateno, ()=> {
  estateno.value = route.params.estateno
  getEstate()
})

// ■■■■■ function end ■■■■■

// ■■■■■ event start ■■■■■

// ■■■■■ event end ■■■■■

// ■■■■■ 카카오맵 start ■■■■■
const key = import.meta.env.VITE_KAKAO_API_KEY
const kakaoMapURL = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${key}&libraries=services,clusterer,drawing`
const script = document.createElement('script')
const map = ref(null)
// const lat = ref(37.500613)
// const lng = ref(127.036431)
const lat = ref()
const lng = ref()

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    loadMap(lat.value, lng.value)
  } else {
    loadScript()
  }
})

onBeforeUnmount(() => {
  placeOverlay.setMap(null);
  removeMarker();
});

const loadScript = () => {
  script.src = kakaoMapURL
  script.onload = () => kakao.maps.load(() => loadMap(lat.value, lng.value))
  document.head.appendChild(script)
}

// 장소 검색 객체를 생성합니다
let ps = ''

const loadMap = (getLat, getLng) => {
  const container = document.getElementById('map')
  const options = {
    center: new kakao.maps.LatLng(getLat, getLng),
    level: 3
  }
  map.value = new kakao.maps.Map(container, options)

  // 마커가 표시될 위치입니다
  let markerPosition = new kakao.maps.LatLng(lat.value, lng.value);
// 마커를 생성합니다
  let marker = new kakao.maps.Marker({
    position: markerPosition
  });
// 마커가 지도 위에 표시되도록 설정합니다
  marker.setMap(toRaw(map.value));


  // 장소 검색 객체를 생성합니다
  ps = new kakao.maps.services.Places(toRaw(map.value));

  // 마커
  placeOverlay = new kakao.maps.CustomOverlay({zIndex: 1});
  const contentNode = document.createElement('div');

  // 지도에 idle 이벤트를 등록합니다
  kakao.maps.event.addListener(toRaw(map.value), 'idle', searchPlaces);

  // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
  contentNode.className = 'placeinfo_wrap';


  // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
  // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
  addEventHandle(contentNode, 'mousedown', kakao.maps.event.preventMap);
  addEventHandle(contentNode, 'touchstart', kakao.maps.event.preventMap);

  // 커스텀 오버레이 컨텐츠를 설정합니다
  placeOverlay.setContent(contentNode);

  // 각 카테고리에 클릭 이벤트를 등록합니다
  // addCategoryClickEvent();

  onClickCategory(kakaocategories.value[0].id)
}

// 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
const addEventHandle = (target, type, callback) => {
  if (target.addEventListener) {
    target.addEventListener(type, callback);
  } else {
    target.attachEvent('on' + type, callback);
  }
}

// 카테고리 검색을 요청하는 함수입니다
const searchPlaces = () => {
  if (!currCategory.value) {
    return;
  }

  // 커스텀 오버레이를 숨깁니다
  placeOverlay.setMap(null);

  // 지도에 표시되고 있는 마커를 제거합니다
  removeMarker();

  ps.categorySearch(currCategory.value, placesSearchCB, {useMapBounds: true});
};


// 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
let markers = [];
let placeOverlay = '';
// let map;
const currCategory = ref('');


// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
const placesSearchCB = (data, status, pagination) => {
  if (status === kakao.maps.services.Status.OK) {
    // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
    displayPlaces(data);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요

  } else if (status === kakao.maps.services.Status.ERROR) {
    // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요

  }
};

// 지도에 마커를 표출하는 함수입니다
const displayPlaces = (places) => {

  // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
  // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
  const order = parseInt(document.getElementById(currCategory.value).getAttribute('data-order'), 10);

  for (let i = 0; i < places.length; i++) {

    // 마커를 생성하고 지도에 표시합니다
    const marker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

    // 마커와 검색결과 항목을 클릭 했을 때
    // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
    ((marker, place) => {
      kakao.maps.event.addListener(marker, 'click', () => {
        displayPlaceInfo(place);
      });
    })(marker, places[i]);
  }
};

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
const addMarker = (position, order) => {
  // 마커 이미지 url, 스프라이트 이미지를 씁니다
  const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png';
  const imageSize = new kakao.maps.Size(27, 28); // 마커 이미지의 크기
  const imgOptions = {
    spriteSize: new kakao.maps.Size(72, 208),               // 스프라이트 이미지의 크기
    spriteOrigin: new kakao.maps.Point(46, (order * 36)),   // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
    offset: new kakao.maps.Point(11, 28),                   // 마커 좌표에 일치시킬 이미지 내에서의 좌표
  };

  const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
  const marker = new kakao.maps.Marker({
    position: position, // 마커의 위치
    image: markerImage,
  });

  marker.setMap(toRaw(map.value));   // 지도 위에 마커를 표출합니다
  markers.push(marker); // 배열에 생성된 마커를 추가합니다

  return marker;
};

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
const removeMarker = () => {
  for (let i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
};

// 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
const displayPlaceInfo = (place) => {
  const content = `
    <div class="placeinfo">
      <a class="title" href="${place.place_url}" target="_blank" title="${place.place_name}">
        ${place.place_name}
      </a>
      ${place.road_address_name ?
      `<span title="${place.road_address_name}">${place.road_address_name}</span>
        <span class="jibun" title="${place.address_name}">(지번 : ${place.address_name})</span>` :
      `<span title="${place.address_name}">${place.address_name}</span>`}
      <span class="tel">${place.phone}</span>
    </div>
    <div class="after"></div>
  `;

  const contentNode = document.querySelector('.placeinfo_wrap');
  contentNode.innerHTML = content;

  placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
  placeOverlay.setMap(toRaw(map.value));
};

// 각 카테고리에 클릭 이벤트를 등록합니다
// const addCategoryClickEvent = () => {
//   const category = document.getElementById('category');
//   const children = category.children;
//
//   for (let i = 0; i < children.length; i++) {
//     children[i].addEventListener('click', () => {
//       onClickCategory(children[i].id);
//     });
//   }
// };

// 카테고리를 클릭했을 때 호출되는 함수입니다
const onClickCategory = (id) => {
  console.log(`onClickCategory : id=> ${id}`)

  placeOverlay.setMap(null);

  if (currCategory.value === id) {
    currCategory.value = '';
    changeCategoryClass();
    removeMarker();
  } else {
    currCategory.value = id;
    changeCategoryClass();
    searchPlaces();
  }
};

// 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
const changeCategoryClass = () => {
  const category = document.getElementById('category');
  const children = category.children;

  for (let i = 0; i < children.length; i++) {
    children[i].classList.remove('on');
  }

  const el = document.getElementById(currCategory.value);
  if (el) {
    el.classList.add('on');
  }
};
// ■■■■■ 카카오맵 end ■■■■■

// Hook
watch(estateno.value, () => {
  console.log(`watch 실행`)
  getEstate()
})

watch(categoryId, (category) => {
  // console.log(`categoryId.value 현2재 val=> ${category}`)
  onClickCategory(category)
})

getEstate()
</script>

<template>

  <div class="container">

    <div class="bk-color-white">
      <!--### container-top start ###-->
      <div class="container-top">
        <!--### header start ###-->
        <div class="detail-top row">
          <!--사진 영역-->
          <div class="q-pa-md top-left">
            <q-carousel
                animated
                v-model="slide"
                navigation
                infinite
                arrows
                height="350px"
                :autoplay="autoplay"
                transition-prev="slide-right"
                transition-next="slide-left"
                @mouseenter="autoplay = false"
                @mouseleave="autoplay = true"
            >
              <q-carousel-slide :name="1" img-src="https://cdn.quasar.dev/img/mountains.jpg"/>
              <q-carousel-slide :name="2" img-src="https://cdn.quasar.dev/img/parallax1.jpg"/>
              <q-carousel-slide :name="3" img-src="https://cdn.quasar.dev/img/parallax2.jpg"/>
              <q-carousel-slide :name="4" img-src="https://cdn.quasar.dev/img/quasar.jpg"/>
            </q-carousel>
          </div>
          <!--간단한 정보영역-->
          <div class="top-right q-pa-lg">
            <div class="">
              <!--매물번호-->
              <p class="no-margin">
                <span class="text-weight-medium">매물번호 : </span>
                <span>{{ estateSaleInfo.id }}</span>
              </p>
              <!--매물 종류 & 가격 . 글씨 좀 크게-->
              <h4 class="text-weight-bold">
                {{ viewTransactionType }} {{ viewAmount }}
              </h4>
              <p style="margin: 0; margin-top: 12px;">3,129만원(3.3m²)</p>
              <hr>
            </div>
            <div>
              <!--행정동-->
              <p class="text-weight-medium" style="font-size: 24px;">
                {{ estateSaleInfo.houseInfo?.dongSearch.gugunName }} {{ estateSaleInfo.houseInfo?.dongSearch.dongName }}
              </p>
              <!--상세주소-->
              <p>
                {{ estateSaleInfo.houseInfo?.roadName }}
                <br>
                {{ estateSaleInfo.houseInfo?.apartmentName }}
              </p>
            </div>
            <div class="row items-center" style="height: 50px;">
              <!--관심매물버튼-->
              <div class="q-mr-md">
                <!--관심매물에 있으면 => 속이 찬 하트 favorite -->
<!--TODO: 회원 로그인 후 관심매물 이벤트 처리할 수 있으면 하기-->
                <q-btn outline text-color="black" icon-right="favorite_border" label="관심매물" class="top-right-btn"/>
              </div>
              <!--공유하기버튼-->
              <div>
                <q-btn outline round color="blue-grey-3" text-color="black" icon="ios_share" class="top-right-btn"/>
              </div>
            </div>
          </div>

        </div>
        <!--### header end ###-->
      </div>
      <!--### container-top end ###-->
    </div>

    <!--### container-content start ###-->
    <div class="container-content">
      <!--### main start ###-->
      <div class="detail-content row relative-position">
        <!--### content-left start ###-->
        <div class="content-left">
          <!--해시태그 정보들-->
          <div class="bk-color-white full-width" style="margin-top: 0">
            <h4>{{ estateSaleInfo.title }}</h4>
            <div>
              <div class="hashtag-list">
                <span v-for="tag in hashtags" class="hashtag">
                  {{ tag }}
                </span>
              </div>
            </div>
          </div>
          <!--매물 정보-->
          <div class="bk-color-white">
            <h4>매물정보</h4>
            <div>
              <table>
                <colgroup>
                  <col style="width: 140px">
                  <col style="width: 205px">
                  <col style="width: 140px">
                  <col style="">
                </colgroup>
                <tbody>
                <tr>
                  <th><span>매물종류</span></th>
                  <td colspan="3">{{ viewTransactionType }}</td>
                </tr>
                <tr>
                  <th><span>소재지</span></th>
                  <td colspan="3">
                    {{ estateSaleInfo.houseInfo.dongSearch.sidoName }}
                    {{ estateSaleInfo.houseInfo.dongSearch.gugunName }}
                    {{ estateSaleInfo.houseInfo.dongSearch.dongName }}
                  </td>
                </tr>
                <tr>
                  <th><span>공급면적 <br> 전용면적</span></th>
                  <td>
                    {{ area.exclusiveArea }}
                    ({{ area.exclusiveAreaCalc }}P)
                    <br>
                    {{ area.supplyArea }}
                    ({{ area.supplyAreaCalc }}P)
                  </td>
                  <th><span>전용률</span></th>
                  <td>34%</td>
                </tr>
                <tr>
                  <th><span>해당층/전체층</span></th>
                  <td>5/19</td>
                  <th><span>입주가능일</span></th>
                  <td>즉시입주</td>
                </tr>
                <tr>
                  <th><span>방수</span></th>
                  <td>2</td>
                  <th><span>욕실수</span></th>
                  <td>1</td>
                </tr>
                <tr>
                  <th><span>방향</span></th>
                  <td>동</td>
                  <th><span>현관구조</span></th>
                  <td>계단</td>
                </tr>
                <tr>
                  <th><span>내부구조</span></th>
                  <td>단층</td>
                  <th><span>베란다(확장유무)</span></th>
                  <td>무</td>
                </tr>
                <!--나머지 매물정보 더 있으면 수정-->
                </tbody>
              </table>
            </div>
          </div>
          <!--가격정보-->
          <div class="bk-color-white">
            <h4>가격정보</h4>
            <table>
              <colgroup>
                <col style="width: 140px">
                <col style="">
              </colgroup>
              <tbody>
              <tr>
                <th>
                  <span>
                    {{ viewTransactionType }}
                  </span>
                </th>
                <td>
                  {{ viewAmount }}
                </td>
              </tr>
              </tbody>
            </table>
          </div>
          <!--매물설명-->
          <div class=" bk-color-white">
            <h4>매물설명</h4>
            <hr>
            <div v-html="formattedText">
            </div>
          </div>
          <div class="bk-color-white">
            <h4>위치 및 주변정보</h4>
            <hr>
            <!--탭 패널 지도/로드뷰-->
            <div>
              <div>
                <q-tabs
                    v-model="tab"
                    narrow-indicator
                    dense
                    align="justify"
                    class="text-primary"
                >
                  <q-tab :ripple="false" name="map" label="지도"/>
                  <!--                  <q-tab :ripple="false" name="roadView" label="로드뷰"/>-->
                </q-tabs>
              </div>
              <div>
                <q-tab-panels v-model="tab" animated>
                  <q-tab-panel name="map">
                    <div class="map_wrap">
                      <div id="map"/>
                      <div>
                        <q-tabs v-model="categoryId" class="text-teal-6" id="category">
                          <template v-for="(cate, idx) in kakaocategories" :key="idx">
                            <q-tab :id="cate.id" :name="cate.id" :icon="cate.icon" :label="cate.name"
                                   :data-order="idx"/>
                          </template>
                        </q-tabs>
                      </div>
                    </div>
                  </q-tab-panel>
                </q-tab-panels>
              </div>
            </div>
          </div>

          <div class="q-mt-lg">
          </div>
        </div>
        <!--### content-left end ###-->

        <!--### content-right start ###-->
        <div class="content-right full-height">
          <!--사업자 정보 => 일정 스크롤하면 top 위에 고정-->
          <div class="bk-color-white q-pt-lg">
            <!--공인중개사 프로필사진-->
            <div class="agent-info">
              <div class="img-box">
                <img src="@/assets/image/어피치.png" alt="이미지없음">
              </div>
              <!--중개사명, 이름-->
              <div>
                <h5>{{ estateSaleInfo.agent.name }}</h5>
                <p>공인중개사 김철수</p>
              </div>
            </div>
            <hr style="height: 2px; background: #d9d9d9; width: 80%">
            <!--중개사 주소-->
            <div style="padding-left: 28px;">
              <p class="no-margin text-weight-medium">
                <span class="text-grey-7">주소:</span>
                <span >{{estateSaleInfo.agent.address}}</span>
              </p>
              <p class="text-weight-medium">
                <span class="text-grey-7">중개등록번호: </span><span>11209281</span>
              </p>
            </div>
            <!--미니홈 -> 클릭시 등록한 매물들 페이지 (회원 관심매물 페이지와 비슷)-->
            <div class="row justify-center">
              <q-btn color="secondary" icon="mail" label="미니홈"/>
            </div>
            <!--TOP-->
            <div class="top-move q-mt-lg">
              <p>^ TOP</p>
            </div>
          </div>
        </div>
        <!--### content-right end ###-->
      </div>
      <!--### main end ###-->
    </div>
    <!--### container-content end ###-->

  </div>


</template>

<style scoped lang="scss">
@import "@/assets/css/reuse-var";

@include list-none;

img {
  @include full-w-h;
}

hr {
  //width: 90%;
}

h4 {
  margin: 0 0 24px 0;
}

h5 {
  margin: 0;
  font-size: 1.25rem;
}

table {
  border-top: 2px solid $blue-grey-4;
  border-bottom: 2px solid $blue-grey-4;
  border-collapse: collapse;
  width: 100%;

  tr > th {
    background: #fafafa;
    text-align: left;
  }

  th, td {
    //border-bottom: 1px solid $blue-grey-12;
    border-bottom: 1px solid #dddddd;
    //text-align: left;
    padding: 8px;
  }

}

// 공통
.bk-color-white {
  background: white;
}

// detail content
.container {
  background: #EFEFF0;

  .container-top {
    background: white;
  }

  .container-top, .container-content {
    width: 1080px;
    margin: 24px auto 0;
  }

  .top-left, .content-left {
    width: 750px;
  }

  .top-right, .content-right {
    width: calc(1080px - 750px);
    padding-left: 24px;
  }

  .content-left {
    > div {
      padding: 24px 24px;
      margin-top: 24px;
    }

  }
}

.content-right {
  position: absolute;
  top: 0;
  right: 0;

  > div {
    position: sticky;
    top: 20px;
  }
}

.agent-info {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  @include full-w-h;

  .img-box {
    width: 80px;
    height: 80px;
    overflow: hidden;
    border-radius: 70%;
    //border: 1px solid black;
  }

}

.top-move {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30px;
  background: #f5f5f5;
  border-top: 1px solid #c5c5c5;

  p {
    margin: 0
  }
}

.top-right-btn::before {
  border: 1px solid $blue-grey-12;
}

.hashtag-list {

  .hashtag {
    display: inline-flex;
    height: 33px;
    padding: 0 13px;
    margin: 10px 0 0 5px;
    align-items: center;
    font-size: 15px;
    color: #7790ad;
    border: 1px solid #e5e5e5;
    border-radius: 16px;
    font-weight: 400;
  }
}


// 카카오맵 CSS START
#map {
  width: 100%;
  height: 400px;
}

#category {
  display: flex;
  padding: 0;

  > li {
    &:not(:first-child) {
      margin-left: 12px;
    }

  }
}


// 카카오맵 CSS END
</style>
<script setup>
/* global kakao*/
import {onMounted, reactive, ref, toRaw} from 'vue'
import marketAPI from '@/api/marketprice'
import LineChart from "@/components/chart/LineChart.vue";

const key = import.meta.env.VITE_KAKAO_API_KEY
const kakaoMapURL = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${key}&libraries=services,clusterer,drawing`
const script = document.createElement('script')

const map = ref(null) // 카카오맵

// 최초 로딩시 위도 경도 설정
// const lat = ref(37.500613)
// const lng = ref(127.036431)
const latlng = reactive({
      lat: 37.500613,
      lng: 127.036431
    }
)

const mapCenter = reactive({
  centerLat: 0,
  centerLng: 0
})

// ■■■■■■■■■■ axios start ■■■■■■■■■■
//
//TODO: 해당 중심좌표를 기준으로 DB 실거래가 조회
const responseData = ref() // 서버 data 반환용
const getMarketPriceList = () => {
  /**
   * ### axios 통신 ok => 데이터 형식 ###
   * count
   * data = {
   *   no: 116801811000008,     => 거래 번호
   *   dealAmount: "84,000",    => 가격 (천만원)
   *   dealYear: 2018,          => 거래년도
   *   area: "109.2",           => 면적
   *   aptCode: 11680000000623, => 아파트 코드
   *   lat: "37.4969514046371", => 위도
   *   lng: "127.0372270238",   => 경도
   * }
   */
  marketAPI.getMarketPrice(
      // latlng.value,
      requestLatLng.value,
      ({data}) => {
        responseData.value = data.data;
        makeMarker();
      }, (error) => {
        console.error(`실패 : ${error}`);
      })
}

const detailInfo = ref() // 상세정보 담을 객체
/**
 * axios: getmarkerPriceDetail
 * 마커 클릭시 발생하는 이벤트
 * 해당 거래 정보 가져오기
 * @param lat
 * @param lng
 */
const getMarketPriceDetail = (lat, lng) => {
  console.log(`getMarketPriceDetail 파라미터 : ${lat} \t ${lng}`)
  const param = {lat:lat, lng:lng}
  marketAPI.getMarketPriceDetail(
      param,
      ({data}) => {
        // console.log(`받은 데이터 : ${JSON.stringify(data.data)}`)
        detailInfo.value = data;

        // 거래정보가있으면 차트정보에 넣기
        if (detailInfo.value.count > 0) {

          const getLabels = []
          const getAmounts = []

          detailInfo.value.data.forEach(item => {
                getLabels.push(`${item.dealYear}/${item.dealMonth}/${item.dealDay}`)
                getAmounts.push(item.dealAmount)
              }
          )
          console.log(`getLabels : ${getLabels}`)
          makeChartData(getLabels, getAmounts)
        }

      },
      (error) => {
        console.error(`에러 발생 : ${error}`)
      }
  )
}
// ■■■■■■■■■■ axios end ■■■■■■■■■■

// ■■■■■■■■■■ Function Start ■■■■■■■■■■

function numberFormat(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function numberToKorean(number) {
  let inputNumber = number < 0 ? false : number;
  let unitWords = ['', '만', '억', '조', '경'];
  let splitUnit = 10000;
  let splitCount = unitWords.length;
  let resultArray = [];
  let resultString = '';

  for (let i = 0; i < splitCount; i++) {
    let unitResult = (inputNumber % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i);
    unitResult = Math.floor(unitResult);
    if (unitResult > 0) {
      resultArray[i] = unitResult;
    }
  }

  for (let i = 0; i < resultArray.length; i++) {
    if (!resultArray[i]) continue;
    resultString = String(numberFormat(resultArray[i])) + unitWords[i] + resultString;
  }

  return resultString;
}

const makeViewChartData = (amount) => {
  console.log(`받은 amount : ${amount} \t 타입 : ${typeof amount}`)
  const koreanAmount = []
  const convertedAmounts = amount.map(value => {
    const numericValue = parseFloat(value.replace(',', '')); // 쉼표 제거하고 숫자로 변환
    const calc = numericValue * 10000; // 10000을 곱함
    koreanAmount.push(numberToKorean(calc))
  });

  console.log(`koreanAmount : ${koreanAmount}`)
  return koreanAmount
}

/**
 * 차트 정보 Data 생성
 */

const makeChartData = (labels, data) => {
  console.log(`makeChartData : labels=> ${labels} \t data => ${data}`)

  const inputData = {
    // labels: ['월', '화', '수', '목', '금', '토', '일'], // => 해당 날짜 값들 dealYear + dealMonth + dealDay 날짜로 해서 오름차순
    labels: labels,
    datasets: [{
      label: '실거래가',
      // data: [65, 59, 80, 81, 56, 55, 40], // => dealAmount * 100000
      data: data,
      fill: false,
      borderColor: 'rgb(75, 192, 192)',
      tension: 0.1
    }]
  }

  const inputOption = {
    options: {
      responsive: false,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'top',
        },
        title: {
          display: true,
          text: 'Chart.js Line Chart'
        }
      }
    }
  }
  inputChartInfo(inputData, inputOption)
}

// 차트 정보
const chartData = ref();
const chartOptions = ref()

const inputChartInfo = (chartDataParam, chartOptionParam) => {
  chartData.value = chartDataParam;
  chartOptions.value = chartOptions
  console.log(`inputChartInfo :차트데이터 => ${chartData.value} \t 차트옵션 => ${chartOptions.value}`)
}

// 카카오맵 시작###
const loadScript = () => {
  script.src = kakaoMapURL
  // script.onload = () => kakao.maps.load(() => loadMap(lat.value, lng.value))
  script.onload = () => kakao.maps.load(() => loadMap(latlng.lat, latlng.lng))
  document.head.appendChild(script)
}

const loadMap = (getLat, getLng) => {
  const container = document.getElementById('map')
  const options = {
    center: new kakao.maps.LatLng(getLat, getLng),
    level: 3
  }
  map.value = new kakao.maps.Map(container, options)

  getLatLng();
  getMarketPriceList();

  // 카카오맵 이벤트 등록
  addKakaoMapEvent();
}

/**
 * 지도의 현재 중심좌표 얻어오기
 */
const getMapCenterInfo = () => {
  let center = map.value.getCenter()
  mapCenter.centerLat = center.getLat()
  mapCenter.centerLng = center.getLng()
}

const requestLatLng = ref() // 서버 요청용 파라미터

/**
 * 남서, 북동 좌표 얻어오기
 */
const getLatLng = () => {
  // 지도의 현재 영역을 얻어옵니다
  var bounds = map.value.getBounds();
  // 영역의 남서쪽 좌표를 얻어옵니다
  let swLatLng = bounds.getSouthWest();
  // 영역의 북동쪽 좌표를 얻어옵니다
  let neLatLng = bounds.getNorthEast();
  let latArr = [swLatLng.getLat(), neLatLng.getLat()]
  let lngArr = [swLatLng.getLng(), neLatLng.getLng()]

  requestLatLng.value = {
    lat: latArr,
    lng: lngArr
  }
}

/**
 * 현재 맵 범위에 해당하는 거래 정보를 불러와 마커를 생성한다.
 */
const makeMarker = () => {
  // console.log(`makeMarker `)

  var positions = [];
  // latlng.value.forEach(pos => {
  responseData.value.forEach(data => {
        // console.log(`마커위치 : lat:${data.lat} \t lng:${data.lng}`)
        positions.push({
              // latlng: new kakao.maps.LatLng(pos.lat, pos.lng)
              latlng: new kakao.maps.LatLng(data.lat, data.lng)
            }
        )
      }
  )

  // 마커 이미지의 이미지 주소입니다
  //   var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
  for (var i = 0; i < positions.length; i++) {
    // 마커 이미지의 이미지 크기 입니다
    // var imageSize = new kakao.maps.Size(24, 35);

    // 마커 이미지를 생성합니다
    // var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // marker.value = new kakao.maps.Marker({
    const marker = new kakao.maps.Marker({
      map: toRaw(map.value), // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
      clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트 발생x
      // title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      // image : markerImage // 마커 이미지
    });

    // 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', markerClick(marker));
  }
}

const markerClick = (marker) => {
  return function () {
    console.log(`마커 클릭이벤트 발생`)
    // let lat = marker.getPosition().getLat().toFixed(10)
    // let lng = marker.getPosition().getLng().toFixed(10)
    let lat = marker.getPosition().getLat()
    let lng = marker.getPosition().getLng()
    console.log(`lat : ${lat}, \t lng : ${lng}`)

    getMarketPriceDetail(lat, lng);
  }
}

const getDetailMarketPrice = () => {

}

// ■■■■■■■■■■ Function End ■■■■■■■■■■

// ■■■■■■■■■■ Event Start ■■■■■■■■■■

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    // loadMap(lat.value, lng.value)
    loadMap(latlng.lat, latlng.lng)
  } else {
    loadScript()
  }

  // 임시 차트 데이터

  makeChartData(labels, data)
})

const addKakaoMapEvent = () => {
  // 드래그 끝나는 시점에 발생
  kakao.maps.event.addListener(toRaw(map.value), 'dragend', function () {
    // console.log(`dragend 이벤트 실행`)
    //STEP01:
    // getMapCenterInfo();
    getLatLng();
    getMarketPriceList();
  });

}


// ■■■■■■■■■■ Event End ■■■■■■■■■■

</script>

<template>
  <div class="map-container absolute-top-left">
    <div class="relative-position full-height">
      <!--### 검색바 ###-->
      <div class="map-top absolute-top-left">
        <div class="row top-content">
        </div>
      </div>
      <div class="map-view row">
        <div class="map-content position-height-100 full-width">
          <!--### 컨텐츠 좌측 영역 ###-->
          <div class="map-left">
            <div style="width: 450px; height: 250px">
<!--              <line-chart :chartOptions="chartOptions" :chartData="chartData"/>-->
<!--              <line :options="chartOptions" :chartData="chartData"/>-->
              <line-chart :data="chartData" :options="chartOptions"/>
            </div>

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

@mixin ease-i-o {
  transition: 0.3s ease-in-out;
}

@mixin full-w-h {
  width: 100%;
  height: 100%;
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

.map-content {

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

</style>

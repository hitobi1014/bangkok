import {localAxios} from "@/utils/http-commons";

const marketAPI = localAxios()

// const getEstates = (pagenum, success, fail) => {
//     estateAPI.get(`/api/estate/sales/${pagenum}`).then(success).catch(fail)
// }

const getMarketPrice = (latlng, success, fail) => {
    console.log(`axios 받아온 데이터 :${JSON.stringify(latlng) }`)
    marketAPI.post('/api/market/list',latlng).then(success).catch(fail)
}

const getMarketPriceDetail = (param, success, fail) => {
    console.log(`axios 받아온 데이터 lat:${param.lat} \t lng:${param.lng}`)
    marketAPI.get(`/api/market/detail/${param.lat}/${param.lng}`)
        .then(success).catch(fail)
}



export default {getMarketPrice, getMarketPriceDetail}
import { localAxios } from '../utils/http-commons'
import axios from "axios";

const estateAPI = localAxios()
const {VITE_API_BASE_URL} = import.meta.env


const getEstates = (success, fail) => {
  estateAPI.get(`/api/estate/sales`).then(success).catch(fail)
}

const getEstate = (eid, success, fail) => {
  estateAPI.get(`/api/estate/sale/${eid}`).then(success).catch(fail)
}

const registEstate = (param, success, fail) => {

  const url = `${VITE_API_BASE_URL}/api/estate/regist`

  axios.post(url, param, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(success).catch(fail);
}

const modifyEstate = (estate, success, fail) => {
  estateAPI.put(`/api/estate/modify/${estate.id}`, estate).then(success).catch(fail)
}

const removeEstate = (eid, success, fail) => {
  estateAPI.delete(`/api/estate/delete/${eid}`).then(success).catch(fail)
}

const getEstatesWithName = (name, success, fail) => {
  estateAPI.get(`/api/estate/houseinfosbyname?name=${name}`).then(success).catch(fail)
}

const getSelectSearch = (info, success, fail) => {
  console.log(`getSelectSearch info => ${JSON.stringify(info)}`)
  estateAPI.post(`/api/estate/search-select`, info).then(success).catch(fail)
}

export default { getEstates, getEstate, registEstate, modifyEstate, removeEstate, getEstatesWithName, getSelectSearch}

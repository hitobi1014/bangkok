import { localAxios } from '@/utils/http-commons'
import axios from "axios";

const memberAPI = localAxios()
const {VITE_API_BASE_URL} = import.meta.env

// 회원 가입
const joinMember = (param, success, fail) => {
  // memberAPI.post('/api/member/join', member).then(success).catch(fail)

  const url = `${VITE_API_BASE_URL}/api/member/join`

  // TODO:localAxios에서 설정한 상태에서 content-Type 변경할 수 있으면 해보기
  axios.post(url, param, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(success).catch(fail);
}

// 회원 전체 조회
const getMemberList = (success, fail) => {
  memberAPI.get('/api/member/list').then(success).catch(fail)
}

// 회원 로그인
const memberLogin = (member, success, fail) => {
  memberAPI.post('/api/member/login', member).then(success).catch(fail)
}

// 회원 로그아웃
const memberLogout = (success, fail) => {
  memberAPI.get('/api/member/logout').then(success).catch(fail)
}

// 관심매물 조회
const getLikeHomes = (member, success, fail) => {
  console.log(`받은 회원정보 :${JSON.stringify(member)}`)
  memberAPI.post(`/api/member/likehomes`,member).then(success).catch(fail)
}

export default { joinMember, getMemberList, memberLogin, memberLogout,getLikeHomes}

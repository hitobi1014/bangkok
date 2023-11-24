import { localAxios } from '@/utils/http-commons'

const boardAPI = localAxios()

// TODO: 조회 확인하기, 통신에러
// 게시글 전체조회
const getBoardList = (type, success, fail) => {
  boardAPI.get(`/api/boards/${type}`).then(success).catch(fail)
}

// 게시글 상세조회
const getBoardDetail = (id, success, fail) => {
  boardAPI.get('/api/boards/detail', { params: id }).then(success).catch(fail)
}

// 게시글 등록
const registBoard = (board, success, fail) => {
  boardAPI.post('/api/boards/regist', board).then(success).catch(fail)
}

// 게시글 수정
const modifyBoard = (board, id, success, fail) => {
  boardAPI.patch('/api/boards/modify', board, { params: id }).then(success).catch(fail)
}

// 게시글 삭제
const deleteBoard = (id, success, fail) => {
  boardAPI.delete('/api/boards/remove', { params: id }).then(success).catch(fail)
}

export default { getBoardList, getBoardDetail, registBoard, modifyBoard, deleteBoard }

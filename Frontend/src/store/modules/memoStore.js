/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'


const api_url = API_BASE_URL + '/memo'
const albumStore = {
  namespaced: true,
  state: {
    albumName: '',
    memoAllList: []
  },
  getters: {

  },
  mutations: {
    // SET_FOLDER_LIST: (state, folderList) => {
    //   state.folderList = folderList
    // },
    SET_MEMO_ALL_LIST(state, memoList) {
      state.memoAllList = memoList
    }
  },
  actions: {
    //메모 생성
    async createMemo({commit}, createMemoRequestDto) {
      await instance({
        url: api_url,
        method: 'POST',
        data: JSON.stringify(createMemoRequestDto),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((res) => {
        alert('등록되었습니다')
        router.push({ name: 'memo' })
      })
      .catch((err) => {
      })
    },
    //전체 메모 리스트 조회(냉장고 페이지에서 보여질 것)
    async getMemoAllList({commit}, familyId) {
      await instance({
        url: api_url + `/list/${familyId}`,
        method: 'GET',
      })
      .then((res) => {
        commit('SET_MEMO_ALL_LIST', res.data)
      })
      .catch((err) => {
      })
    },
    //메모 삭제
    async deleteMemo({commit}, memoId) {
      await instance({
        url: api_url + `/${memoId}`,
        method: 'DELETE'
      })
      .then((res) => {
      })
      .catch((err) => {
      })
    }
  }
}

export default albumStore

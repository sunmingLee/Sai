/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'

const api_url = API_BASE_URL + '/family'
const familyStore = {
  namespaced: true,
  state: {
    familyId: '',
    familyCallsignList: [],
    familyInfo: [],
    notificationList: []
  },
  getters: {
  },
  mutations: {
    SET_FAMILY_ID: (state, familyId) => {
      state.familyId = familyId
    },
    CALLSIGN_LIST (state, callsign) {
      state.familyCallsignList = callsign
    },
    SET_FAMILY_INFO(state, familyInfo) {
      state.familyInfo = familyInfo
    }
  },
  actions: {
    // 가족 들어가기 신청
    applyFamily ({ commit }, userInfo) {
      const params = {
        familyId: userInfo.familyId,
        userId: userInfo.userId
      }
      axios({
        url: api_url + '/join/apply',
        method: 'POST',
        // params
        data: JSON.stringify(params),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            alert('가족 들어가기 신청 완료')
            commit('SET_FAMILY_ID', userInfo.familyId)
            router.push({ name: 'applywait' })
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
          alert('잘못된 코드입니다')
        })
    },
    // 가족 아이디 생성
    createFamilyId ({ commit }, userInfo) {
      axios({
        url: api_url + '/create/' + userInfo,
        method: 'POST'
      })
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            commit('SET_FAMILY_ID', res.data.familyId)
            router.push({ name: 'familyInvite' })
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 가족 콜사인 리스트
    callsignList ({ commit }, user) {
      const userId = user
      axios({
        url: api_url + '/list/' + userId,
        method: 'GET'
      })
        .then((res) => {
          commit('CALLSIGN_LIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 가족 신청 취소
    deleteFamilyApply ({ commit }, userId) {
      axios({
        url: api_url + `/join/${userId}`,
        method: 'Delete'
      })
        .then((res) => {
          alert('가족 신청 취소가 완료되었습니다.')
          commit('SET_FAMILY_ID', '')
          router.push({ name: 'familyCode' })
        })
        .catch((err) => {
          console.log(err)
        })
    },
    //가족 정보 조회
    getFamilyInfo({commit}, familyId) {
      axios({
        url: api_url + "/" + familyId,
        method: 'GET'
      })
      .then((res) => {
        commit('SET_FAMILY_INFO', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
    },

    //가족 정보 수정
    updateFamilyInfo({commit}, modifiedFamilyInfo) {
    // 게시글 작성
    // boardCreate ({ commit }, boardInfo) {
      const files = modifiedFamilyInfo.fileList
      const updateFamilyRequestDto = modifiedFamilyInfo.updateFamilyRequestDto
      const formData = new FormData()

      if (files !== undefined) {
        formData.append('file', files[0])
      }

      formData.append('updateFamilyRequestDto', new Blob([JSON.stringify(updateFamilyRequestDto)], { type: 'application/json' }))
      axios({
        url: api_url + '/modify',
        method: 'PUT',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            alert('가족 정보가 수정되었습니다.')
            // router.push({ name: 'feed' })
          } else {
            console.log(res)
            alert('가족 정보 수정 중 오류가 발생했습니다.')
          }
        })
        .catch((err) => {
          console.log(err)
          alert('가족 정보 수정 중 오류가 발생했습니다.')
        })
    },

  },

  modules: {
  }
}
export default familyStore
/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'

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
    SET_FAMILY_INFO (state, familyInfo) {
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
      instance({
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
      instance({
        url: api_url + '/create/' + userInfo,
        method: 'POST'
      })
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            localStorage.setItem('familyId', res.data.familyId)
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
      instance({
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
      instance({
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
    // 가족 정보 조회
    getFamilyInfo ({ commit }, familyId) {
      instance({
        url: api_url + '/' + familyId,
        method: 'GET'
      })
        .then((res) => {
          commit('SET_FAMILY_INFO', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },

    // 가족 정보 수정
    updateFamilyInfo ({ commit }, modifiedFamilyInfo) {
    // 게시글 작성
    // boardCreate ({ commit }, boardInfo) {
      const files = modifiedFamilyInfo.fileList
      const updateFamilyRequestDto = modifiedFamilyInfo.updateFamilyRequestDto
      const formData = new FormData()

      if (files !== undefined) {
        formData.append('file', files[0])
      }

      formData.append('updateFamilyRequestDto', new Blob([JSON.stringify(updateFamilyRequestDto)], { type: 'application/json' }))
      instance({
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
    // 가족 신청 수락, 거절
    answerFamilyRegister ({ commit }, info) {
      const data = {
        approveYn: info.approveYn,
        familyRegisterId: info.familyRegisterId
      }
      instance({
        url: api_url + '/join/response/' + info.userId,
        method: 'PATCH',
        data: JSON.stringify(data),
        headers: {
          'Content-type': 'application/json'
        }
      })
        .then((res) => {
          if (info.approveYn) { // 가족 신청 수락
            alert(info.userId + '님의 가족신청을 수락했습니다.')
          } else { // 가족 신청 거절
            alert(info.userId + '님의 가족신청을 거절했습니다.')
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }

  },

  modules: {
  }
}
export default familyStore

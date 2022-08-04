/* eslint-disable camelcase */
import router from '@/router'
import axios from 'axios'

import Vuex from 'vuex'

export default new Vuex.Store({
  state: {
    familyId: 234567,
    userId: 'tnqls',
    callsign: ["소독용", "에탄올", "스타벅스", "아이패드"]
  },
  getters: {
  },
  mutations: {
    SET_FAMILY_ID: (state, familyId) => {
      state.familyId = familyId
    }
  },
  actions: {
    // 가족 들어가기 신청
    applyFamily ({ commit }, userInfo) {
      const api_url = 'http://localhost:8080/family/join/apply'
      const params = {
        familyId: userInfo.familyId,
        userId: userInfo.userId
      }
      axios({
        url: api_url,
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
      const api_url = 'http://localhost:8080/family/create/'
      axios({
        url: api_url + userInfo,
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
    // 아이디 찾기
    findId ({ commit }, userInfo) {
      const api_url = 'http://localhost:8080/api/user/findId'
      const params = {
        userName: userInfo.userName,
        email: userInfo.email
      }
      axios({
        url: api_url,
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.msg.indexOf('입력하신 이메일로 아이디가 전송되었습니다.') != -1) {
            alert(res.data.msg)
            router.push({ name: 'home' })
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 비밀번호 찾기
    findPassword ({ commit }, userInfo) {
      const api_url = 'http://localhost:8080/api/user/findPw'
      const params = {
        userName: userInfo.userName,
        userId: userInfo.userId,
        email: userInfo.email
      }
      axios({
        url: api_url,
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.msg.indexOf('입력하신 이메일로 임시 비밀번호가 전송되었습니다.') != -1) {
            alert(res.data.msg)
            router.push({ name: 'home' })
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  modules: {
  }
})

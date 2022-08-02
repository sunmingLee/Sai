/* eslint-disable camelcase */
import router from '@/router'
import axios from 'axios'
import Vue from 'vue'
import Vuex from 'vuex'

export default new Vuex.Store({
  state: {
    msg: ''
  },
  getters: {
  },
  mutations: {

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
    }
  },
  modules: {
  }
})

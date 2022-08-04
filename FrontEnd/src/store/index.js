/* eslint-disable camelcase */
// import { createStore } from 'vuex'
// import createPersistedState from "vuex-persistedstate";
// import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from '@/router/index.js'

import userStore from './userStore.js'

const store = new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    login ({ commit }, user) {
      const api_url = 'http://localhost:8080/api/user/login'
      const data = {
        userId: user.userId,
        password: user.password
      }
      axios.post(api_url, data, {
      })
        .then((res) => {
          console.log(res)
          // console.log(res.headers)
          if (res.status === 200) {
            // const jwtToken = res.headers['Set-Cookie']
            // console.log(jwtToken)
            localStorage.setItem('userId', data.userId)
            this.dispatch('getUserInfo', localStorage.getItem('userId'))
          }
        })
        .catch((err) => {
          console.log(err)
          alert('아이디와 비밀번호를 다시한번 확인해주세요.')
        })
    },
    getUserInfo ({ commit }, userId) {
      const api_url = 'http://localhost:8080/api/user/'
      axios.get(api_url + userId)
        .then((res) => {
          console.log(res)
          // familyId가 있는 경우, 메인으로 이동
          if (res.status === 200) {
            router.push({ name: 'about' })
          }
          // familyId가 없는 경우
          // else {

          // }
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  modules: {
    userStore
  }
})
export default store

/* eslint-disable camelcase */
// import { createStore } from 'vuex'
// import createPersistedState from "vuex-persistedstate";
// import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

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
      const params = {
        userId: user.userId,
        password: user.password
      }
      axios({
        url: api_url,
        method: 'POST',
        params
      })
        .then((res) => {
          console.log(res)
          // if (res.data.msg.indexOf('입력하신 이메일로 아이디가 전송되었습니다.') != -1) {
          //   alert(res.data.msg)
          //   router.push({ name: 'home' })
          // } else {
          //   alert(res.data.msg)
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

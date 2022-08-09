/* eslint-disable camelcase */
import Vuex from 'vuex'

// vuex 새로고침시 상태 초기화 방지
import createPersistedState from 'vuex-persistedstate'

import familyStore from './modules/familyStore.js'
import userStore from './modules/userStore.js'
import boardStore from './modules/boardStore.js'
import joinStore from './modules/joinStore.js'
import notificationStore from './modules/notificationStore.js'

const store = new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    familyStore,
    userStore,
    boardStore,
    notificationStore,
    joinStore
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      // storage: sessionStorage
    })
  ]

})
export default store

/* eslint-disable camelcase */
import Vuex from 'vuex'

import familyStore from './modules/familyStore.js'
import userStore from './modules/userStore.js'
import boardStore from './modules/boardStore.js'
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
    notificationStore
  }
})
export default store

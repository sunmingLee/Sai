/* eslint-disable camelcase */
import Vuex from 'vuex'

import familyStore from './modules/familyStore.js'
import userStore from './modules/userStore.js'
import boardStore from './modules/boardStore.js'
import joinStore from './modules/joinStore.js'

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
    joinStore
  },
})
export default store

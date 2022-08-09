/* eslint-disable camelcase */
import Vuex from 'vuex'

import familyStore from './modules/familyStore.js'
import userStore from './modules/userStore.js'
import boardStore from './modules/boardStore.js'
import joinStore from './modules/joinStore.js'
import notificationStore from './modules/notificationStore.js'
import albumStore from './modules/albumStore.js'

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
    joinStore,
    albumStore
  }
})
export default store

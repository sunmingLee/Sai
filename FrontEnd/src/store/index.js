import Vuex from 'vuex'

import userStore from './modules/userStore.js'
import familyStore from './modules/familyStore.js'

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
    userStore,
    familyStore
  }
})
export default store

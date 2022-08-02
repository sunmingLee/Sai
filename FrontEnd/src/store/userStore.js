import jwtDecode from 'jwt-decode'
import { login, findById, updateUser } from '@/api/user.js'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo
    }
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      // state.isLogin = isLogin
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true
      state.userInfo = userInfo
    }
  },
  actions: {
    async userConfirm ({ commit }, user) {
      console.log(user)
      await login(
        user,
        (response) => {
          console.log(response)
          if (response.data.code === 200) {
            const token = response.data['access-token']
            commit('SET_IS_LOGIN', true)
            commit('SET_IS_LOGIN_ERROR', false)
            sessionStorage.setItem('access-token', token)
          } else {
            commit('SET_IS_LOGIN', false)
            commit('SET_IS_LOGIN_ERROR', true)
          }
        },
        (response) => {
          console.log(response)
          commit('SET_IS_LOGIN', false)
          commit('SET_IS_LOGIN_ERROR', true)
        }
      )
    },
    getUserInfo ({ commit }, token) {
      const decodeToken = jwtDecode(token)
      console.log(decodeToken)
      findById(
        decodeToken.id,
        (response) => {
          console.log(response)
          if (response.data) {
            commit('SET_USER_INFO', response.data)
          } else {
            console.log('유저 정보 없음!!')
          }
        },
        (error) => {
          console.log(error)
        }
      )
    },

    updateUserInfo ({ commit }, user) {
      updateUser(
        user,
        (response) => {
          console.log(response)
          if (response.data === 1) {
            commit('SET_USER_INFO', user)
          }
        },
        (error) => {
          console.log(error)
        }
      )
    }
  }
}

export default userStore

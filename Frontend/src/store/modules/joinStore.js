import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'


const api_url = API_BASE_URL + '/api'
const joinStore = {
  namespaced: true,
  state: {
    isChecked: true,
    accessToken: ''
  },
  getters: {
  },
  mutations: {
    SET_CHECKED: (state, isChecked) => { state.isChecked = isChecked },
    SET_ACCESSTOKEN: (state, accessToken) => {
      state.accessToken = accessToken
      // axios.defaults.headers.common.Authorization = 'Bearer accessToken'
    }
  },
  actions: {
    // 중복체크
    checkDuplicateId (context, id) {
      const params = {
        userId: id
      }
      instance({
        method: 'get',
        // eslint-disable-next-line camelcase
        url: api_url + '/user/duplication/id',
        params
      }).then(res => {
        if (res.data === true) {
          alert('중복된 아이디입니다!')
          context.commit('SET_CHECKED', false)
        } else {
          alert('사용가능한 아이디입니다!')
          context.commit('SET_CHECKED', true)
        }
      }).catch((res) => {
      })
    },
    checkDuplicateEmail (context, email) {
      const params = {
        email: email
      }
      instance({
        method: 'get',
        url: api_url + '/user/duplication/email',
        params
      }).then(res => {
        if (res.data === true) {
          alert('중복된 이메일입니다!')
          context.commit('SET_CHECKED', false)
        } else {
          alert('사용가능한 이메일입니다!')
          context.commit('SET_CHECKED', true)
        }
      }).catch((res) => {
      })
    },
    checkJoin (context, userJoin) {
      instance.post(api_url + '/user/join', userJoin, {
      }).then(res => {
        if (context.state.isChecked === true) {
          alert('회원가입이 완료되었습니다!')
          localStorage.setItem('userId', userJoin.userId)
          localStorage.setItem('accessToken', res.data)
          context.commit('SET_ACCESSTOKEN', res.data)
          router.push({ name: 'addInformation' })
        } else {
          //중복확인을 누르지 않았을 시
          alert('아이디중복 또는 이메일중복을 확인해주세요.')
          context.commit('SET_CHECKED', true)
        }
      }).catch((err) => {
        if  (err.response.status === 409) {
          alert(err.response.data)
        }else {
          alert('회원가입에 실패하였습니다.')
        }
      })
    }
  },
  modules: {
  }
}
export default joinStore

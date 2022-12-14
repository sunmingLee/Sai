/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'

// import vueCookies from 'vue-cookies'

// axios.defaults.withCredentials = true
// const instance = axios.create({
//   headers: {
//     Authorization: this.state.accessToken
//   }
// })

// const { cookies } = VueCookies()
const api_url = API_BASE_URL + '/api/user'
const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: [],
    accessToken: '',
    // userInfo: {
    //   userId: 'cjftn',
    //   familyId: 123456,
    //   userName: '이철수',
    //   email: 'cjftn@naver.com',
    //   password: 'asdf@1234'
    // }
    isAddInfo: true
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo
    }
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo
    },
    SET_ACCESSTOKEN: (state, accessToken) => {
      state.accessToken = accessToken
      // axios.defaults.headers.common.Authorization = 'Bearer accessToken'
    }
  },
  actions: {
    // 로그인
    login ({ commit, dispatch }, user) {
      const data = {
        userId: user.userId,
        password: user.password
      }
      instance.post(api_url + '/login', data, {
      })
        .then((res) => {
          if (res.status === 200) {
            // const jwtToken = res.headers['Set-Cookie']
            // vueCookies.set('accessToken', res.data) // return this
            localStorage.setItem('userId', data.userId)
            localStorage.setItem('accessToken', res.data)
            commit('SET_ACCESSTOKEN', res.data)
            commit('SET_IS_LOGIN', true)
            commit('SET_IS_LOGIN_ERROR', false)
            dispatch('getUserInfo', data.userId)
          }
        })
        .catch((err) => {
          alert('아이디와 비밀번호를 다시한번 확인해주세요.')
        })
    },
    // 로그인 후 회원정보 요청
    getUserInfo ({ commit }, user) {
      const data = {
        userId: user
        // password: user.password
      }
      instance.post(api_url + '/login/info', data)
        .then((res) => {
          // familyId가 있는 경우, 메인으로 이동
          if (res.status === 200 & res.data.familyId != null) {
            localStorage.setItem('familyId', res.data.familyId)
            alert('환영합니다! 가족들의 피드를 보러갈까요!')
            router.replace({ name: 'feed' })
          } else { // familyId가 없는 경우
            if (!res.data.familyRegYN) { // 가족 미신청
              router.push({ name: 'familyCode' })
            } else if (res.data.familyRegYN && res.data.approvedYN === null) { // 가족 신청 후 대기
              alert('아직 가족 신청이 수락되지 않았습니다.')
              router.push({ name: 'applywait' })
            } else if (res.data.familyRegYN && !res.data.approvedYN) { // 가족 신청 후 거절당함
              alert('가족 신청이 거절되었습니다.')
              router.push({ name: 'applyDecline' })
            }
          }
        })
        .catch((err) => {
        })
    },
    // 아이디 찾기
    findId ({ commit }, userInfo) {
      const params = {
        userName: userInfo.userName,
        email: userInfo.email
      }
      instance({
        url: api_url + '/findId',
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.msg.indexOf('입력하신 이메일로 아이디가 전송되었습니다.') != -1) {
            alert(res.data.msg)
            router.push({ name: 'home' })
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
        })
    },
    // 비밀번호 찾기
    findPassword ({ commit }, userInfo) {
      const params = {
        userName: userInfo.userName,
        userId: userInfo.userId,
        email: userInfo.email
      }
      instance({
        url: api_url + '/findPw',
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.msg.indexOf('입력하신 이메일로 임시 비밀번호가 전송되었습니다.') != -1) {
            alert(res.data.msg)
            router.push({ name: 'home' })
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
        })
    },
    // 비밀번호 확인
    checkPassword ({ commit }, userInfo) {
      const params = userInfo.password
      instance({
        url: api_url + '/verify/' + userInfo.userId,
        method: 'POST',
        data: params,
        headers: {
          'Content-Type': 'text/html; charset=utf-8'
        }
      })
        .then((res) => {
          router.push({ name: 'account' })
        })
        .catch((err) => {
          alert('비밀번호가 틀렸습니다')
        })
    },
    // 비밀번호 변경
    updatePassword ({ commit }, userInfo) {
      const params = {
        userId: userInfo.id,
        password: userInfo.password
      }
      // const password = userInfo.password
      instance({
        url: api_url + `/profile/${userInfo.id}`,
        method: 'PATCH',
        params
      })
        .then((res) => {
          alert('비밀번호가 변경되었습니다.')
        })
        .catch((err) => {
        })
    },
    // 회원 탈퇴
    withdrawalMember ({ commit }, userId) {
      instance({
        url: api_url + `/${userId}`,
        method: 'DELETE'
      })
        .then((res) => {
          alert('회원 탈퇴가 되었습니다. 그동안 이용해주셔서 감사합니다.')
          router.push({ name: 'home' })
        })
        .catch((err) => {
        })
    },
    // 유저(회원) 정보 조회
    checkUserInfo ({ commit }, userId) {
      instance({
        url: api_url + '/' + userId,
        method: 'GET'
      })
        .then((res) => {
          commit('SET_USER_INFO', res.data)
          localStorage.setItem('userInfo', JSON.stringify(res.data))
        })
        .catch((err) => {
        })
    },
    // 회원 정보 추가
    addUserInfo ({ commit }, userInfo) {
      const files = userInfo.fileList
      const addInfo = userInfo.userInfo

      const formData = new FormData()
      if (files !== undefined) {
        formData.append('file', files[0])
      }
      formData.append('addInfo', new Blob([JSON.stringify(addInfo)], { type: 'application/json' }))
      instance({
        url: api_url + '/addInfo',
        method: 'POST',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then((res) => {
          alert('추가 정보가 입력되었습니다')
          // 회원 가입 후
          router.push({ name: 'familyCode' })
        })
        .catch((err) => {
        })
    },
    // 회원정보 수정
    modifyUserInfo ({ commit }, userInfo) {
      const files = userInfo.fileList
      const addInfo = userInfo.userInfo
      const formData = new FormData()
      if (files !== undefined) {
        formData.append('file', files[0])
      }
      formData.append('addInfo', new Blob([JSON.stringify(addInfo)], { type: 'application/json' }))
      instance({
        url: api_url + '/addInfo',
        method: 'POST',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then((res) => {
          alert('추가 정보가 입력되었습니다')
          // 개인 페이지에서 온 경우
          router.push({ name: 'myPage' })
        })
        .catch((err) => {
        })
    },
    out ({ commit }) {
      instance({
        url: api_url + '/logout',
        method: 'POST'
      })
        .then((res) => {
          window.localStorage.clear()
          router.push({ name: 'login' })
        })
        .catch((err) => {
        })
    }

  },
  modules: {

  }
}

export default userStore

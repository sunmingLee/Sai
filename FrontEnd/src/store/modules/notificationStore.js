/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'

const api_url = API_BASE_URL + '/notification'
const notificationStore = {
  namespaced: true,
  state: {
    notificationList: []
  },
  getters: {
  },
  mutations: {
    SET_NOTIFICATION_LIST: (state, notificationList) => {
      state.notificationList = notificationList
    }
  },
  actions: {
    // 알림 확인 처리
    readNotification ({ commit }, userId) {
      axios.put(api_url + `/${userId}`)
        .then((res) => {
          if (res.status === 200) {
            // console.log(res)
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 알림 리스트 조회
    listNotification ({commit}, pageInfo) {
      const params = {
        page: pageInfo.currentPage,
        size: pageInfo.perPage
      }
      // console.log(params)
      axios.get(api_url + '/' + localStorage.getItem('userId'), { params })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            // console.log(res.data.notiList)
            commit('SET_NOTIFICATION_LIST', res.data.notiList)
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 알림 삭제
    deleteNotification ({ commit }, notiId) {
      axios.delete(api_url + `${notiId}`, { data: localStorage.userId })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            // 새로고침
            router.go()
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 알림 전체 삭제
    deleteAllNotification ({ commit }) {
      axios.delete(api_url, { data: localStorage.userId })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            // 새로고침
            router.go()
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}

export default notificationStore

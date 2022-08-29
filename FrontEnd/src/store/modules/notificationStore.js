/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'


const api_url = API_BASE_URL + '/notification'
const notificationStore = {
  namespaced: true,
  state: {
    notificationList: []
  },
  getters: {
    notificationCount(state) {
      const notiList = state.notificationList
      let count = 0;
      for(let i = 0; i < notiList.length; i++) {
        if(!notiList[i].notiReadYn) {
          count++;
        }
      }
      return count
    }
  },
  mutations: {
    SET_NOTIFICATION_LIST: (state, notificationList) => {
      state.notificationList = notificationList
    }
  },
  actions: {
    // 알림 확인 처리
    readNotification ({ commit }, userId) {
      instance.put(api_url + `/${userId}`)
        .then((res) => {
          if (res.status === 200) {
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
        })
    },
    // 알림 리스트 조회
    listNotification ({ commit }, pageInfo) {
      const params = {
        page: pageInfo.currentPage,
        size: pageInfo.perPage
      }
      instance.get(api_url + '/' + localStorage.getItem('userId'), { params })
        .then((res) => {
          if (res.status === 200) {
            commit('SET_NOTIFICATION_LIST', res.data.notiList)
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
        })
    },
    // 알림 삭제
    deleteNotification ({ commit, dispatch }, info) {
      instance.delete(api_url + `/${info.notiId}`, { data: localStorage.userId })
        .then((res) => {
          if (res.status === 200) {
            dispatch('listNotification', info.pageInfo)
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
        })
    },
    // 알림 전체 삭제
    deleteAllNotification ({ commit, dispatch }, pageInfo) {
      instance.delete(api_url, { data: localStorage.userId })
        .then((res) => {
          if (res.status === 200) {
            dispatch('listNotification', pageInfo)
          } else {
            alert(res.data.msg)
          }
        })
        .catch((err) => {
        })
    }
  }
}

export default notificationStore

/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'


const api_url = API_BASE_URL + '/plan'
const planStore = {
  namespaced: true,
  state: {
    planList: [],
    plan: []
  },
  getters: {

  },
  mutations: {
    PLAN_All_LIST (state, planList) {
      state.planList = planList
      console.log(state.planList)
    },
    SET_FEED (state, plan) {
      state.plan = plan
    }
  },
  actions: {
    // 일정 작성
    planCreate ({ commit }, planInfo) {
      console.log(planInfo)
      instance({
        url: api_url,
        method: 'POST',
        data: planInfo
      })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            alert('작성이 완료되었습니다')
          } else {
            alert('안됐지롱')
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
          alert('잘못된 코드입니다')
        })
    },
    // 일정 조회
    planAllList ({ commit }, info) {
      const familyId = info.familyId
      // const userId = info.userId
      instance({
        url: api_url + '/' + familyId,
        method: 'GET'
      })
        .then((res) => {
          console.log(res.data)
          const attrs = []
          for (let i = 0; i < res.data.length; i++) {
            if (res.data[i].allDayYn) {
              const plan = {
                highlight: 'red',
                date: new Date(res.data[i].mainPlanStartDatetime)
              }
              attrs.push(plan)
            } else {
              const plan = {
                highlight: {
                  color: 'red',
                  fillMode: 'light'
                },
                dates: {
                  start: new Date(res.data[i].mainPlanStartDatetime),
                  end: new Date(res.data[i].mainPlanEndDatetime)
                }
              }
              attrs.push(plan)
            }
          }
          // commit('PLAN_All_LIST', res.data)
          commit('PLAN_All_LIST', attrs)
        })
        .catch((err) => {
          console.log('에러')
          console.log(err)
        })
    },
    setPlanId ({ commit }, planId) {
      // console.log('boardId: ' + boardId)
      localStorage.setItem('planId', planId)
      router.push({ name: 'planDetail' })
    },
    // 일정 상세보기
    getOneFeed ({ commit }, info) {
      instance({
        url: api_url + `/${info.boardId}/${info.userId}`,
        method: 'GET'
      })
        .then((res) => {
          console.log(res)
          commit('SET_FEED', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 일정 삭제
    deleteFeed ({ commit }, boardId) {
      instance({
        url: api_url + `/board/${boardId}`,
        method: 'DELETE'
      })
        .then((res) => {
          alert('게시글이 삭제되었습니다.')
          router.push({ name: 'feed' })
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  modules: {

  }
}
export default planStore

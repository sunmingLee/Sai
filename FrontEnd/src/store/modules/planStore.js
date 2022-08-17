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
    plan: [],
    planDetail: []
  },
  getters: {
  },
  mutations: {
    PLAN_All_LIST (state, planList) {
      state.planList = planList
      // console.log(state.planList)
    },
    SET_PLAN_DETAIL (state, planDetail) {
      state.planDetail = planDetail
      console.log(state.planDetail)
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
            router.go()
          } else {
            alert('일정 등록 중 문제가 생겼습니다.')
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
          alert('잘못된 코드입니다')
        })
    },
    // 일정 조회
    planAllList ({ commit }, familyId) {
      axios({
        url: api_url + '/' + familyId,
        method: 'GET'
      })
        .then((res) => {
          // console.log(res.data)
          commit('SET_PLAN_DETAIL', res.data)
          const attrs = []
          for (let i = 0; i < res.data.length; i++) {
            const plan = {}
            // 하루 일정
            if (res.data[i].mainPlanEndDatetime == null || res.data[i].mainPlanStartDatetime.slice(0, 10) === res.data[i].mainPlanEndDatetime.slice(0, 10)) {
              const year = res.data[i].mainPlanStartDatetime.slice(0, 4)
              const month = res.data[i].mainPlanStartDatetime.slice(5, 7) - 1
              const date = res.data[i].mainPlanStartDatetime.slice(8, 10)
              // console.log(year + ' ' + month + ' ' + date)
              plan.dates = new Date(year, month, date)
              if (res.data[i].planType === 'personal') {
                plan.dot = 'red'
              } else if (res.data[i].planType === 'family') {
                plan.dot = 'green'
              } else {
                plan.dot = 'blue'
              }
            } else { // 기간 일정
              plan.dates = {
                start: new Date(res.data[i].mainPlanStartDatetime),
                end: new Date(res.data[i].mainPlanEndDatetime)
              }
              plan.highlight = {
                fillMode: 'light'
              }
              if (res.data[i].planType === 'personal') {
                plan.highlight.color = 'red'
              } else if (res.data[i].planType === 'family') {
                plan.highlight.color = 'green'
              } else {
                plan.highlight.color = 'blue'
              }
            }
            plan.popover = {
              label: res.data[i].planTitle
            }
            attrs.push(plan)
          }
          // commit('PLAN_All_LIST', res.data)
          commit('PLAN_All_LIST', attrs)
        })
        .catch((err) => {
          console.log('에러')
          console.log(err)
        })
    },
    // 일정 수정하기
    planModify ({ commit, dispatch }, info) {
      // console.log(info.plan)
      axios({
        url: api_url + `/${info.mainPlanId}`,
        method: 'PUT',
        data: info
      })
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            alert('수정이 완료되었습니다')
            dispatch('planAllList', info.familyId)
          } else {
            alert('일정 수정 중 문제가 생겼습니다.')
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
          alert('잘못된 코드입니다')
        })
    },
    // 일정 삭제
    planDelete ({ commit, dispatch }, planId) {
      axios({
        url: api_url + `/${planId}`,
        method: 'DELETE'
      })
        .then((res) => {
          dispatch('planAllList', localStorage.getItem('familyId'))
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

import { createStore } from 'vuex'
import router from '@/router'
import axios from 'axios'

const api_url = `http://localhost:8080/api`

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    // 중복체크
    checkDupilicateId ({ commit }, id) {
      const params = {
        userId: id
      }
      axios({
        method: 'get',
        // eslint-disable-next-line camelcase
        url: api_url + `/user/duplication/id`,
        params
      }).then(res => {
        if (res.data === true) {
          alert('중복된 아이디입니다!')
        } else {
          console.log(res)
          alert('사용가능한 아이디입니다!')
        }
      }).catch((res) => {
        console.log(res)
      })
    },
    checkDupilicateEmail ({ commit }, email) {
      console.log(email)
      const params = {
        email: email
      }
      axios({
        method: 'get',
        url: api_url + `/user/duplication/email`,
        params,
        //data: JSON.stringify(params),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(res => {
        if (res.data === true) {
          console.log(res)
          alert('중복된 이메일입니다!')
        } else {
          alert('사용가능한 이메일입니다!')
          this.emailPass = true
        }
      }).catch((res) => {
        console.log(res)
      })
    },
    join ({ commit }, userJoin) {
      const params = {
        userInfo: userJoin
      }
      axios({
        method: 'post',
        url: api_url + '/join',
        params
      }).then(res => {

      })
    }
  },
  modules: {
  }
})

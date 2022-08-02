import router from '@/router'
import axios from 'axios'
import Vue from 'vue'
import Vuex from 'vuex'

export default new Vuex.Store({
  state: {
    userId: 'tnqls',
    userName: 'subin',
    email: '5515983@naver.com'
  },
  getters: {
  },
  mutations: {
    
  },
  actions: {
    //아이디 찾기
    findId({commit}, userInfo) {
        const api_url = `http://localhost:8080/api/user/findId`
        const params = {
            userName: userInfo.userName,
            email: userInfo.email
        }
        axios({
            url: api_url,
            method: 'GET',
            params
        })
        .then((res)=> {
            if(res.data.msg.indexOf('입력하신 이메일로 아이디가 전송되었습니다.') != -1) {
                alert(res.data.msg)
                router.push({name : 'home'})
            }
            else {
                alert(res.data.msg)
            }
        })
        .catch((err)=> {
            console.log(err)
        })
    },
    //비밀번호 찾기
    findPassword({commit}, userInfo) {
        const api_url = `http://localhost:8080/api/user/findPw`
        const params = {
            userName: userInfo.userName,
            userId: userInfo.userId,
            email: userInfo.email
        }
        axios({
            url: api_url,
            method: 'GET',
            params
        })
        .then((res)=> {
            if(res.data.msg.indexOf('입력하신 이메일로 임시 비밀번호가 전송되었습니다.') != -1) {
                alert(res.data.msg)
                router.push({name : 'home'})
            }
            else {
                alert(res.data.msg)
            }
        })
        .catch((err)=> {
            console.log(err)
        })
    },
    //비밀번호 변경
    updatePassword({commit}, userInfo) {
        const api_url= `http://localhost:8080/api/user/profile/${userInfo.id}`
        const params = {
            userId: userInfo.id,
            password: userInfo.password
        }
        // const password = userInfo.password
        axios({
            url: api_url,
            method: 'PATCH',
            params
        })
        .then((res) => {
            alert('비밀번호가 변경되었습니다.')
            console.log(res)
        })
        .catch((err) => {
            console.log(err)
        })
    },
    //회원 탈퇴
    withdrawalMember({commit}, userId) {
        const api_url = `http://localhost:8080/api/user/${userId.id}`
        axios({
            url: api_url,
            method: 'DELETE',
        })
        .then((res) => {
            alert('회원 탈퇴가 되었습니다. 그동안 이용해주셔서 감사합니다.')
            router.push({name : 'home'})
        })
        .catch((err) => {
            console.log(err)
        })
    }
  },
  modules: {
  }
})
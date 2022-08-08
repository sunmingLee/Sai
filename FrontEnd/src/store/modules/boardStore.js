/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'

const api_url = 'http://localhost:8080/feed'
// const api_url = 'http://i7a305.p.ssafy.io:8080/feed'
const boardStore = {
  namespaced: true,
  state: {
    feedList: [],
    replyList: []
  },
  getters: {

  },
  mutations: {
    FEED_All_LIST (state, feed) {
      state.feedList = feed
    },
    SET_REPLY_LIST (state, replyList) {
      state.replyList = replyList
    }
  },
  actions: {
    // 게시글 작성
    boardCreate ({ commit }, boardInfo) {
      console.log('작성')
      console.log(boardInfo)
      axios({
        url: api_url + '/board',
        method: 'POST',
        // params
        data: JSON.stringify(boardInfo),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          // console.log(res)
          if (res.status === 200) {
            alert('작성이 완료되었습니다')
            router.push({ name: 'feed' })
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
    // 피드 조회
    feedAllList ({ commit }, info) {
      const familyId = info.familyId
      const userId = info.userId

      axios({
        url: api_url + familyId + '/' + userId,
        method: 'GET'
      })
        .then((res) => {
          commit('FEED_All_LIST', res.data)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 댓글 조회
    getReplyList ({ commit }, boardId) {
      axios({
        url: api_url + `/${boardId}` + '/reply',
        method: 'GET'
      })
        .then((res) => {
          // console.log(res)
          commit('SET_REPLY_LIST', res.data.replyList)
        })
        .catch((err) => {
          console.log(err)
        })
    }
  },
  modules: {

  }
}
export default boardStore

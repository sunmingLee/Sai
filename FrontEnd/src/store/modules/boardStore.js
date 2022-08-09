/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'

const api_url = API_BASE_URL + '/feed'
const boardStore = {
  namespaced: true,
  state: {
    feedList: [],
    replyList: [],
    feed: []
  },
  getters: {

  },
  mutations: {
    FEED_All_LIST (state, feed) {
      state.feedList = feed
      console.log(state.feedList)
    },
    SET_REPLY_LIST (state, replyList) {
      state.replyList = replyList
    },
    SET_FEED (state, feed) {
      state.feed = feed
    }
  },
  actions: {
    // 게시글 작성
    boardCreate ({ commit }, boardInfo) {
      const files = boardInfo.fileList
      const createBoardRequestDto = boardInfo.createBoardRequestDto
      const formData = new FormData()
      if (files !== undefined) {
        for (let i = 0; i < files.length; i++) {
          console.log(files[i])
          formData.append('files', files[i])
        }
      }
      formData.append('createBoardRequestDto', new Blob([JSON.stringify(createBoardRequestDto)], { type: 'application/json' }))
      axios({
        url: api_url + '/board',
        method: 'POST',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
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
      console.log('피드 리스트 조회')
      console.log(familyId + ' - ' + userId)
      axios({
        url: api_url + '/' + familyId + '/' + userId,
        method: 'GET'
      })
        .then((res) => {
          console.log('졸려')
          commit('FEED_All_LIST', res.data)
        })
        .catch((err) => {
          console.log('에러')
          console.log(err)
        })
    },
    // 게시글 상세보기
    getOneFeed ({ commit }, info) {
      axios({
        url: api_url + `/board/${info.boardId}/${info.userId}`,
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
    // 게시글 삭제
    deleteFeed ({ commit }, boardId) {
      axios({
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
    },
    // 댓글 작성
    createReply ({ commit, dispatch }, info) {
      const data = {
        userId: info.userId,
        replyContent: info.replyContent
      }
      axios({
        url: api_url + `/${info.boardId}` + '/reply',
        method: 'POST',
        data: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          dispatch('getReplyList', data.boardId)
          router.go()
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 댓글 수정
    // 댓글 삭제
    deleteReply ({ commit, dispatch }, info) {
      const data = {
        replyId: info.replyId,
        userId: info.userId
      }
      axios({
        url: api_url + `/${info.boardId}` + '/reply',
        method: 'DELETE',
        data: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          dispatch('getReplyList', data.boardId)
          router.go()
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

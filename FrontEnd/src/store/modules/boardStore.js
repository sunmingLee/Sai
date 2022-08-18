/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'
import { instance } from '@/api/index.js'

const api_url = API_BASE_URL + '/feed'
const boardStore = {
  namespaced: true,
  state: {
    feedList: [],
    replyList: [],
    feed: [],
    myFeedList: [],
    myFeedCnt: 0,
    likeList: [],
    myFeedPagenationCnt: 0,
  },
  getters: {
    
  },
  mutations: {
    FEED_All_LIST (state, feed) {
      state.feedList = state.feedList.concat(feed)
    },
    SET_REPLY_LIST (state, replyList) {
      state.replyList = replyList
    },
    SET_FEED (state, feed) {
      state.feed = feed
    },
    MY_FEED_All_LIST (state, myFeed) {
      state.myFeedList = myFeed
    },
    MY_FEED_All_LIST_COUNT (state, myFeedNum) {
      state.myFeedCnt = myFeedNum
    },
    FEED_RESET (state) {
      state.feedList = []
    },
    MY_FEED_PAGINATION(state, myFeedNum) {
      state.myFeedPagenationCnt = Math.ceil(myFeedNum / 3) 
    },
    LIKE_CHANGE(state, likeInfo) {
      for(let i = 0; i < state.feedList.length; i++) {
        if(state.feedList[i].viewBoardResponseDto.boardId === likeInfo.boardId) {
          console.log(state.feedList[i].boardLiked)
          if(!state.feedList[i].boardLiked) {
            state.feedList[i].viewBoardResponseDto.boardLikeCnt = state.feedList[i].viewBoardResponseDto.boardLikeCnt + 1
            state.feedList[i].boardLiked = true
          } else {
            state.feedList[i].viewBoardResponseDto.boardLikeCnt = state.feedList[i].viewBoardResponseDto.boardLikeCnt - 1
            state.feedList[i].boardLiked = false
          }
        }
      }
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
      instance({
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
    feedAllList ({ commit, state }, info) {
      const familyId = info.familyId
      const userId = info.userId
      const params = {
        page: info.page
      }
      instance({
        url: api_url + '/' + familyId + '/' + userId,
        method: 'GET',
        params
      })
        .then((res) => {
          if (res.data.length === 0 & info.page !== 0) {
            alert('더 이상 불러올 게시물이 없습니다')
          } else {
            commit('FEED_All_LIST', res.data)
          }
        })
        .catch((err) => {
          console.log('에러')
          console.log(err)
        })
    },
    setBoardId ({ commit }, boardId) {
      // console.log('boardId: ' + boardId)
      localStorage.setItem('boardId', boardId)
      router.push({ name: 'feedDetail' })
    },
    // 게시글 상세보기
    getOneFeed ({ commit }, info) {
      instance({
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
    },
    // 게시글 수정
    boardUpdate ({ commit }, updateBoardRequestDto) {
      instance({
        url: api_url + '/board',
        method: 'PUT',
        data: JSON.stringify(updateBoardRequestDto),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          console.log('수정이 됐어요')
          alert("수정되었습니다.")
          router.push({ name: 'feed' })
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 투표 선택 (임시 / 쿠키 세팅 이슈)
    chooseVote ({ commit }, info) {
      const data = {
        choiceId: info.choiceId
      }
      instance({
        url: API_BASE_URL + `/api/poll/${info.pollId}/votes`,
        method: 'POST',
        data: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          router.go()
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 좋아요 등록
    upBoardLike ({ commit, dispatch }, info) {
      instance({
        url: api_url + `/board/${info.boardId}/like/${info.userId}`,
        method: 'POST'
      })
        .then((res) => {
          dispatch('getOneFeed', info)
          commit('LIKE_CHANGE', info)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 좋아요 취소
    downBoardLike ({ commit, dispatch }, info) {
      instance({
        url: api_url + `/board/${info.boardId}/like/${info.userId}`,
        method: 'DELETE'
      })
        .then((res) => {
          dispatch('getOneFeed', info)
          commit('LIKE_CHANGE', info)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 댓글 조회
    getReplyList ({ commit }, boardId) {
      instance({
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
      instance({
        url: api_url + `/${info.boardId}` + '/reply',
        method: 'POST',
        data: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          dispatch('getReplyList', info.boardId)
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
      instance({
        url: api_url + `/${info.boardId}` + '/reply',
        method: 'DELETE',
        data: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then((res) => {
          dispatch('getReplyList', info.boardId)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 개인페이지 피드 조회
    myFeedAllList ({ commit }, info) {
      const params = {
        page: info.page
      }
      instance({
        url: API_BASE_URL + '/api/user/myPage/' + info.userId,
        method: 'GET',
        params
      })
        .then((res) => {
          console.log(res.data)
          commit('MY_FEED_All_LIST', res.data.readFeedResponseDtos)
          commit('MY_FEED_All_LIST_COUNT', res.data.userBoardNum)
          commit('MY_FEED_PAGINATION', res.data.userBoardNum)
        })
        .catch((err) => {
          console.log('에러')
          console.log(err)
        })
    },
    // 게시글 초기화
    feedReset ({ commit }) {
      commit('FEED_RESET')
    }
  },
  modules: {

  }
}
export default boardStore

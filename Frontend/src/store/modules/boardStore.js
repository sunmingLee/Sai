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
    pollChoiceList: []
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
    MY_FEED_PAGINATION (state, myFeedNum) {
      // 한 페이지당 게시글
      state.myFeedPagenationCnt = Math.ceil(myFeedNum / 6)
    },
    LIKE_CHANGE (state, likeInfo) {
      for (let i = 0; i < state.feedList.length; i++) {
        if (state.feedList[i].viewBoardResponseDto.boardId === likeInfo.boardId) {
          if (!state.feedList[i].boardLiked) {
            state.feedList[i].viewBoardResponseDto.boardLikeCnt = state.feedList[i].viewBoardResponseDto.boardLikeCnt + 1
            state.feedList[i].boardLiked = true
          } else {
            state.feedList[i].viewBoardResponseDto.boardLikeCnt = state.feedList[i].viewBoardResponseDto.boardLikeCnt - 1
            state.feedList[i].boardLiked = false
          }
        }
      }
    },
    SUB_ONE_VOTE (state, choiceId) {
      for (let i = 0; i < state.feed.pollResponse.choices.length; i++) {
        if (state.feed.pollResponse.choices[i].id === choiceId) {
          state.feed.pollResponse.choices[i].voteCount--
        }
      }
    },
    ADD_ONE_VOTE (state, choiceId) {
      for (let i = 0; i < state.feed.pollResponse.choices.length; i++) {
        if (state.feed.pollResponse.choices[i].id === choiceId) {
          state.feed.pollResponse.choices[i].voteCount++
        }
      }
    }
    // BEST_POLL_CHOICE(state, feed) {
    //   //투표가 있을 때
    //   if(feed.pollResponse !== null) {
    //     for(let i = 0; i < feed.pollResponse.choices.length; i++) {

    //     }
    //     this.pollChoiceList.push({
    //       boardId: feed.viewBoardResponseDto.boardId,

    //     })
    //   }
    // }
  },
  actions: {
    // 게시글 작성
    boardCreate ({ commit }, boardInfo) {
      const files = boardInfo.fileList
      const createBoardRequestDto = boardInfo.createBoardRequestDto
      const formData = new FormData()
      if (files !== undefined) {
        for (let i = 0; i < files.length; i++) {
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
          if (res.status === 200) {
            alert('작성이 완료되었습니다')
            router.push({ name: 'feed' })
          } else {
            alert('안됐지롱')
          }
        })
        .catch((err) => {
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
            // commit('BEST_POLL_CHOICE', res.data)
          }
        })
        .catch((err) => {
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
          commit('SET_FEED', res.data)
        })
        .catch((err) => {
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
          alert('수정되었습니다.')
          router.push({ name: 'feed' })
        })
        .catch((err) => {
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
        .then(res => {
          if (res.data.isDup) {
            alert('선택이 취소되었습니다.')
            commit('SUB_ONE_VOTE', data.choiceId)
            // router.go()
          } else {
            alert('선택하였습니다.')
            commit('ADD_ONE_VOTE', data.choiceId)
            // router.go()
          }
        })
        .catch((err) => {
          if (err.response.status === 400) {
            alert(err.response.data)
          }
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
        })
    },
    // 댓글 조회
    getReplyList ({ commit }, boardId) {
      instance({
        url: api_url + `/${boardId}` + '/reply',
        method: 'GET'
      })
        .then((res) => {
          commit('SET_REPLY_LIST', res.data.replyList)
        })
        .catch((err) => {
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
          commit('MY_FEED_All_LIST', res.data.readFeedResponseDtos)
          commit('MY_FEED_All_LIST_COUNT', res.data.userBoardNum)
          commit('MY_FEED_PAGINATION', res.data.userBoardNum)
        })
        .catch((err) => {
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

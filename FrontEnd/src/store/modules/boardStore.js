/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'

const api_url = 'http://localhost:8080/feed'
// const api_url = 'http://i7a305.p.ssafy.io:8080/feed'
const boardStore = {
  namespaced: true,
  state: {
    feedList: []
  },
  getters: {

  },
  mutations: {
    FEED_All_LIST (state, feed) {
      state.feedList = feed
    }
  },
  actions: {
    // 게시글 작성
    boardCreate ({ commit }, boardInfo) {
      // console.log('작성')
      const createBoardRequestDto = boardInfo.createBoardRequestDto;
      const files = boardInfo.fileList
      console.log(createBoardRequestDto)
      console.log(files)
      // let formData = new FormData();
      // formData.append('createBoardRequestDto', new Blob([JSON.stringify(createBoardRequestDto)], {type: "application/json"}));
      // formData.append('files', files)
      // axios({
      //   url: api_url + '/board',
      //   method: 'POST',
      //   data: formData,
      //   headers: {
      //     'Content-Type': 'multipart/form-data'
      //   }
      // })
      //   .then((res) => {
      //     // console.log(res)
      //     if (res.status === 200) {
      //       alert('작성이 완료되었습니다')
      //       router.push({ name: 'feed' })
      //     } else {
      //       alert('안됐지롱')
      //       console.log(res)
      //     }
      //   })
      //   .catch((err) => {
      //     console.log(err)
      //     alert('잘못된 코드입니다')
      //   })
    },
    // 피드 조회
    feedAllList ({ commit }, info) {
      const familyId = info.familyId
      const userId = info.userId
      console.log("피드 리스트 조회")
      console.log(familyId + " - " + userId)
      axios({
        url: api_url + '/' + familyId + '/' + userId,
        method: 'GET'
      })
      .then((res) => {
        console.log("졸려")
        commit('FEED_All_LIST', res.data)
      })
      .catch((err) => {
        console.log("에러")
        console.log(err)
      })
    }
  },
  modules: {

  }
}
export default boardStore

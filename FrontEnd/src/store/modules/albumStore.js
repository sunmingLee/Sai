/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'

const api_url = 'http://localhost:8080/album'
// const api_url = 'http://i7a305.p.ssafy.io:8080/album'
const albumStore = {
  namespaced: true,
  state: {
    folderList: []
  },
  getters: {

  },
  mutations: {
    SET_FOLDER_LIST: (state, folderList) => {
      state.folderList = folderList
    }
  },
  actions: {
    // 앨범 리스트 조회
    getFolderList ({ commit }, familyId) {
      axios.get(api_url + `/family/${familyId}`)
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            commit('SET_FOLDER_LIST', res.data)
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 앨범 생성
    makeAlbum ({ commit }, info) {
      axios.post(api_url + '/', info)
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            router.go()
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}

export default albumStore

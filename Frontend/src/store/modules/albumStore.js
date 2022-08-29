/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'

const api_url = API_BASE_URL + '/album'
const albumStore = {
  namespaced: true,
  state: {
    folderList: [],
    albumId: 0,
    albumName: '',
    mediaList: []
  },
  getters: {

  },
  mutations: {
    SET_FOLDER_LIST: (state, folderList) => {
      state.folderList = folderList
    },
    SET_ALBUM_ID: (state, albumId) => {
      state.albumId = albumId
    },
    SET_ALBUM_NAME: (state, albumName) => {
      state.albumName = albumName
    },
    SET_MEDIA_LIST: (state, mediaList) => {
      state.mediaList = mediaList
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
    makeAlbum ({ commit, dispatch }, info) {
      axios.post(api_url + '/', info)
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            dispatch('getFolderList', info.familyId)
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 앨범 삭제
    deleteAlbum ({ commit, dispatch }, albumId) {
      axios.delete(api_url + '/' + albumId)
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            dispatch('getFolderList', localStorage.getItem('familyId'))
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 앨범 정보 저장 후 앨범 상세 조회 페이지로 이동(로컬스토리지)
    setAlbumInfo ({ commit }, info) {
    //   commit('SET_ALBUM_ID', info.albumId)
    //   commit('SET_ALBUM_NAME', info.albumName)
      localStorage.setItem('albumId', info.albumId)
      localStorage.setItem('albumName', info.albumName)
      router.push({ name: 'picture' })
    },
    // 앨범 상세 조회(미디어리스트 조회)
    getMediaList ({ commit }) {
      axios.get(api_url + '/' + localStorage.getItem('albumId'))
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            commit('SET_MEDIA_LIST', res.data)
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 앨범 미디어 등록
    insertMedia ({ commit, dispatch }, mediaList) {
      const formData = new FormData()
      for (let i = 0; i < mediaList.length; i++) {
        console.log(mediaList[i])
        formData.append('files', mediaList[i])
      }
      axios({
        url: api_url + '/' + localStorage.getItem('albumId'),
        method: 'POST',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            dispatch('getMediaList')
          } else {
            console.log(res)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // 앨범 미디어 삭제
    deleteMedia ({ commit, dispatch }, albumMediaId) {
      axios.delete(api_url + '/media/' + albumMediaId)
        .then((res) => {
          console.log(res)
          if (res.status === 200) {
            dispatch('getMediaList')
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

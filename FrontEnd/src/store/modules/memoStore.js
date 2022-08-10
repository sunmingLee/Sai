/* eslint-disable camelcase */
import axios from 'axios'
import router from '@/router/index.js'
import { API_BASE_URL } from '@/config'

const api_url = API_BASE_URL + '/memo'
const albumStore = {
  namespaced: true,
  state: {
    albumName: '',
    memoAllList: []
  },
  getters: {

  },
  mutations: {
    // SET_FOLDER_LIST: (state, folderList) => {
    //   state.folderList = folderList
    // },
    SET_MEMO_ALL_LIST(state, memoList) {
      state.memoAllList = memoList
    }
  },
  actions: {
    //메모 생성
    async createMemo({commit}, createMemoRequestDto) {
      await axios({
        url: api_url,
        method: 'POST',
        data: JSON.stringify(createMemoRequestDto),
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((res) => {
        console.log(res)
        alert('등록되었습니다')
        router.push({ name: 'memo' })
      })
      .catch((err) => {
        console.log(err)
      })
    },
    //전체 메모 리스트 조회(냉장고 페이지에서 보여질 것)
    async getMemoAllList({commit}, familyId) {
      await axios({
        url: api_url + `/list/${familyId}`,
        method: 'GET',
      })
      .then((res) => {
        commit('SET_MEMO_ALL_LIST', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    //메모 삭제
    async deleteMemo({commit}, memoId) {
      await axios({
        url: api_url + `/${memoId}`,
        method: 'DELETE'
      })
      .then((res) => {
        console.log(res)
      })
      .catch((err) => {
        console.log(err)
      })
    }
    // 앨범 리스트 조회
  //   getFolderList ({ commit }, familyId) {
  //     axios.get(api_url + `/family/${familyId}`)
  //       .then((res) => {
  //         console.log(res)
  //         if (res.status === 200) {
  //           commit('SET_FOLDER_LIST', res.data)
  //         } else {
  //           console.log(res)
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err)
  //       })
  //   },
  //   // 앨범 생성
  //   makeAlbum ({ commit }, info) {
  //     axios.post(api_url + '/', info)
  //       .then((res) => {
  //         console.log(res)
  //         if (res.status === 200) {
  //           router.go()
  //         } else {
  //           console.log(res)
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err)
  //       })
  //   },
  //   // 앨범 삭제
  //   deleteAlbum ({ commit }, albumId) {
  //     axios.delete(api_url + '/' + albumId)
  //       .then((res) => {
  //         console.log(res)
  //         if (res.status === 200) {
  //           router.go()
  //         } else {
  //           console.log(res)
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err)
  //       })
  //   },
  //   // 앨범 정보 저장 (로컬스토리지)
  //   setAlbumInfo ({ commit }, info) {
  //   //   commit('SET_ALBUM_ID', info.albumId)
  //   //   commit('SET_ALBUM_NAME', info.albumName)
  //     localStorage.setItem('albumId', info.albumId)
  //     localStorage.setItem('albumName', info.albumName)
  //     router.push({ name: 'picture' })
  //   },
  //   // 앨범 상세 조회(미디어리스트 조회)
  //   getMediaList ({ commit }) {
  //     axios.get(api_url + '/' + localStorage.getItem('albumId'))
  //       .then((res) => {
  //         console.log(res)
  //         if (res.status === 200) {
  //           commit('SET_MEDIA_LIST', res.data)
  //         } else {
  //           console.log(res)
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err)
  //       })
  //   },
  //   // 앨범 미디어 등록
  //   insertMedia (context, mediaList) {
  //     const formData = new FormData()
  //     for (let i = 0; i < mediaList.length; i++) {
  //       console.log(mediaList[i])
  //       formData.append('files', mediaList[i])
  //     }
  //     axios({
  //       url: api_url + '/' + localStorage.getItem('albumId'),
  //       method: 'POST',
  //       data: formData,
  //       headers: {
  //         'Content-Type': 'multipart/form-data'
  //       }
  //     })
  //       .then((res) => {
  //         console.log(res)
  //         if (res.status === 200) {
  //           router.go()
  //         } else {
  //           console.log(res)
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err)
  //       })
  //   },
  //   // 앨범 미디어 삭제
  //   deleteMedia ({ commit }, albumMediaId) {
  //     axios.delete(api_url + '/media/' + albumMediaId)
  //       .then((res) => {
  //         console.log(res)
  //         if (res.status === 200) {
  //           router.go()
  //         } else {
  //           console.log(res)
  //         }
  //       })
  //       .catch((err) => {
  //         console.log(err)
  //       })
  //   }
  }
}

export default albumStore

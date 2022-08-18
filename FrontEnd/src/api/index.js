import router from '@/router'
import axios from 'axios'
// import store from '@/store/index.js'

export const instance = axios.create(
)

instance.interceptors.request.use(
  (config) => {
    // config.headers.Authorization = store.state.accessToken
    // config.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    config.headers.Authorization = localStorage.getItem('accessToken')
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response.status === 401 || error.response.status === 403) {
      console.log('로그인 페이지로 갓!')
      router.replace({
        path: '/login'
        // query: { redirect: router.currentRoute.fullPath }
      })
    }
    return Promise.reject(error)
  }
)

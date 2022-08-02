import axios from 'axios'
import { API_BASE_URL } from '@/config'
import userStore from '@/store/userStore'
import router from '@/router'

// axios 객체 생성
function apiInstance () {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      'Content-type': 'application/json'
    }
  })

  instance.interceptors.request.use(
    (config) => {
      // HTTP Authorization 요청 헤더에 jwt-token을 넣음
      // 서버측 미들웨어에서 이를 확인하고 검증한 후 해당 API에 요청함.
      const token = sessionStorage.getItem('access-token')
      if (token === null) return config
      try {
        config.headers.Authorization = `Bearer ${token}`

        return config
      } catch (err) {
        console.error('[_axios.interceptors.request] config : ' + err)
      }
      return config
    },
    (error) => {
      // 요청 에러 직전 호출됩니다.
      return Promise.reject(error)
    }
  )

  /**
   2. 응답 인터셉터
   2개의 콜백 함수를 받습니다.
   */
  instance.interceptors.response.use(
    (response) => {
      /*
          http status가 200인 경우
          응답 성공 직전 호출됩니다.
          .then() 으로 이어집니다.
      */

      return response
    },

    (error) => {
      /*
          http status가 200이 아닌 경우
          응답 에러 직전 호출됩니다.
          .catch() 으로 이어집니다.
      */
      const {
        response: { status }
      } = error
      if (status === 401 || status === 500) {
        userStore.mutations.SET_IS_LOGIN(false)
        userStore.mutations.SET_USER_INFO(null)
        sessionStorage.removeItem('access-token')
        console.log('error')
        router.replace({
          path: 'login'
        })
      } else if (status === 403) {
        window.location.href = '/overview'
        alert('권한이 없습니다!')
      }
      return Promise.reject(error)
    }
  )
  return instance
}

export { apiInstance }

// import store from '@/store/index.js'
// import { instance } from '@/api/index.js'

// instance.interceptors.request.use(
//   (config) => {
//     // config.headers.Authorization = store.state.accessToken
//     console.log('나는 인터셉터야')
//     config.headers.Authorization = '되어랏!'
//     return config
//   },
//   (error) => {
//     return Promise.reject(error)
//   }
// )

// Add a response interceptor
//   instance.interceptors.response.use(
//     function (response) {
//       return response
//     },
//     function (error) {
//       return Promise.reject(error)
//     }
//   )

//   return instance

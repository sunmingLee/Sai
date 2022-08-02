import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index.js'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import AddInfoView from '../views/user/AddInfoView.vue'

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters['userStore/checkUserInfo']
  const getUserInfo = store._actions['userStore/getUserInfo']
  const token = sessionStorage.getItem('access-token')
  if (checkUserInfo == null && token) {
    await getUserInfo(token)
  }
  if (checkUserInfo === null) {
    alert('로그인이 필요한 페이지입니다..')
    next({ name: 'login' })
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next()
  }
}

const routes = [
  {
    path: '/',
    name: 'home',
    beforeEnter: onlyAuthUser,
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/user/addinfo',
    name: 'addinfo',
    component: AddInfoView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

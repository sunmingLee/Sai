import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index.js'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import AddInformationView from '../views/user/AddInformationView.vue'
import FamilyInviteView from '@/views/FamilyInviteView.vue'
import FamilyCodeView from '../views/FamilyCodeView.vue'
import ApplyWaitView from '../views/ApplyWaitView.vue'
import SearchIdView from '@/views/SearchIdView.vue'
import SearchPasswordView from '@/views/SearchPasswordView.vue'
import NotificationView from '@/views/NotificationView.vue'
import AlbumView from '@/views/AlbumView.vue'
import AlbumFolder from '@/components/album/AlbumFolder.vue'
import AlbumPicture from '@/components/album/AlbumPicture.vue'
import AlbumPictureDetail from '@/components/album/AlbumPictureDetail.vue'

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
    component: AddInformationView
  },
  {
    path: '/user/addinformation',
    name: 'addinformation',
    component: AddInformationView
  },
  {
    path: '/familyCode',
    name: 'familyCode',
    component: FamilyCodeView
  },
  {
    path: '/applywait',
    name: 'applywait',
    component: ApplyWaitView
  },
  {
    path: '/searchId',
    name: 'searchId',
    component: SearchIdView
  },
  {
    path: '/searchPassword',
    name: 'searchPassword',
    component: SearchPasswordView
  },
  {
    path: '/familyInvite',
    name: 'familyInvite',
    component: FamilyInviteView
  },
  {
    path: '/notification',
    name: 'notification',
    component: NotificationView
  },
  {
    path: '/album',
    name: 'album',
    component: AlbumView,
    redirect: '/album/folder',
    children: [
      {
        path: 'folder',
        name: 'folder',
        component: AlbumFolder
      },
      {
        path: 'picture',
        name: 'picture',
        component: AlbumPicture,
        props: true
      },
      {
        path: 'pictureDetail',
        name: 'pictureDetail',
        component: AlbumPictureDetail
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

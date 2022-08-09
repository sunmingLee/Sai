import { createRouter, createWebHistory } from 'vue-router'
import AccountConfirmView from '@/views/AccountConfirmView.vue'
import AccountManagementView from '@/views/AccountManagementView.vue'
import FeedView from '@/views/FeedView.vue'
import FeedCreateView from '@/views/FeedCreateView.vue'
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
import JoinView from '@/views/JoinView.vue'
import ApplyDeclineView from '@/views/ApplyDeclineView.vue'
// import test from '@/views/test.vue'

import store from '@/store/index.js'

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
    // redirect: '/login',
    // beforeEnter: onlyAuthUser,
    component: LoginView
  },
  // {
  //   path: '/',
  //   name: 'test',
  //   compoennt: test
  // },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/user/addinformation',
    name: 'addinformation',
    component: AddInformationView
  },
  {
    path: '/feed',
    name: 'feed',
    component: FeedView
  },
  {
    path: '/feedCreate',
    name: 'feedCreate',
    component: FeedCreateView
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
    path: '/applyDecline',
    name: 'applyDecline',
    component: ApplyDeclineView
  },
  {
    path: '/account',
    name: 'account',
    component: AccountManagementView
  },
  {
    path: '/accountConfirm',
    name: 'accountConfirm',
    component: AccountConfirmView
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
  },
  {
    path: '/join',
    name: 'join',
    component: JoinView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

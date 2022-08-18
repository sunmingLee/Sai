import { createRouter, createWebHistory } from 'vue-router'
import AccountConfirmView from '@/views/AccountConfirmView.vue'
import AccountManagementView from '@/views/AccountManagementView.vue'
import FeedView from '@/views/FeedView.vue'
import FeedCreateView from '@/views/FeedCreateView.vue'
import FeedDetailView from '../views/FeedDetailView.vue'
import LoginView from '../views/LoginView.vue'
import AddInformationView from '../views/user/AddInformationView.vue'
import ModifyInformationView from '../views/user/ModifyInformationView.vue'
import FamilyInviteView from '@/views/FamilyInviteView.vue'
import FamilyCodeView from '../views/FamilyCodeView.vue'
import FamilyInformationChangeView from '../views/FamilyInformationChangeView.vue'
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
import MemoView from '@/views/memo/MemoView.vue'
import MemoCreateView from '@/views/memo/MemoCreateView.vue'
import MypageView from '@/views/user/MypageView.vue'
import CalendarView from '@/views/CalendarView.vue'
import FeedUpdateView from '@/views/FeedUpdateView.vue'
// import test from '@/views/test.vue'

// import store from '@/store/index.js'

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
// const onlyAuthUser = async (to, from, next) => {
//   // console.log(store);
//   const checkUserInfo = store.getters['userStore/checkUserInfo']
//   const getUserInfo = store._actions['userStore/getUserInfo']
//   const token = sessionStorage.getItem('access-token')
//   if (checkUserInfo == null && token) {
//     await getUserInfo(token)
//   }
//   if (checkUserInfo === null) {
//     alert('로그인이 필요한 페이지입니다..')
//     next({ name: 'login' })
//     // router.push({ name: "signIn" });
//   } else {
//     // console.log("로그인 했다.");
//     next()
//   }
// }

const routes = [
  {
    path: '/',
    name: 'home',
    // redirect: '/login',
    // beforeEnter: onlyAuthUser,
    component: LoginView
  },
  // {
  //   path: '/test',
  //   name: 'test',
  //   compoennt: test
  // },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/user/addInformation',
    name: 'addInformation',
    meta: {
      requireAuth: true
    },
    component: AddInformationView,
    props: true
  },
  {
    path: '/user/modifyInformation',
    name: 'modifyInformation',
    meta: {
      requireAuth: true
    },
    component: ModifyInformationView,
    props: true
  },
  {
    path: '/feed',
    name: 'feed',
    meta: {
      requireAuth: true
    },
    component: FeedView
  },
  {
    path: '/feedCreate',
    name: 'feedCreate',
    meta: {
      requireAuth: true
    },
    component: FeedCreateView
  },
  {
    path: '/familyCode',
    name: 'familyCode',
    meta: {
      requireAuth: true
    },
    component: FamilyCodeView
  },
  {
    path: '/applywait',
    name: 'applywait',
    meta: {
      requireAuth: true
    },
    component: ApplyWaitView
  },
  {
    path: '/applyDecline',
    name: 'applyDecline',
    meta: {
      requireAuth: true
    },
    component: ApplyDeclineView
  },
  {
    path: '/account',
    name: 'account',
    meta: {
      requireAuth: true
    },
    component: AccountManagementView
  },
  {
    path: '/accountConfirm',
    name: 'accountConfirm',
    meta: {
      requireAuth: true
    },
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
    meta: {
      requireAuth: true
    },
    component: FamilyInviteView
  },
  {
    path: '/notification',
    name: 'notification',
    meta: {
      requireAuth: true
    },
    component: NotificationView
  },
  {
    path: '/album',
    name: 'album',
    meta: {
      requireAuth: true
    },
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
  },
  {
    path: '/memo',
    name: 'memo',
    meta: {
      requireAuth: true
    },
    component: MemoView
  },
  {
    path: '/memoCreate',
    name: 'memoCreate',
    meta: {
      requireAuth: true
    },
    component: MemoCreateView
  },
  {
    path: '/myPage',
    name: 'myPage',
    meta: {
      requireAuth: true
    },
    component: MypageView
  },
  {
    path: '/feedDetail',
    name: 'feedDetail',
    meta: {
      requireAuth: true
    },
    component: FeedDetailView,
    props: true
  },
  {
    path: '/familyInfoChange',
    name: 'familyInfoChange',
    meta: {
      requireAuth: true
    },
    component: FamilyInformationChangeView
  },
  {
    path: '/calendar',
    name: 'calendar',
    meta: {
      requireAuth: true
    },
    component: CalendarView
  },
  {
    path: '/feedUpdate',
    name: 'feedUpdate',
    meta: {
      requireAuth: true
    },
    component: FeedUpdateView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) { // 경로를 로그인 해야하는지 결정
    if (localStorage.getItem('accessToken')) { // 현재 token가 localStorage를 통해 얻어 지는지 여부
      next()
    } else {
      next({
        path: '/login'
        // query: { redirect: to.fullPath } // 점프의 라우팅을 매개 변수로 path로 돌린 다음 성공적으로 로깅 후 라우팅으로 이동합니다.
      })
    }
  } else {
    next()
  }
})

export default router

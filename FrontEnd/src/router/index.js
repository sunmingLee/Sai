import { createRouter, createWebHistory } from 'vue-router'
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

const routes = [
  // {
  //   path: '/',
  //   name: 'home',
  //   redirect: '/login',
  //   component: LoginView
  // },
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

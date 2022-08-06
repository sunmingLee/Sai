import { createRouter, createWebHistory } from 'vue-router'
import FamilyInviteView from '@/views/FamilyInviteView.vue'
import FamilyCodeView from '../views/FamilyCodeView.vue'
import ApplyWaitView from '../views/ApplyWaitView.vue'
import SearchIdView from '@/views/SearchIdView.vue'
import SearchPasswordView from '@/views/SearchPasswordView.vue'
import AlbumView from '@/views/AlbumView.vue'

const routes = [
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
    path: '/album',
    name: 'album',
    component: AlbumView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

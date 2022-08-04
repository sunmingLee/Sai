import { createRouter, createWebHistory } from 'vue-router'
import FeedView from '@/views/FeedView.vue'
import FeedCreateView from '@/views/FeedCreateView.vue'
import FamilyInviteView from '@/views/FamilyInviteView.vue'
import FamilyCodeView from '../views/FamilyCodeView.vue'
import ApplyWaitView from '../views/ApplyWaitView.vue'
import SearchIdView from '@/views/SearchIdView.vue'
import SearchPasswordView from '@/views/SearchPasswordView.vue'

const routes = [
  {
    path: '/feed',
    name: 'feed',
    component: FeedView
  },
  {
    path: '/feedCreate',
    name: 'feedCreate',
    component: FeedCreateView,
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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

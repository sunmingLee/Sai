import { createRouter, createWebHistory } from 'vue-router'
import SearchIdView from '@/views/SearchIdView.vue'
import SearchPasswordView from '@/views/SearchPasswordView.vue'
import JoinView from '@/views/JoinView.vue'
import FamilyInformationChangeView from '@/views/FamilyInformationChangeView.vue'
import FamilyInviteView from '@/views/FamilyInviteView.vue'

const routes = [

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
    path: '/join',
    name: 'join',
    component: JoinView
  },
  {
    path: '/familyInformationChange',
    name: 'familyInformationChange',
    component: FamilyInformationChangeView
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

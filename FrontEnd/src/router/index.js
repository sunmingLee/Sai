import { createRouter, createWebHistory } from 'vue-router'
import FamilyInviteView from '@/views/FamilyInviteView.vue'
import SearchIdView from '@/views/SearchIdView.vue'
import SearchPasswordView from '@/views/SearchPasswordView.vue'

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

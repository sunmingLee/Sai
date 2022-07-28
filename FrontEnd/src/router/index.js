import { createRouter, createWebHistory } from 'vue-router'
import FamilyInviteView from '@/views/FamilyInviteView.vue'

const routes = [
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

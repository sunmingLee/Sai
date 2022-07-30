import { createRouter, createWebHistory } from 'vue-router'
import FeedView from '@/views/FeedView.vue'

const routes = [
  {
    path: '/feed',
    name: 'feed',
    component: FeedView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

import { createRouter, createWebHistory } from 'vue-router'
import FeedView from '@/views/FeedView.vue'
import FeedCreateView from '@/views/FeedCreateView.vue'

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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

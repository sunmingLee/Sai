import { createRouter, createWebHistory } from 'vue-router'
import FeedView from '@/views/FeedView.vue'
import FeedCreateView from '@/views/FeedCreateView.vue'
import FeedCreateAdditionalView from '@/views/FeedCreateAdditionalView.vue'
import FeedCreatePollView from '@/views/FeedCreatePollView.vue'

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
    children: [
      {
        path: '/feedCreateAdditional',
        name: 'feedCreateAdditional',
        component: FeedCreateAdditionalView
      },
      {
        path: '/feedCreatePoll',
        name: 'feedCreatePoll',
        component: FeedCreatePollView
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

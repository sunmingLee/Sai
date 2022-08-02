import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
<<<<<<< HEAD
import AccountManagementView from '@/views/AccountManagementView.vue'
import AccountPasswordView from '@/views/AccountPasswordView.vue'
=======
import SearchIdView from '@/views/SearchIdView.vue'
import SearchPasswordView from '@/views/SearchPasswordView.vue'
>>>>>>> fb13be29169e3c2e65dbbe91d635a40d6bc20805

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
<<<<<<< HEAD
    path: '/account',
    name: 'account',
    component: AccountManagementView
  },
  {
    path: '/accountCheck',
    name: 'accountCheck',
    component: AccountPasswordView
=======
    path: '/searchId',
    name: 'searchId',
    component: SearchIdView
  },
  {
    path: '/searchPassword',
    name: 'searchPassword',
    component: SearchPasswordView
>>>>>>> fb13be29169e3c2e65dbbe91d635a40d6bc20805
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

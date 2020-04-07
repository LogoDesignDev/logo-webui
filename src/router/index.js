import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import personalRoutes from '@/router/personal'
import accountRoutes from '@/router/account'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    hidden: false,
    meta: {
      title: '首页'
    },
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    hidden: false,
    meta: {
      title: '其他'
    },
    component: () => import('../views/About.vue')
  },
  {
    path: '/login',
    name: 'login',
    hidden: true,
    component: () => import('../views/login/loginIndex.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  routes: [
    ...routes,
    ...personalRoutes,
    ...accountRoutes
  ]
})

export default router

import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/homeIndex.vue'

import personalRoutes from '@/router/personal'
import accountRoutes from '@/router/account'
import searchRoutes from '@/router/search'
import creationRoutes from '@/router/creation'

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
    path: '/rank',
    name: 'rank',
    hidden: false,
    meta: {
      title: '排行榜'
    },
    component: () => import('../views/rank/index.vue')
  },
  {
    path: '/mylogo',
    name: 'MyLogo',
    hidden: false,
    meta: {
      title: 'LOGO管理'
    },
    component: () => import('../views/mylogo/MyLogo.vue')
  },
  {
    path: '/mylogo/detail/:id',
    name: 'Detail',
    hidden: true,
    component: () => import('../views/mylogo/Detail.vue')
  },
  {
    path: '/mylogo/logodetail/:logoid',
    name: 'LogoDetail',
    hidden: true,
    component: () => import('../views/mylogo/LogoDetail.vue')
  },
  {
    path: '/login',
    name: 'login',
    hidden: true,
    component: () => import('../views/login/loginIndex.vue')
  },
  {
    path: '/rank',
    name: 'rank',
    hidden: true,
    component: () => import('../views/rank/index.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  routes: [
    ...routes,
    ...personalRoutes,
    ...accountRoutes,
    ...searchRoutes,
    ...creationRoutes
  ]
})

export default router

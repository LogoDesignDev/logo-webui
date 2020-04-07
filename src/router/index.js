import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

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
  },
  {
    path: '/account',
    redirect: '/account/base',
    name: 'account',
    hidden: true,
    component: () => import('../views/account/accountIndex.vue'),
    children: [
      {
        path: 'base',
        name: 'base',
        component: () => import('../views/account/base/baseIndex.vue')
      },
      {
        path: 'security',
        name: 'security',
        component: () => import('../views/account/security/securityIndex.vue')
      }]
  }
]

const router = new VueRouter({
  routes
})

export default router

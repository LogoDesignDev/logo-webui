import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    hidden: false,
    meta: {
      title: '首页'
    },
    component: Home
  },
  {
    path: '/about',
    name: 'About',
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
  routes
})

export default router

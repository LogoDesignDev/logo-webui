export default [
  {
    path: '/personal',
    name: 'home',
    hidden: false,
    meta: {
      title: '个人中心'
    },
    component: () => import('@/views/personal/personalIndex.vue')
  }
]

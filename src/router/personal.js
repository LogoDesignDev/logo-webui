export default [
  {
    path: '/personal',
    name: 'personal',
    hidden: true,
    meta: {
      title: '个人中心'
    },
    component: () => import('@/views/personal/personalIndex.vue')
  }
]

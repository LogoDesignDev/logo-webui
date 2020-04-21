export default [
  {
    path: '/personal',
    redirect: '/personal/followAndFans',
    name: 'personal',
    hidden: true,
    meta: {
      title: '个人中心'
    },
    component: () => import('@/views/personal/personalIndex.vue'),
    children: [
      {
        path: 'followAndFans',
        name: 'followAndFans',
        component: () => import('@/views/personal/followAndFans/FAFIndex.vue')
      }
    ]
  }
]

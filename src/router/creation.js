export default [
  {
    path: '/creation',
    name: 'creation',
    hidden: false,
    meta: {
      title: '创作'
    },
    component: () => import('@/views/creation/creationIndex.vue')
    // children: [
    //   {
    //     path: 'followAndFans',
    //     name: 'followAndFans',
    //     component: () => import('@/views/personal/followAndFans/FAFIndex.vue')
    //   }
    // ]
  }
]

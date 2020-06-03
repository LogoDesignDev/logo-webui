export default [
  {
    path: '/creation',
    name: 'creation',
    hidden: false,
    meta: {
      title: '创作'
    },
    component: () => import('@/views/creation/creationIndex.vue')
  }, {
    path: '/creation/generate',
    name: 'generate',
    hidden: true,
    meta: {
      title: '创作——生成'
    },
    component: () => import('@/views/creation/generate/generateIndex.vue')
  }]

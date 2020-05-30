export default [
  {
    path: '/search',
    name: 'search',
    hidden: true,
    meta: {
      title: '搜索'
    },
    component: () => import('@/views/search/searchIndex.vue'),
    children: []
  }
]

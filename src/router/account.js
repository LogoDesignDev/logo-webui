export default [
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

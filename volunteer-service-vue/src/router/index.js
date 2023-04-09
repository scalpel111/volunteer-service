import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/index',
    name: 'Index',
    component: () => import('../views/Index.vue'),
    children: [
      {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Home.vue')
      },
      {
        path: '/activity',
        name: 'Activity',
        component: () => import('../views/Activity.vue')
      },
      {
        path: '/institution',
        name: 'Institution',
        component: () => import('../views/Institution.vue')
      }
      
      // {
      //   path: '/admin',
      //   name: 'AdminManage',
      //   component: () => import('../views/admin/AdminManage.vue')
      // },
      // {
      //   path: '/user',
      //   name: 'UserManage',
      //   component: () => import('../views/user/UserManage.vue')
      // }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router

import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // ... 路由配置保持不变
  {
    path: '/admin',
    component: () => import('@/views/admin/Layout.vue'),
    children: [
      {
        path: '',  // 默认子路由
        redirect: '/admin/students'
      },
      {
        path: 'students',
        name: 'StudentManage',
        component: () => import('@/views/admin/StudentManage.vue')
      },
      {
        path: 'courses',
        name: 'CourseManage', 
        component: () => import('@/views/admin/CourseManage.vue')
      }
    ],
    meta: { requiresAdmin: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 添加路由守卫
router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  
  // 如果未登录，除了登录和注册页面，其他页面都跳转到登录页
  if (!user.role && to.path !== '/login' && to.path !== '/register') {
    next('/login')
    return
  }
  
  // 根据角色控制页面访问权限
  if (user.role) {
    if (user.role === 'admin' && !to.path.startsWith('/admin')) {
      next('/admin/users')
      return
    }
    if (user.role === 'student' && !to.path.startsWith('/student')) {
      next('/student')
      return
    }
    if (user.role === 'teacher' && !to.path.startsWith('/teacher')) {
      next('/teacher')
      return
    }
  }
  
  next()
})

export default router 
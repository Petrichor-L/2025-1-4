import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/admin',
    component: () => import('@/views/admin/Layout.vue'),
    children: [
      {
        path: 'users',
        component: () => import('@/views/admin/UserManage.vue')
      },
      {
        path: 'courses',
        component: () => import('@/views/admin/CourseManage.vue')
      }
    ],
    meta: { requiresAdmin: true }
  },
  // 添加学生路由
  {
    path: '/student',
    component: () => import('@/views/student/Layout.vue'),
    children: [
      {
        path: '',
        component: () => import('@/views/student/Dashboard.vue')
      },
      {
        path: 'grades',
        component: () => import('@/views/student/Grades.vue')
      }
    ]
  },
  // 添加教师路由
  {
    path: '/teacher',
    component: () => import('@/views/teacher/Layout.vue'),
    children: [
      {
        path: '',
        component: () => import('@/views/teacher/Dashboard.vue')
      },
      {
        path: 'courses',
        component: () => import('@/views/teacher/Courses.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 
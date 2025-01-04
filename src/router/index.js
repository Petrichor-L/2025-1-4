import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login.vue'
import Register from '../views/register.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: () => import('@/views/login.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: Register
    },
    // 学生路由
    {
        path: '/student',
        component: () => import('@/views/student/Dashboard.vue'),
        meta: { requiresAuth: true, role: 'student' },
        children: [
            {
                path: 'grades',
                component: () => import('@/views/student/Grades.vue')
            },
            {
                path: 'courses',
                component: () => import('@/views/student/Courses.vue')
            }
        ]
    },
    // 教师路由
    {
        path: '/teacher',
        component: () => import('@/views/teacher/Dashboard.vue'),
        children: [
            {
                path: 'grades',
                component: () => import('@/views/teacher/Grades.vue')
            },
            {
                path: 'courses',
                component: () => import('@/views/teacher/Courses.vue')
            },
            {
                path: 'grade-input',
                component: () => import('@/views/teacher/GradeInput.vue')
            }
        ]
    },
    // 管理员路由
    {
        path: '/admin',
        component: () => import('@/views/admin/AdminDashboard.vue'),
        meta: { requiresAuth: true, role: 'admin' },
        children: [
            {
                path: 'students',
                component: () => import('@/views/admin/Students.vue')
            },
            {
                path: 'courses',
                component: () => import('@/views/admin/Courses.vue')
            },
            {
                path: 'grades',
                component: () => import('@/views/admin/Grades.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    const userRole = localStorage.getItem('userRole')

    // 如果要访问的页面需要认证
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 检查是否已登录
        if (!token) {
            next('/login')
        } else {
            // 检查角色权限
            if (to.meta.role && to.meta.role !== userRole) {
                next('/login') // 或者跳转到错误页面
            } else {
                next()
            }
        }
    } else {
        // 如果已登录且要访问登录页，重定向到对应的首页
        if (token && to.path === '/login') {
            const routes = {
                admin: '/admin/students',
                teacher: '/teacher/grades',
                student: '/student/grades'
            }
            next(routes[userRole])
        } else {
            next()
        }
    }
})

export default router
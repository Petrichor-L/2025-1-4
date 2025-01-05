import { createRouter, createWebHistory } from 'vue-router'
import Register from '../views/register.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'login',
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
        children: [
            {
                path: 'grades',
                name: 'student-grades',
                component: () => import('@/views/student/Grades.vue')
            },
            {
                path: 'courses',
                name: 'student-courses',
                component: () => import('@/views/student/Courses.vue')
            }
        ],
        meta: {
            requiresAuth: true,
            role: 'student'
        }
    },
    // 教师路由
    {
        path: '/teacher',
        component: () => import('@/views/teacher/Dashboard.vue'),
        children: [
            {
                path: 'grades',
                name: 'teacher-grades',
                component: () => import('@/views/teacher/Grades.vue')
            },
            {
                path: 'courses',
                name: 'teacher-courses',
                component: () => import('@/views/teacher/Courses.vue')
            },
            {
                path: 'grade-input',
                name: 'grade-input',
                component: () => import('@/views/teacher/GradeInput.vue')
            }
        ],
        meta: {
            requiresAuth: true,
            role: 'teacher'
        }
    },
    // 管理员路由
    {
        path: '/admin',
        component: () => import('@/views/admin/Dashboard.vue'),
        children: [
            {
                path: 'students',
                name: 'admin-students',
                component: () => import('@/views/admin/Students.vue')
            },
            {
                path: 'courses',
                name: 'admin-courses',
                component: () => import('@/views/admin/Courses.vue')
            },
            {
                path: 'grades',
                name: 'admin-grades',
                component: () => import('@/views/admin/Grades.vue')
            }
        ],
        meta: {
            requiresAuth: true,
            role: 'admin'
        }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
    const userInfo = localStorage.getItem('user')
    const user = userInfo ? JSON.parse(userInfo) : null

    // 如果已登录且访问登录页或注册页，重定向到对应角色的首页
    if (user && (to.path === '/login' || to.path === '/register')) {
        const routes = {
            admin: '/admin/students',
            teacher: '/teacher/grades',
            student: '/student/grades'
        }
        next(routes[user.role])
        return
    }

    // 检查是否需要认证
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!user) {
            // 未登录，跳转到登录页
            next('/login')
        } else {
            // 检查用户角色是否匹配
            const requiredRole = to.matched.find(record => record.meta.role)?.meta.role
            if (requiredRole && requiredRole !== user.role) {
                // 角色不匹配，跳转到403页面
                next('/403')
            } else {
                next()
            }
        }
    } else {
        next()
    }
})

export default router
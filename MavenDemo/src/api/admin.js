import request from '@/utils/request'

// 用户管理
export function getUsers() {
  return request({
    url: '/api/admin/users',
    method: 'get'
  })
}

// 课程管理
export function getCourses() {
  return request({
    url: '/api/admin/courses',
    method: 'get'
  })
}

// 获取学生列表
export function getStudents(params) {
  return request({
    url: '/api/admin/students',
    method: 'get',
    params
  })
} 
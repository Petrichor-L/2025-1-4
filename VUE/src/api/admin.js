import request from '@/utils/request'

// 用户管理相关 API
export function getUsers() {
  return request({
    url: '/api/admin/users',
    method: 'get'
  })
}

export function getStudents(params) {
  return request({
    url: '/api/admin/students',
    method: 'get',
    params
  })
}

export function addUser(data) {
  return request({
    url: '/api/admin/users',
    method: 'post',
    data
  })
}

export function updateUser(username, data) {
  return request({
    url: `/api/admin/users/${username}`,
    method: 'put',
    data
  })
}

export function deleteUser(username) {
  return request({
    url: `/api/admin/users/${username}`,
    method: 'delete'
  })
}

// 课程管理相关 API
export function getCourses(params) {
  return request({
    url: '/api/admin/courses',
    method: 'get',
    params
  })
}

export function addCourse(data) {
  return request({
    url: '/api/admin/courses',
    method: 'post',
    data
  })
}

export function updateCourse(courseId, data) {
  return request({
    url: `/api/admin/courses/${courseId}`,
    method: 'put',
    data
  })
}

export function deleteCourse(courseId) {
  return request({
    url: `/api/admin/courses/${courseId}`,
    method: 'delete'
  })
} 
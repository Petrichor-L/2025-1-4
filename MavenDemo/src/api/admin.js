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

// 修改前端删除用户的API调用
export function deleteUser(username) {
  return request({
    url: `/api/admin/users/${username}`,
    method: 'delete'
  })
}

/**
 * 添加用户
 * @param {Object} data
 * @param {string} data.username - 学号
 * @param {string} data.name - 姓名
 * @param {string} [data.password] - 密码（可选）
 * @param {string} data.phone - 联系方式
 * @param {string} data.role - 角色
 */
export function addUser(data) {
  return request({
    url: '/api/admin/users',
    method: 'post',
    data
  })
}

/**
 * 更新用户信息
 * @param {string} username - 学号
 * @param {Object} data
 * @param {string} data.name - 姓名
 * @param {string} data.phone - 联系方式
 */
export function updateUser(username, data) {
  return request({
    url: `/api/admin/users/${username}`,
    method: 'put',
    data
  })
} 
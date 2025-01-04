import request from '@/utils/request'

// 获取学生列表
export function getStudents(params) {
  // 如果没有关键字，就不传这个参数
  const queryParams = params.keyword ? { keyword: params.keyword } : {}
  
  return request({
    url: '/api/admin/students',
    method: 'get',
    params: queryParams
  })
}

// 获取所有用户
export function getUsers() {
  return request({
    url: '/api/admin/users',
    method: 'get'
  })
}

// 删除用户
export function deleteUser(username) {
  return request({
    url: `/api/admin/users/${username}`,
    method: 'delete'
  });
}

// 添加学生（创建用户）
export function addStudent(data) {
  return request({
    url: '/api/admin/users',
    method: 'post',
    data
  })
}

// 更新用户信息
export function updateUser(username, data) {
  return request({
    url: `/api/admin/users/${username}`,
    method: 'put',
    data: {
      username: username,  // 添加 username 字段
      name: data.name,
      phone: data.phone
    }
  });
} 
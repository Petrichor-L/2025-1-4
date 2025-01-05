import request from '@/utils/request'

// 用户登录
export const login = (data) => {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}

// 用户注册
export const register = (data) => {
  return request({
    url: '/api/user/register',
    method: 'post',
    data
  })
}

// 用户登出
export const logout = () => {
  // 清除所有用户相关信息
  localStorage.removeItem('user')
  localStorage.removeItem('userRole')
  localStorage.removeItem('token')
} 
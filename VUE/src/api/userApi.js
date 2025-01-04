import request from '@/utils/request'

// 基本的用户登录请求
export const login = (data) => {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}

// 基本的用户注册请求
export const register = (data) => {
  return request({
    url: '/api/user/register',
    method: 'post',
    data
  })
}

// 用户登出
export const logout = () => {
  return request({
    url: '/api/user/logout',
    method: 'post'
  })
} 
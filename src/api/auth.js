import request from '@/utils/request'

export const login = (data) => {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}

export const register = (data) => {
  return request({
    url: '/api/user/register',
    method: 'post',
    data
  })
}

export const logout = () => {
  return request({
    url: '/api/user/logout',
    method: 'post'
  })
} 
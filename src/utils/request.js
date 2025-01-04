import axios from 'axios'
import router from '@/router'

// 创建 axios 实例
const request = axios.create({
  baseURL: 'http://localhost:8081',  // 基础URL，接口路径会拼接在后面
  timeout: 5000,
  withCredentials: true
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从 localStorage 获取 token (用户id)
    const token = localStorage.getItem('token')
    if (token) {
      // 根据后端要求设置认证头
      config.headers['Authorization'] = token // 移除 Bearer 前缀，根据后端要求调整
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // token 过期或无效，清除本地存储并跳转到登录页
          localStorage.removeItem('token')
          localStorage.removeItem('userRole')
          router.push('/login')
          break
        case 403:
          // 权限不足
          alert('权限不足')
          break
        default:
          alert('服务器错误，请稍后重试')
      }
    }
    return Promise.reject(error)
  }
)

export default request
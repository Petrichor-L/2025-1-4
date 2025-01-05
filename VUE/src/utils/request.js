import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建 axios 实例
const request = axios.create({
  timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 从 localStorage 获取完整的用户信息
    const userInfo = localStorage.getItem('user')
    if (userInfo) {
      const user = JSON.parse(userInfo)
      // 如果有 token，添加到请求头
      if (user.token) {
        config.headers['Authorization'] = `Bearer ${user.token}`
      }
    }
    return config
  },
  error => {
    console.error('Request Error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.error('Response Error:', error)
    if (error.response) {
      const { status } = error.response
      if (status === 401) {
        // 未登录或 token 失效
        ElMessage.error('登录状态已失效，请重新登录')
        localStorage.clear()  // 清除所有存储的数据
        router.push('/login')
      } else if (status === 403) {
        // 无权限访问
        ElMessage.error('无权限访问')
        router.push('/403')
      }
    }
    return Promise.reject(error)
  }
)

export default request
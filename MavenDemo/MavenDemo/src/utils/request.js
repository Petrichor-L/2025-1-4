import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 5000,
  withCredentials: true  // 允许携带cookie
})

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '错误')
      return Promise.reject(new Error(res.message || '错误'))
    }
    return res
  },
  error => {
    console.error('请求错误：', error)
    ElMessage.error(error.message || '请求失败')
    return Promise.reject(error)
  }
)

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 从localStorage获取用户信息
    const user = JSON.parse(localStorage.getItem('user') || '{}')
    if (user.role) {
      config.headers['X-User-Role'] = user.role
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

export default service 
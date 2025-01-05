import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    console.log('发送请求:', config.url)  // 添加日志
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

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
    console.error('响应错误:', error)
    ElMessage.error(error.message || '请求失败')
    return Promise.reject(error)
  }
)

export default service 
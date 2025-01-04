import axios from 'axios'

// 创建 axios 实例
const request = axios.create({
  timeout: 5000
})

// 响应拦截器 - 只保留基本的数据返回
request.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.error('请求错误:', error)
    alert('请求失败，请稍后重试')
    return Promise.reject(error)
  }
)

export default request
import axios from 'axios'

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
      const { status, data } = error.response
      switch (status) {
        case 500:
          console.error('服务器错误:', data)
          // 打印更详细的错误信息
          if (data.message) {
            console.error('错误信息:', data.message)
          }
          if (data.stack) {
            console.error('错误堆栈:', data.stack)
          }
          break
        case 404:
          console.error('资源不存在:', data)
          break
        case 400:
          console.error('请求参数错误:', data)
          break
      }
    }
    return Promise.reject(error)
  }
)

export default request
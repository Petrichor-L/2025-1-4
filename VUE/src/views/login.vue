<template>
  <div class="login-wrapper">
    <div class="area">
      <ul class="circles">
        <li v-for="n in 10" :key="n"></li>
      </ul>
    </div>
    
    <div class="login-box">
      <div class="login-title">用户登录</div>
      <div class="login-form">
        <div class="form-item">
          <select v-model="loginForm.role">
            <option value="">请选择角色</option>
            <option value="student">学生</option>
            <option value="teacher">教师</option>
            <option value="admin">管理员</option>
          </select>
        </div>
        
        <div class="form-item">
          <input type="text" v-model="loginForm.username" placeholder="请输入用户名">
        </div>
        
        <div class="form-item">
          <input type="password" v-model="loginForm.password" placeholder="请输入密码">
        </div>

        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>

        <div class="form-item">
          <button @click="handleLogin" :disabled="loading">
            {{ loading ? '登录中...' : '登录' }}
          </button>
        </div>

        <div class="register-link">
          还没有账号？<router-link to="/register">立即注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/api/auth'

const router = useRouter()
const loginForm = ref({
  role: '',
  username: '',
  password: ''
})
const loading = ref(false)
const errorMessage = ref('')

const handleLogin = async () => {
  // 表单验证
  if (!loginForm.value.role || !loginForm.value.username || !loginForm.value.password) {
    errorMessage.value = '请填写完整信息'
    return
  }

  loading.value = true
  errorMessage.value = ''
  
  try {
    const response = await login(loginForm.value)
    console.log('登录响应:', response)

    if (response.code === 200 && response.data) {
      const userData = response.data
      // 将整个用户信息存储为JSON字符串
      localStorage.setItem('user', JSON.stringify(userData))
      
      // 确认数据已存储
      console.log('存储的用户信息:', localStorage.getItem('user'))

      // 等待数据存储完成后再跳转
      await nextTick()

      const routes = {
        admin: '/admin/students',
        teacher: '/teacher/grades',
        student: '/student/grades'
      }

      const targetRoute = routes[userData.role]
      if (targetRoute) {
        console.log('准备跳转到:', targetRoute)
        await router.push(targetRoute)
      } else {
        errorMessage.value = '未知的用户角色'
      }
    } else {
      errorMessage.value = response.message || '登录失败'
    }
  } catch (error) {
    console.error('登录错误:', error)
    errorMessage.value = error.message || '登录失败，请稍后重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-wrapper {
  width: 100%;
  height: 100vh;
  background: linear-gradient(135deg, #1890ff, #36cfc9);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.login-box {
  width: 380px;
  padding: 30px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  position: relative;
  z-index: 1;
  animation: fadeIn 0.5s ease-out;
}

.login-title {
  font-size: 32px;
  color: #1890ff;
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-item {
  margin-bottom: 20px;
  position: relative;
}

.form-item input,
.form-item select {
  width: 100%;
  height: 45px;
  padding: 0 20px;
  border: 2px solid rgba(24, 144, 255, 0.2);
  border-radius: 12px;
  font-size: 16px;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.form-item input:focus,
.form-item select:focus {
  border-color: #1890ff;
  box-shadow: 0 0 12px rgba(24, 144, 255, 0.2);
  transform: translateY(-1px);
}

.form-item button {
  width: 100%;
  height: 45px;
  background: linear-gradient(45deg, #1890ff, #36cfc9);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 18px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(24, 144, 255, 0.3);
}

.form-item button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(24, 144, 255, 0.4);
}

.error-message {
  color: #ff4d4f;
  margin: 16px 0;
  padding: 12px;
  background-color: #fff2f0;
  border: 1px solid #ffccc7;
  border-radius: 8px;
  text-align: center;
  font-size: 14px;
  animation: shake 0.5s ease-in-out;
}

.register-link {
  text-align: center;
  margin-top: 20px;
  font-size: 15px;
  color: #666;
}

.register-link a {
  color: #1890ff;
  text-decoration: none;
  font-weight: 500;
  margin-left: 5px;
  transition: color 0.3s ease;
}

.register-link a:hover {
  color: #40a9ff;
  text-decoration: underline;
}

/* 动画相关的样式保持不变 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  75% { transform: translateX(5px); }
}

/* 背景动画效果 */
.circles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.circles li {
  position: absolute;
  display: block;
  list-style: none;
  width: 20px;
  height: 20px;
  background: rgba(255, 255, 255, 0.2);
  animation: animate 25s linear infinite;
  bottom: -150px;
  border-radius: 50%;
}

.circles li:nth-child(1) { left: 25%; width: 80px; height: 80px; animation-delay: 0s; }
.circles li:nth-child(2) { left: 10%; width: 20px; height: 20px; animation-delay: 2s; animation-duration: 12s; }
.circles li:nth-child(3) { left: 70%; width: 20px; height: 20px; animation-delay: 4s; }
.circles li:nth-child(4) { left: 40%; width: 60px; height: 60px; animation-delay: 0s; animation-duration: 18s; }
.circles li:nth-child(5) { left: 65%; width: 20px; height: 20px; animation-delay: 0s; }

@keyframes animate {
  0% {
    transform: translateY(0) rotate(0deg);
    opacity: 1;
    border-radius: 0;
  }
  100% {
    transform: translateY(-1000px) rotate(720deg);
    opacity: 0;
    border-radius: 50%;
  }
}
</style>
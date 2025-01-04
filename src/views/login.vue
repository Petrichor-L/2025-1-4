<template>
  <div class="login-wrapper">
    <!-- 添加背景动画元素 -->
    <div class="area">
      <ul class="circles">
        <li v-for="n in 10" :key="n"></li>
      </ul>
    </div>
    
    <div class="login-box">
      <div class="login-title">学生成绩管理系统</div>
      <div class="login-form">
        <div class="form-item">
          <select v-model="loginForm.role">
            <option value="">请选择角色</option>
            <option value="admin">管理员</option>
            <option value="teacher">教师</option>
            <option value="student">学生</option>
          </select>
        </div>
        <div class="form-item">
          <input type="text" v-model="loginForm.username" placeholder="请输入账号">
        </div>
        <div class="form-item">
          <input type="password" v-model="loginForm.password" placeholder="请输入密码">
        </div>
        <div class="error-message" v-if="errorMessage">{{ errorMessage }}</div>
        <div class="form-item">
          <button @click="handleLogin" :disabled="loading">
            {{ loading ? '登录中...' : '登录' }}
          </button>
        </div>
        <!-- 添加注册按钮 -->
        <div class="register-link">
          还没有账号？<router-link to="/register">立即注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
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
    if (response.code === 200) {
      // 保存登录信息到本地存储
      localStorage.setItem('token', response.data.id) // 使用用户id作为token
      localStorage.setItem('userRole', response.data.role)
      localStorage.setItem('username', response.data.username)
      
      // 根据角色跳转到不同页面
      const routes = {
        admin: '/admin/students',
        teacher: '/teacher/grades',
        student: '/student/grades'
      }
      router.push(routes[response.data.role])
    } else {
      errorMessage.value = response.message || '登录失败'
    }
  } catch (error) {
    console.error('登录错误:', error)
    errorMessage.value = '登录失败，请稍后重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-wrapper {
  width: 100%;
  height: 100vh;
  background: linear-gradient(to bottom right, #1890ff, #36cfc9);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  position: relative;
  z-index: 1;
  animation: fadeIn 0.5s ease-out;
}

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

.login-title {
  font-size: 28px;
  color: #1890ff;
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.form-item {
  margin-bottom: 20px;
}

.form-item input,
.form-item select {
  width: 100%;
  height: 44px;
  line-height: 44px;
  padding: 0 15px;
  box-sizing: border-box;
  border: 1px solid rgba(24, 144, 255, 0.2);
  border-radius: 8px;
  color: #333;
  outline: none;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.9);
}

.form-item input:focus,
.form-item select:focus {
  border-color: #1890ff;
  box-shadow: 0 0 8px rgba(24, 144, 255, 0.2);
}

.form-item select {
  cursor: pointer;
}

.form-item select option {
  color: #333;
}

.form-item input::placeholder {
  color: #bfbfbf;
}

.form-item button {
  width: 100%;
  height: 44px;
  background: #1890ff;
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(24, 144, 255, 0.3);
}

.form-item button:hover {
  background: #40a9ff;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(24, 144, 255, 0.4);
}

.form-item button:disabled {
  background: #bfbfbf;
  transform: none;
  box-shadow: none;
  cursor: not-allowed;
}

.error-message {
  color: #ff4d4f;
  font-size: 14px;
  text-align: center;
  margin-bottom: 15px;
  animation: shake 0.5s ease-in-out;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  75% { transform: translateX(5px); }
}

/* 背景动画样式 */
.area {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}

.circles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
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

.circles li:nth-child(1) {
  left: 25%;
  width: 80px;
  height: 80px;
  animation-delay: 0s;
}

.circles li:nth-child(2) {
  left: 10%;
  width: 20px;
  height: 20px;
  animation-delay: 2s;
  animation-duration: 12s;
}

.circles li:nth-child(3) {
  left: 70%;
  width: 20px;
  height: 20px;
  animation-delay: 4s;
}

.circles li:nth-child(4) {
  left: 40%;
  width: 60px;
  height: 60px;
  animation-delay: 0s;
  animation-duration: 18s;
}

.circles li:nth-child(5) {
  left: 65%;
  width: 20px;
  height: 20px;
  animation-delay: 0s;
}

.circles li:nth-child(6) {
  left: 75%;
  width: 110px;
  height: 110px;
  animation-delay: 3s;
}

.circles li:nth-child(7) {
  left: 35%;
  width: 150px;
  height: 150px;
  animation-delay: 7s;
}

.circles li:nth-child(8) {
  left: 50%;
  width: 25px;
  height: 25px;
  animation-delay: 15s;
  animation-duration: 45s;
}

.circles li:nth-child(9) {
  left: 20%;
  width: 15px;
  height: 15px;
  animation-delay: 2s;
  animation-duration: 35s;
}

.circles li:nth-child(10) {
  left: 85%;
  width: 150px;
  height: 150px;
  animation-delay: 0s;
  animation-duration: 11s;
}

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

/* 添加注册链接样式 */
.register-link {
  text-align: center;
  font-size: 14px;
  color: #666;
  margin-top: 15px;
}

.register-link a {
  color: #1890ff;
  text-decoration: none;
  margin-left: 5px;
  transition: color 0.3s ease;
}

.register-link a:hover {
  color: #40a9ff;
}
</style>
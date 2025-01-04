<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/auth'

const router = useRouter()
const registerForm = ref({
  name: '',
  password: '',
  confirmPassword: '',
  role: 'student',
  phone: '',
  email: '',
  studentId: '',
  teacherId: ''
})

const loading = ref(false)
const errorMessage = ref('')

const handleRegister = async () => {
  // 表单验证
  if (!registerForm.value.name.trim()) {
    errorMessage.value = '请输入真实姓名'
    return
  }

  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    errorMessage.value = '两次输入的密码不一致'
    return
  }

  // 验证手机号格式
  const phoneRegex = /^1[3-9]\d{9}$/
  if (!phoneRegex.test(registerForm.value.phone)) {
    errorMessage.value = '请输入正确的手机号'
    return
  }

  loading.value = true
  errorMessage.value = ''
  
  try {
    // 构造发送给后端的数据
    const registerData = {
      username: registerForm.value.role === 'student' ? 
        registerForm.value.studentId : 
        registerForm.value.teacherId,
      password: registerForm.value.password,
      role: registerForm.value.role,
      phone: registerForm.value.phone,
      email: registerForm.value.email,
      name: registerForm.value.name
    }

    // 打印发送的数据，用于调试
    console.log('发送到后端的注册数据:', registerData)

    const response = await register(registerData)
    console.log('后端返回的响应:', response)
    
    if (response.code === 200) {
      alert('注册成功')
      router.push('/login')
    } else {
      errorMessage.value = response.message || '注册失败'
    }
  } catch (error) {
    console.error('注册错误:', error)
    errorMessage.value = '注册失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

const handleRoleChange = () => {
  // 切换角色时清空相关字段
  registerForm.value.studentId = ''
  registerForm.value.teacherId = ''
}
</script>

<template>
  <div class="register-wrapper">
    <div class="area">
      <ul class="circles">
        <li v-for="n in 10" :key="n"></li>
      </ul>
    </div>
    
    <div class="register-box">
      <div class="register-title">用户注册</div>
      <div class="register-form">
        <div class="form-item">
          <select v-model="registerForm.role" @change="handleRoleChange">
            <option value="student">学生</option>
            <option value="teacher">教师</option>
          </select>
        </div>
        
        <div class="form-item" v-show="registerForm.role === 'student'">
          <input type="text" v-model="registerForm.studentId" placeholder="请输入学号">
        </div>
        <div class="form-item" v-show="registerForm.role === 'teacher'">
          <input type="text" v-model="registerForm.teacherId" placeholder="请输入工号">
        </div>
        <div class="form-item">
          <input 
            type="tel" 
            v-model="registerForm.phone" 
            placeholder="请输入手机号"
            maxlength="11"
          >
        </div>

        <div class="form-item">
          <input type="text" v-model="registerForm.name" placeholder="请输入真实姓名">
        </div>
        <div class="form-item">
          <input type="password" v-model="registerForm.password" placeholder="请输入密码">
        </div>
        <div class="form-item">
          <input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码">
        </div>

        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>
        <div class="form-item">
          <button @click="handleRegister" :disabled="loading">
            {{ loading ? '注册中...' : '注册' }}
          </button>
        </div>
        <div class="login-link">
          已有账号？<router-link to="/login">立即登录</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        name: '',
        password: '',
        confirmPassword: '',
        role: 'student',
        phone: '',
        email: '',
        studentId: '',
        teacherId: ''
      },
      loading: false,
      errorMessage: ''
    }
  },
  methods: {
    handleRoleChange() {
      // 切换角色时清空相关字段
      this.registerForm.studentId = ''
      this.registerForm.teacherId = ''
    },
    // ... 其他方法保持不变
  }
}
</script>

<style scoped>
.register-wrapper {
  width: 100%;
  height: 100vh;
  background: linear-gradient(to bottom right, #1890ff, #36cfc9);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.register-box {
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

.register-title {
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

.login-link {
  text-align: center;
  font-size: 14px;
  color: #666;
  margin-top: 15px;
}

.login-link a {
  color: #1890ff;
  text-decoration: none;
  margin-left: 5px;
  transition: color 0.3s ease;
}

.login-link a:hover {
  color: #40a9ff;
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
</style> 
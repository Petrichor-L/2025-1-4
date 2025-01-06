<template>
  <div class="login-wrapper">
    <div class="area">
      <ul class="circles">
        <li v-for="n in 8" :key="n"></li>
      </ul>
    </div>
    
    <div class="login-box">
      <div class="system-title">学生成绩管理系统</div>
      <div class="login-title">{{ isChangingPassword ? '修改密码' : '用户登录' }}</div>
      
      <div class="login-form">
        <!-- 登录表单 -->
        <template v-if="!isChangingPassword">
          <div class="form-item">
            <div class="role-select">
              <div 
                v-for="role in roles" 
                :key="role.value"
                class="role-option"
                :class="{ active: loginForm.role === role.value }"
                @click="loginForm.role = role.value"
              >
                <el-icon><component :is="role.icon" /></el-icon>
                <span>{{ role.label }}</span>
              </div>
            </div>
          </div>
          
          <div class="form-item">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名/学号"
              prefix-icon="User"
            />
          </div>
          
          <div class="form-item">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              show-password
            />
          </div>

          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <div class="form-item">
            <el-button 
              type="primary" 
              :loading="loading" 
              @click="handleLogin"
              class="submit-btn"
            >
              {{ loading ? '登录中...' : '登录' }}
            </el-button>
          </div>

          <div class="form-links">
            <router-link to="/register" class="register-link">立即注册</router-link>
            <a href="javascript:;" @click="toggleChangePassword" class="change-password-link">修改密码</a>
          </div>
        </template>

        <!-- 修改密码表单 -->
        <template v-else>
          <div class="form-item">
            <input type="text" v-model="passwordForm.username" placeholder="请输入用户名">
          </div>
          
          <div class="form-item">
            <input type="password" v-model="passwordForm.oldPassword" placeholder="请输入原密码">
          </div>

          <div class="form-item">
            <input type="password" v-model="passwordForm.newPassword" placeholder="请输入新密码">
          </div>

          <div class="form-item">
            <input type="password" v-model="passwordForm.confirmPassword" placeholder="请确认新密码">
          </div>

          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <div class="form-item">
            <button @click="handleUpdatePassword" :disabled="loading">
              {{ loading ? '修改中...' : '确认修改' }}
            </button>
          </div>

          <div class="form-links">
            <a href="javascript:;" @click="toggleChangePassword" class="back-link">返回登录</a>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, School, Notebook, Management } from '@element-plus/icons-vue'
import { login, updatePassword } from '@/api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const errorMessage = ref('')
const isChangingPassword = ref(false)

// 登录表单
const loginForm = ref({
  role: '',
  username: '',
  password: ''
})

// 修改密码表单
const passwordForm = ref({
  username: '',
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 角色选项
const roles = [
  { label: '学生', value: 'student', icon: 'School' },
  { label: '教师', value: 'teacher', icon: 'Notebook' },
  { label: '管理员', value: 'admin', icon: 'Management' }
]

// 切换修改密码模式
const toggleChangePassword = () => {
  isChangingPassword.value = !isChangingPassword.value
  errorMessage.value = ''
  passwordForm.value = {
    username: '',
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
  }
}

// 修改密码处理
const handleUpdatePassword = async () => {
  // 表单验证
  if (!passwordForm.value.username || !passwordForm.value.oldPassword || 
      !passwordForm.value.newPassword || !passwordForm.value.confirmPassword) {
    errorMessage.value = '请填写完整信息'
    return
  }

  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    errorMessage.value = '两次输入的新密码不一致'
    return
  }

  if (passwordForm.value.newPassword.length < 6) {
    errorMessage.value = '新密码长度不能小于6位'
    return
  }

  loading.value = true
  errorMessage.value = ''
  
  try {
    const res = await updatePassword({
      username: passwordForm.value.username,
      oldPassword: passwordForm.value.oldPassword,
      newPassword: passwordForm.value.newPassword
    })

    if (res.code === 200) {
      ElMessage.success('密码修改成功，请重新登录')
      toggleChangePassword()
    } else {
      errorMessage.value = res.message || '修改失败'
    }
  } catch (error) {
    console.error('修改密码错误:', error)
    errorMessage.value = error.response?.data?.message || '修改失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

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
      // 验证选择的角色是否与实际角色匹配
      if (loginForm.value.role !== response.data.role) {
        errorMessage.value = '选择的用户类型与账号不匹配'
        loading.value = false
        return
      }

      // 保存完整的用户信息
      localStorage.setItem('user', JSON.stringify(response.data))
      localStorage.setItem('userRole', response.data.role)

      // 根据角色跳转
      if (response.data.role === 'admin') {
        router.push('/admin/students')
      } else if (response.data.role === 'teacher') {
        router.push('/teacher/grades')
      } else if (response.data.role === 'student') {
        router.push('/student/grades')
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
  background: linear-gradient(135deg, 
    #1890ff 0%, 
    #36cfc9 50%, 
    #1890ff 100%);
  background-size: 200% 200%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  animation: gradientBG 10s ease infinite;
}

.login-box {
  width: 400px;
  padding: 30px;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
  border-radius: 16px;
  position: relative;
  z-index: 1;
  animation: fadeIn 0.5s ease-out;
}

.system-title {
  font-size: 28px;
  color: #1890ff;
  text-align: center;
  margin-bottom: 20px;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.login-title {
  font-size: 20px;
  color: #333;
  text-align: center;
  margin-bottom: 30px;
}

.role-select {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  background: #f5f7fa;
  border-radius: 8px;
  padding: 5px;
}

.role-option {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px;
  cursor: pointer;
  border-radius: 6px;
  transition: all 0.3s;
  gap: 8px;
}

.role-option:hover {
  background: #e8f4ff;
}

.role-option.active {
  background: #1890ff;
  color: white;
}

.role-option.active :deep(.el-icon) {
  color: white;
}

.role-option :deep(.el-icon) {
  font-size: 24px;
  color: #909399;
}

.submit-btn {
  width: 100%;
  height: 45px;
  font-size: 16px;
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

.form-links {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  font-size: 15px;
}

.register-link,
.change-password-link,
.back-link {
  color: #1890ff;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.register-link:hover,
.change-password-link:hover,
.back-link:hover {
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
  background: rgba(255, 255, 255, 0.25);
  animation: animate 20s linear infinite;
  bottom: -150px;
  backdrop-filter: blur(5px);
  border-radius: 50%;
  box-shadow: 0 0 20px rgba(255, 255, 255, 0.3);
}

.circles li:nth-child(1) {
  left: 25%;
  width: 120px;
  height: 120px;
  animation-delay: 0s;
  animation-duration: 15s;
}

.circles li:nth-child(2) {
  left: 10%;
  width: 40px;
  height: 40px;
  animation-delay: 1s;
  animation-duration: 12s;
}

.circles li:nth-child(3) {
  left: 70%;
  width: 80px;
  height: 80px;
  animation-delay: 2s;
  animation-duration: 18s;
}

.circles li:nth-child(4) {
  left: 40%;
  width: 100px;
  height: 100px;
  animation-delay: 0s;
  animation-duration: 14s;
}

.circles li:nth-child(5) {
  left: 65%;
  width: 60px;
  height: 60px;
  animation-delay: 3s;
  animation-duration: 16s;
}

.circles li:nth-child(6) {
  left: 85%;
  width: 90px;
  height: 90px;
  animation-delay: 1.5s;
  animation-duration: 17s;
}

.circles li:nth-child(7) {
  left: 15%;
  width: 70px;
  height: 70px;
  animation-delay: 2.5s;
  animation-duration: 13s;
}

.circles li:nth-child(8) {
  left: 50%;
  width: 50px;
  height: 50px;
  animation-delay: 1s;
  animation-duration: 15s;
}

@keyframes animate {
  0% {
    transform: translateY(0) rotate(0deg) scale(1);
    opacity: 0.8;
    border-radius: 40%;
  }
  
  50% {
    transform: translateY(-500px) rotate(360deg) scale(1.2);
    opacity: 0.4;
    border-radius: 60%;
  }

  100% {
    transform: translateY(-1000px) rotate(720deg) scale(1);
    opacity: 0;
    border-radius: 50%;
  }
}

@keyframes gradientBG {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}
</style>
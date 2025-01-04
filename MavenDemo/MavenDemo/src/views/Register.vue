<template>
  <div class="register-container">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
      <h3 class="title">用户注册</h3>
      
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" placeholder="用户名" />
      </el-form-item>
      
      <el-form-item prop="password">
        <el-input v-model="registerForm.password" type="password" placeholder="密码" />
      </el-form-item>
      
      <el-form-item prop="name">
        <el-input v-model="registerForm.name" placeholder="真实姓名" />
      </el-form-item>
      
      <el-form-item prop="role">
        <el-select v-model="registerForm.role" placeholder="选择角色">
          <el-option label="学生" value="student" />
          <el-option label="教师" value="teacher" />
          <el-option label="管理员" value="admin" />
        </el-select>
      </el-form-item>
      
      <el-form-item prop="phone">
        <el-input v-model="registerForm.phone" placeholder="手机号" />
      </el-form-item>
      
      <el-form-item>
        <el-button :loading="loading" type="primary" @click="handleRegister">注册</el-button>
        <el-button @click="$router.push('/login')">返回登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { register } from '@/api/user'
import { ElMessage } from 'element-plus'

export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        name: '',
        role: '',
        phone: ''
      },
      registerRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        role: [{ required: true, message: '请选择角色', trigger: 'change' }]
      },
      loading: false
    }
  },
  methods: {
    async handleRegister() {
      try {
        this.loading = true
        const res = await register(this.registerForm)
        if (res.code === 200) {
          ElMessage.success('注册成功')
          this.$router.push('/login')
        }
      } catch (error) {
        console.error('注册失败：', error)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f3f3f3;
}

.register-form {
  width: 400px;
  padding: 30px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  margin-bottom: 30px;
}
</style> 
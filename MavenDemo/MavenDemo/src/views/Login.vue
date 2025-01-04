<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">用户登录</h3>
      
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" placeholder="用户名" />
      </el-form-item>
      
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" placeholder="密码" />
      </el-form-item>
      
      <el-form-item>
        <el-button :loading="loading" type="primary" @click="handleLogin">登录</el-button>
        <el-button @click="$router.push('/register')">去注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { login } from '@/api/user'
import { ElMessage } from 'element-plus'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      loading: false
    }
  },
  methods: {
    async handleLogin() {
      try {
        this.loading = true;
        const res = await login(this.loginForm);
        if (res.code === 200) {
          ElMessage.success('登录成功');
          // 存储用户信息
          localStorage.setItem('user', JSON.stringify(res.data));
          // 根据用户角色跳转到不同页面
          if (res.data.role === 'admin') {
            this.$router.push('/admin/users');
          } else if (res.data.role === 'student') {
            this.$router.push('/student');  // 学生首页
          } else if (res.data.role === 'teacher') {
            this.$router.push('/teacher');  // 教师首页
          }
        }
      } catch (error) {
        console.error('登录失败：', error);
        ElMessage.error('登录失败，请重试');
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f3f3f3;
}

.login-form {
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
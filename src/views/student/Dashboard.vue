<template>
  <div class="dashboard-container">
    <!-- 侧边栏导航 -->
    <div class="sidebar">
      <h2>学生系统</h2>
      <nav>
        <router-link to="/student/grades">我的成绩</router-link>
        <router-link to="/student/courses">我的课程</router-link>
      </nav>
      <!-- 添加退出登录按钮 -->
      <div class="logout-section">
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </div>
    </div>
    
    <!-- 主内容区域 -->
    <div class="main-content">
      <!-- 子路由将在这里渲染 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: 'StudentDashboard',
  setup() {
    const router = useRouter()
    
    const handleLogout = () => {
      // 清除本地存储的登录信息
      localStorage.removeItem('token')
      localStorage.removeItem('userRole')
      localStorage.removeItem('username')
      
      // 跳转到登录页
      router.push('/login')
    }
    
    return {
      handleLogout
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 200px;
  background-color: #f5f5f5;
  padding: 20px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.sidebar nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.sidebar a {
  text-decoration: none;
  color: #333;
  padding: 10px;
}

.sidebar a.router-link-active {
  background-color: #e0e0e0;
  border-radius: 4px;
}

.main-content {
  flex: 1;
  padding: 20px;
}

.logout-section {
  margin-top: auto;
  padding: 20px;
}

.logout-btn {
  width: 100%;
  padding: 10px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-btn:hover {
  background-color: #c82333;
}
</style> 
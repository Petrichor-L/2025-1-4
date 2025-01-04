<template>
  <div class="dashboard-container">
    <!-- 侧边栏导航 -->
    <div class="sidebar">
      <div class="logo-section">
        <h2>学生成绩管理系统</h2>
        <div class="admin-tag">管理员</div>
      </div>
      <nav>
        <router-link to="/admin/students">
          <i class="fas fa-users"></i>
          学生管理
        </router-link>
        <router-link to="/admin/courses">
          <i class="fas fa-book"></i>
          课程管理
        </router-link>
        <router-link to="/admin/grades">
          <i class="fas fa-chart-line"></i>
          成绩管理
        </router-link>
      </nav>
      <div class="logout-section">
        <button class="logout-btn" @click="handleLogout">
          <i class="fas fa-sign-out-alt"></i>
          退出登录
        </button>
      </div>
    </div>
    
    <!-- 主内容区域 -->
    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: 'AdminDashboard',
  setup() {
    const router = useRouter()
    
    const handleLogout = () => {
      localStorage.clear()
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
  background: #f0f2f5;
}

.sidebar {
  width: 250px;
  background: #001529;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.15);
  padding: 20px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.logo-section {
  text-align: center;
  padding: 20px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  margin-bottom: 20px;
}

.logo-section h2 {
  color: #fff;
  font-size: 1.5rem;
  margin-bottom: 10px;
  font-weight: bold;
}

.admin-tag {
  display: inline-block;
  padding: 4px 12px;
  background: rgba(24, 144, 255, 0.2);
  color: #1890ff;
  border-radius: 15px;
  font-size: 0.9rem;
}

.sidebar nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.sidebar a {
  text-decoration: none;
  color: rgba(255, 255, 255, 0.65);
  padding: 12px 16px;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 10px;
}

.sidebar a i {
  width: 20px;
  text-align: center;
}

.sidebar a:hover {
  background: rgba(255, 255, 255, 0.08);
  color: #fff;
}

.sidebar a.router-link-active {
  background: #1890ff;
  color: white;
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.main-content {
  flex: 1;
  padding: 30px;
  overflow-y: auto;
  background: #fff;
  border-radius: 16px 0 0 16px;
  margin: 16px 16px 16px 0;
  box-shadow: -4px 0 8px rgba(0, 0, 0, 0.03);
}

.logout-section {
  margin-top: auto;
  padding: 20px 0;
}

.logout-btn {
  width: 100%;
  padding: 12px;
  background: rgba(245, 34, 45, 0.1);
  color: #ff4d4f;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 1rem;
}

.logout-btn:hover {
  background: #ff4d4f;
  color: white;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(245, 34, 45, 0.3);
}

.logout-btn i {
  font-size: 1.1rem;
}
</style> 
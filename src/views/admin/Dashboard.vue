<template>
  <div class="dashboard-container">
    <!-- 侧边栏导航 -->
    <div class="sidebar">
      <h2>管理后台</h2>
      <nav>
        <router-link to="/admin/grades">成绩管理</router-link>
        <router-link to="/admin/courses">课程查询</router-link>
        <router-link to="/admin/grade-input">成绩录入</router-link>
        <router-link to="/admin/users">用户管理</router-link>
        <router-link to="/admin/course-manage">课程管理</router-link>
      </nav>
    </div>
    
    <!-- 主内容区域 -->
    <div class="main-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard'
}
</script>

<style scoped>
.dashboard-container {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 220px;
  background-color: #1e1e2d;
  padding: 20px;
  color: white;
}

.sidebar h2 {
  color: #fff;
  margin-bottom: 20px;
  font-size: 1.5rem;
  padding-bottom: 15px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.sidebar a {
  text-decoration: none;
  color: #a3a3b5;
  padding: 12px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.sidebar a:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
}

.sidebar a.router-link-active {
  background-color: #667eea;
  color: white;
}

.main-content {
  flex: 1;
  padding: 20px;
  background-color: #f5f6fa;
}

@media (max-width: 768px) {
  .sidebar {
    width: 180px;
  }
  
  .sidebar h2 {
    font-size: 1.2rem;
  }
  
  .sidebar a {
    padding: 10px;
  }
}
</style> 
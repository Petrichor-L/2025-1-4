<template>
  <div class="dashboard-container">
    <!-- 侧边栏导航 -->
    <div class="sidebar">
      <h2>教师系统</h2>
      <nav>
        <router-link to="/teacher/grades">学生成绩</router-link>
        <router-link to="/teacher/courses">课程管理</router-link>
        <router-link to="/teacher/grade-input">成绩录入</router-link>
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
  name: 'TeacherDashboard'
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
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.sidebar h2 {
  color: #333;
  margin-bottom: 20px;
  font-size: 1.5rem;
}

.sidebar nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.sidebar a {
  text-decoration: none;
  color: #333;
  padding: 12px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.sidebar a:hover {
  background-color: #e0e0e0;
}

.sidebar a.router-link-active {
  background-color: #667eea;
  color: white;
}

.main-content {
  flex: 1;
  padding: 20px;
  background-color: #fff;
}
</style> 
<template>
  <div class="admin-dashboard">
    <div class="sidebar">
      <h2>管理系统</h2>
      <nav>
        <router-link to="/admin/students">学生管理</router-link>
        <router-link to="/admin/courses">课程管理</router-link>
        <router-link to="/admin/grades">成绩管理</router-link>
      </nav>
    </div>
    
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
.admin-dashboard {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 200px;
  background-color: #2c3e50;
  padding: 20px;
  color: white;
}

.sidebar h2 {
  margin-bottom: 20px;
  text-align: center;
}

.sidebar nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.sidebar a {
  color: white;
  text-decoration: none;
  padding: 10px;
  border-radius: 4px;
}

.sidebar a.router-link-active {
  background-color: #34495e;
}

.main-content {
  flex: 1;
  padding: 20px;
  background-color: #f5f5f5;
}
</style> 
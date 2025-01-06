<template>
  <div class="dashboard-container">
    <!-- 顶部导航栏 -->
    <div class="header">
      <div class="logo">{{ title }}</div>
      <div class="user-info">
        <span>{{ username }}</span>
        <el-button link @click="handleLogout">退出登录</el-button>
      </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="main-container">
      <!-- 侧边栏 -->
      <div class="sidebar">
        <el-menu
          :default-active="activeMenu"
          router
          class="menu"
          background-color="#e9ecf2"
        >
          <slot name="menu"></slot>
        </el-menu>
      </div>

      <!-- 内容区域 -->
      <div class="content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { logout } from '@/api/auth'

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  activeMenu: {
    type: String,
    required: true
  }
})

const router = useRouter()

// 获取用户信息
const username = computed(() => {
  const userInfo = localStorage.getItem('user')
  return userInfo ? JSON.parse(userInfo).username : ''
})

// 退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确认退出登录吗？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    logout()
    router.push('/login')
  } catch (error) {
    // 用户取消退出
  }
}
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f0f2f5;
}

.header {
  height: 60px;
  background: #6b9ac4;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

.logo {
  font-size: 20px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.main-container {
  flex: 1;
  display: flex;
  margin: 20px;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.sidebar {
  width: 200px;
  background: #e9ecf2;
  border-right: 1px solid #e6e6e6;
}

.menu {
  border-right: none;
}

.content {
  flex: 1;
  padding: 20px;
  background: white;
}

:deep(.el-menu-item) {
  color: #606266;
}

:deep(.el-menu-item:hover) {
  background-color: #dde2eb !important;
}

:deep(.el-menu-item.is-active) {
  background-color: #d0d7e3 !important;
  color: #6b9ac4 !important;
  font-weight: 500;
}

:deep(.el-menu-item .el-icon) {
  color: #909399;
}

:deep(.el-menu-item.is-active .el-icon) {
  color: #6b9ac4 !important;
}
</style> 
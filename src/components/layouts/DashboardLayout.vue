<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { logout } from '@/api/auth'

const props = defineProps({
  userType: {
    type: String,
    required: true
  }
})

const router = useRouter()
const isCollapsed = ref(false)

const menuItems = computed(() => {
  const baseItems = [
    { icon: 'fas fa-chart-line', label: '成绩查询', route: `/${props.userType}/grades` },
    { icon: 'fas fa-book', label: '课程查询', route: `/${props.userType}/courses` }
  ]

  if (props.userType === 'teacher' || props.userType === 'admin') {
    baseItems.push({ 
      icon: 'fas fa-edit', 
      label: '成绩录入', 
      route: `/${props.userType}/grade-input` 
    })
  }

  if (props.userType === 'admin') {
    baseItems.push(
      { icon: 'fas fa-users', label: '用户管理', route: '/admin/users' },
      { icon: 'fas fa-tasks', label: '课程管理', route: '/admin/course-manage' }
    )
  }

  return baseItems
})

const handleLogout = async () => {
  try {
    await logout()
    localStorage.removeItem('token')
    localStorage.removeItem('userRole')
    router.push('/login')
  } catch (error) {
    console.error('登出错误:', error)
    alert('登出失败，请稍后重试')
  }
}
</script>

<template>
  <div class="dashboard-container">
    <!-- 侧边栏 -->
    <div class="sidebar" :class="{ collapsed: isCollapsed }">
      <div class="logo-container">
        <img src="/path-to-your-logo.png" alt="Logo" class="logo">
        <span v-if="!isCollapsed">成绩管理系统</span>
      </div>
      
      <div class="menu-items">
        <router-link 
          v-for="item in menuItems" 
          :key="item.route"
          :to="item.route"
          class="menu-item"
          active-class="active"
        >
          <i :class="item.icon"></i>
          <span v-if="!isCollapsed">{{ item.label }}</span>
        </router-link>
      </div>

      <div class="sidebar-footer">
        <button class="collapse-btn" @click="isCollapsed = !isCollapsed">
          <i :class="isCollapsed ? 'fas fa-angle-right' : 'fas fa-angle-left'"></i>
        </button>
        <button class="logout-btn" @click="handleLogout">
          <i class="fas fa-sign-out-alt"></i>
          <span v-if="!isCollapsed">退出登录</span>
        </button>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content" :class="{ 'sidebar-collapsed': isCollapsed }">
      <div class="header">
        <div class="breadcrumb">
          <slot name="breadcrumb"></slot>
        </div>
        <div class="user-info">
          <i class="fas fa-user-circle"></i>
          <span>{{ userType === 'admin' ? '管理员' : userType === 'teacher' ? '教师' : '学生' }}</span>
        </div>
      </div>
      <div class="content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard-container {
  display: flex;
  min-height: 100vh;
  background: #f5f6fa;
}

.sidebar {
  width: 240px;
  background: #fff;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
}

.sidebar.collapsed {
  width: 80px;
}

.logo-container {
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
  border-bottom: 1px solid #eee;
}

.logo {
  width: 40px;
  height: 40px;
}

.menu-items {
  flex: 1;
  padding: 20px 0;
}

.menu-item {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  color: #666;
  text-decoration: none;
  transition: all 0.3s ease;
  gap: 12px;
}

.menu-item:hover {
  background: #f0f2f5;
  color: #667eea;
}

.menu-item.active {
  background: #667eea;
  color: white;
}

.menu-item i {
  width: 20px;
  text-align: center;
}

.sidebar-footer {
  padding: 20px;
  border-top: 1px solid #eee;
}

.collapse-btn, .logout-btn {
  width: 100%;
  padding: 10px;
  border: none;
  background: none;
  cursor: pointer;
  color: #666;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: all 0.3s ease;
}

.collapse-btn:hover, .logout-btn:hover {
  color: #667eea;
}

.main-content {
  flex: 1;
  margin-left: 240px;
  transition: all 0.3s ease;
}

.main-content.sidebar-collapsed {
  margin-left: 80px;
}

.header {
  background: #fff;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #666;
}

.content {
  padding: 20px;
}

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    z-index: 1000;
    height: 100vh;
  }

  .main-content {
    margin-left: 0;
    width: 100%;
  }

  .main-content.sidebar-collapsed {
    margin-left: 80px;
  }
}
</style> 
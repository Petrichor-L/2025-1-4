<template>
  <div class="admin-layout">
    <el-container>
      <el-aside width="200px">
        <el-menu router>
          <el-menu-item index="/admin/students">
            <el-icon><User /></el-icon>
            <span>学生管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/courses">
            <el-icon><Reading /></el-icon>
            <span>课程管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { User, Reading } from '@element-plus/icons-vue'
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}
.el-container {
  height: 100%;
}
.el-aside {
  background-color: #304156;
}
.el-menu {
  border-right: none;
}
</style> 
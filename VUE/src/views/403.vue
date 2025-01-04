<template>
  <div class="forbidden">
    <h1>403</h1>
    <p>抱歉，您没有权限访问该页面</p>
    <el-button @click="goBack">返回首页</el-button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
const goBack = () => {
  const userRole = localStorage.getItem('userRole')
  router.push(`/${userRole}`)
}
</script> 
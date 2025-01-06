<template>
  <div class="courses-view">
    <el-table 
      :data="courseList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
    >
      <el-table-column prop="courseId" label="课程编号" width="180" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="teacherName" label="任课教师" width="120" />
      <el-table-column label="上课时间" width="180" align="center">
        <template #default="scope">
          {{ generateClassTime() }}
        </template>
      </el-table-column>
      <el-table-column prop="classroom" label="上课教室" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStudentCourses } from '@/api/student'

const courseList = ref([])
const loading = ref(false)

// 生成随机上课时间
const generateClassTime = () => {
  const weekdays = ['一', '二', '三', '四', '五']
  const sections = [
    '1-2节',
    '3-4节',
    '5-6节',
    '7-8节',
    '9-10节'
  ]
  
  const randomWeekday = weekdays[Math.floor(Math.random() * weekdays.length)]
  const randomSection = sections[Math.floor(Math.random() * sections.length)]
  
  return `星期${randomWeekday} 第${randomSection}`
}

// 加载课程列表
const loadCourses = async () => {
  loading.value = true
  try {
    const res = await getStudentCourses()
    if (res && res.code === 200) {
      courseList.value = res.data || []
    } else {
      ElMessage.error(res?.message || '获取课程列表失败')
    }
  } catch (error) {
    console.error('加载课程列表错误:', error)
    ElMessage.error('获取课程列表失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadCourses()
})
</script>

<style scoped>
.courses-view {
  padding: 20px;
}
</style> 
<template>
  <div class="courses-view">
    <h2 class="title">我的课程</h2>
    <el-table 
      :data="courseList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
      class="courses-table"
      style="width: 100%;"
    >
      <el-table-column prop="courseId" label="课程编号" width="150" />
      <el-table-column prop="courseName" label="课程名称" width="200" />
      <el-table-column prop="classroom" label="上课教室" width="150" />
      <el-table-column prop="teacherId" label="教师ID" width="150" />
      <el-table-column label="上课时间" width="300">
        <template #default="scope">
          {{ generateClassTime() }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getTeacherCourses } from '@/api/teacher'

const courseList = ref([])
const loading = ref(false)

// 随机生成上课时间
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
    const user = JSON.parse(localStorage.getItem('user'))
    const res = await getTeacherCourses(user.username) // 使用教师的工号
    console.log('课程列表返回:', res) // 打印返回值
    if (res && Array.isArray(res)) {
      courseList.value = res // 直接赋值
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
  background-color: #f9f9f9; /* 背景颜色 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 阴影效果 */
  max-width: 100%; /* 限制最大宽度为100% */
  margin: 0; /* 去掉外边距 */
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333; /* 标题颜色 */
}

.courses-table {
  border-radius: 8px; /* 表格圆角 */
  overflow: hidden; /* 避免内容溢出 */
}

.courses-table .el-table__header {
  background-color: #1890ff; /* 表头背景颜色 */
  color: white; /* 表头文字颜色 */
}

.courses-table .el-table__header th {
  font-weight: bold; /* 表头加粗 */
}

.courses-table .el-table__body tr:hover {
  background-color: #f5f5f5; /* 鼠标悬停行的背景颜色 */
}

.courses-table .el-table__body td {
  padding: 15px; /* 单元格内边距 */
}
</style>
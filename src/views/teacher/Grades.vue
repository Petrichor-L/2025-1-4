<template>
  <div class="grades-container">
    <h2>学生成绩管理</h2>
    <div class="filter-section">
      <select v-model="selectedCourse">
        <option value="">选择课程</option>
        <option v-for="course in courses" :key="course.id" :value="course.id">
          {{ course.name }}
        </option>
      </select>
    </div>
    <div class="grades-table">
      <!-- 成绩表格内容 -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'TeacherGrades',
  data() {
    return {
      selectedCourse: '',
      courses: [
        { id: 1, name: '高等数学' },
        { id: 2, name: '大学物理' }
      ]
    }
  }
}
</script>

<style scoped>
/* 样式与学生成绩页面类似 */
</style> 
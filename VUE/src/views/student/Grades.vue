<template>
  <div class="grades-view">
    <!-- 成绩统计卡片 -->
    <div class="statistics-cards">
      <el-card class="stat-card">
        <template #header>
          <div class="card-header">
            <span>课程总数</span>
          </div>
        </template>
        <div class="card-value">{{ gradeList.length }}</div>
      </el-card>
      
      <el-card class="stat-card">
        <template #header>
          <div class="card-header">
            <span>平均分</span>
          </div>
        </template>
        <div class="card-value" :class="getGradeClass(Number(averageGrade))">
          {{ averageGrade }}
        </div>
      </el-card>
    </div>

    <!-- 成绩表格 -->
    <el-table 
      :data="gradeList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
    >
      <el-table-column prop="courseId" label="课程编号" width="180" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="grade" label="成绩" width="100" align="center">
        <template #default="scope">
          <span :class="getGradeClass(scope.row.grade)">
            {{ scope.row.grade }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="等级" min-width="120" align="center">
        <template #default="scope">
          <el-tag :type="getGradeTagType(scope.row.grade)">
            {{ getGradeLevel(scope.row.grade) }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStudentGrades } from '@/api/student'

const gradeList = ref([])
const loading = ref(false)

// 计算平均分
const averageGrade = computed(() => {
  if (gradeList.value.length === 0) return '0.0'
  const total = gradeList.value.reduce((sum, item) => sum + Number(item.grade), 0)
  return (total / gradeList.value.length).toFixed(1)
})

// 获取成绩等级
const getGradeLevel = (grade) => {
  if (grade >= 90) return '优秀'
  if (grade >= 80) return '良好'
  if (grade >= 70) return '中等'
  if (grade >= 60) return '及格'
  return '不及格'
}

// 获取成绩标签类型
const getGradeTagType = (grade) => {
  if (grade >= 90) return 'success'
  if (grade >= 80) return ''
  if (grade >= 70) return 'warning'
  if (grade >= 60) return 'info'
  return 'danger'
}

// 获取成绩文字样式
const getGradeClass = (grade) => {
  return {
    'grade-excellent': grade >= 90,
    'grade-good': grade >= 80 && grade < 90,
    'grade-medium': grade >= 70 && grade < 80,
    'grade-pass': grade >= 60 && grade < 70,
    'grade-fail': grade < 60
  }
}

// 加载成绩列表
const loadGrades = async () => {
  loading.value = true
  try {
    const res = await getStudentGrades()
    if (res && res.code === 200) {
      gradeList.value = res.data || []
    } else {
      ElMessage.error(res?.message || '获取成绩列表失败')
    }
  } catch (error) {
    console.error('加载成绩列表错误:', error)
    ElMessage.error('获取成绩列表失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadGrades()
})
</script>

<style scoped>
.grades-view {
  padding: 20px;
}

.statistics-cards {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  flex: 1;
  max-width: 200px;
  transition: all 0.3s;
}

/* 课程总数卡片 */
.stat-card:first-child {
  background: linear-gradient(135deg, #e6f3ff 0%, #ffffff 100%);
}

.stat-card:first-child .el-card__header {
  background: linear-gradient(135deg, #d0e7ff 0%, #e6f3ff 100%);
  border-bottom: none;
}

/* 平均分卡片 */
.stat-card:last-child {
  background: linear-gradient(135deg, #f0f9eb 0%, #ffffff 100%);
}

.stat-card:last-child .el-card__header {
  background: linear-gradient(135deg, #e1f3d8 0%, #f0f9eb 100%);
  border-bottom: none;
}

/* 鼠标悬停效果 */
.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
  color: #606266;
}

.card-value {
  font-size: 28px;
  font-weight: bold;
  text-align: center;
  padding: 10px 0;
}

.stat-card:first-child .card-value {
  color: #409EFF;
}

.grade-excellent {
  color: #67C23A;
  font-weight: bold;
}

.grade-good {
  color: #409EFF;
  font-weight: bold;
}

.grade-medium {
  color: #E6A23C;
  font-weight: bold;
}

.grade-pass {
  color: #909399;
  font-weight: bold;
}

.grade-fail {
  color: #F56C6C;
  font-weight: bold;
}
</style> 
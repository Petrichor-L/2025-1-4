<template>
  <div class="grades-view">
    <!-- 统计卡片 -->
    <div class="statistics-cards">
      <el-card class="stat-card">
        <template #header>
          <div class="card-header">
            <span>总学生数</span>
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

    <!-- 操作栏 -->
    <div class="header">
      <el-input
        v-model="searchKeyword"
        placeholder="输入学号或姓名搜索"
        style="width: 300px"
        clearable
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button @click="handleSearch">
            <el-icon><Search /></el-icon>
          </el-button>
        </template>
      </el-input>
      
      <el-button type="primary" @click="handleAdd">
        录入成绩
      </el-button>
    </div>

    <!-- 成绩表格 -->
    <el-table 
      :data="gradeList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
    >
      <el-table-column prop="studentId" label="学号" width="180" />
      <el-table-column prop="studentName" label="学生姓名" width="120" />
      <el-table-column prop="courseId" label="课程编号" width="180" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="grade" label="成绩" width="100" align="center">
        <template #default="scope">
          <span :class="getGradeClass(scope.row.grade)">
            {{ scope.row.grade }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="等级" width="100" align="center">
        <template #default="scope">
          <el-tag :type="getGradeTagType(scope.row.grade)">
            {{ getGradeLevel(scope.row.grade) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑成绩对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form 
        :model="gradeForm" 
        :rules="rules"
        ref="formRef"
        label-width="100px"
      >
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="gradeForm.studentId" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="课程编号" prop="courseId">
          <el-input v-model="gradeForm.courseId" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="成绩" prop="grade">
          <el-input-number 
            v-model="gradeForm.grade" 
            :min="0" 
            :max="100" 
            :precision="1"
            style="width: 180px"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getTeacherGrades, addGrade, updateGrade } from '@/api/teacher'

const searchKeyword = ref('')
const gradeList = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref(null)

const gradeForm = ref({
  studentId: '',
  courseId: '',
  grade: 0
})

// 计算平均分
const averageGrade = computed(() => {
  if (gradeList.value.length === 0) return '0.0'
  const total = gradeList.value.reduce((sum, item) => sum + Number(item.grade), 0)
  return (total / gradeList.value.length).toFixed(1)
})

// 表单验证规则
const rules = {
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  courseId: [
    { required: true, message: '请输入课程编号', trigger: 'blur' }
  ],
  grade: [
    { required: true, message: '请输入成绩', trigger: 'blur' }
  ]
}

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
    const user = JSON.parse(localStorage.getItem('user'))
    const res = await getTeacherGrades(user.username)
    if (res && Array.isArray(res)) {  // 后端直接返回数组
      gradeList.value = res
    } else {
      throw new Error('获取成绩列表失败')
    }
  } catch (error) {
    console.error('加载成绩列表错误:', error)
    ElMessage.error('获取成绩列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索处理
const handleSearch = () => {
  loadGrades()
}

// 添加成绩
const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '录入成绩'
  gradeForm.value = {
    studentId: '',
    courseId: '',
    grade: 0
  }
  dialogVisible.value = true
}

// 编辑成绩
const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '修改成绩'
  gradeForm.value = {
    studentId: row.studentId,
    courseId: row.courseId,
    grade: row.grade
  }
  dialogVisible.value = true
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    submitting.value = true
    
    const apiMethod = isEdit.value ? updateGrade : addGrade
    const res = await apiMethod(
      gradeForm.value.studentId,
      gradeForm.value.courseId,
      Number(gradeForm.value.grade)  // 确保成绩是数字类型
    )

    // 后端返回的是数字（影响的行数），不是标准的 response 格式
    if (res > 0) {  // 如果影响行数大于0，说明操作成功
      ElMessage.success(isEdit.value ? '修改成功' : '添加成功')
      dialogVisible.value = false
      loadGrades()  // 重新加载成绩列表
    } else {
      throw new Error(isEdit.value ? '修改失败' : '添加失败')
    }
  } catch (error) {
    console.error('提交表单错误:', error)
    ElMessage.error(error.message || (isEdit.value ? '修改失败' : '添加失败'))
  } finally {
    submitting.value = false
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

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
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
<template>
  <div class="grade-manage">
    <div class="header">
      <el-input
        v-model="searchKeyword"
        placeholder="输入学号或课程编号搜索"
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
        添加成绩
      </el-button>
    </div>
    
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
      <el-table-column prop="grade" label="成绩" width="100" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(scope.row)">删除</el-button>
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
import { ref, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { getGrades, addGrade, updateGrade, deleteGrade, searchGrades } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

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

// 表单验证规则
const rules = {
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  courseId: [
    { required: true, message: '请输入课程编号', trigger: 'blur' }
  ],
  grade: [
    { required: true, message: '请输入成绩', trigger: 'blur' },
    { type: 'number', min: 0, max: 100, message: '成绩必须在0-100之间', trigger: 'blur' }
  ]
}

// 加载成绩列表
const loadGrades = async () => {
  loading.value = true
  try {
    const res = await searchGrades(searchKeyword.value)
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

// 搜索处理
const handleSearch = () => {
  loadGrades()
}

// 添加成绩
const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '添加成绩'
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
  dialogTitle.value = '编辑成绩'
  gradeForm.value = {
    studentId: row.studentId,
    courseId: row.courseId,
    grade: row.grade
  }
  dialogVisible.value = true
}

// 删除成绩
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该成绩记录吗？', '确认', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await deleteGrade(row.studentId, row.courseId)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      loadGrades()
    } else {
      ElMessage.error(res?.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除成绩错误:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    submitting.value = true
    
    const data = {
      studentId: gradeForm.value.studentId,
      courseId: gradeForm.value.courseId,
      grade: gradeForm.value.grade
    }

    const res = await (isEdit.value ? updateGrade(data) : addGrade(data))

    if (res.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadGrades()
    } else {
      ElMessage.error(res?.message || (isEdit.value ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    console.error('提交表单错误:', error)
    if (error.response?.data?.message) {
      ElMessage.error(error.response.data.message)
    } else {
      ElMessage.error(isEdit.value ? '更新失败' : '添加失败')
    }
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadGrades()
})
</script>

<style scoped>
.grade-manage {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style> 
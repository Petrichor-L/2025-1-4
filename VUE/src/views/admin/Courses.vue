<template>
  <div class="course-manage">
    <div class="header">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索课程名称或编号"
        style="width: 300px"
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button @click="handleSearch">
            <el-icon><Search /></el-icon>
          </el-button>
        </template>
      </el-input>
      
      <el-button type="primary" @click="handleAdd">
        添加课程
      </el-button>
    </div>
    
    <el-table 
      :data="courseList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
    >
      <el-table-column prop="courseId" label="课程编号" width="180" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="teacherName" label="任课教师" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑课程对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form 
        :model="courseForm" 
        :rules="rules"
        ref="formRef"
        label-width="80px"
      >
        <el-form-item label="课程编号" prop="courseId">
          <el-input v-model="courseForm.courseId" />
        </el-form-item>
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="courseForm.courseName" />
        </el-form-item>
        <el-form-item label="任课教师" prop="teacherName">
          <el-input v-model="courseForm.teacherName" />
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
import { getCourses, addCourse, updateCourse, deleteCourse } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const searchKeyword = ref('')
const courseList = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const submitting = ref(false)
const formRef = ref(null)
const isEdit = ref(false)

const courseForm = ref({
  courseId: '',
  courseName: '',
  teacherName: ''
})

// 表单验证规则
const rules = {
  courseId: [
    { required: true, message: '请输入课程编号', trigger: 'blur' }
  ],
  courseName: [
    { required: true, message: '请输入课程名称', trigger: 'blur' }
  ],
  teacherName: [
    { required: true, message: '请输入任课教师', trigger: 'blur' }
  ]
}

// 加载课程列表
const loadCourses = async () => {
  loading.value = true
  try {
    const res = await getCourses({
      keyword: searchKeyword.value
    })
    console.log('课程列表响应:', res)
    console.log('课程数据:', res.data)
    
    if (res && res.code === 200) {
      courseList.value = res.data || []
    } else {
      ElMessage.error(res?.message || '获取课程列表失败')
    }
  } catch (error) {
    console.error('加载课程列表错误:', error)
    if (error.response) {
      console.error('错误响应:', error.response)
      ElMessage.error(error.response?.data?.message || '获取课程列表失败')
    } else {
      ElMessage.error('获取课程列表失败')
    }
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  loadCourses()
}

// 添加课程
const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '添加课程'
  courseForm.value = {
    courseId: '',
    courseName: '',
    teacherName: ''
  }
  dialogVisible.value = true
}

// 编辑课程
const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑课程'
  courseForm.value = {
    courseId: row.courseId,
    courseName: row.courseName,
    teacherName: row.teacherName
  }
  dialogVisible.value = true
}

// 删除课程
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该课程吗？', '确认', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await deleteCourse(row.courseId)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      loadCourses()
    } else {
      ElMessage.error(res?.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除课程错误:', error)
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
    
    let res
    if (isEdit.value) {
      const data = {
        courseName: courseForm.value.courseName,
        teacherName: courseForm.value.teacherName
      }
      res = await updateCourse(courseForm.value.courseId, data)
    } else {
      const data = {
        courseId: courseForm.value.courseId,
        courseName: courseForm.value.courseName,
        teacherName: courseForm.value.teacherName
      }
      res = await addCourse(data)
    }

    if (res.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadCourses()
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
  loadCourses()
})
</script>

<style scoped>
.course-manage {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>   
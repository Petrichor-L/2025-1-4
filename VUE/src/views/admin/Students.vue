<template>
  <div class="student-manage">
    <div class="header">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索学生姓名或学号"
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
        添加学生
      </el-button>
    </div>
    
    <el-table 
      :data="studentList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
    >
      <el-table-column prop="username" label="学号" width="180" />
      <el-table-column prop="name" label="姓名" width="180" />
      <el-table-column prop="phone" label="联系方式" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 添加/编辑学生对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form 
        :model="studentForm" 
        :rules="rules"
        ref="formRef"
        label-width="80px"
      >
        <el-form-item label="学号" prop="username">
          <el-input v-model="studentForm.username" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="studentForm.name" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="studentForm.password" type="password" />
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="studentForm.phone" />
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
import { getStudents, deleteUser, addUser, updateUser } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const searchKeyword = ref('')
const studentList = ref([])
const loading = ref(false)

const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref(null)

const studentForm = ref({
  username: '',
  name: '',
  password: '',
  phone: ''
})

const rules = {
  username: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ]
}

const loadStudents = async () => {
  loading.value = true
  try {
    const res = await getStudents({
      keyword: searchKeyword.value
    })
    
    if (res && res.code === 200) {
      studentList.value = res.data || []
    } else {
      ElMessage.error(res?.message || '获取数据失败')
    }
  } catch (error) {
    console.error('加载学生列表错误:', error)
    ElMessage.error(error.response?.data?.message || '获取学生列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  loadStudents()
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该学生吗？', '确认', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await deleteUser(row.username)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      loadStudents()
    } else {
      ElMessage.error(res?.message || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除学生错误:', error)
      ElMessage.error(error.response?.data?.message || '删除失败')
    }
  }
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '添加学生'
  studentForm.value = {
    username: '',
    name: '',
    password: '',
    phone: ''
  }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑学生'
  studentForm.value = {
    username: row.username,
    name: row.name,
    phone: row.phone,
    password: ''
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  try {
    await formRef.value.validate()
    submitting.value = true
    
    let res
    if (isEdit.value) {
      const data = {
        name: studentForm.value.name,
        phone: studentForm.value.phone,
        username: studentForm.value.username
      }
      res = await updateUser(studentForm.value.username, data)
    } else {
      const data = {
        username: studentForm.value.username,
        password: studentForm.value.password,
        name: studentForm.value.name,
        phone: studentForm.value.phone,
        role: 'student'
      }
      res = await addUser(data)
    }

    if (res.code === 200) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadStudents()
    } else {
      ElMessage.error(res?.message || (isEdit.value ? '更新失败' : '添加失败'))
    }
  } catch (error) {
    console.error('提交表单错误:', error)
    if (error.response?.data?.message) {
      ElMessage.error(error.response.data.message)
    } else {
      ElMessage.error(isEdit.value ? '更新失败，请检查表单信息' : '添加失败，请检查表单信息')
    }
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadStudents()
})
</script>

<style scoped>
.student-manage {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style> 
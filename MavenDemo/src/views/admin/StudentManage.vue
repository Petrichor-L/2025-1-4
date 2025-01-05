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
    
    <el-table :data="studentList" border>
      <el-table-column prop="username" label="学号" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="phone" label="联系方式" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStudents, deleteUser } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search } from '@element-plus/icons-vue'

const searchKeyword = ref('')
const studentList = ref([])

const loadStudents = async () => {
  try {
    const res = await getStudents({ keyword: searchKeyword.value })
    if (res.code === 200) {
      studentList.value = res.data
    } else {
      ElMessage.error(res.message || '获取学生列表失败')
    }
  } catch (error) {
    console.error('获取学生列表失败：', error)
    ElMessage.error('获取学生列表失败')
  }
}

const handleSearch = () => {
  loadStudents()
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该学生吗？')
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    loadStudents()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

onMounted(() => {
  loadStudents()
})
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style> 
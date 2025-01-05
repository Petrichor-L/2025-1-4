<template>
  <div class="grades-view">
    <div class="header">
      <el-input
        v-model="searchKeyword"
        placeholder="输入课程编号或名称搜索"
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
    </div>
    
    <el-table 
      :data="gradeList" 
      border
      v-loading="loading"
      empty-text="暂无数据"
    >
      <el-table-column prop="courseId" label="课程编号" width="180" />
      <el-table-column prop="courseName" label="课程名称" width="180" />
      <el-table-column prop="grade" label="成绩" width="100" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getStudentGrades } from '@/api/student'

const searchKeyword = ref('')
const gradeList = ref([])
const loading = ref(false)

// 加载成绩列表
const loadGrades = async () => {
  loading.value = true
  try {
    const res = await getStudentGrades(searchKeyword.value)
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

onMounted(() => {
  loadGrades()
})
</script>

<style scoped>
.grades-view {
  padding: 20px;
}

.header {
  margin-bottom: 20px;
}
</style> 
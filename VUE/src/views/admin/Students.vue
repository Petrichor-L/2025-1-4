<template>
  <div class="students-manage">
    <!-- 搜索和添加区域 -->
    <div class="action-bar">
      <div class="search-box">
        <input 
          type="text" 
          v-model="searchQuery" 
          placeholder="搜索学生姓名或学号"
        >
        <button class="search-btn">
          <i class="fas fa-search"></i>
        </button>
      </div>
      <button class="add-btn" @click="showAddDialog = true">
        <i class="fas fa-plus"></i> 添加学生
      </button>
    </div>

    <!-- 学生列表 -->
    <div class="table-container">
      <table class="data-table">
        <thead>
          <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>班级</th>
            <th>联系方式</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="student in students" :key="student.id">
            <td>{{ student.studentId }}</td>
            <td>{{ student.name }}</td>
            <td>{{ student.className }}</td>
            <td>{{ student.phone }}</td>
            <td class="actions">
              <button class="edit-btn" @click="handleEdit(student)">
                <i class="fas fa-edit"></i>
              </button>
              <button class="delete-btn" @click="handleDelete(student.id)">
                <i class="fas fa-trash"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <button :disabled="currentPage === 1" @click="currentPage--">上一页</button>
      <span>{{ currentPage }} / {{ totalPages }}</span>
      <button :disabled="currentPage === totalPages" @click="currentPage++">下一页</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
  name: 'StudentsManage',
  setup() {
    const searchQuery = ref('')
    const currentPage = ref(1)
    const totalPages = ref(1)
    const students = ref([])
    const showAddDialog = ref(false)

    // 获取学生列表
    const fetchStudents = async () => {
      try {
        // 这里调用API获取学生数据
        // const response = await getStudents(currentPage.value, searchQuery.value)
        // students.value = response.data
        // totalPages.value = response.totalPages
      } catch (error) {
        console.error('获取学生列表失败:', error)
      }
    }

    const handleEdit = (student) => {
      // 实现编辑功能
    }

    const handleDelete = async (id) => {
      if (confirm('确定要删除这名学生吗？')) {
        try {
          // await deleteStudent(id)
          await fetchStudents()
        } catch (error) {
          console.error('删除失败:', error)
        }
      }
    }

    onMounted(fetchStudents)

    return {
      searchQuery,
      currentPage,
      totalPages,
      students,
      showAddDialog,
      handleEdit,
      handleDelete
    }
  }
}
</script>

<style scoped>
.students-manage {
  height: 100%;
}

.action-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  gap: 20px;
}

.search-box {
  display: flex;
  flex: 1;
  max-width: 400px;
}

.search-box input {
  flex: 1;
  padding: 8px 16px;
  border: 2px solid rgba(24, 144, 255, 0.2);
  border-radius: 8px 0 0 8px;
  font-size: 14px;
  transition: all 0.3s;
}

.search-box input:focus {
  border-color: #1890ff;
  outline: none;
}

.search-btn {
  padding: 8px 16px;
  background: #1890ff;
  border: none;
  border-radius: 0 8px 8px 0;
  color: white;
  cursor: pointer;
}

.add-btn {
  padding: 8px 20px;
  background: linear-gradient(45deg, #1890ff, #36cfc9);
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 8px;
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.3);
}

.table-container {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th,
.data-table td {
  padding: 12px 16px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

.data-table th {
  background: #fafafa;
  font-weight: 600;
  color: #666;
}

.data-table tr:hover {
  background: #fafafa;
}

.actions {
  display: flex;
  gap: 8px;
}

.edit-btn,
.delete-btn {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.edit-btn {
  background: #1890ff;
  color: white;
}

.delete-btn {
  background: #ff4d4f;
  color: white;
}

.edit-btn:hover,
.delete-btn:hover {
  opacity: 0.8;
  transform: translateY(-1px);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  gap: 16px;
}

.pagination button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  background: #1890ff;
  color: white;
  cursor: pointer;
  transition: all 0.3s;
}

.pagination button:disabled {
  background: #d9d9d9;
  cursor: not-allowed;
}

.pagination button:not(:disabled):hover {
  background: #40a9ff;
}
</style> 
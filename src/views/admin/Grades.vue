<template>
  <div class="grades-manage">
    <div class="header">
      <h2>成绩管理</h2>
      <button class="add-btn" @click="showAddDialog">录入成绩</button>
    </div>

    <div class="filter-section">
      <select v-model="selectedCourse">
        <option value="">选择课程</option>
        <option v-for="course in courses" :key="course.id" :value="course.id">
          {{ course.name }}
        </option>
      </select>
    </div>

    <table class="data-table">
      <thead>
        <tr>
          <th>学号</th>
          <th>学生姓名</th>
          <th>课程</th>
          <th>成绩</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="grade in grades" :key="grade.id">
          <td>{{ grade.studentId }}</td>
          <td>{{ grade.studentName }}</td>
          <td>{{ grade.courseName }}</td>
          <td>{{ grade.score }}</td>
          <td>
            <button @click="editGrade(grade)">编辑</button>
            <button @click="deleteGrade(grade.id)" class="delete-btn">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 添加/编辑成绩的对话框 -->
    <div v-if="showDialog" class="dialog-overlay">
      <div class="dialog">
        <h3>{{ isEditing ? '编辑成绩' : '录入成绩' }}</h3>
        <form @submit.prevent="submitGrade">
          <div class="form-group">
            <label>学号：</label>
            <input type="text" v-model="currentGrade.studentId" required>
          </div>
          <div class="form-group">
            <label>课程：</label>
            <select v-model="currentGrade.courseId" required>
              <option v-for="course in courses" :key="course.id" :value="course.id">
                {{ course.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>成绩：</label>
            <input type="number" v-model="currentGrade.score" min="0" max="100" required>
          </div>
          <div class="dialog-buttons">
            <button type="submit">保存</button>
            <button type="button" @click="closeDialog">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GradesManage',
  data() {
    return {
      selectedCourse: '',
      showDialog: false,
      isEditing: false,
      currentGrade: {
        studentId: '',
        courseId: '',
        score: ''
      },
      courses: [
        { id: 1, name: '高等数学' },
        { id: 2, name: '大学英语' }
      ],
      grades: [
        {
          id: 1,
          studentId: '2024001',
          studentName: '张三',
          courseName: '高等数学',
          score: 85
        },
        {
          id: 2,
          studentId: '2024002',
          studentName: '李四',
          courseName: '大学英语',
          score: 90
        }
      ]
    }
  },
  methods: {
    showAddDialog() {
      this.isEditing = false;
      this.currentGrade = {
        studentId: '',
        courseId: '',
        score: ''
      };
      this.showDialog = true;
    },
    editGrade(grade) {
      this.isEditing = true;
      this.currentGrade = { ...grade };
      this.showDialog = true;
    },
    closeDialog() {
      this.showDialog = false;
    },
    submitGrade() {
      // 这里实现保存成绩的逻辑
      // 如果是编辑模式，更新现有成绩
      // 如果是添加模式，添加新成绩
      this.showDialog = false;
    },
    deleteGrade(id) {
      if (confirm('确定要删除这条成绩记录吗？')) {
        // 这里实现删除成绩的逻辑
      }
    }
  }
}
</script>

<style scoped>
.grades-manage {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.filter-section {
  margin-bottom: 20px;
}

.filter-section select {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ddd;
  min-width: 200px;
}

.add-btn {
  background-color: #42b983;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.data-table th,
.data-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.data-table th {
  background-color: #f8f9fa;
}

.delete-btn {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  margin-left: 5px;
  cursor: pointer;
}

/* 对话框样式 */
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.dialog {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 400px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.dialog-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.dialog-buttons button {
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.dialog-buttons button[type="submit"] {
  background-color: #42b983;
  color: white;
  border: none;
}

.dialog-buttons button[type="button"] {
  background-color: #6c757d;
  color: white;
  border: none;
}
</style> 
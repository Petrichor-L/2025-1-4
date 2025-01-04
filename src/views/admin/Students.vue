<template>
  <div class="students-manage">
    <div class="header">
      <h2>学生管理</h2>
      <button class="add-btn" @click="showAddDialog">添加学生</button>
    </div>

    <table class="data-table">
      <thead>
        <tr>
          <th>学号</th>
          <th>姓名</th>
          <th>班级</th>
          <th>专业</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="student in students" :key="student.id">
          <td>{{ student.studentId }}</td>
          <td>{{ student.name }}</td>
          <td>{{ student.class }}</td>
          <td>{{ student.major }}</td>
          <td>
            <button @click="editStudent(student)">编辑</button>
            <button @click="deleteStudent(student.id)" class="delete-btn">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'StudentsManage',
  data() {
    return {
      students: [
        {
          id: 1,
          studentId: '2024001',
          name: '张三',
          class: '计算机2班',
          major: '计算机科学'
        },
        // 更多示例数据...
      ]
    }
  },
  methods: {
    showAddDialog() {
      // 实现添加学生的逻辑
    },
    editStudent(student) {
      // 实现编辑学生的逻辑
    },
    deleteStudent(id) {
      // 实现删除学生的逻辑
    }
  }
}
</script>

<style scoped>
.students-manage {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
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
</style> 
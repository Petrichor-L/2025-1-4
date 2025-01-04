<template>
  <div class="courses-manage">
    <div class="header">
      <h2>课程管理</h2>
      <button class="add-btn" @click="showAddDialog">添加课程</button>
    </div>

    <table class="data-table">
      <thead>
        <tr>
          <th>课程编号</th>
          <th>课程名称</th>
          <th>授课教师</th>
          <th>学分</th>
          <th>上课时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.courseId }}</td>
          <td>{{ course.name }}</td>
          <td>{{ course.teacher }}</td>
          <td>{{ course.credit }}</td>
          <td>{{ course.time }}</td>
          <td>
            <button @click="editCourse(course)">编辑</button>
            <button @click="deleteCourse(course.id)" class="delete-btn">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'CoursesManage',
  data() {
    return {
      courses: [
        {
          id: 1,
          courseId: 'CS001',
          name: '高等数学',
          teacher: '张教授',
          credit: 4,
          time: '周一 1-2节'
        },
        // 更多示例数据...
      ]
    }
  },
  methods: {
    showAddDialog() {
      // 实现添加课程的逻辑
    },
    editCourse(course) {
      // 实现编辑课程的逻辑
    },
    deleteCourse(id) {
      // 实现删除课程的逻辑
    }
  }
}
</script>

<style scoped>
.courses-manage {
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
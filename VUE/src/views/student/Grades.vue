<template>
  <div class="grades-container">
    <h2>我的成绩</h2>
    <div class="grades-table">
      <table>
        <thead>
          <tr>
            <th>课程名称</th>
            <th>学分</th>
            <th>成绩</th>
            <th>绩点</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="grade in grades" :key="grade.id">
            <td>{{ grade.courseName }}</td>
            <td>{{ grade.credit }}</td>
            <td>{{ grade.score }}</td>
            <td>{{ grade.gpa }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StudentGrades',
  data() {
    return {
      grades: [
        { id: 1, courseName: '高等数学', credit: 4, score: 85, gpa: 3.5 },
        { id: 2, courseName: '大学物理', credit: 3, score: 88, gpa: 3.7 },
        // 示例数据
      ]
    }
  }
}
</script>

<style scoped>
.grades-container {
  padding: 20px;
}

.grades-table {
  margin-top: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background-color: #f8f9fa;
  font-weight: 600;
}

tr:hover {
  background-color: #f5f5f5;
}
</style> 
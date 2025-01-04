<template>
  <div class="courses-container">
    <h2>我的课程</h2>
    <div class="courses-grid">
      <div v-for="course in courses" :key="course.id" class="course-card">
        <h3>{{ course.name }}</h3>
        <div class="course-info">
          <p><i class="fas fa-user"></i> 教师：{{ course.teacher }}</p>
          <p><i class="fas fa-clock"></i> 时间：{{ course.time }}</p>
          <p><i class="fas fa-map-marker-alt"></i> 地点：{{ course.location }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StudentCourses',
  data() {
    return {
      courses: [
        {
          id: 1,
          name: '高等数学',
          teacher: '张教授',
          time: '周一 1-2节',
          location: '教学楼A-101'
        },
        // 示例数据
      ]
    }
  }
}
</script>

<style scoped>
.courses-container {
  padding: 20px;
}

.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.course-card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.course-card h3 {
  margin-bottom: 15px;
  color: #333;
}

.course-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.course-info p {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #666;
}

.course-info i {
  color: #667eea;
}
</style> 
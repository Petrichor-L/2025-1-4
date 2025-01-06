<template>
  <div class="courses-container">
    <h2>我的课程</h2>
    <ul>
      <li v-for="course in courses" :key="course.courseId" class="course-item">
        <div class="course-details">
          <h3 class="course-name">{{ course.courseName }}</h3>
          <p class="class-time">{{ course.classTime }}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { fetchCourses } from '@/api/teacher'; // 导入API

export default {
  name: 'TeacherCourses',
  
  data() {
    return {
      courses: [] // 存储课程信息
    };
  },
  
  mounted() {
    this.loadCourses(); // 组件挂载后获取课程数据
  },
  
  methods: {
    async loadCourses() {
      try {
        const data = await fetchCourses('t1'); // 调用API
        console.log('从后端收到的数据:', data);
        
        // 处理课程数据
        this.courses = data.map(course => ({
          courseId: course.courseId,
          courseName: course.courseName,
          classTime: this.generateRandomClassTime() // 生成随机上课时间
        }));
      } catch (error) {
        console.error('获取课程失败:', error);
      }
    },
    
    generateRandomClassTime() {
      const days = ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天'];
      const period = Math.floor(Math.random() * 10) + 1; // 假设有10节课
      const day = days[Math.floor(Math.random() * days.length)];
      return `${day} 第${period}节`; // 格式化为“星期几第几节课”
    }
  }
}
</script>

<style scoped>
/* 相关样式 */
.courses-container {
  padding: 25px; /* 增加内边距 */
  background-color: #e9f5ff; /* 更明亮的背景色 */
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  max-width: 500px; /* 增加最大宽度 */
  margin: 20px auto; /* 上下留白，居中对齐 */
}

h2 {
  color: #333;
  text-align: center; /* 标题居中 */
  margin-bottom: 20px; /* 标题下方间距 */
  font-size: 24px; /* 增加标题字体大小 */
}

ul {
  list-style-type: none;
  padding: 0;
}

.course-item {
  padding: 10px; /* 内边距 */
  border: 1px solid #007BFF; /* 使用蓝色边框 */
  border-radius: 5px;
  margin-bottom: 8px; /* 列表项之间的间距 */
  background-color: #ffffff; /* 白色背景 */
  transition: background-color 0.3s, transform 0.3s; /* 背景色和缩放过渡效果 */
  display: flex; /* 使用flex布局 */
  justify-content: space-between; /* 使内容两端对齐 */
}

.course-item:hover {
  background-color: #f0f8ff; /* 悬停时的背景色 */
  transform: scale(1.02); /* 悬停时轻微放大 */
}

.course-details {
  display: flex;
  justify-content: space-between; /* 使课程名称和上课时间两端对齐 */
  width: 100%; /* 使内容占满整个宽度 */
}

.course-name {
  margin: 0; /* 去掉默认的外边距 */
  color: #007BFF; /* 课程名称颜色 */
  font-size: 18px; /* 增加课程名称字体大小 */
}

.class-time {
  margin: 0; /* 去掉默认的外边距 */
  color: #555; /* 上课时间颜色 */
  text-align: right; /* 右对齐 */
}
</style> 
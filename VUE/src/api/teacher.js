import request from '@/utils/request'


// 获取教师的课程
export function getTeacherCourses(teacherId) {
  return request({
    url: '/api/teacher/Courses',
    method: 'get',
    params: { teacherId }
  })
}

// 获取教师所教课程的所有成绩
export function getTeacherGrades(teacherId) {
  return request({
    url: '/api/teacher/allGrade',
    method: 'get',
    params: { teacherId }
  })
}

// 添加成绩
export function addGrade(studentId, courseId, grade) {
  return request({
    url: '/api/teacher/addGrade',
    method: 'get',  // 后端用的是 GetMapping
    params: {
      studentId,
      courseId,
      grade
    }
  })
}

// 更新成绩
export function updateGrade(studentId, courseId, grade) {
  return request({
    url: '/api/teacher/upGrade',
    method: 'get',  // 后端用的是 GetMapping
    params: {
      studentId,
      courseId,
      grade
    }
  })
}

// 课程添加学生
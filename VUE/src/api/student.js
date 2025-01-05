import request from '@/utils/request'

// 获取学生成绩
export function getStudentGrades(keyword) {
  const user = JSON.parse(localStorage.getItem('user'))
  return request({
    url: '/api/student/grades',
    method: 'get',
    params: { 
      studentId: user.username,
      keyword 
    }
  })
}

// 获取学生课程
export function getStudentCourses() {
  const user = JSON.parse(localStorage.getItem('user'))
  return request({
    url: '/api/student/courses',
    method: 'get',
    params: {
      studentId: user.username
    }
  })
} 
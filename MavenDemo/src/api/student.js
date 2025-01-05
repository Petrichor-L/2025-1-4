import request from '@/utils/request'

// 获取学生成绩
export function getMyGrades() {
  return request({
    url: '/api/student/grades',
    method: 'get'
  })
}

// 获取学生课程
export function getMyCourses() {
  return request({
    url: '/api/student/courses',
    method: 'get'
  })
} 
import request from '@/utils/request'

// 获取教师所教课程的所有成绩
export function getTeacherGrades(teacherId) {
  return request({
    url: '/api/teacher/allGrade',
    method: 'get',
    params: { teacherId }
  })
}

// 根据学号或学生姓名查询成绩
export function searchGrades(keyword, teacherId) {
  return request({
    url: '/api/teacher/searchGrades',
    method: 'get',
    params: { keyword, teacherId }
  })
}

// 获取教师的课程
export function getTeacherCourses(teacherId) {
  return request({
    url: '/api/teacher/courses',
    method: 'get',
    params: { teacherId }
  })
}

// 添加成绩
export function addGrade(studentId, courseId, grade) {
  return request({
    url: '/api/teacher/addGrade',
    method: 'get',
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
    method: 'get',
    params: {
      studentId,
      courseId,
      grade
    }
  })
}

// 课程添加学生
export function addStudentToCourse(studentId, courseId) {
  return request({
    url: '/api/teacher/linkStu',
    method: 'get',
    params: {
      studentId,
      courseId
    }


  })







} 
// src/api/teacher.js
export async function fetchCourses(teacherId) {
  const response = await fetch(`http://localhost:8081/api/teacher/Courses?teacherId=${teacherId}`);
  if (!response.ok) {
    throw new Error('网络响应不正常');
  }
  return await response.json();
}
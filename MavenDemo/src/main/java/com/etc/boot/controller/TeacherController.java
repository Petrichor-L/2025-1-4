package com.etc.boot.controller;

import com.etc.boot.Pojo.Course;
import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.Service.ITeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@Slf4j
@CrossOrigin
public class TeacherController {

    @Autowired
    private ITeacherService iTeacherService;

    // 成绩管理
    // 获取所有成绩
    @GetMapping("/allGrade")
    public List<GetGrade> getGrades(String teacherId) {

        return iTeacherService.getGrade(teacherId);
    }

    // 设置成绩
    @GetMapping("/addGrade")
    public int addGrade(String studentId, int courseId, int grade) {
        return iTeacherService.addGrade(studentId, courseId, grade);
    }

    // 更新成绩
    @GetMapping("/upGrade")
    public int upGrade(String studentId, int courseId, int grade) {
        return iTeacherService.upGrade(studentId, courseId, grade);
    }

    // 课程管理
    // 课程添加学生
    @GetMapping("/linkStu")
    public int linkStuCourse(String studentId, int courseId) {
        return iTeacherService.linkStuCourse(studentId, courseId);
    }

    @GetMapping("/Courses")
    public List<Course> findTeacherCourses(String teacherId) {
        return iTeacherService.findTeacherCourses(teacherId);
    }

    @GetMapping("/searchGrades")
    public List<GetGrade> searchStudentGradesForTeacher(String keyword, String teacherId) {
        return iTeacherService.searchStudentGradesForTeacher(keyword, teacherId);
    }

}
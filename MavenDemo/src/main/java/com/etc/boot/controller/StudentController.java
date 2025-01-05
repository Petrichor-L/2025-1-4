package com.etc.boot.controller;

import com.etc.boot.Pojo.Course;
import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.Pojo.User;
import com.etc.boot.Service.StudentService;
import com.etc.boot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/student")
@Slf4j
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private HttpSession session;

    // 查询学生自己的成绩
    @GetMapping("/grades")
    public Result<List<GetGrade>> getMyGrades() {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null || !"student".equals(user.getRole())) {
                return Result.error("请先登录或无权限访问");
            }
            List<GetGrade> grades = studentService.getStudentGrades(user.getUsername());
            return Result.success(grades);
        } catch (Exception e) {
            log.error("查询成绩失败", e);
            return Result.error("查询成绩失败：" + e.getMessage());
        }
    }

    // 查询学生选修的课程
    @GetMapping("/courses")
    public Result<List<Course>> getMyCourses() {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null || !"student".equals(user.getRole())) {
                return Result.error("请先登录或无权限访问");
            }
            List<Course> courses = studentService.getStudentCourses(user.getUsername());
            return Result.success(courses);
        } catch (Exception e) {
            log.error("查询课程失败", e);
            return Result.error("查询课程失败：" + e.getMessage());
        }
    }
}

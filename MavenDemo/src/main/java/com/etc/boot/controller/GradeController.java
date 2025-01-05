package com.etc.boot.controller;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.Pojo.Grade;
import com.etc.boot.Service.IGradeService;
import com.etc.boot.common.Result;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Slf4j
public class GradeController {
    @Autowired
    private IGradeService gradeService;

    // 获取成绩列表（管理员可查看所有，教师只能查看自己课程的，学生只能查看自己的）
    @GetMapping
    public Result<List<GetGrade>> getGrades(@RequestParam(required = false) String studentId,
                                          @RequestParam(required = false) String courseId,
                                          @RequestParam(required = false) String teacherId) {
        try {
            // 从token或session中获取当前用户角色和ID
            String role = getCurrentUserRole();
            String userId = getCurrentUserId();
            
            List<GetGrade> grades;
            switch (role) {
                case "admin":
                    // 管理员可以查看所有成绩
                    grades = gradeService.getGradeAll();
                    break;
                case "teacher":
                    // 教师只能查看自己课程的成绩
                    grades = gradeService.getGradesByTeacher(userId);
                    break;
                case "student":
                    // 学生只能查看自己的成绩
                    grades = gradeService.getGradeStu(Integer.parseInt(userId));
                    break;
                default:
                    return Result.error("无权限访问");
            }
            return Result.success(grades);
        } catch (Exception e) {
            log.error("查询成绩失败", e);
            return Result.error("查询成绩失败：" + e.getMessage());
        }
    }

    // 添加/修改成绩（管理员和教师可用）
    @PostMapping
    public Result<Integer> saveGrade(@RequestBody Grade grade) {
        try {
            String role = getCurrentUserRole();
            String userId = getCurrentUserId();
            
            // 只有管理员和教师可以修改成绩
            if (!("admin".equals(role) || "teacher".equals(role))) {
                return Result.error("无权限操作");
            }
            
            // 如果是教师，需要验证是否是该课程的授课教师
            if ("teacher".equals(role)) {
                boolean isTeacherOfCourse = gradeService.validateTeacherCourse(userId, grade.getCourseId());
                if (!isTeacherOfCourse) {
                    return Result.error("您不是该课程的授课教师");
                }
            }
            
            int result = gradeService.addGrade(grade.getStudentId(), grade.getCourseId(), grade.getGrade());
            return Result.success(result);
        } catch (Exception e) {
            log.error("保存成绩失败", e);
            return Result.error("保存成绩失败：" + e.getMessage());
        }
    }

    // 删除成绩（仅管理员可用）
    @DeleteMapping("/{studentId}/{courseId}")
    public Result<Integer> deleteGrade(@PathVariable int studentId, @PathVariable int courseId) {
        try {
            String role = getCurrentUserRole();
            if (!"admin".equals(role)) {
                return Result.error("仅管理员可删除成绩");
            }
            
            int result = gradeService.deleteGrade(studentId, courseId);
            return Result.success(result);
        } catch (Exception e) {
            log.error("删除成绩失败", e);
            return Result.error("删除成绩失败：" + e.getMessage());
        }
    }

    // 添加这些方法来获取当前用户信息
    private String getCurrentUserRole() {
        // TODO: 从 Session 或 Token 中获取
        return "admin"; // 临时返回，需要实现真实的用户角色获取逻辑
    }
    
    private String getCurrentUserId() {
        // TODO: 从 Session 或 Token 中获取
        return "1"; // 临时返回，需要实现真实的用户ID获取逻辑
    }
}

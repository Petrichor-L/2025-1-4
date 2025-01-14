package com.etc.boot.controller;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.Pojo.Grade;
import com.etc.boot.Service.IGradeService;
import com.etc.boot.common.Result;
import com.etc.boot.Pojo.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/grades")
@Slf4j
public class GradeController {
    @Autowired
    private IGradeService gradeService;

    @Autowired
    private HttpServletRequest request;

    // 获取成绩列表（管理员可查看所有，教师只能查看自己课程的，学生只能查看自己的）
    @GetMapping
    public Result<List<GetGrade>> getGrades(@RequestParam(required = false) String keyword) {
        try {
            List<GetGrade> grades;
            if (StringUtils.hasText(keyword)) {
                grades = gradeService.searchGrades(keyword);
            } else {
                grades = gradeService.getGradeAll();
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
            // 添加成绩
            boolean success = gradeService.addGrade(grade.getStudentId(), 
                                                  grade.getCourseId(), 
                                                  grade.getGrade());
            
            return success ? Result.success(1) : Result.error("添加成绩失败");
        } catch (Exception e) {
            log.error("保存成绩失败", e);
            return Result.error("保存成绩失败：" + e.getMessage());
        }
    }

    // 删除成绩（仅管理员可用）
    @DeleteMapping("/{studentId}/{courseId}")
    public Result<Integer> deleteGrade(@PathVariable String studentId, @PathVariable Integer courseId) {
        try {
            // 从token或session中获取当前用户角色
            String role = getCurrentUserRole();
            if (!"admin".equals(role)) {
                return Result.error("仅管理员可删除成绩");
            }
            
            int result = gradeService.deleteGrade(studentId, courseId);
            if (result > 0) {
                return Result.success(result);
            } else {
                return Result.error("删除成绩失败");
            }
        } catch (Exception e) {
            log.error("删除成绩失败", e);
            return Result.error("删除成绩失败：" + e.getMessage());
        }
    }

    @PutMapping
    public Result<String> updateGrade(@RequestBody Grade grade) {
        try {
            int result = gradeService.updateGrade(
                grade.getStudentId(), 
                grade.getCourseId(), 
                grade.getGrade()
            );
            return result > 0 ? Result.success("成绩修改成功") : Result.error("成绩修改失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 添加这些方法来获取当前用户信息
    private String getCurrentUserRole() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new RuntimeException("请先登录");
        }
        return user.getRole();
    }
    
    private String getCurrentUserId() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new RuntimeException("请先登录");
        }
        return user.getUsername();
    }
}

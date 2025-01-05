package com.etc.boot.controller;

import com.etc.boot.Pojo.User;
import com.etc.boot.Pojo.Course;
import com.etc.boot.common.Result;
import com.etc.boot.Service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@CrossOrigin
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    // 用户管理
    @GetMapping("/users")
    public Result<List<User>> listUsers() {
        return Result.success(adminService.getAllUsers());
    }
    
    // 课程管理
    @GetMapping("/courses")
    public Result<List<Course>> listCourses() {
        return Result.success(adminService.getAllCourses());
    }
    
    @PostMapping("/courses")
    public Result<Course> addCourse(@RequestBody Course course) {
        return Result.success(adminService.addCourse(course));
    }
    
    @PutMapping("/courses/{id}")
    public Result<Course> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        course.setId(id);
        return Result.success(adminService.updateCourse(course));
    }
    
    @DeleteMapping("/courses/{id}")
    public Result<Void> deleteCourse(@PathVariable Integer id) {
        adminService.deleteCourse(id);
        return Result.success(null);
    }
    
    @GetMapping("/students")
    public Result<List<User>> listStudents(@RequestParam(required = false) String keyword) {
        log.info("查询学生列表，关键字：{}", keyword);
        List<User> students;
        try {
            if (StringUtils.hasText(keyword)) {
                students = adminService.searchStudents(keyword);
            } else {
                students = adminService.getAllStudents();
            }
            return Result.success(students);
        } catch (Exception e) {
            log.error("查询学生列表失败", e);
            return Result.error("查询学生列表失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/users")
    public Result<User> addUser(@RequestBody User user) {
        log.info("添加用户：{}", user);
        try {
            // 检查用户名是否已存在
            if (adminService.checkUsernameExist(user.getUsername())) {
                return Result.error("用户名已存在");
            }
            
            // 如果没有设置密码，使用默认密码
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword("123456"); // 默认密码
                log.info("用户未设置密码，使用默认密码");
            }
            
            return Result.success(adminService.addUser(user));
        } catch (Exception e) {
            log.error("添加用户失败", e);
            return Result.error("添加用户失败：" + e.getMessage());
        }
    }
    
    @PutMapping("/users/{username}")
    public Result<User> updateUser(@PathVariable String username, @RequestBody User user) {
        log.info("更新用户信息：{}", user);
        log.info("接收到的用户信息：{}", user);
        try {
            // 确保用户名匹配
            if (!username.equals(user.getUsername())) {
                return Result.error("用户名不匹配");
            }
            return Result.success(adminService.updateUser(user));
        } catch (Exception e) {
            log.error("更新用户失败", e);
            return Result.error("更新用户失败：" + e.getMessage());
        }
    }
    
    @DeleteMapping("/users/{username}")
    public Result<Void> deleteUser(@PathVariable String username) {
        log.info("删除用户：{}", username);
        try {
            // 检查要删除的用户是否为学生
            User user = adminService.getUserByUsername(username);
            if (user == null) {
                return Result.error("用户不存在");
            }
            if (!"student".equals(user.getRole())) {
                return Result.error("只能删除学生账户");
            }
            
            adminService.deleteUserByUsername(username);
            return Result.success(null);
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return Result.error("删除用户失败：" + e.getMessage());
        }
    }
} 
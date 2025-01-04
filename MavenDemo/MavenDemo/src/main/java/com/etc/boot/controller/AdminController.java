package com.etc.boot.controller;

import com.etc.boot.Pojo.User;
import com.etc.boot.Pojo.Course;
import com.etc.boot.common.Result;
import com.etc.boot.Service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
    @DeleteMapping("/users/{id}")
    public Result<Void> deleteUser(@PathVariable Integer id) {
        adminService.deleteUser(id);
        return Result.success(null);
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
} 
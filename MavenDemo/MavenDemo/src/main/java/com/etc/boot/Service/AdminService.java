package com.etc.boot.Service;

import com.etc.boot.Pojo.User;
import com.etc.boot.Pojo.Course;
import java.util.List;

public interface AdminService {
    // 用户管理
    List<User> getAllUsers();
    void deleteUser(Integer id);
    
    // 课程管理
    List<Course> getAllCourses();
    Course addCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourse(Integer id);
} 
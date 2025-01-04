package com.etc.boot.Service.impl;

import com.etc.boot.Pojo.User;
import com.etc.boot.Pojo.Course;
import com.etc.boot.mapper.UserMapper;
import com.etc.boot.mapper.CourseMapper;
import com.etc.boot.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
    
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }
    
    @Override
    public List<Course> getAllCourses() {
        return courseMapper.findAll();
    }
    
    @Override
    @Transactional
    public Course addCourse(Course course) {
        courseMapper.insert(course);
        return course;
    }
    
    @Override
    @Transactional
    public Course updateCourse(Course course) {
        courseMapper.update(course);
        return course;
    }
    
    @Override
    @Transactional
    public void deleteCourse(Integer id) {
        courseMapper.deleteById(id);
    }
} 
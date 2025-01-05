package com.etc.boot.Service.impl;

import com.etc.boot.Pojo.User;
import com.etc.boot.Pojo.Course;
import com.etc.boot.mapper.UserMapper;
import com.etc.boot.mapper.CourseMapper;
import com.etc.boot.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    
    @Override
    public List<User> getAllStudents() {
        return userMapper.findAllStudents();
    }
    
    @Override
    public List<User> searchStudents(String keyword) {
        return userMapper.searchStudents(keyword);
    }
    
    @Override
    public boolean checkUsernameExist(String username) {
        return userMapper.findByUsername(username) != null;
    }
    
    @Override
    @Transactional
    public User addUser(User user) {
        userMapper.insert(user);
        return user;
    }
    
    @Override
    @Transactional
    public User updateUser(User user) {
        // 不允许修改用户名
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }
        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        userMapper.update(existingUser);
        return existingUser;
    }
    
    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        userMapper.deleteByUsername(username);
    }

    @Override
    public User getUserByUsername(String username) {
        // 通过用户名查询用户
        return userMapper.selectByUsername(username);
    }
} 
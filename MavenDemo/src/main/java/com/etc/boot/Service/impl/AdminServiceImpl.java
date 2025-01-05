package com.etc.boot.Service.impl;

import com.etc.boot.Pojo.User;
import com.etc.boot.Pojo.Course;
import com.etc.boot.mapper.UserMapper;
import com.etc.boot.mapper.CourseMapper;
import com.etc.boot.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    
    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    
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
        List<Course> courses = courseMapper.findAll();
        log.debug("查询到的课程数量: {}", courses.size());
        return courses;
    }
    
    @Override
    @Transactional
    public boolean addCourse(Course course) {
        return courseMapper.insert(course) > 0;
    }
    
    @Override
    @Transactional
    public Course updateCourse(Course course) {
        // 确保课程存在
        if (course.getCourseId() == null) {
            throw new RuntimeException("课程编号不能为空");
        }
        courseMapper.update(course);
        return course;
    }
    
    @Override
    @Transactional
    public void deleteCourse(Integer id) {
        // 这里也需要修改
        courseMapper.deleteById(id.toString());
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

    @Override
    public Course getCourseById(String courseId) {
        return courseMapper.findByCourseId(courseId);
    }

    @Override
    public User getTeacherByName(String name) {
        return userMapper.findTeacherByName(name);
    }

    @Override
    public List<Course> searchCourses(String keyword) {
        return courseMapper.searchCourses(keyword);
    }
} 
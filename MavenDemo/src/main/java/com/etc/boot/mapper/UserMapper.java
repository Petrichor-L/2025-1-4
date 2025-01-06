package com.etc.boot.mapper;

import com.etc.boot.Pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名和密码查询用户
     */
    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(String username, String password);
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
    
    /**
     * 插入新用户
     */
    @Insert("INSERT INTO users (username, name, password, role, phone) " +
            "VALUES (#{username}, #{name}, #{password}, #{role}, #{phone})")
    int insert(User user);
    
    @Select("SELECT * FROM users")
    List<User> findAll();
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
    
    /**
     * 查询所有学生
     */
    @Select("SELECT username, name, phone FROM users WHERE role = 'student'")
    List<User> findAllStudents();
    
    /**
     * 根据姓名或学号查询学生
     */
    @Select("SELECT username, name, phone FROM users " +
            "WHERE role = 'student' " +
            "AND (username LIKE CONCAT('%',#{keyword},'%') " +
            "OR name LIKE CONCAT('%',#{keyword},'%'))")
    List<User> searchStudents(@Param("keyword") String keyword);
    
    @Update("UPDATE users SET name=#{name}, phone=#{phone} WHERE username=#{username}")
    int update(User user);
    
    @Delete("DELETE FROM users WHERE username = #{username}")
    int deleteByUsername(@Param("username") String username);
    
    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectByUsername(@Param("username") String username);
    
    @Select("SELECT * FROM users WHERE name = #{name} AND role = 'teacher'")
    User findTeacherByName(@Param("name") String name);
    
    /**
     * 更新用户密码
     */
    @Update("UPDATE users SET password = #{newPassword} WHERE username = #{username}")
    int updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
} 
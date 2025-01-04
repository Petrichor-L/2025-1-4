package com.etc.boot.mapper;

import com.etc.boot.Pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    
    @Select("SELECT c.*, u.name as teacher_name FROM courses c " +
            "LEFT JOIN users u ON c.teacher_id = u.username " +
            "WHERE u.role = 'teacher'")
    List<Course> findAll();
    
    @Select("SELECT c.*, u.name as teacher_name FROM courses c " +
            "LEFT JOIN users u ON c.teacher_id = u.username " +
            "WHERE c.id = #{id} AND u.role = 'teacher'")
    Course findById(@Param("id") Integer id);
    
    @Insert("INSERT INTO courses (course_id, course_name, classroom, teacher_id) " +
            "VALUES (#{courseId}, #{courseName}, #{classroom}, #{teacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);
    
    @Update("UPDATE courses SET course_id=#{courseId}, course_name=#{courseName}, " +
            "classroom=#{classroom}, teacher_id=#{teacherId} " +
            "WHERE id=#{id}")
    int update(Course course);
    
    @Delete("DELETE FROM courses WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
} 
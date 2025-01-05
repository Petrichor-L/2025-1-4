package com.etc.boot.mapper;

import com.etc.boot.Pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    
    @Select("SELECT c.*, u.name as teacher_name " +
            "FROM course c " +
            "LEFT JOIN users u ON c.teacher_id = u.username")
    List<Course> findAll();
    
    @Insert("INSERT INTO course (course_id, course_name, classroom, teacher_id) " +
            "VALUES (#{courseId}, #{courseName}, #{classroom}, #{teacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);
    
    @Update("UPDATE course SET course_name=#{courseName}, " +
            "classroom=#{classroom}, teacher_id=#{teacherId} " +
            "WHERE course_id=#{courseId}")
    int update(Course course);
    
    @Delete("DELETE FROM course WHERE course_id=#{courseId}")
    int deleteById(@Param("courseId") String courseId);
    
    @Select("SELECT c.*, u.name as teacher_name " +
            "FROM course c " +
            "LEFT JOIN users u ON c.teacher_id = u.username " +
            "WHERE c.course_id = #{courseId}")
    Course findByCourseId(@Param("courseId") String courseId);
} 
package com.etc.boot.mapper;

import com.etc.boot.Pojo.GetGrade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IGradeMapper {
    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "u.name as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.user_id " +
            "LEFT JOIN course c ON g.courseId = c.course_id")
    List<GetGrade> getGradeAll();

    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "u.name as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.user_id " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE g.studentId = #{studentId}")
    List<GetGrade> getGradeStu(int studentId);

    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "u.name as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.user_id " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE c.teacher_id = #{teacherId}")
    List<GetGrade> getGradesByTeacher(@Param("teacherId") String teacherId);

    @Insert("INSERT INTO grade (studentId, courseId, grade) " +
            "VALUES (#{studentId}, #{courseId}, #{grade})")
    int addGrade(@Param("studentId") int studentId, 
                 @Param("courseId") int courseId, 
                 @Param("grade") int grade);

    @Update("UPDATE grade SET grade = #{grade} " +
            "WHERE studentId = #{studentId} AND courseId = #{courseId}")
    int updateGrade(@Param("studentId") int studentId, 
                   @Param("courseId") int courseId, 
                   @Param("grade") int grade);

    @Delete("DELETE FROM grade " +
            "WHERE studentId = #{studentId} AND courseId = #{courseId}")
    int deleteGrade(@Param("studentId") int studentId, 
                   @Param("courseId") int courseId);

    @Select("SELECT COUNT(*) FROM course " +
            "WHERE course_id = #{courseId} AND teacher_id = #{teacherId}")
    int validateTeacherCourse(@Param("teacherId") String teacherId, 
                             @Param("courseId") int courseId);
}

package com.etc.boot.mapper;

import com.etc.boot.Pojo.GetGrade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IGradeMapper {
    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "COALESCE(u.name, u.username) as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.username " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE u.role = 'student'")
    List<GetGrade> getGradeAll();

    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "COALESCE(u.name, u.username) as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.username " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE g.studentId = #{studentId} AND u.role = 'student'")
    List<GetGrade> getGradeStu(String studentId);

    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "COALESCE(u.name, u.username) as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.username " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE c.teacher_id = #{teacherId} AND u.role = 'student'")
    List<GetGrade> getGradesByTeacher(@Param("teacherId") String teacherId);

    @Insert("INSERT INTO grade (studentId, courseId, grade) " +
            "VALUES (#{studentId}, #{courseId}, #{grade})")
    int addGrade(@Param("studentId") String studentId,
                 @Param("courseId") Integer courseId,
                 @Param("grade") Integer grade);

    @Update("UPDATE grade SET grade = #{grade} WHERE studentId = #{studentId} AND courseId = #{courseId}")
    int updateGrade(@Param("studentId") String studentId,
                    @Param("courseId") Integer courseId,
                    @Param("grade") Integer grade);

    @Delete("DELETE FROM grade WHERE studentId = #{studentId} AND courseId = #{courseId}")
    int deleteGrade(@Param("studentId") String studentId,
                    @Param("courseId") Integer courseId);

    @Select("SELECT COUNT(*) FROM course " +
            "WHERE course_id = #{courseId} AND teacher_id = #{teacherId}")
    int validateTeacherCourse(@Param("teacherId") String teacherId,
                              @Param("courseId") int courseId);

    @Select("SELECT COUNT(*) FROM course WHERE course_id = #{courseId}")
    int checkCourseExists(@Param("courseId") int courseId);

    @Select("SELECT COUNT(*) FROM users WHERE username = #{studentId} AND role = 'student'")
    int checkStudentExists(@Param("studentId") String studentId);

    @Select("SELECT COUNT(*) FROM grade WHERE studentId = #{studentId} AND courseId = #{courseId}")
    int checkGradeExists(@Param("studentId") String studentId, @Param("courseId") Integer courseId);


    //搜索成绩，模糊查询，可以匹配学生姓名、课程id
    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "COALESCE(u.name, u.username) as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.username " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE u.role = 'student' " +
            "AND (g.studentId LIKE CONCAT('%',#{keyword},'%') " +
            "OR c.course_id LIKE CONCAT('%',#{keyword},'%') " +
            "OR c.course_name LIKE CONCAT('%',#{keyword},'%'))")
    List<GetGrade> searchGrades(@Param("keyword") String keyword);

    @Select("SELECT g.studentId as studentId, g.courseId as courseId, g.grade, " +
            "COALESCE(u.name, u.username) as studentName, c.course_name as courseName " +
            "FROM grade g " +
            "LEFT JOIN users u ON g.studentId = u.username " +
            "LEFT JOIN course c ON g.courseId = c.course_id " +
            "WHERE g.studentId = #{studentId} " +
            "AND (c.course_name LIKE CONCAT('%',#{keyword},'%') " +
            "OR c.course_id LIKE CONCAT('%',#{keyword},'%'))")
    List<GetGrade> searchStudentGrades(@Param("studentId") String studentId,
                                       @Param("keyword") String keyword);

    // 通过学号和姓名（未完成）查询成绩（当前老师）
    @Select("SELECT g.studentId AS studentId, g.courseId AS courseId, g.grade AS grade,\n" +
            "       COALESCE(u.name, u.username) AS studentName, c.course_name AS courseName\n" +
            "FROM grade g\n" +
            "LEFT JOIN users u ON g.studentId = u.username\n" +
            "LEFT JOIN course c ON g.courseId = c.course_id\n" +
            "WHERE c.teacher_id = #{teacherId}\n" +
            "  AND (g.studentId = #{keyword} OR u.name LIKE CONCAT('%', #{keyword}, '%'));")
    List<GetGrade> searchStudentGradesForTeacher(String keyword, String teacherId);

    // 给课程添加学生
    @Insert("INSERT INTO grade (studentId, courseId) VALUES (#{studentId}, #{courseId})")
    int linkStuCourse(String studentId, int courseId);
}

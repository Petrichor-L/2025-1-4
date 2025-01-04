package com.etc.boot.mapper;

import com.etc.boot.Pojo.GetGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GradeMapper {
    // 获取某个学生成绩，接收学生id
    @Select("SELECT u.id AS studentId, u.name AS studentName, c.courseName, g.grade " +
            "FROM users u " +
            "JOIN grade g ON u.id = g.studentId " +
            "JOIN course c ON g.courseId = c.courseId " +
            "WHERE u.id = #{studentId}")
    List<GetGrade> getGradeStu(int studentId);

    // 获取所有学生成绩，无参
    @Select("SELECT u.id AS studentId, u.name AS studentName, c.courseName, g.grade " +
            "FROM users u " +
            "JOIN grade g ON u.id = g.studentId " +
            "JOIN course c ON g.courseId = c.courseId ")
    List<GetGrade> getGradeAll();
}

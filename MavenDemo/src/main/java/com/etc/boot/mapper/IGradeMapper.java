package com.etc.boot.mapper;

import com.etc.boot.Pojo.GetGrade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IGradeMapper {
    // 获取某个学生成绩，接收学生id
    @Select("SELECT DISTINCT u.id AS studentId, u.name AS studentName, c.courseName, g.grade " +
            "FROM users u " +
            "JOIN grade g ON u.id = g.studentId " +
            "JOIN course c ON g.courseId = c.courseId " +
            "WHERE u.id = #{studentId}")
    List<GetGrade> getGradeStu(int studentId);

    // 获取所有学生成绩，无参
    @Select("SELECT DISTINCT u.id AS studentId, u.name AS studentName, c.courseName, g.grade " +
            "FROM users u " +
            "JOIN grade g ON u.id = g.studentId " +
            "JOIN course c ON g.courseId = c.courseId ")
    List<GetGrade> getGradeAll();

    // 添加学生成绩
    @Insert("INSERT INTO `grade`.`grade` (`studentId`, `courseId`, `grade`) VALUES (#{studentId}, #{courseId}, #{grade})")
    int addGrade(int studentId, int courseId, int grade);

    // 更新学生成绩
    @Update("UPDATE `grade`.`grade` SET `grade` = #{grade} WHERE `studentId` = #{studentId} AND `courseId` = #{courseId}")
    int updateGrade(int studentId, int courseId, int grade);

    // 删除学生某个课程的全部成绩
    @Delete("DELETE FROM `grade`.`grade` WHERE `studentId` = #{studentId} AND `courseId` = #{courseId}")
    int deleteGrade(int studentId, int courseId);
}

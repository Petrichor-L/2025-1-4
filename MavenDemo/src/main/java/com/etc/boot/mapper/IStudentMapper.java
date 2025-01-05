package com.etc.boot.mapper;

import com.etc.boot.Pojo.GetStudent;
import com.etc.boot.Pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IStudentMapper {
    // 获取老师名下的所有学生，参数为学生id，返回一个user列表
    @Select("SELECT DISTINCT u.id AS id, u.username AS username, u.name AS name,  u.phone AS phone\n" +
            "FROM users u\n" +
            "JOIN grade g ON u.id = g.studentId\n" +
            "JOIN course c ON g.courseId = c.courseId\n" +
            "WHERE c.teacherId = (SELECT id FROM users WHERE role = 'teacher' AND id = #{teacherId});")
    List<GetStudent> getStudentAll(int teacherId);
}

package com.etc.boot.Service;

import com.etc.boot.Pojo.Course;
import com.etc.boot.Pojo.GetGrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITeacherService {
    List<GetGrade> getGrade(String teacherId);

    int addGrade(String studentId, int courseId, int grade);

    int linkStuCourse(String studentId, int courseId);

    int upGrade(String studentId, int courseId, int grade);

    List<Course> findTeacherCourses(String teacherId);

    List<GetGrade> searchStudentGradesForTeacher(String keyword, String teacherId);

//    List<Course> searchCourses(String keyword);
//
//    List<Course> getAllCourses();
}

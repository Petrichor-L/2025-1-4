package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;

import java.util.List;

public interface IGradeService {
    List<GetGrade> getGradeAll();

    List<GetGrade> getGradeStu(String studentId);

    List<GetGrade> getGradesByTeacher(String teacherId);

    boolean addGrade(String studentId, Integer courseId, Integer grade);

    int updateGrade(String studentId, Integer courseId, Integer grade);

    int deleteGrade(String studentId, Integer courseId);

    boolean validateTeacherCourse(String teacherId, int courseId);

    List<GetGrade> searchGrades(String keyword);
}

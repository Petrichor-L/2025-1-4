package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;

import java.util.List;

public interface IGradeService {
    List<GetGrade> getGradeAll();

    List<GetGrade> getGradeStu(int studentId);

    int addGrade(int studentId, int courseId, int grade);

    int updateGrade(int studentId, int courseId, int grade);

    int deleteGrade(int studentId, int courseId);
}

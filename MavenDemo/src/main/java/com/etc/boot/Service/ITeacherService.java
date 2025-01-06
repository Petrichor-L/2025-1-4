package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;

import java.util.List;

public interface ITeacherService {
    List<GetGrade> getGrade(String teacherId);

    int addGrade(String studentId, int courseId, int grade);

    boolean linkStuCourse(String studentId, int courseId);
}

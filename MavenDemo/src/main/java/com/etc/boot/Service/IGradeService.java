package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;

import java.util.List;

public interface IGradeService {
    public List<GetGrade> getGradeAll();

    public List<GetGrade> getGradeStu(int studentId);
}
package com.etc.boot.controller;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.Service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Grade")
public class GradeController {
    @Autowired
    private IGradeService iGradeService;

    @RequestMapping("All")
    public List<GetGrade> getGradeAll() {
        return iGradeService.getGradeAll();
    }

    @RequestMapping("Stu")
    public List<GetGrade> getGradestu(int studentId) {
        return iGradeService.getGradeStu(studentId);
    }

    @RequestMapping("Add")
    public int addGrade(int studentId, int courseId, int grade) {
        return iGradeService.addGrade(studentId, courseId, grade);
    }
}

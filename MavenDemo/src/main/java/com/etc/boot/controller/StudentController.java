package com.etc.boot.controller;

import com.etc.boot.Pojo.GetStudent;
import com.etc.boot.Pojo.User;
import com.etc.boot.Service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Student")
public class StudentController {
    @Autowired
    private IStudentService iStudentService;

    @RequestMapping("All")
    public List<GetStudent> getStudentAll(int teacherId) {
        return iStudentService.getStudentAll(teacherId);
    }
}

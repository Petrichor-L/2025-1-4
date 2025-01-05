package com.etc.boot.Service;

import com.etc.boot.Pojo.GetStudent;
import com.etc.boot.Pojo.User;

import java.util.List;

public interface IStudentService {
    public List<GetStudent> getStudentAll(int teacherId);
}

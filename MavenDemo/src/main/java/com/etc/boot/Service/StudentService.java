package com.etc.boot.Service;

import com.etc.boot.Pojo.GetStudent;
import com.etc.boot.Pojo.User;
import com.etc.boot.mapper.IStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentMapper iStudentMapper;

    @Override
    public List<GetStudent> getStudentAll(int teacherId) {
        return iStudentMapper.getStudentAll(teacherId);
    }
}

package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.mapper.IGradeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GradeService implements IGradeService {
    @Autowired
    private IGradeMapper gradeMapper;

    private static final Logger log = LoggerFactory.getLogger(GradeService.class);

    @Override
    public List<GetGrade> getGradeAll() {
        return gradeMapper.getGradeAll();
    }

    @Override
    public List<GetGrade> getGradeStu(String studentId) {
        return gradeMapper.getGradeStu(studentId);
    }

    @Override
    public List<GetGrade> getGradesByTeacher(String teacherId) {
        return gradeMapper.getGradesByTeacher(teacherId);
    }

    @Override
    @Transactional
    public boolean addGrade(String studentId, Integer courseId, Integer grade) {
        log.info("开始添加成绩，学号：{}，课程ID：{}，成绩：{}", studentId, courseId, grade);
        
        // 验证学生是否存在
        int studentExists = gradeMapper.checkStudentExists(studentId);
        log.info("检查学生 {} 是否存在的SQL结果：{}", studentId, studentExists);
        
        if (studentExists == 0) {
            log.error("学生不存在，学号：{}，查询结果：{}", studentId, studentExists);
            throw new RuntimeException("学生不存在");
        }
        
        // 验证课程是否存在
        if (gradeMapper.checkCourseExists(courseId) == 0) {
            throw new RuntimeException("课程不存在");
        }
        
        // 验证成绩范围
        if (grade < 0 || grade > 100) {
            throw new RuntimeException("成绩必须在0-100之间");
        }

        // 检查是否已存在该学生的该课程成绩
        if (gradeMapper.checkGradeExists(studentId, courseId) > 0) {
            throw new RuntimeException("该学生的这门课程成绩已存在");
        }
        
        return gradeMapper.addGrade(studentId, courseId, grade) > 0;
    }

    @Override
    public int updateGrade(String studentId, Integer courseId, Integer grade) {
        return gradeMapper.updateGrade(studentId, courseId, grade);
    }

    @Override
    public int deleteGrade(String studentId, Integer courseId) {
        return gradeMapper.deleteGrade(studentId, courseId);
    }

    @Override
    public boolean validateTeacherCourse(String teacherId, int courseId) {
        return gradeMapper.validateTeacherCourse(teacherId, courseId) > 0;
    }
}

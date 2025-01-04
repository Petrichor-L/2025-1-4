package com.etc.boot.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 班级实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    private Integer id;
    private String className;     // 班级名称
    private String grade;         // 年级
    private String department;    // 所属院系
    private Integer teacherId;    // 班主任ID
} 
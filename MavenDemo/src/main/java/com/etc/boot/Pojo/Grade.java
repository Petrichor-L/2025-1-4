package com.etc.boot.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private String studentId;
    private Integer courseId;
    private Integer grade;
}

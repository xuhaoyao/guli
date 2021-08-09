package com.scnu.edu.vo;

import lombok.Data;

@Data
public class CourseQuery {
    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

    private String status;
}

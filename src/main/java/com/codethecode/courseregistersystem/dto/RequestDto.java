package com.codethecode.courseregistersystem.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RequestDto {
    // An initial requestDto.
    // Which student requests which course from which teacher.
    // Relational (Foreign) keys are necessary to be implemented.
    private Long studentId;
    private Long courseId;
    private Long teacherId;
}

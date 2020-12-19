package com.tinyplan.exam.entity.dto;

public class UpdateJobOrder {
    private final String examNo;
    private final String oldExamStart;
    private final String newExamStart;
    private final String oldExamEnd;
    private final String newExamEnd;

    public UpdateJobOrder(String examNo, String oldExamStart, String newExamStart, String oldExamEnd, String newExamEnd) {
        this.examNo = examNo;
        this.oldExamStart = oldExamStart;
        this.newExamStart = newExamStart;
        this.oldExamEnd = oldExamEnd;
        this.newExamEnd = newExamEnd;
    }

    public String getExamNo() {
        return examNo;
    }

    public String getOldExamStart() {
        return oldExamStart;
    }

    public String getNewExamStart() {
        return newExamStart;
    }

    public String getOldExamEnd() {
        return oldExamEnd;
    }

    public String getNewExamEnd() {
        return newExamEnd;
    }
}

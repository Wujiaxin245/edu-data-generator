package com.example.edudata;

import java.time.LocalDateTime;

public class StudentRecord {
    public String studentId;
    public String studentCode;
    public int duration;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String isMorningStudy;
    public String status;
    public String durationStatus;

    public StudentRecord(String studentId, String studentCode, int duration, LocalDateTime startTime,
                         LocalDateTime endTime, String isMorningStudy, String status, String durationStatus) {
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isMorningStudy = isMorningStudy;
        this.status = status;
        this.durationStatus = durationStatus;
    }
}

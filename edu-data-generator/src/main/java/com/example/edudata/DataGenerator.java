package com.example.edudata;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private int total;
    private double abnormalRatio;
    private double morningRatio;
    private double incompleteRatio;
    private Random random;

    public DataGenerator(int total, double abnormalRatio, double morningRatio, double incompleteRatio) {
        this.total = total;
        this.abnormalRatio = abnormalRatio;
        this.morningRatio = morningRatio;
        this.incompleteRatio = incompleteRatio;
        this.random = new Random();
    }

    public void generate(String filename) {
        List<StudentRecord> records = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            String studentId = String.format("2023%04d", i + 1);
            String studentCode = "学员" + String.format("%03d", i + 1);

            boolean isAbnormal = random.nextDouble() < abnormalRatio;
            boolean isMorning = random.nextDouble() < morningRatio;
            boolean isIncomplete = random.nextDouble() < incompleteRatio;

            int duration = isAbnormal
                    ? (random.nextBoolean() ? random.nextInt(30) : 91 + random.nextInt(60))
                    : 30 + random.nextInt(61);

            LocalDateTime startTime = generateStartTime(isMorning);
            LocalDateTime endTime = startTime.plusMinutes(duration);

            String isMorningStudy = startTime.getHour() < 5 ? "是" : "否";
            String status = isIncomplete ? "未完成" : "完成";
            String durationStatus = duration >= 30 && duration <= 90 ? "正常" : "异常";

            records.add(new StudentRecord(studentId, studentCode, duration, startTime, endTime, isMorningStudy, status, durationStatus));
        }

        writeExcel(records, filename);
    }

    private LocalDateTime generateStartTime(boolean forceMorning) {
        int hour = forceMorning ? random.nextInt(5) : 7 + random.nextInt(15);
        int minute = random.nextInt(60);
        return LocalDateTime.of(2025, 7, 1, hour, minute);
    }

    private void writeExcel(List<StudentRecord> records, String filename) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学习数据");

        String[] headers = {
                "学号", "学员编号", "学习时长", "开始时间", "结束时间", "是否凌晨学习", "完成状态", "时长状态"
        };

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (StudentRecord r : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.studentId);
            row.createCell(1).setCellValue(r.studentCode);
            row.createCell(2).setCellValue(r.duration + " 分钟");
            row.createCell(3).setCellValue(r.startTime.toString());
            row.createCell(4).setCellValue(r.endTime.toString());
            row.createCell(5).setCellValue(r.isMorningStudy);
            row.createCell(6).setCellValue(r.status);
            row.createCell(7).setCellValue(r.durationStatus);
        }

        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Excel 文件生成成功：" + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

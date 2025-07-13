package com.example.edudata;

public class Main {
    public static void main(String[] args) {
        int totalRecords = 1000;
        double abnormalRate = 0.1;
        double morningRate = 0.05;
        double incompleteRate = 0.15;

        DataGenerator generator = new DataGenerator(
                totalRecords, abnormalRate, morningRate, incompleteRate);
        generator.generate("模拟教育平台数据_1000条.xlsx");
    }
}

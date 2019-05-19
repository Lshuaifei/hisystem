package com.xgs.hisystem.pojo.vo.toll;

/**
 * @author xgs
 * @date 2019-5-14
 * @description:
 */
public class TollMedicalRecordRspVO {

    private String date;

    private String name;

    private String sex;

    private String nationality;

    private int age;

    private String diagnosisResult;

    private double drugCost;

    private String prescription;

    private String medicalOrder;

    private double examinationCost;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosisResult() {
        return diagnosisResult;
    }

    public void setDiagnosisResult(String diagnosisResult) {
        this.diagnosisResult = diagnosisResult;
    }

    public double getDrugCost() {
        return drugCost;
    }

    public void setDrugCost(double drugCost) {
        this.drugCost = drugCost;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getMedicalOrder() {
        return medicalOrder;
    }

    public void setMedicalOrder(String medicalOrder) {
        this.medicalOrder = medicalOrder;
    }

    public double getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(double examinationCost) {
        this.examinationCost = examinationCost;
    }
}

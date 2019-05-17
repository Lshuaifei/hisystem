package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;

/**
 * @author xgs
 * @date 2019-5-8
 * @description:
 */
@Entity
@Table(name = "his_medical_record")
public class MedicalRecordEntity extends BaseEntity {

    @Column(name = "conditionDescription", nullable = true, length = 255)
    private String conditionDescription;  //主诉

    @Column(name = "prescription", nullable = true, length = 500)
    private String prescription;  //处方

    @OneToOne
    @JoinColumn(name = "registerId", referencedColumnName = "id")
    private RegisterEntity register;

    @Column(name = "prescriptionNum", nullable = false, length = 255)
    private String prescriptionNum;  //处方号

    @Column(name = "drugCost", nullable = true, length = 50)
    private double drugCost; //药物总费用

    @Column(name = "diagnosisResult", nullable = true, length = 255)
    private String diagnosisResult;  //初步诊断结果

    @Column(name = "medicalOrder", nullable = true, length = 255)
    private String medicalOrder; //医嘱

    @Column(name = "examinationCost", nullable = true, length = 20)
    private String examinationCost;  //检查费用

    @Column(name = "tollFrequency", nullable = false, length = 20)
    private int tollFrequency;  //划价收费次数

    @Column(name = "takingDrugFrequency", nullable = false, length = 20)
    private int takingDrugFrequency;  //取药次数

    @Column(name = "tollOperator", nullable = true, length = 255)
    private String tollOperator;  //收费操作员

    @Column(name = "tollDateTime", nullable = true, length = 50)
    private String tollDateTime;  //收费时间

    @Column(name = "takingDrugOperator", nullable = true, length = 255)
    private String takingDrugOperator;  //药房操作员

    @Column(name = "takingDrugDateTime", nullable = true, length = 50)
    private String takingDrugDateTime;  //拿药时间

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public RegisterEntity getRegister() {
        return register;
    }

    public void setRegister(RegisterEntity register) {
        this.register = register;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
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

    public String getDiagnosisResult() {
        return diagnosisResult;
    }

    public void setDiagnosisResult(String diagnosisResult) {
        this.diagnosisResult = diagnosisResult;
    }

    public String getMedicalOrder() {
        return medicalOrder;
    }

    public void setMedicalOrder(String medicalOrder) {
        this.medicalOrder = medicalOrder;
    }

    public String getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(String examinationCost) {
        this.examinationCost = examinationCost;
    }

    public int getTollFrequency() {
        return tollFrequency;
    }

    public void setTollFrequency(int tollFrequency) {
        this.tollFrequency = tollFrequency;
    }

    public int getTakingDrugFrequency() {
        return takingDrugFrequency;
    }

    public void setTakingDrugFrequency(int takingDrugFrequency) {
        this.takingDrugFrequency = takingDrugFrequency;
    }

    public String getTollOperator() {
        return tollOperator;
    }

    public void setTollOperator(String tollOperator) {
        this.tollOperator = tollOperator;
    }

    public String getTollDateTime() {
        return tollDateTime;
    }

    public void setTollDateTime(String tollDateTime) {
        this.tollDateTime = tollDateTime;
    }

    public String getTakingDrugOperator() {
        return takingDrugOperator;
    }

    public void setTakingDrugOperator(String takingDrugOperator) {
        this.takingDrugOperator = takingDrugOperator;
    }

    public String getTakingDrugDateTime() {
        return takingDrugDateTime;
    }

    public void setTakingDrugDateTime(String takingDrugDateTime) {
        this.takingDrugDateTime = takingDrugDateTime;
    }
}

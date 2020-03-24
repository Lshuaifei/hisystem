package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @date 2019-5-18
 * @description: 体检
 */
@Entity
@Table(name = "his_medicalExamination")
public class MedicalExaminationEntity extends BaseEntity {

    @Column(name = "bodyTemperature", nullable = true, length = 50)
    private String bodyTemperature; //体温

    @Column(name = "pulse", nullable = true, length = 50)
    private String pulse;   //脉搏

    @Column(name = "heartRate", nullable = true, length = 50)
    private String heartRate;  //心率

    @Column(name = "bloodPressure", nullable = true, length = 50)
    private String bloodPressure;  //血压

    @Column(name = "examinationCost", nullable = true, length = 50)
    private double examinationCost;


    @Column(name = "prescriptionNum", nullable = false, length = 100)
    private String prescriptionNum;

    @Column(name = "examinationOperator", nullable = true, length = 255)
    private String examinationOperator;  //体检操作员

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(double examinationCost) {
        this.examinationCost = examinationCost;
    }

    public String getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(String prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public String getExaminationOperator() {
        return examinationOperator;
    }

    public void setExaminationOperator(String examinationOperator) {
        this.examinationOperator = examinationOperator;
    }
}

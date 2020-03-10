package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;

/**
 * @author xgs
 * @date 2019/4/22
 * @description:
 */
@Entity
@Table(name = "his_register")
public class RegisterEntity extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @Column(name = "department", nullable = false, length = 32)
    private String department;

    @Column(name = "registerType", nullable = false, length = 10)
    private String registerType;

    @Column(name = "doctor", nullable = false, length = 32)
    private String doctor;

    @Column(name = "doctorId", nullable = false, length = 255)
    private String doctorId;

    @Column(name = "treatmentPrice", nullable = false, length = 10)
    private String treatmentPrice;

    @Column(name = "payType", nullable = false, length = 10)
    private String payType;

    @Column(name = "operatorName", nullable = false, length = 10)
    private String operatorName;   //操作员

    @Column(name = "operatorEmail", nullable = false, length = 50)
    private String operatorEmail;   //操作员

    @Column(name = "registerStatus", nullable = false, length = 2)
    private int registerStatus; //挂号状态

    @Column(name = "treatmentStatus", nullable = false, length = 2)
    private int treatmentStatus;//门诊状态

    @Column(name = "chargeStatus", nullable = false, length = 2)
    private int chargeStatus;//收费状态

    @Column(name = "registeredNum", nullable = false, length = 50)
    private String registeredNum;  //挂号编号

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getTreatmentPrice() {
        return treatmentPrice;
    }

    public void setTreatmentPrice(String treatmentPrice) {
        this.treatmentPrice = treatmentPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public int getTreatmentStatus() {
        return treatmentStatus;
    }

    public void setTreatmentStatus(int treatmentStatus) {
        this.treatmentStatus = treatmentStatus;
    }

    public int getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(int chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getRegisteredNum() {
        return registeredNum;
    }

    public void setRegisteredNum(String registeredNum) {
        this.registeredNum = registeredNum;
    }
}

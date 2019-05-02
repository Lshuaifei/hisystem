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

    @Column(name = "treatmentPrice", nullable = false, length = 10)
    private String treatmentPrice;

    @Column(name = "payType", nullable = false, length = 10)
    private String payType;

    @Column(name = "operator", nullable = false, length = 10)
    private String operator;   //操作员


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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}

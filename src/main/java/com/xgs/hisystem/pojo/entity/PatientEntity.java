package com.xgs.hisystem.pojo.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author xgs
 * @date 2019/4/22
 * @description:
 */
@Entity
@Table(name = "his_patient")
public class PatientEntity extends BaseEntity {

    @Column(name = "cardId", nullable = false, length = 20)
    private String cardId;

    @Column(name = "registerStatus", nullable = false, length = 2)
    private int registerStatus;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "sex", nullable = false, length = 4)
    private String sex;

    @Column(name = "birthday", nullable = false, length = 20)
    private String birthday;

    @Column(name = "age", nullable = true, length = 4)
    private int age;

    @Column(name = "nationality", nullable = false, length = 4)
    private String nationality;

    @Column(name = "address", nullable = false, length = 20)
    private String address;

    @Column(name = "idCard", nullable = false, length = 20)
    private String idCard;

    @Column(name = "telphone", nullable = true, length = 20)
    private String telphone;

    @Column(name = "zy", nullable = true, length = 20)
    private String zy;  //职业

    @Column(name = "hyzk", nullable = true, length = 10)
    private String hyzk;  //婚姻状况

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegisterEntity> registerList;  //挂号信息

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public List<RegisterEntity> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(List<RegisterEntity> registerList) {
        this.registerList = registerList;
    }
}

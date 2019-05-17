package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @date 2019-5-11
 * @description:
 */
@Entity
@Table(name = "his_drug")
public class DrugEntity extends BaseEntity {

    @Column(name = "num", nullable = false, length = 50)
    private double num = System.currentTimeMillis();

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "drugType", nullable = false, length = 50)
    private String drugType;  //注射剂、片剂、胶囊、、、

    @Column(name = "specification", nullable = false, length = 50)
    private String specification;  //规格：多少

    @Column(name = "unit", nullable = false, length = 50)
    private String unit;   //规格单位

    @Column(name = "limitStatus", nullable = false, length = 50)
    private String limitStatus;  //是否限制药物

    @Column(name = "efficacyClassification", nullable = false, length = 50)
    private String efficacyClassification;  //功效分类

    @Column(name = "wholesalePrice", nullable = false, length = 50)
    private double wholesalePrice;  //批发价

    @Column(name = "price", nullable = false, length = 50)
    private double price;  //售价

    @Column(name = "manufacturer", nullable = false, length = 255)
    private String manufacturer;  //生产厂家

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(String limitStatus) {
        this.limitStatus = limitStatus;
    }

    public String getEfficacyClassification() {
        return efficacyClassification;
    }

    public void setEfficacyClassification(String efficacyClassification) {
        this.efficacyClassification = efficacyClassification;
    }

    public double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}



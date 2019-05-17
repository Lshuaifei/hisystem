package com.xgs.hisystem.pojo.vo.drugStorage;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
public class DrugReqVO {

    @NotBlank(message = "药品名不能为空！")
    private String name;

    @NotBlank(message = "请选择剂型！")
    private String drugType;  //注射剂、片剂、胶囊、、、

    @NotBlank(message = "药品规格不能为空！")
    private String specification;  //规格：多少

    @NotBlank(message = "药品规格单位不能为空！")
    private String unit;   //规格单位

    @NotBlank(message = "请选择是否限制药物！")
    private String limitStatus;  //是否限制药物

    @NotBlank(message = "请选择药品功效分类！")
    private String efficacyClassification;  //功效分类

    @NotBlank(message = "药品批发价不能为空！")
    private double wholesalePrice;  //批发价

    @NotBlank(message = "药品售价不能为空！")
    private double price;  //售价

    @NotBlank(message = "药品生产厂家不能为空！")
    private String manufacturer;  //生产厂家

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

package com.xgs.hisystem.pojo.vo.register;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019/4/27
 * @description:
 */
public class PatientInforReqVO {

    @NotBlank(message = "请读取就诊卡号！")
    private String cardId;

    @NotBlank(message = "请读取二代身份证！")
    private String address;

    @NotBlank(message = "请读取二代身份证！")

    private String name;
    @NotBlank(message = "请读取二代身份证！")

    private String nationality;
    @NotBlank(message = "请读取二代身份证！")

    private String idcard;
    @NotBlank(message = "请读取二代身份证！")

    private String sex;
    @NotBlank(message = "请读取二代身份证！")
    private String birth;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}

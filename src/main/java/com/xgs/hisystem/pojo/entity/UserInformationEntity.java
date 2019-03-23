package com.xgs.hisystem.pojo.entity;

import com.xgs.hisystem.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/22
 */
@Entity
@Table(name ="user_information" )
public class UserInformationEntity extends BaseEntity {

    @Column(name = "sex")
    private String sex;

    @Column(name = "age")
    private String age;

    @Column(name = "phone")
    private String phone;

    @Column(name = "login_datetime", length = 20, nullable = false)
    private String loginDatetime = DateUtil.getCurrentDateToString();



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author XueGuiSheng
 * @date 2020/3/23
 * @description:
 */
@Entity
@Table(name = "his_department")
public class DepartmentEntity extends BaseEntity{

    @Column(name = "code")
    private Integer code;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "name_code")
    private String nameCode;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }
}

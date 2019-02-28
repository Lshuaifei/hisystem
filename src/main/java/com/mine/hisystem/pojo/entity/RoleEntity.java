package com.mine.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "his_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RoleEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "desrciption")
    private String desrciption;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesrciption() {
        return desrciption;
    }

    public void setDesrciption(String desrciption) {
        this.desrciption = desrciption;
    }
}

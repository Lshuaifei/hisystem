package com.xgs.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "his_role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RoleEntity extends BaseEntity{

    @Column(name = "role")
    private String role;

    @Column(name = "value")
    private String value;

    @Column(name = "desrciption")
    private String desrciption;

    @ManyToMany(mappedBy = "roleList")
    private List<UserEntity> userList;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDesrciption() {
        return desrciption;
    }

    public void setDesrciption(String desrciption) {
        this.desrciption = desrciption;
    }

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}

package com.xgs.hisystem.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
@Entity
@Table(name = "his_user_role")
public class UserRoleEntity extends BaseEntity {

    @Column(name = "uid")
    private String uId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "desciption")
    private String desciption;

    @Column(name = "role_status")
    private Integer roleStatus;         //管理员审核状态

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }
}

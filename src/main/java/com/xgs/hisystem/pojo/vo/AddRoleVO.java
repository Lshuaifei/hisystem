package com.xgs.hisystem.pojo.vo;

import com.xgs.hisystem.pojo.entity.RoleEntity;

import java.util.List;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public class AddRoleVO {

    private String email;

    private List<String> roleList;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }
}

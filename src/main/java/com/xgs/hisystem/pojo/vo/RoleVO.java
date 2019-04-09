package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public class RoleVO {

    @NotBlank(message = "角色名不能为空")
    private String rolename;

    @NotBlank(message = "角色编码不能为空")
    private String roleValue;

    @NotBlank(message = "描述不能为空")
    private String desciption;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}

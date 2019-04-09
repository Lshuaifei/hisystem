package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @Description:
 * @date 2019/2/14
 */
public class UserRegisterReqVO {

    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "初始角色不能为空")
    private String roleValue;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the roleValue
     */
    public String getRoleValue() {
        return roleValue;
    }

    /**
     * @param roleValue the roleValue to set
     */
    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

}

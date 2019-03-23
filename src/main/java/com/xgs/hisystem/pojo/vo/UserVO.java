package com.xgs.hisystem.pojo.vo;

/**
 * @author xgs
 * @Description:
 * @date 2019/2/14
 */
public class UserVO {

    private String email;

    private String username;

    private String password;

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

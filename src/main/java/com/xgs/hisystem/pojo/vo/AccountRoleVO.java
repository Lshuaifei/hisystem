package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.Min;

/**
 * @author xgs
 * @date 2019-4-30
 * @description:
 */
public class AccountRoleVO {

    @Min(1)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

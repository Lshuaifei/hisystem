package com.xgs.hisystem.pojo.vo;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019/4/5
 * @description:
 */
public class ChangePasswordReqVO {

    @NotBlank(message = "原始密码不能为空!")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空!")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空!")
    private String okPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOkPassword() {
        return okPassword;
    }

    public void setOkPassword(String okPassword) {
        this.okPassword = okPassword;
    }
}

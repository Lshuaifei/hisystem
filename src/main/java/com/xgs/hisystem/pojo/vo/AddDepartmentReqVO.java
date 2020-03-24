package com.xgs.hisystem.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author XueGuiSheng
 * @date 2020/3/23
 * @description:
 */
@ApiModel
public class AddDepartmentReqVO {

    @ApiModelProperty(value = "科室名称",example = "科室名称")
    @NotBlank(message = "科室名称不能为空！")
    private String departmentName;

    @ApiModelProperty(value = "科室地址，例：门诊大楼二楼D区",example = "科室地址，例：门诊大楼二楼D区")
    @NotBlank(message = "科室地址不能为空！")
    private String departmentAddress;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }
}

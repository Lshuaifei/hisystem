package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BasePageReqBO;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
@Controller
@ResponseBody
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IAdminService iadminService;


    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    public String createRole(@RequestBody RoleVO roleVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(roleVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().toString();
        }
        BaseResponse baseResponse = iadminService.createRole(roleVO);
        return baseResponse.getMessage();

    }

    /**
     * 后台添加账户
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String saveUserAndSendEmailTemp(@RequestBody UserRegisterReqVO reqVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().toString();
        }
        BaseResponse baseResponse = iadminService.saveUserAndSendEmailTemp(reqVO);
        return baseResponse.getMessage();
    }

    /**
     * 后台添加角色
     *
     * @param addRoleVO
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public BaseResponse addRole(@RequestBody AddRoleVO addRoleVO) {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(addRoleVO);
        if (validateBo.isHasErrors()) {
            return BaseResponse.errormsg(validateBo.getErrorMsg().toString());
        }
        BaseResponse baseResponse = iUserService.addRole(addRoleVO);
        return baseResponse;
    }


    /**
     * 获取审核角色
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getRoleApply")
    public PageRspBO<applyRspVO> getRoleApply(BasePageReqBO reqBO) {


        return iadminService.getRoleApply(reqBO);

    }


    /**
     * 修改角色状态
     *
     * @param status
     * @param email
     */
    @PostMapping(value = "/changeRoleStatus")
    public void changeRoleStatus(@RequestParam int status, @RequestParam String email) {

        iadminService.changeRoleStatus(status, email);
    }

}

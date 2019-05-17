package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.service.IAdminService;
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
    private IAdminService iadminService;

    /**
     * 新建角色
     *
     * @param roleVO
     * @return
     */
    @RequestMapping(value = "/createRole", method = RequestMethod.POST)
    public String createRole(@RequestBody RoleVO roleVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(roleVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
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
            String result = validateBo.getErrorMsg().values().toString();
            return result;
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
        BaseResponse baseResponse = iadminService.addRole(addRoleVO);
        return baseResponse;
    }


    /**
     * 获取审核角色
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getRoleApply")
    public PageRspBO<applyRspVO> getRoleApply(BasePageReqVO reqVO) {


        return iadminService.getRoleApply(reqVO);

    }


    /**
     * 修改角色状态
     *
     * @param status
     * @param email
     */
    @PostMapping(value = "/changeRoleStatus")
    public void changeRoleStatus(@RequestParam String status, @RequestParam String email) {

        iadminService.changeRoleStatus(status, email);
    }

    /**
     * 公告
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/addAnnouncement")
    public String AddAnnouncement(@RequestBody AnnouncementVO reqVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }
        BaseResponse baseResponse = iadminService.addAnnouncement(reqVO);
        return baseResponse.getMessage();
    }

    @RequestMapping(value = "/getAnnouncement")
    public PageRspBO<AnnouncementVO> getAnnouncement(BasePageReqVO reqVO) {


        return iadminService.getAnnouncement(reqVO);
    }

    @PostMapping(value = "/changeAnnouncement")
    public String changeAnnouncement(@RequestBody AnnouncementVO announcementVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(announcementVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iadminService.changeAnnouncement(announcementVO);
        return baseResponse.getMessage();
    }

    @PostMapping(value = "/deleteAnnouncement")
    public String deleteAnnouncement(@RequestParam String id) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(id);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iadminService.deleteAnnouncement(id);

        return baseResponse.getMessage();
    }

    @PostMapping(value = "/add_Announcement")
    public String add_Announcement(@RequestParam String id) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(id);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iadminService.add_Announcement(id);

        return baseResponse.getMessage();
    }

    @PostMapping(value = "/sub_Announcement")
    public String sub_Announcement(@RequestParam String id) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(id);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iadminService.sub_Announcement(id);

        return baseResponse.getMessage();
    }
}

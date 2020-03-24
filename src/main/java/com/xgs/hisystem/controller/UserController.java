package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户管理API")
public class UserController {

    @Autowired
    private IUserService iUserService;


    /**
     * 登录验证
     *
     * @param reqVO
     * @param model
     * @return
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(@RequestBody UserLoginReqVO reqVO, Model model) {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }
        BaseResponse baseResponse = iUserService.doLogin(reqVO);

        return baseResponse.getMessage();
    }

    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public String registered(@RequestBody UserRegisterReqVO reqVO, Model model) {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }
        BaseResponse baseResponse = iUserService.saveUserAndSendEmail(reqVO);

        return baseResponse.getMessage();
    }


    /**
     * 获取登录日志
     *
     * @param reqVO
     * @return
     */
    @RequestMapping(value = "/getLoginfor",method = RequestMethod.GET)
    public PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqVO reqVO) {

        return iUserService.getLoginfor(reqVO);
    }

    /**
     * 修改密码
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/changePassword")
    public String changePassword(@RequestBody ChangePasswordReqVO reqVO) {

        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }
        BaseResponse baseResponse = iUserService.changePassword(reqVO);

        return baseResponse.getMessage();
    }

    /**
     * 个人资料设置
     *
     * @return
     */
    @PostMapping(value = "/getUserInfo")
    public List<UserInfoVO> getUserInfo() {

        return iUserService.getUserInfo();
    }

    @PostMapping(value = "/changeUserInfo")
    public String changeUserInfo(@RequestBody UserInfoVO reqVO) {
        ValidationResultBO validateBo = ParamsValidationUtils.validateEntity(reqVO);
        if (validateBo.isHasErrors()) {
            return validateBo.getErrorMsg().values().toString();
        }

        BaseResponse baseResponse = iUserService.changeUserInfo(reqVO);

        return baseResponse.getMessage();
    }

    @PostMapping(value = "/getAnnContent")
    public AnnRspVO getAnnContent(@RequestParam String id) {

        return iUserService.getAnnContent(id);
    }

    @PostMapping(value = "/addAnotherRole")
    public String addAnotherRole(@RequestBody AccountRoleVO reqVO) {

        BaseResponse baseResponse = iUserService.addAnotherRole(reqVO);
        return baseResponse.getMessage();
    }


    /**
     * 获取所有角色
     * @param
     * @return
     */
    @PostMapping(value = "/getAllRole")
    public List<GetAllRoleRspVO> getAllRole() {
        return iUserService.getAllRole();
    }

}

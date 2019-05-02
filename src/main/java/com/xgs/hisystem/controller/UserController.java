package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.bo.BasePageReqBO;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.bo.ValidationResultBO;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.repository.ILoginInforRepository;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.util.ParamsValidationUtils;
import com.xgs.hisystem.util.QRcodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ILoginInforRepository iLoginInforRepository;


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
     * 生成二维码
     *
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getCode")
    public String QRcode(Model model) throws IOException {
        String code = QRcodeUtil.crateQRCode("hello");
        model.addAttribute("code", code);
        return "tables";
    }


    /**
     * 获取登录日志
     *
     * @param reqBO
     * @return
     */
    @RequestMapping(value = "/getLoginfor")
    public PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqBO reqBO) {

        return iUserService.getLoginfor(reqBO);
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
    public String getAnnContent(@RequestParam String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        return iUserService.getAnnContent(id);
    }

    @PostMapping(value = "/addAnotherRole")
    public String addAnotherRole(@RequestBody AccountRoleVO reqVO) {

        BaseResponse baseResponse = iUserService.addAnotherRole(reqVO);
        return baseResponse.getMessage();
    }

}

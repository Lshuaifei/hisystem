package com.xgs.hisystem.controller;

import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.vo.AddRoleVO;
import com.xgs.hisystem.repository.IUserRepository;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.xgs.hisystem.util.GoEasyUtil;
import com.xgs.hisystem.util.QRcodeUtil;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class UserController {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
     * 登录验证
     *
     * @param email
     * @param password
     * @param rememberMe
     * @param model
     * @return
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(@RequestParam(value = "email", required = false) String email,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "rememberMe", required = false) String rememberMe, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        if (rememberMe != null) {
            if (rememberMe.equals("on")) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
        }
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        boolean subjects = subject.isAuthenticated();
        UserEntity userEntity = iUserRepository.findByEmail(email);
        int status = userEntity.getStatus();
        if (subjects && status == 1) {
            GoEasyUtil.PushMessage("hello", "hello");
            model.addAttribute("username", userEntity.getUsername());
            return "test";
        } else {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("该账户未激活！");
            model.addAttribute("errorMessage", baseResponse.getMessage());
            return "error";
        }

    }

    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param userVO
     * @return
     */
    @RequestMapping(value = "/doregister", method = RequestMethod.POST)
    public String registered(UserVO userVO, Model model) {

        BaseResponse baseResponse = iUserService.saveUserAndSendEmail(userVO);
        if (baseResponse.getStatus().equals(1)) {
            model.addAttribute("email", userVO.getEmail());
            return "email/fmail";
        } else {
            model.addAttribute("errorMessage", baseResponse.getMessage());
            return "error";
        }
    }

    /**
     * 激活用户状态
     *
     * @param email
     * @param validateCode
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public String activation(String email, String validateCode, Model model) throws ParseException {

        BaseResponse baseResponse = iUserService.activation(email, validateCode);
        if (baseResponse.getStatus().equals(1)) {
            return "email/dmail";
        } else {
            model.addAttribute("errorMessage", baseResponse.getMessage());
            return "error";
        }
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


    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public String addRole(@RequestBody AddRoleVO addRoleVO) {

        BaseResponse baseResponse = iUserService.addRole(addRoleVO);
        return baseResponse.getMessage();
    }

}

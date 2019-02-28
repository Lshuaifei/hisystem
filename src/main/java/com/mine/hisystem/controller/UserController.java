package com.mine.hisystem.controller;

import com.mine.hisystem.pojo.entity.UserEntity;
import com.mine.hisystem.repository.IUserRepository;
import com.mine.hisystem.service.IUserService;
import com.mine.hisystem.pojo.vo.BaseResponse;
import com.mine.hisystem.pojo.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
public class UserController {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IUserService iUserService;


    @RequestMapping(value = "/")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/dologin")
    public String doLogin(UserVO userVO) {
        UsernamePasswordToken token = new UsernamePasswordToken(userVO.getEmail(), userVO.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        boolean m = subject.isAuthenticated();
        UserEntity userEntity = iUserRepository.findByEmail(userVO.getEmail());
        int status = userEntity.getStatus();
        if (m && status == 1) {
            return "default";
        } else {
            return "403";
        }

    }


    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param userVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/doregister", method = RequestMethod.GET)
    public BaseResponse<?> registered(UserVO userVO) {
        try {
            iUserService.saveUserAndSendEmail(userVO);
            return BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errormsg(e.getMessage());
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
    @ResponseBody
    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public BaseResponse<?> activation(String email, String validateCode) throws ParseException {

        try {
            iUserService.activation(email, validateCode);
            return BaseResponse.success();
        } catch (ParseException e) {
            e.printStackTrace();
            return BaseResponse.errormsg(e.getMessage());
        }
    }
}

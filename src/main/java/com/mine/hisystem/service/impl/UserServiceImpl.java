package com.mine.hisystem.service.impl;

import com.mine.hisystem.pojo.entity.UserEntity;
import com.mine.hisystem.repository.IUserRepository;
import com.mine.hisystem.service.IEmailService;
import com.mine.hisystem.service.IUserService;
import com.mine.hisystem.util.DateUtil;
import com.mine.hisystem.util.MD5Util;
import com.mine.hisystem.pojo.vo.BaseResponse;
import com.mine.hisystem.pojo.vo.UserVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    IEmailService iEmailService;

    private static final Logger looger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param userVO
     * @return
     */
    @Transactional
    @Override
    public BaseResponse<?> saveUserAndSendEmail(UserVO userVO) {
        if (userVO.getEmail().isEmpty()) {
            return BaseResponse.errormsg("邮箱不能为空！");
        }
        if (userVO.getPassword().isEmpty()) {
            return BaseResponse.errormsg("密码不能为空！");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userVO.getEmail());
        userEntity.setUsername(userVO.getUsername());
        String salt = MD5Util.md5Encrypt32Lower(userVO.getEmail());
        String password = new SimpleHash("MD5", userVO.getPassword(), salt, 1024).toHex(); //使用SimpleHash类对原始密码进行加密
        userEntity.setPassword(password);
        userEntity.setSalt(salt);
        userEntity.setValidateCode(MD5Util.md5Encrypt32Upper(userVO.getEmail()));

        String title = "账户激活";
        Context context = new Context();
        context.setVariable("email", userEntity.getEmail());
        context.setVariable("validateCode", userEntity.getValidateCode());
        String emailContent = templateEngine.process("email", context);
        try {
            iUserRepository.saveAndFlush(userEntity);
            iEmailService.sendMail(userVO.getEmail(), title, emailContent);
            return BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errormsg("保存用户信息或邮件发送异常!");
        }

    }


    @Transactional
    @Override
    public BaseResponse<?> activation(String email, String validateCode) throws ParseException {
        UserEntity userEntity = iUserRepository.findByEmail(email);
        if (userEntity == null) {
            return BaseResponse.errormsg("未查询到该邮箱！");
        }

        String nowDate = DateUtil.getCurrentDateToString();
        String createDate = userEntity.getCreateDatetime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(createDate);
        Date end = sdf.parse(nowDate);
        long cha = end.getTime() - start.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result > 48) {
            return BaseResponse.errormsg("链接已过期！");
        }
        if (!validateCode.equals(userEntity.getValidateCode())) {
            return BaseResponse.errormsg("激活码错误！");
        }
        if (userEntity.getStatus()==1){
            return BaseResponse.errormsg("账户已被激活！");
        }
        userEntity.setStatus(1);
        try {
            iUserRepository.saveAndFlush(userEntity);
            return BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errormsg("更新状态码异常！");
        }
    }

}

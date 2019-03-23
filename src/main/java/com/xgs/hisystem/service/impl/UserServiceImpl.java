package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.pojo.entity.RoleEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.entity.UserRoleEntity;
import com.xgs.hisystem.pojo.vo.AddRoleVO;
import com.xgs.hisystem.pojo.vo.RoleVO;
import com.xgs.hisystem.repository.IRoleRespository;
import com.xgs.hisystem.repository.IUserRepository;
import com.xgs.hisystem.repository.IUserRoleRepository;
import com.xgs.hisystem.service.IEmailService;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.util.DateUtil;
import com.xgs.hisystem.util.MD5Util;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.UserVO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    IEmailService iEmailService;

    @Autowired
    IRoleRespository iRoleRespository;

    @Autowired
    IUserRoleRepository iUserRoleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        if(userVO.getRoleValue().isEmpty()){
            return BaseResponse.errormsg("未选择用户角色！");
        }
        String email = userVO.getEmail();
        UserEntity checkUser = iUserRepository.findByEmail(email);
        if (checkUser != null) {
            if (checkUser.getStatus().equals(0)) {
                logger.info("--**--账户：{} 已注册，但未激活！--**--", email);
                return BaseResponse.errormsg("该账户已注册，但未激活！");
            } else {
                logger.info("--**--账户：{} 已存在！--**--", email);
                return BaseResponse.errormsg("该账户已存在！");
            }
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setUsername(userVO.getUsername());
        String salt = MD5Util.md5Encrypt32Lower(userVO.getEmail());
        String password = new SimpleHash("MD5", userVO.getPassword(), salt, 1024).toHex(); // 使用SimpleHash类对原始密码进行加密

        List<RoleEntity> roleList = new ArrayList<>();
        RoleEntity role = new RoleEntity();
        role.setValue(userVO.getRoleValue());
        roleList.add(role);

        userEntity.setPassword(password);
        userEntity.setSalt(salt);
        userEntity.setValidateCode(MD5Util.md5Encrypt32Upper(userVO.getEmail()));
        userEntity.setRoleList(roleList);

        String title = "账户激活";
        Context context = new Context();
        context.setVariable("email", userEntity.getEmail());
        context.setVariable("validateCode", userEntity.getValidateCode());
        String emailContent = templateEngine.process("email/email", context);
        try {
            iUserRepository.saveAndFlush(userEntity);
            iEmailService.sendMail(userVO.getEmail(), title, emailContent);
            return BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errormsg("保存用户信息或邮件发送异常!");
        }

    }

    /**
     * 激活账户
     *
     * @param email
     * @param validateCode
     * @return
     * @throws ParseException
     */

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
        if (userEntity.getStatus() == 1) {
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

    /**
     * 用户添加角色
     *
     * @param addRoleVO
     * @return
     */
    @Transactional
    @Override
    public BaseResponse<?> addRole(AddRoleVO addRoleVO) {

        UserEntity user = iUserRepository.findByEmail(addRoleVO.getEmail());

        String uId = user.getId();
        try {
            addRoleVO.getRoleList().forEach(role -> {

                RoleEntity roleEntity = iRoleRespository.findByRole(role);
                String roleId = roleEntity.getId();
                UserRoleEntity checkUserRole = iUserRoleRepository.findByUIdAndRoleId(uId, roleId);
                if (checkUserRole != null) {
                    logger.info("--**--账户：{} 已拥有 {} 角色--**--", user.getEmail(), roleEntity.getRole());
                    return;
                }

                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setuId(uId);
                userRoleEntity.setRoleId(roleId);
                String desciption = user.getEmail() + "#" + roleEntity.getRole();
                userRoleEntity.setDesciption(desciption);
                iUserRoleRepository.saveAndFlush(userRoleEntity);
            });
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.errormsg(e.getMessage());
        }

    }

}

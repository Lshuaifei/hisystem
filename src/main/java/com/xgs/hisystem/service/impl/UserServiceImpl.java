package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.config.Contants;
import com.xgs.hisystem.pojo.bo.BasePageReqBO;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.entity.*;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.repository.*;
import com.xgs.hisystem.service.IEmailService;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.task.AsyncTask;
import com.xgs.hisystem.util.DateUtil;
import com.xgs.hisystem.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private ILoginInforRepository iLoginInforRepository;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private IEmailService iEmailService;
    @Autowired
    private IRoleRespository iRoleRespository;
    @Autowired
    private IUserRoleRepository iUserRoleRepository;
    @Autowired
    private IAnnouncementRepository iAnnouncementRepository;

    @Autowired
    private AsyncTask asyncTask;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 登录验证
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> doLogin(UserLoginReqVO reqVO) {

        String email = reqVO.getEmail();
        String password = reqVO.getPassword();

        UserEntity user = iUserRepository.findByEmail(email);

        if (StringUtils.isEmpty(user)) {
            return BaseResponse.errormsg(Contants.user.USER_not_EXIST);
        }

        UsernamePasswordToken token = new UsernamePasswordToken(email, password);

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return BaseResponse.errormsg(Contants.user.PASSWORD_ERROR);
        }
        if (user.getEmailStatus().equals(0)) {
            return BaseResponse.errormsg(Contants.user.EMAIL_STATUS_0);
        }

        List<UserRoleEntity> userRoleList = iUserRoleRepository.findByUId(user.getId());

        long statusCount_1 = userRoleList.stream()
                .filter(userRole -> userRole.getRoleStatus().equals(1)).count();
        if (statusCount_1 == 0) {
            long statusCount_0 = userRoleList.stream()
                    .filter(userRole -> userRole.getRoleStatus().equals(0)).count();
            if (statusCount_0 >= 1) {
                return BaseResponse.errormsg(Contants.user.ROLE_STATUS_0);
            } else {
                return BaseResponse.errormsg(Contants.user.ROLE_STATUS_0_BAD);
            }
        }

        asyncTask.saveLoginInfor(reqVO.getIp(), reqVO.getBroswer(), email);

        return BaseResponse.success(Contants.user.SUCCESS);
    }


    /**
     * 保存用户注册信息，向用户发送激活链接
     *
     * @param reqVO
     * @return
     */
    @Transactional
    @Override
    public BaseResponse<?> saveUserAndSendEmail(UserRegisterReqVO reqVO) {

        String email = reqVO.getEmail();
        int roleValue = reqVO.getRoleValue();

        UserEntity checkUser = iUserRepository.findByEmail(email);

        if (checkUser != null) {

            return BaseResponse.errormsg(Contants.user.ACCOUNT_EXIST);
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setUsername(reqVO.getUsername());
        userEntity.setPlainPassword(reqVO.getPassword());
        //生成盐和加盐密码
        String salt = MD5Util.md5Encrypt32Lower(reqVO.getEmail());
        String password = new SimpleHash("MD5", reqVO.getPassword(), salt, 1024).toHex(); // 使用SimpleHash类对原始密码进行加密

        userEntity.setPassword(password);
        userEntity.setSalt(salt);
        //生成激活码
        String validateCode = MD5Util.md5Encrypt32Upper(reqVO.getEmail());
        userEntity.setValidateCode(validateCode);
        userEntity.setEmailStatus(0);

        String title = "账户激活";
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("roleValue", roleValue);
        context.setVariable("validateCode", validateCode);
        String emailContent = templateEngine.process("email/email", context);
        try {
            iUserRepository.saveAndFlush(userEntity);
            //保存角色
            UserEntity user = iUserRepository.findByEmail(email);
            String uId = user.getId();

            RoleEntity role = iRoleRespository.findByRoleValue(roleValue);
            String roleId = role.getId();

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setuId(uId);
            userRole.setRoleId(roleId);
            String desciption = user.getEmail() + "#" + role.getRole();
            userRole.setDesciption(desciption);
            userRole.setRoleStatus(0);

            iUserRoleRepository.saveAndFlush(userRole);

            //发送邮件
            iEmailService.sendMail(reqVO.getEmail(), title, emailContent);
            return BaseResponse.success(Contants.user.SUCCESS);
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
        if (userEntity.getEmailStatus() == 1) {
            return BaseResponse.errormsg("账户已被激活！");
        }
        userEntity.setEmailStatus(1);
        try {
            iUserRepository.saveAndFlush(userEntity);
            return BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errormsg("更新状态码异常！");
        }
    }


    /**
     * 获取登录信息
     *
     * @param reqBO
     * @return
     */

    @Override
    public PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqBO reqBO) {

        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        Page<LoginInforEntity> page = iLoginInforRepository.findAll(new Specification<LoginInforEntity>() {
            @Override
            public Predicate toPredicate(Root<LoginInforEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<>();

                Predicate logininfor = criteriaBuilder.equal(root.get("user"), userEntity);
                predicateList.add(logininfor);
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, PageRequest.of(reqBO.getPageNumber(), reqBO.getPageSize()));
        if (page == null) {
            return null;
        }
        List<LoginInforEntity> userList = page.getContent();
        List<LoginInforRspVO> loginInforList = new ArrayList<>();
        userList.forEach(user -> {
            LoginInforRspVO loginInfor = new LoginInforRspVO();
            loginInfor.setLoginIp(user.getLoginIp());
            loginInfor.setLoginBroswer(user.getLoginBroswer());
            loginInfor.setLoginAddress(user.getLoginAddress());
            loginInfor.setCreateDatetime(user.getCreateDatetime());
            loginInforList.add(loginInfor);
        });

        PageRspBO pageRspBO = new PageRspBO();
        pageRspBO.setTotal((int) page.getTotalElements());

        pageRspBO.setRows(loginInforList);

        return pageRspBO;
    }

    @Override
    public BaseResponse<?> changePassword(ChangePasswordReqVO reqVO) {

        String oldPassword = reqVO.getOldPassword();
        String newPassword = reqVO.getNewPassword();
        String okPassword = reqVO.getOkPassword();


        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        if (!user.getPlainPassword().equals(oldPassword)) {
            logger.info("原始密码错误！");
            return BaseResponse.errormsg(Contants.user.PLAIN_PASSWORD_ERROR);
        }
        if (!newPassword.equals(okPassword)) {

            logger.info("密码确认输入不一致！");
            return BaseResponse.errormsg(Contants.user.OLD_NO_NEW);
        }
        if (oldPassword.equals(newPassword)) {
            return BaseResponse.errormsg(Contants.user.OLD_EQUALS_NEW_PASSWORD);
        }
        String salt = MD5Util.md5Encrypt32Lower(user.getEmail());
        String password = new SimpleHash("MD5", reqVO.getNewPassword(), salt, 1024).toHex(); // 使用SimpleHash类对原始密码进行加密

        user.setPlainPassword(newPassword);
        user.setPassword(password);

        iUserRepository.saveAndFlush(user);

        return BaseResponse.success(Contants.user.CHANGE_OK);
    }

    /**
     * 获取个人信息
     *
     * @return
     */
    @Override
    public List<UserInfoVO> getUserInfo() {

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        List<UserInfoVO> userInfoList = new ArrayList<>();
        UserInfoVO userInfo = new UserInfoVO();

        userInfo.setUsername(user.getUsername());
        userInfo.setSex(user.getSex());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setPhone(user.getPhone());
        userInfo.setPoliticalStatus(user.getPoliticalStatus());
        userInfo.setAddress(user.getAddress());

        userInfoList.add(userInfo);
        return userInfoList;
    }

    /**
     * 修改个人信息
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> changeUserInfo(UserInfoVO reqVO) {

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        user.setUsername(reqVO.getUsername());
        user.setSex(reqVO.getSex());
        user.setBirthday(reqVO.getBirthday());
        user.setPhone(reqVO.getPhone());
        user.setPoliticalStatus(reqVO.getPoliticalStatus());
        user.setAddress(reqVO.getAddress());


        try {
            iUserRepository.saveAndFlush(user);
            return BaseResponse.success(Contants.user.CHANGE_OK);
        } catch (Exception e) {
            return BaseResponse.errormsg(Contants.user.FAIL);
        }
    }

    /**
     * 获取显示在主页的公告
     */
    @Override
    public List<AnnouncementVO> annDisplay() {
        List<AnnouncementEntity> announcementList = iAnnouncementRepository.findByAnnStatus(1);
        if (announcementList.size() == 0) {
            return null;
        }
        List<AnnouncementVO> announcementVOList = new ArrayList<>();
        announcementList.forEach(announcement -> {
            AnnouncementVO announcementVO = new AnnouncementVO();
            announcementVO.setId(announcement.getId());
            announcementVO.setTitle(announcement.getTitle());
            announcementVO.setAnnDate(announcement.getAnnDate());
            announcementVOList.add(announcementVO);
        });
        return announcementVOList;
    }

    @Override
    public String getAnnContent(String id) {
        Optional<AnnouncementEntity> announcement = iAnnouncementRepository.findById(id);
        if (announcement.get() == null) {
            return null;
        }

        return announcement.get().getContents();
    }

    @Override
    public List<AccountRoleVO> getAccountRole() {

        List<AccountRoleVO> accountRoleList = new ArrayList<>();
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        user.getRoleList().forEach(role -> {

            UserRoleEntity userRole = iUserRoleRepository.findByUIdAndRoleId(user.getId(), role.getId());
            if (userRole.getRoleStatus().equals(1)) {
                AccountRoleVO accountRole = new AccountRoleVO();
                accountRole.setRole(role.getDesrciption());

                accountRoleList.add(accountRole);
            }
        });

        return accountRoleList;
    }

    /**
     * 用户角色添加
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> addAnotherRole(AccountRoleVO reqVO) {

        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        List<UserRoleEntity> userRoleList = iUserRoleRepository.findByUId(user.getId());

        long statusCount_0 = userRoleList.stream()
                .filter(userRole -> userRole.getRoleStatus().equals(0)).count();
        long statusCount_1 = userRoleList.stream()
                .filter(userRole -> userRole.getRoleStatus().equals(-1)).count();
        if (statusCount_0 >= 1 || statusCount_1 >= 1) {
            return BaseResponse.errormsg("存在未审核或未通过的角色，禁止再申请添加！");
        }

        for (RoleEntity role1 : user.getRoleList()) {

            if (role1.getRole().equals(reqVO.getRole())) {

                return BaseResponse.errormsg("该角色已存在，不需要重复添加！");
            }
        }

        RoleEntity role2 = iRoleRespository.findByRole(reqVO.getRole());
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setuId(user.getId());
        userRole.setRoleId(role2.getId());
        String desciption = user.getEmail() + "#" + role2.getRole();
        userRole.setDesciption(desciption);
        userRole.setRoleStatus(0);

        try {
            iUserRoleRepository.saveAndFlush(userRole);
            return BaseResponse.success(Contants.user.SUCCESS);
        } catch (Exception e) {
            return BaseResponse.errormsg("角色添加异常，请稍后再试！");
        }


    }

}

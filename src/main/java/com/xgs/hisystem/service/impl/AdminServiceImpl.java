package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.pojo.bo.BasePageReqBO;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.entity.RoleEntity;
import com.xgs.hisystem.pojo.entity.UserEntity;
import com.xgs.hisystem.pojo.vo.*;
import com.xgs.hisystem.repository.IRoleRespository;
import com.xgs.hisystem.repository.IUserRepository;
import com.xgs.hisystem.service.IAdminService;
import com.xgs.hisystem.service.IUserService;
import com.xgs.hisystem.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IRoleRespository iRoleRespository;

    @Autowired
    private IUserService iUserService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 新增角色
     *
     * @param roleVO
     * @return
     */
    @Override
    public BaseResponse<?> createRole(RoleVO roleVO) {

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(roleVO.getRolename());
        roleEntity.setValue(roleVO.getRoleValue());
        roleEntity.setDesrciption(roleVO.getDesciption());

        try {
            iRoleRespository.saveAndFlush(roleEntity);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.errormsg(e.getMessage());
        }

    }

    @Override
    public PageRspBO<applyRspVO> getRoleApply(BasePageReqBO reqBO) {

        Page<UserEntity> page = iUserRepository.findAll(new Specification<UserEntity>() {
            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                Predicate userRole = criteriaBuilder.equal(root.get("roleStatus"), 0);
                predicateList.add(userRole);
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, PageRequest.of(reqBO.getPageNumber(), reqBO.getPageSize()));
        if (page == null) {
            return null;
        }
        List<UserEntity> userEntityList = page.getContent();

        List<applyRspVO> applyRspVOList = new ArrayList<>();

        userEntityList.forEach(user -> {
            applyRspVO applyRspVO = new applyRspVO();
            applyRspVO.setEmail(user.getEmail());
            applyRspVO.setUsername(user.getUsername());
            applyRspVO.setDateTime(user.getCreateDatetime());

            user.getRoleList().forEach(role -> {
                applyRspVO.setRole(role.getDesrciption());
            });
            applyRspVOList.add(applyRspVO);
        });

        PageRspBO pageRspBO = new PageRspBO();
        pageRspBO.setRows(applyRspVOList);
        pageRspBO.setTotal((int) page.getTotalElements());
        return pageRspBO;
    }

    /**
     * 后台添加账户
     *
     * @param reqVO
     * @return
     */
    @Override
    public BaseResponse<?> saveUserAndSendEmailTemp(UserRegisterReqVO reqVO) {
        String email = reqVO.getEmail();

        UserEntity checkUser = iUserRepository.findByEmail(email);
        if (checkUser != null) {
            if (checkUser.getEmailStatus().equals(0)) {
                logger.info("--**--账户：{} 已注册，但未激活！--**--", email);
                return BaseResponse.errormsg("该账户已注册，但未激活！");
            } else {
                logger.info("--**--账户：{} 已存在！--**--", email);
                return BaseResponse.errormsg("该账户已存在！");
            }
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

        //组装角色参数
        List<String> roleList = new ArrayList<>();
        roleList.add(reqVO.getRoleValue());
        AddRoleVO addRoleVO = new AddRoleVO();
        addRoleVO.setEmail(email);
        addRoleVO.setRoleList(roleList);

        try {
            iUserRepository.saveAndFlush(userEntity);
            //保存角色
            iUserService.addRole(addRoleVO);

            return BaseResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.errormsg("保存用户信息或邮件发送异常!");
        }
    }

    /**
     * 审核角色的通知
     *
     * @return
     */
    @Override
    public List<applyRspVO> getRoleNotice() {

        //从shiro中获取当前登录用户信息
        UserEntity userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();

        List<applyRspVO> applyRspList = new ArrayList<>();
        //若为管理员，组装审核角色通知参数
        long checkCount = userEntity.getRoleList()
                .stream().filter(roleEntity -> roleEntity.getRole().equals("admin")).count();

        if (checkCount > 0) {
            List<UserEntity> userList = iUserRepository.findByRoleStatus(0);

            if (userList != null && userList.size() > 0) {
                userList.forEach(user -> {
                    applyRspVO applyRspVO = new applyRspVO();
                    applyRspVO.setEmail(user.getEmail());
                    applyRspVO.setUsername(user.getUsername());
                    applyRspVO.setDateTime(user.getCreateDatetime());

                    user.getRoleList().forEach(role -> {
                        applyRspVO.setRole(role.getDesrciption());
                    });
                    applyRspList.add(applyRspVO);
                });
            }
        }
        return applyRspList;
    }

    /**
     * 审核角色
     *
     * @param status
     * @param email
     * @return
     */
    @Override
    public BaseResponse<?> changeRoleStatus(int status, String email) {

        if (!StringUtils.isEmpty(status)) {
            UserEntity user = iUserRepository.findByEmail(email);
            user.setRoleStatus(status);
            iUserRepository.saveAndFlush(user);
        }
        return BaseResponse.success();
    }
}

package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BasePageReqBO;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.*;

import java.text.ParseException;
import java.util.List;

public interface IUserService {

    BaseResponse<?> doLogin(UserLoginReqVO reqVO);

    BaseResponse<?> saveUserAndSendEmail(UserRegisterReqVO reqVO);

    BaseResponse<?> activation(String email, String validateCode) throws ParseException;

    PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqBO reqBO);

    BaseResponse<?> changePassword(ChangePasswordReqVO reqVO);

    List<UserInfoVO> getUserInfo();

    BaseResponse<?> changeUserInfo(UserInfoVO reqVO);

    List<AnnouncementVO> annDisplay();

    String getAnnContent(String id);

    List<AccountRoleVO> getAccountRole();

    BaseResponse<?> addAnotherRole(AccountRoleVO reqVO);
}

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

    BaseResponse<?> addRole(AddRoleVO addRoleVO);

    PageRspBO<LoginInforRspVO> getLoginfor(BasePageReqBO reqBO);

    BaseResponse<?> changePassword(ChangePasswordReqVO reqVO);

    List<UserInfoRspVO> getUserInfo();

    BaseResponse<?> changeUserInfo(UserInfoReqVO reqVO);
}

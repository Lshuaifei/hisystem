package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.vo.AddRoleVO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.RoleVO;
import com.xgs.hisystem.pojo.vo.UserVO;

import java.text.ParseException;

public interface IUserService {

    BaseResponse<?> saveUserAndSendEmail(UserVO userVO);

    BaseResponse<?> activation(String email, String validateCode) throws ParseException;

    BaseResponse<?> addRole(AddRoleVO addRoleVO);
}

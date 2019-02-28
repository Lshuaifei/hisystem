package com.mine.hisystem.service;

import com.mine.hisystem.pojo.vo.BaseResponse;
import com.mine.hisystem.pojo.vo.UserVO;

import java.text.ParseException;

public interface IUserService {

    BaseResponse<?> saveUserAndSendEmail(UserVO userVO);

    BaseResponse<?> activation(String email, String validateCode) throws ParseException;
}

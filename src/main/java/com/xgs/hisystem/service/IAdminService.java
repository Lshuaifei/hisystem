package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.bo.BasePageReqBO;
import com.xgs.hisystem.pojo.bo.PageRspBO;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.RoleVO;
import com.xgs.hisystem.pojo.vo.UserRegisterReqVO;
import com.xgs.hisystem.pojo.vo.applyRspVO;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
public interface IAdminService {

    BaseResponse<?> createRole(RoleVO roleVO);

    PageRspBO<applyRspVO> getRoleApply(BasePageReqBO reqBO);

    BaseResponse<?> saveUserAndSendEmailTemp(UserRegisterReqVO reqVO);

    List<applyRspVO> getRoleNotice();

    BaseResponse<?> changeRoleStatus(int status, String email);
}

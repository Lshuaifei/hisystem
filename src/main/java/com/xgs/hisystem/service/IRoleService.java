package com.xgs.hisystem.service;

import com.xgs.hisystem.pojo.entity.RoleEntity;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.RoleVO;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public interface IRoleService {

    BaseResponse<?> createRole(RoleVO roleVO);
}

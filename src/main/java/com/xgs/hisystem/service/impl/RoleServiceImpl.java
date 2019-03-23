package com.xgs.hisystem.service.impl;

import com.xgs.hisystem.pojo.entity.RoleEntity;
import com.xgs.hisystem.pojo.vo.BaseResponse;
import com.xgs.hisystem.pojo.vo.RoleVO;
import com.xgs.hisystem.repository.BaseRepository;
import com.xgs.hisystem.repository.IRoleRespository;
import com.xgs.hisystem.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRespository iRoleRespository;
    @Override
    public BaseResponse<?> createRole(RoleVO roleVO) {

        RoleEntity roleEntity=new RoleEntity();
        roleEntity.setRole(roleVO.getRolename());
        roleEntity.setDesrciption(roleVO.getDesciption());

        try {
            iRoleRespository.saveAndFlush(roleEntity);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.errormsg(e.getMessage());
        }

    }
}

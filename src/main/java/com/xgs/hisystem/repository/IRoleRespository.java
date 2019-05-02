package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.RoleEntity;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public interface IRoleRespository extends BaseRepository<RoleEntity> {

    RoleEntity findByRole(String role);

    RoleEntity findByRoleValue(Integer value);
}

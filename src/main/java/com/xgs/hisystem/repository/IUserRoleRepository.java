package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.UserRoleEntity;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/20
 */
public interface IUserRoleRepository extends BaseRepository<UserRoleEntity> {

    UserRoleEntity findByUIdAndRoleId(String uid,String roleId);

}

package com.xgs.hisystem.repository;

import com.xgs.hisystem.pojo.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends BaseRepository<UserEntity> {

    UserEntity findByEmail(String email);

}

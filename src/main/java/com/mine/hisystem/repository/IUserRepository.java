package com.mine.hisystem.repository;

import com.mine.hisystem.pojo.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends BaseRepository<UserEntity> {

    UserEntity findByEmail(String email);

}

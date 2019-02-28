package com.mine.hisystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author LT
 * @version $Id: BaseRepository.java, v 0.1 2017年9月13日 上午10:37:31 LT Exp $
 */
//一般用作父类的repository，有这个注解，spring不会去实例化该repository。在低版本的spring boot 中不加这个注解也不会报错，高版本的spring boot 不加这个
//注解这spring 会去实例化接口，失败。org.springframework.beans.factory.BeanCreationException: 
//Error creating bean with name 'baseRepository': Invocation of init method failed; 
//nested exception is java.lang.IllegalArgumentException: Not a managed type: class java.lang.Object
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
    //JpaRepository<T, String>  实体， 主键类型

}

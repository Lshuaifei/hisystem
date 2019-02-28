package com.mine.hisystem.pojo.entity;

import com.mine.hisystem.util.DateUtil;

import javax.persistence.*;

/**
 * @author xgs
 * @Description:
 * @date 2019/2/14
 */
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "createUser",length = 64)
    private String createUser;

    @Column(name = "updateUser",length = 64)
    private String updateUser;

    @Column(name = "create_datetime",length = 20,nullable = false)
    private String createDatetime= DateUtil.getCurrentDateToString();// 创建时间 yyyy-MM-dd hh:mm:ss

    @Column(name = "update_datetime",length = 20)
    private String updateDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}

package com.xgs.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;

@Entity
@Table(name = "his_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserEntity extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "plain_password")
    private String plainPassword;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "status")
    private Integer status;            //用户状态码

    @Column(name = "validate_code")
    private String validateCode;       //邮箱激活验证码

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "his_user_role", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    private List<RoleEntity> roleList;

    @OneToOne(targetEntity = UserInformationEntity.class)
    @JoinColumn(name = "info_id",referencedColumnName = "id")
    private UserInformationEntity userInformation;  //用户个人信息

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public List<RoleEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleEntity> roleList) {
        this.roleList = roleList;
    }

    public UserInformationEntity getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformationEntity userInformation) {
        this.userInformation = userInformation;
    }
}

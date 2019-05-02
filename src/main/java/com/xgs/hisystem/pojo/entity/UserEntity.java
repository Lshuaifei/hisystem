package com.xgs.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "his_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserEntity extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "political_status")
    private String politicalStatus;

    @Column(name = "plain_password")
    private String plainPassword;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "email_status")
    private Integer emailStatus;        //激活状态

    @Column(name = "validate_code")
    private String validateCode;        //邮箱激活验证码

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "his_user_role", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    private List<RoleEntity> roleList;  //用户角色

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoginInforEntity> loginInforList;  //用户登录信息

    @Column(name = "department", nullable = true, length = 30)
    private String department;  //所属科室

    @Column(name = "departmentType", nullable = true, length = 10)
    private String departmentType; //科室类型

    @Column(name = "grade", nullable = true, length = 10)
    private String grade;     //医生等级

    @Column(name = "workAddress", nullable = true, length = 30)
    private String workAddress;   //门诊的具体地址

    @Column(name = "workStatus", nullable = true, length = 10)
    private String workStatus;    //上班或请假

    @Column(name = "workDateTime", nullable = true, length = 20)
    private String workDateTime;  //值班时间

    @Column(name = "treatmentPrice", nullable = true, length = 10)
    private String treatmentPrice;  //诊疗费，由等级决定

    @Column(name = "allowNum", nullable = true, length = 10)
    private Integer allowNum;

    @Column(name = "nowNum", nullable = true, length = 10)
    private Integer nowNum;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<RoleEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleEntity> roleList) {
        this.roleList = roleList;
    }

    public List<LoginInforEntity> getLoginInforList() {
        return loginInforList;
    }

    public void setLoginInforList(List<LoginInforEntity> loginInforList) {
        this.loginInforList = loginInforList;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getTreatmentPrice() {
        return treatmentPrice;
    }

    public void setTreatmentPrice(String treatmentPrice) {
        this.treatmentPrice = treatmentPrice;
    }

    public String getWorkDateTime() {
        return workDateTime;
    }

    public void setWorkDateTime(String workDateTime) {
        this.workDateTime = workDateTime;
    }

    public Integer getAllowNum() {
        return allowNum;
    }

    public void setAllowNum(Integer allowNum) {
        this.allowNum = allowNum;
    }

    public Integer getNowNum() {
        return nowNum;
    }

    public void setNowNum(Integer nowNum) {
        this.nowNum = nowNum;
    }

    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }
}

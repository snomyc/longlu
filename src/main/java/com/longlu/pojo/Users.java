package com.longlu.pojo;

public class Users {
    private Long id;

    private Integer deptid; //部门id

    private String roleid; //角色id,支持多个，多个角色id用,隔开

    private String username; //用户名

    private String password; //密码，md5加密

    private String xingming; //姓名

    private String sex; //性别

    private String email; //邮箱

    private String phone; //联系电话

    private String telphone; //座机电话

    private String position;//职位

    private String lastlogin; //最后登录时间

    private String f0; //供应商, 多个供应商用,号分隔

    private String f1; //是否邮件推送  0:不推送，1:推送 

    private String f2;

    private String f3;

    private String f4;

    private String f5;

    private String f6;

    private String f7;

    private String f8;

    private String f9;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming == null ? null : xingming.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin == null ? null : lastlogin.trim();
    }

    public String getF0() {
        return f0;
    }

    public void setF0(String f0) {
        this.f0 = f0 == null ? null : f0.trim();
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1 == null ? null : f1.trim();
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2 == null ? null : f2.trim();
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3 == null ? null : f3.trim();
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4 == null ? null : f4.trim();
    }

    public String getF5() {
        return f5;
    }

    public void setF5(String f5) {
        this.f5 = f5 == null ? null : f5.trim();
    }

    public String getF6() {
        return f6;
    }

    public void setF6(String f6) {
        this.f6 = f6 == null ? null : f6.trim();
    }

    public String getF7() {
        return f7;
    }

    public void setF7(String f7) {
        this.f7 = f7 == null ? null : f7.trim();
    }

    public String getF8() {
        return f8;
    }

    public void setF8(String f8) {
        this.f8 = f8 == null ? null : f8.trim();
    }

    public String getF9() {
        return f9;
    }

    public void setF9(String f9) {
        this.f9 = f9 == null ? null : f9.trim();
    }
}
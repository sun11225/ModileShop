package com.sun.mobileshop.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/3 0003.
 */

public class MemberEntity implements Serializable {

    private int member_id;
    private String uName;
    private String password;
    private String email;
    private int sex;
    private String mobile;
    private Object regTime;
    private Object lastLogin;
    private String image;
    private Object memberAddresses;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getRegTime() {
        return regTime;
    }

    public void setRegTime(Object regTime) {
        this.regTime = regTime;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getMemberAddresses() {
        return memberAddresses;
    }

    public void setMemberAddresses(Object memberAddresses) {
        this.memberAddresses = memberAddresses;
    }

    @Override
    public String toString() {

        return "MemberEntity{" +
                "member_id=" + member_id +
                ", uname='" + uName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                ", regtime=" + regTime +
                ", lastlogin=" + lastLogin +
                ", image='" + image + '\'' +
                ", memberAddresses=" + memberAddresses +
                '}';
    }
}

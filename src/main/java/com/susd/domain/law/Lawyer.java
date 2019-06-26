package com.susd.domain.law;

import com.susd.domain.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class Lawyer implements Entity<Lawyer> {

    private int id;

    private int tenantId;

    @NotNull(message = "姓不能为空")
    @Size(max = 10,message = "姓最大长度不能超过10个字符")
    private String surname;

    @NotNull(message = "名字不能为空")
    @Size(max = 20,message = "名字最大长度不能超过20个字符")
    private String name;

    @NotNull(message = "性别不能为空")
    private String sex;

    @NotNull(message = "手机号不能为空")
    @Size(max = 25,message = "手机号最大长度不能超过15个字符")
    private String cell;

    @Size(max = 20,message = "固定电话最大长度不能超过20个字符")
    private String fixed;

    @Size(max = 20,message = "学历最大长度不能超过20个字符")
    private String degree;

    @NotNull(message = "所在律师所不能为空")
    @Size(max = 255,message = "所在律师所最大长度不能超过255个字符")
    private String firm;

    @Size(max = 100,message = "职务最大长度不能超过100个字符")
    private String position;

    @NotNull(message = "执业执号不能为空")
    @Size(max = 100,message = "执业执号最大长度不能超过255个字符")
    private String practiceNo;

    @Size(max = 255,message = "联系地址最大长度不能超过255个字符")
    private String address;

    @Size(max = 100,message = "微信号最大长度不能超过255个字符")
    private String wechat;

    @Size(max = 20,message = "QQ号最大长度不能超过20个字符")
    private String qq;

    @Size(max = 255,message = "备注最大长度不能超过255个字符")
    private String remark;

    private Date addTime;

    private int userId;

    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPracticeNo() {
        return practiceNo;
    }

    public void setPracticeNo(String practiceNo) {
        this.practiceNo = practiceNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        return Integer.toString(getId()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Lawyer other=(Lawyer)obj;

        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(Lawyer other) {
        return other != null && this.getId() == other.getId();
    }
}

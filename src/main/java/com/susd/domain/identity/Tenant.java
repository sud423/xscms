package com.susd.domain.identity;

import com.susd.domain.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class Tenant implements Entity<Tenant> {

    //租户编号
    private int id;
    //租房名称
    @Size(max = 60, message = "租户名称最大长度为60个字符")
    @NotNull(message = "租户名称不能为空")
    private String name;
    //所属公司
    @Size(max = 60, message = "公司名称最大长度为60个字符")
    private String company;
    //联系人
    @Size(max = 30, message = "联系人最大长度为30个字符")
    private String contact;
    //联系电话
    @Size(max = 20, message = "联系电话最大长度为20个字符")
    private String cell;
    //网站域名
    @Size(max = 100, message = "域名最大长度为100个字符")
    private String domain;
    //服务ip
    @Size(max = 64, message = "ip最大长度为64个字符")
    private String ip;
    //连接服务器账号
    @Size(max = 60, message = "服务器账号最大长度为60个字符")
    private String account;
    //连接服务器密码
    @Size(max = 60, message = "服务器密码最大长度为60个字符")
    private String password;
    //连接服务端口
    @Size(max = 6, message = "服务器端口最大长度为6个字符")
    private String port;
    //操作系统
    @Size(max = 30, message = "操作系统最大长度为30个字符")
    private String os;
    //添加时间
    private Date addTime;

    private List<Integer> resources;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public List<Integer> getResources() {
        return resources;
    }

    public void setResources(List<Integer> resources) {
        this.resources = resources;
    }

    @Override
    public int hashCode() {
        return Integer.toString(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        final Tenant other = (Tenant) obj;
        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(Tenant other) {
        return other != null && this.getId() == other.getId();
    }
}

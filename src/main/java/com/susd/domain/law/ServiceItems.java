package com.susd.domain.law;

import com.susd.domain.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ServiceItems implements Entity<ServiceItems> {

    private int id;

    private int tenantId;

    @NotNull(message = "服务名称不能为空")
    @Size(max = 100,message = "服务名称最大长度不能超过100个字符")
    private String name;

    @Size(max=50,message = "服务编码最大长度不能超过50个字符")
    private String code;

    @Size(max = 255,message = "备注最大长度不能超过100个字符")
    private String remark;

    private Date addTime;

    private int version;

    private int userId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

        ServiceItems other=(ServiceItems)obj;

        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(ServiceItems other) {
        return other != null && this.getId() == other.getId();
    }
}

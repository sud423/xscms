package com.susd.domain.law;

import com.susd.domain.Entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ServiceItems implements Entity<ServiceItems> {

    private int id;

    private int tenantId;

    @NotNull(message = "服务内容不能为空")
    @Max(value=100,message = "服务内容最大长度不能超过100个字符")
    private String name;

    @Max(value=255,message = "服务内容最大长度不能超过100个字符")
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

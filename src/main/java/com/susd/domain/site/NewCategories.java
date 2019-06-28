package com.susd.domain.site;

import com.susd.domain.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class NewCategories implements Entity<NewCategories> {

    private int id;

    private int tenantId;

    @NotNull(message = "分类名称不能为空")
    @Size(max = 100,message = "分类名称最大长度为100字符")
    private String name;

    private int parentId;

    @Size(max = 50,message = "分类编码最大长度为50字符")
    private String code;

    private int sort;

    @Size(max = 255,message = "分类图标最大长度为255字符")
    private String icon;

    private int userId;

    private Date addTime;

    @Size(max = 255,message = "备注最大长度为255字符")
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        NewCategories category=(NewCategories)obj;

        return sameIdentityAs(category);
    }

    @Override
    public boolean sameIdentityAs(NewCategories other) {
        return other != null && this.getId() == other.getId();
    }
}

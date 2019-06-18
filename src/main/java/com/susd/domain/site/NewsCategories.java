package com.susd.domain.site;

import com.susd.domain.Entity;

import java.util.Date;

public class NewsCategories implements Entity<NewsCategories> {

    private int id;

    private int tenantId;

    private String name;

    private String code;

    private int sort;

    private String icon;

    private int userId;

    private Date addTime;

    private String remark;

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

        NewsCategories category=(NewsCategories)obj;

        return sameIdentityAs(category);
    }

    @Override
    public boolean sameIdentityAs(NewsCategories other) {
        return other != null && this.getId() == other.getId();
    }
}

package com.susd.domain.complex;

import java.util.Date;

import com.susd.domain.Entity;

public class Dict implements Entity<Dict> {

    private int id;

    private int tenantId;

    /**
     * 字典键
     */
    private String key;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典值对应的文本
     */
    private String text;

    /**
     * 排序
     */
    private int sort;

    private Date addTime;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

        Dict other = (Dict) obj;

        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(Dict other) {
        return other != null && id == other.getId();
    }


}

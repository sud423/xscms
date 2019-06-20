package com.susd.domain.complex;

import com.susd.domain.Entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Express implements Entity<Express> {

    /**
     * 物流编号
     */
    private int id;

    /**
     * 所属租户编号
     */
    private int tenantId;

    @NotNull(message = "物流名称不能为空")
    @Max(value = 100,message = "物流名称不能超过100个字符")
    private String name;

    @Max(value = 100,message = "物流编码不能超过100个字符")
    private String code;

    private int coefficient;

    private byte type;

    @Max(value = 255,message = "备注不能超过255个字符")
    private String remark;

    private Date addTime;

    private int version;

    /**
     * 获取物流编号
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 设置物流编号
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取租户编号
     * @return
     */
    public int getTenantId() {
        return tenantId;
    }

    /**
     * 设置租户编号
     * @param tenantId
     */
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

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
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

        Express other=(Express)obj;

        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(Express other) {
        return other != null && id == other.getId();
    }
}

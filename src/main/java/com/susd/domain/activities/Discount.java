package com.susd.domain.activities;

import com.susd.domain.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class Discount implements Entity<Discount> {

    /**
     * 优惠编号
     */
    private int id;

    /**
     * 优惠类型 10：支付折扣  20：月结达标 30：充值达标
     */
    private byte type;

    /**
     * 所属快递公司编号
     */
    private String express;

    /**
     * 起始有效值
     */
    private int startPeriod;

    /**
     * 结束有效值
     */
    private int endPeriod;

    /**
     * 折扣
     */
    private float discount;

    /**
     * 排序  值越小优先匹配
     */
    private int sort;

    /**
     * 是否叠加使用
     */
    private boolean overlayUse;

    /**
     *最低运费
     */
    private float lowPrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 所属租户
     */
    private int tenantId;

    /**
     * 版本号
     */
    private int version;

    /**
     * 添加人
     */
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(int startPeriod) {
        this.startPeriod = startPeriod;
    }

    public int getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(int endPeriod) {
        this.endPeriod = endPeriod;
    }

    public float getDiscount() {

        BigDecimal m = new BigDecimal(discount);
        return m.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isOverlayUse() {
        return overlayUse;
    }

    public void setOverlayUse(boolean overlayUse) {
        this.overlayUse = overlayUse;
    }

    public float getLowPrice() {
        BigDecimal m = new BigDecimal(lowPrice);
        return m.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
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

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getUserId() {
        return userId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 设置添加人
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }



    @Override
    public boolean sameIdentityAs(Discount other) {
        return other != null && id == other.getId();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        final Discount other = (Discount) object;
        return sameIdentityAs(other);
    }

    @Override
    public int hashCode() {
        return Integer.toString(id).hashCode();
    }
}

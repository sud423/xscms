package com.susd.domain.law;

import com.susd.domain.Entity;

import java.util.Date;

public class LawOrder implements Entity<LawOrder> {

    private int id;

    private int tenantId;

    private String orderNo;

    private int serviceId;

    private int lawyerId;

    private float price;

    private int hours;

    private float amount;

    private float arrivalAmount;

    private byte payChannel;

    private Date payTime;

    private Date arrivalTime;

    private byte status;

    private Date add_time;

    private String remark;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(float arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
    }

    public byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(byte payChannel) {
        this.payChannel = payChannel;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        LawOrder other=(LawOrder)obj;

        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(LawOrder other) {
        return other != null && this.getId() == other.getId();
    }
}

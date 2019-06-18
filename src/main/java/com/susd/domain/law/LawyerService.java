package com.susd.domain.law;

import com.susd.domain.Entity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class LawyerService implements Entity<LawyerService> {

    private int lawyerId;

    private int serviceId;

    private boolean isCommand;

    private float price;

    private int hours;

    public int getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(int lawyerId) {
        this.lawyerId = lawyerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isCommand() {
        return isCommand;
    }

    public void setCommand(boolean command) {
        isCommand = command;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(lawyerId).
                append(serviceId).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        LawyerService other=(LawyerService)obj;

        return sameIdentityAs(other);
    }

    @Override
    public boolean sameIdentityAs(LawyerService other) {
        return other != null && new EqualsBuilder().append(this.lawyerId,other.getLawyerId())
                .append(this.serviceId, other.getServiceId())
                .isEquals();
    }
}

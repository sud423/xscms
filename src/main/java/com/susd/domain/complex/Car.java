package com.susd.domain.complex;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.susd.domain.Entity;

public class Car implements Entity<Car> {
	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id;
	
	private int tenantId;
	
	@NotNull(message="车牌号不能为空")
	@Size(max=20,message="车牌号最大长度为20个字符")
	private String carNumber;

	@NotNull(message="IMEI不能为空")
	@Size(max=100,message="IMEI最大长度为100个字符")
	private String sim;

	@NotNull(message="车辆类型不能为空")
	private byte type;
	
	private float load;
	
	private byte status;
	
	private String remark;
		
	private int maintainId;
	
	private Date addTime;
	
	private int version;
	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public float getLoad() {
		return load;
	}

	public void setLoad(float load) {
		this.load = load;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(int maintainId) {
		this.maintainId = maintainId;
	}

	public String getAddTime() {
		return dateFormat.format(addTime);
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
	public boolean sameIdentityAs(Car other) {
		return other != null && id == other.getId();
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

		Car other=(Car)obj;
		
		return sameIdentityAs(other);
	}
	
	
}

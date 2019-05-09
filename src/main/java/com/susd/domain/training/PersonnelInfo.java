package com.susd.domain.training;

import java.util.Date;

import com.susd.domain.Entity;

public class PersonnelInfo implements Entity<PersonnelInfo> {

	private int id;

	private int tenantId;

	private String sex;

	private String cell;

	private String email;

	private byte source;

	private byte status;

	private String channel;

	private String company;

	private String fti;

	private String remark;

	private Date addTime;
	
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getSource() {
		return source;
	}

	public void setSource(byte source) {
		this.source = source;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFti() {
		return fti;
	}

	public void setFti(String fti) {
		this.fti = fti;
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
	public boolean sameIdentityAs(PersonnelInfo other) {
		return other != null && this.getId() == other.getId();
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
		
		PersonnelInfo other=(PersonnelInfo)obj;
		
		return sameIdentityAs(other);
	}

}

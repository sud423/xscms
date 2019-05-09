package com.susd.domain.training;

import java.util.Date;

import com.susd.domain.Entity;

/**
 * 项目管理
 * 
 * @author susd
 *
 */
public class Project implements Entity<Project> {

	private int id;

	private int tenantId;

	private String name;
	
	private String channel;

	private byte Status;

	private String summary;

	private int userId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public byte getStatus() {
		return Status;
	}

	public void setStatus(byte status) {
		Status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean sameIdentityAs(Project other) {
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
		
		Project other=(Project)obj;
		
		return sameIdentityAs(other);
	}

}

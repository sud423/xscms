package com.susd.domain.identity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.susd.domain.Entity;

public class SysRole implements Entity<SysRole> {

	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private int id;
	
	private int tenantId;
	
	//角色名称
	private String name;
	
	private String alias;
		
	//状态
	private byte status;

	//备注
	private String remark;
	
	private byte type;
	
	private Date addTime;
	
	private int version;
	
	private List<Integer> resources;

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

	/**
	 * 获取角色名称
	 * @return 角色名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置角色名称
	 * @param name 角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 
	 * @return 获取当前状态
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status 状态 1：正常 10：禁用
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * 获取角色备注
	 * @return 角色备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置角色备注
	 * @param remark 角色备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
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
	
	public List<Integer> getResources() {
		return resources;
	}

	public void setResources(List<Integer> resources) {
		this.resources = resources;
	}

	@Override
	public boolean sameIdentityAs(SysRole other) {
		return other != null && id == other.getId();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final SysRole other = (SysRole) object;
		return sameIdentityAs(other);
	}

	@Override
	public int hashCode() {
		return Integer.toString(id).hashCode();
	}

	

}

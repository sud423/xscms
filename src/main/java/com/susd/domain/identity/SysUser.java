package com.susd.domain.identity;


import java.util.List;

import com.susd.domain.Entity;
import com.susd.domain.UserBase;

public class SysUser extends UserBase implements Entity<SysUser>{
	
	private List<Integer> roles;
		
	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	/**
	 * 冻结账户
	 */
	public void freeze() {
		this.setStatus((byte)10);
	}

	/**
	 * 启用账户
	 */
	public void enable() {
		this.setStatus((byte)1);
	}
	
	@Override
	public String toString() {
		return String.format("User{id:%s,userName:%s,password:%s,email:%s,cell:%s}",this.getId(), this.getUserName(),this.getPassword(), this.getEmail(),this.getCell());
	}

	@Override
	public boolean sameIdentityAs(SysUser other) {
		return other != null && this.getId() == other.getId();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final SysUser other = (SysUser) object;
		return sameIdentityAs(other);
	}

	@Override
	public int hashCode() {
		return Integer.toString(this.getId()).hashCode();
	}
	
}

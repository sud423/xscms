package com.susd.domain.site;

import java.util.List;

import com.susd.domain.Entity;
import com.susd.domain.UserBase;

/**
 * 驾驶员/客户
 * @author TY
 *
 */
public class User extends UserBase implements Entity<User> {

	/**
	 * 类型 10：客户 20：驾驶员
	 */
	private byte type;
	
	private String idNumber;
	
	private String auditReason;
	
	private List<String> attach;
	
	private UserLogin userLogin;
		
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}


	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}
	
	public List<String> getAttach() {
		return attach;
	}

	public void setAttach(List<String> attach) {
		this.attach = attach;
	}

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}


	/**
	 * 审核
	 * @param status 1：通过 30：不通过
	 * @param reason 不通过理由
	 */
	public void audit(byte status,String reason) {
		this.setStatus(status);
		if(status==30)
			this.setAuditReason(reason);
	}
	

	@Override
	public boolean sameIdentityAs(User other) {
		return other != null && this.getId() == other.getId();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final User other = (User) object;
		return sameIdentityAs(other);
	}

	@Override
	public int hashCode() {
		return Integer.toString(getId()).hashCode();
	}
}

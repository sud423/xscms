package com.susd.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserBase {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id;

	/**
	 * 租户编号
	 */
	private int tenantId;

	/**
	 * 用户名
	 */
	@Size(max = 60, message = "用户名最大长度为60个字符")
	@NotNull(message = "用户名不能为空")
	private String userName;

	@Size(max = 30, message = "姓名最大长度为30个字符")
	@NotNull(message = "姓名不能为空")
	private String name;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 邮箱
	 */
	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 125, message = "邮箱最大长度为125个字符")
	private String email;

	/**
	 * 手机号
	 */
	@Size(max = 15, message = "手机号最大长度为15个字符")
	@NotNull(message = "手机号不能为空")
	private String cell;

	/**
	 * 状态 1：正常 10：离职 只能查看，不能操作 20：冻结
	 */
	private byte status;

	/**
	 * 加密盐
	 */
	private String salt;

	/**
	 * 添加时间
	 */
	private Date addTime;

	/**
	 * 版本号
	 */
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getAddTime() {
		if (addTime != null)
			return dateFormat.format(addTime);
		else
			return "";
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
}

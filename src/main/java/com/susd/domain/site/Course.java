package com.susd.domain.site;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.susd.domain.Entity;

public class Course implements Entity<Course> {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id;

	private int tenantId;

	/**
	 * 学院
	 */
	private String academy;

	/**
	 * 课程分类
	 */
	private String classify;

	/**
	 * 课程名称
	 */
	private String name;

	/**
	 * 基础信息
	 */
	private String baseInfo;

	/**
	 * 院校简介
	 */
	private String backSummary;

	/**
	 * 讲师简介
	 */
	private String teaSummary;

	/**
	 * 课程模块
	 */
	private String summary;

	/**
	 * 开课日期
	 */
	private String openDate;

	/**
	 * 排序
	 */
	private int sort;

	/**
	 * 学费
	 */
	private float fee;

	/**
	 * 上课地址
	 */
	private String address;

	/**
	 * 创建时间
	 */
	private Date addTime;

	private byte status;

	/**
	 * 管理员编号
	 */
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

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getBackSummary() {
		return backSummary;
	}

	public void setBackSummary(String backSummary) {
		this.backSummary = backSummary;
	}

	public String getTeaSummary() {
		return teaSummary;
	}

	public void setTeaSummary(String teaSummary) {
		this.teaSummary = teaSummary;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public int getTag() {
		return sort;
	}

	public void setTag(int sort) {
		this.sort = sort;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddTime() {
		return dateFormat.format(addTime);
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * @description 获取数据当前的状态
	 * @return 状态 1：有效 10：未发布
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @description 设置当前状态
	 * @param status 状态 1：有效 10：未发布
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @description 设置当前管理者编号
	 * @param userId 管理者编号
	 */
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
	public boolean sameIdentityAs(Course other) {
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
		
		Course cour=(Course)obj;
		
		return sameIdentityAs(cour);
	}
}

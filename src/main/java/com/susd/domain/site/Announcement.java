package com.susd.domain.site;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.susd.domain.Entity;

public class Announcement implements Entity<Announcement>  {

	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private int id;
	
	private int tenantId;
	
	@NotNull(message="活动标题不能为空")
	@Size(max=60,message="活动标题最大长度为60个字符")
	private String title;

	@NotNull(message="活动内容不能为空")
	private String content;
		
	private String beginTime;
	
	private String endTime;
	
	//状态 1：发布中 10：未发布 20：删除 30：已过期
	private byte status;
	
	private int sort;
	
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

	/**
	 * 获取标题
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取内容
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @description 设置内容	
	 * @param content 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 *  @description 活动/通知起始时间
	 * @return 获取活动/通知起始时间，为空表示永久有效
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @description 设置活动/通知起始时间
	 * @param endTime 活动/通知起始时间
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	/**
	 * @description 结束 时间
	 * @return 获取结束时间，为空表示永久有效
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @description 设置结束时间
	 * @param endTime 结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @description 获取当前的状态
	 * @return 状态 1：有效 10：未发布
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @description 设置当前的状态
	 * @param status 状态 1：有效 10：未发布
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @description 获取排序
	 * @return 排序
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @description 设置排序
	 * @param sort 排序
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @description 获取添加时间
	 * @return 添加时间
	 */
	public String getAddTime() {
		return dateFormat.format(addTime);
	}

	/**
	 * @description 设置添加时间
	 * @param addTime 添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * @description 获取管理员编号
	 * @return 管理人员编号
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 *  @description 设置管理员编号
	 * @param userId 管理人员编号
	 */
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
	public boolean sameIdentityAs(Announcement other) {
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

		Announcement other=(Announcement)obj;
		
		return sameIdentityAs(other);
	}
	
	
}

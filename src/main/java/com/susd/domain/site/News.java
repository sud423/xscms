package com.susd.domain.site;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.susd.domain.Entity;

public class News implements Entity<News> {

	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private int id;

	private int categoryId;

	private int tenantId;
	
	private byte target;
	
	@NotNull(message="事件标题不能为空")
	@Size(max=60,message="事件标题最大长度为60个字符")
	private String title;
	
	private String cover;
	
	private String lead;

	@NotNull(message="事件内容不能为空")
	private String content;
	
	private String author;
	
	private String quoteUrl;
	
	private Date addTime;
	
	private byte status;
	
	private int clicks;
	
	private byte isTop;
	
	private byte isRed;
	
	private byte isHot;
	
	private byte isSlide;
	
	private int sort;
	
	private int userId;
	
	private int version;

	private NewCategories newCategories;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public byte getTarget() {
		return target;
	}

	public void setTarget(byte target) {
		this.target = target;
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
	 * 获取封面
	 * @return 封面
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * 设置封面
	 * @param cover 封面
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * 
	 * @return
	 */
	public String getLead() {
		return lead;
	}

	/**
	 * 
	 * @param lead
	 */
	public void setLead(String lead) {
		this.lead = lead;
	}

	/**
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取引用地址
	 * @return 引用地址
	 */
	public String getQuoteUrl() {
		return quoteUrl;
	}

	/**
	 * 设置引用地址
	 * @param quoteUrl 引用地址
	 */
	public void setQuoteUrl(String quoteUrl) {
		this.quoteUrl = quoteUrl;
	}

	/**
	 * 获取添加时间
	 * @return 添加时间
	 */
	public String getAddTime() {

		return dateFormat.format(addTime);
	}

	/**
	 * 设置添加时间
	 * @param addTime 添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 
	 * @return 状态 1：发布 10：未发布
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status 状态 1：发布 10：未发布
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * 获取浏览量
	 * @return 浏览量
	 */
	public int getClicks() {
		return clicks;
	}

	/**
	 * 设置浏览量
	 * @param clicks 浏览量
	 */
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	/**
	 * 获取是否置顶
	 * @return 是否置顶
	 */
	public byte getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * @param isTop 是否置顶
	 */
	public void setIsTop(byte isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取是否推荐
	 * @return 是否推荐
	 */
	public byte getIsRed() {
		return isRed;
	}

	/**
	 * 设置是否推荐
	 * @param isRed 是否推荐
	 */
	public void setIsRed(byte isRed) {
		this.isRed = isRed;
	}

	/**
	 * 获取是否热门
	 * @return 是否热门
	 */
	public byte getIsHot() {
		return isHot;
	}

	/**
	 * 设置是否热门
	 * @param isHot 是否热门
	 */
	public void setIsHot(byte isHot) {
		this.isHot = isHot;
	}

	/**
	 * 获取是否幻灯片滚动
	 * @return 是否幻灯片滚动
	 */
	public byte getIsSlide() {
		return isSlide;
	}

	/**
	 * 设置是否幻灯片滚动
	 * @param isSlide 是否幻灯片滚动
	 */
	public void setIsSlide(byte isSlide) {
		this.isSlide = isSlide;
	}

	/**
	 * 获取排序
	 * @return 排序
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * 设置排序
	 * @param sort 排序
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * 获取管理员编号
	 * @return 管理员编号
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 设置管理员编号
	 * @param userId 管理员编号
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * 浏览量+1
	 */
	public void setClicks() {
		clicks++;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public NewCategories getNewCategories() {
		return newCategories;
	}

	public void setNewCategories(NewCategories newCategories) {
		this.newCategories = newCategories;
	}

	@Override
	public boolean sameIdentityAs(News other) {
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
		
		News news=(News)obj;
		
		return sameIdentityAs(news);
	}
}

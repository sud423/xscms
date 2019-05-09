package com.susd.domain.bill;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.susd.domain.Entity;
import com.susd.domain.site.User;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.Utils;

public class Bill implements Entity<Bill> {

	private int id;
	
	private int tenantId;
	
	/**账单流水号*/
	private String billNumber;
	
	/**账期*/
	private String period;
	
	/**用户编号*/	
	private int userId;
		
	/**总价格*/
	private float totalPrice;
	
	/**折后价格*/
	private float discountPrice;
	
	/**实际到账金额*/
	private float realAmount;
	
	/**支付时间*/
	private Date payTime;
	
	/**实际到账时间*/
	private Date arrivalTime;
	
	/**状态 1：未推送 10：已推送，待支付 20：已支付*/
	private byte status;
	
	/**推送时间*/
	private Date pushTime;
	
	//推送人编号
	private int pusherId;
	
	/**备注*/
	private String remark;
	
	/**做账人编号*/
	private int maintainId;
	
	/**添加时间*/
	private Date addTime;
	
	/**版本号*/
	private int version;
	
	/**所属客户*/
	private User user;
	

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

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public float getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(float discountPrice) {
		this.discountPrice = discountPrice;
	}

	public float getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(float realAmount) {
		this.realAmount = realAmount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public int getPusherId() {
		return pusherId;
	}

	public void setPusherId(int pusherId) {
		this.pusherId = pusherId;
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 创建账单
	 * @param clientId 客户编号
	 * @param totalPrice 总价格
	 * @return
	 */
	public static Bill create(int clientId,float totalPrice) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		Bill bill = new Bill();
		bill.setTenantId(SessionManager.getTenantId());

		bill.setBillNumber(Utils.generateCode());
		Date now = new Date();
		bill.setPeriod(dateFormat.format(now));
		bill.setStatus((byte) 1);

		bill.setAddTime(now);
		bill.setMaintainId(SessionManager.getUserId());

		bill.setUserId(clientId);
		bill.setTotalPrice(totalPrice);
		
		return bill;
	}
	
	@Override
	public boolean sameIdentityAs(Bill other) {

		return other != null && id == other.getId();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final Bill other = (Bill) object;
		return sameIdentityAs(other);
	}

	@Override
	public int hashCode() {
		return Integer.toString(id).hashCode();
	}
	
}

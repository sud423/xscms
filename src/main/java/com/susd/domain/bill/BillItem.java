package com.susd.domain.bill;

import java.util.Date;
import java.util.List;

import com.susd.domain.Entity;
import com.susd.domain.identity.SysUser;
import com.susd.domain.site.User;
import com.susd.domainservice.identity.SessionManager;

public class BillItem implements Entity<BillItem> {

	private int id;
	
	private int tenantId;
	
	private String express;
	
	private String expressNo;

	private byte type;

	private int userId;
	
	private float price;
	
	private float cost;
	
	private int billId;
		
	private float weight;
	
	private float actualWeight;
	
	private float volumeWeight;
	
	private int count;
	
	private String province;
	
	private String city;
	
	private String area;
	
	private String address;
	
	private Date addTime;
	
	private int accountId;
	
	private String remark;
	
	private int version;
	
	private List<BillItemPackage> packages;
	
	private User client;
	
	private SysUser account;
	
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

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(float actualWeight) {
		this.actualWeight = actualWeight;
	}

	public float getVolumeWeight() {
		return volumeWeight;
	}

	public void setVolumeWeight(float volumeWeight) {
		this.volumeWeight = volumeWeight;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<BillItemPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<BillItemPackage> packages) {
		this.packages = packages;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public SysUser getAccount() {
		return account;
	}

	public void setAccount(SysUser account) {
		this.account = account;
	}

	public void setInit() {
		tenantId=SessionManager.getTenantId();
		accountId=SessionManager.getUserId();
		addTime=new Date();
		
	}
	
	@Override
	public boolean sameIdentityAs(BillItem other) {
		return other != null && id == other.getId();
	}

	@Override
	public int hashCode() {
		return Integer.toString(id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final BillItem other = (BillItem) obj;
		return sameIdentityAs(other);
	}
	
	
	
	
}

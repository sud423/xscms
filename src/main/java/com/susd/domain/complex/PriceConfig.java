package com.susd.domain.complex;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.susd.domain.Entity;

public class PriceConfig implements Entity<PriceConfig> {
	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id;
	
	private int tenantId;
	
	private String express;

	private byte type;

	private Address addr;

	private int coefficient;
	
	//标准单价（单位：元/kg）
	private float standardPrice;
	
	//最低运费（单位：元）
	private float lowestPrice;

	private float firstPrice;

	private float firstWeight;

	private float continuedPrice;

	private float continuedWeight;
	
	/**附加费用（单位：元）*/
	private float addFees;
	
	private String remark;
	
	//维护人编号
	private int maintainId;
	
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

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public Address getAddr() {
		return addr;
	}

	public void setAddr(Address addr) {
		this.addr=addr;
	}
	
	
	public float getStandardPrice() {
		return (float)(Math.round(standardPrice*100))/100;
	}

	public void setStandardPrice(float standardPrice) {
		this.standardPrice = standardPrice;
	}

	public float getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(float lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public float getFirstPrice() {
		return (float)(Math.round(firstPrice*100))/100;
	}

	public void setFirstPrice(float firstPrice) {
		this.firstPrice = firstPrice;
	}

	public float getFirstWeight() {
		return (float)(Math.round(firstWeight*100))/100;
	}

	public void setFirstWeight(float firstWeight) {
		this.firstWeight = firstWeight;
	}

	public float getContinuedPrice() {
		return (float)(Math.round(continuedPrice*100))/100;
	}

	public void setContinuedPrice(float continuedPrice) {
		this.continuedPrice = continuedPrice;
	}

	public float getContinuedWeight() {
		return (float)(Math.round(continuedWeight*100))/100;
	}

	public void setContinuedWeight(float continuedWeight) {
		this.continuedWeight = continuedWeight;
	}

	public float getAddFees() {
		return (float)(Math.round(addFees*100))/100;
	}

	public void setAddFees(float addFees) {
		this.addFees = addFees;
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
		
		PriceConfig other=(PriceConfig)obj;
		
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(PriceConfig other) {
		return other != null && id == other.getId();
	}
	
	
}

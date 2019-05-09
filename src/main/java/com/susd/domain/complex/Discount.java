package com.susd.domain.complex;

import java.util.Date;

import com.susd.domain.Entity;

public class Discount implements Entity<Discount> {

	private int id;
	
	private String name;
	
	private byte type;
	
	private float rate;
	
	private int sort;
	
	private byte isAddUse;

	private Date addTime;

	private int version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public byte getIsAddUse() {
		return isAddUse;
	}

	public void setIsAddUse(byte isAddUse) {
		this.isAddUse = isAddUse;
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
		
		Discount other=(Discount)obj;
		
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Discount other) {
		return other != null && id == other.getId();
	}
}

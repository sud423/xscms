package com.susd.domain.bill;


import com.susd.domain.ValueObject;

public class BillItemPackage implements ValueObject<BillItemPackage> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int billItemId;

	private int len;

	private int width;

	private int height;

	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBillItemId() {
		return billItemId;
	}

	public void setBillItemId(int billItemId) {
		this.billItemId = billItemId;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count ;
	}

	@Override
	public boolean sameValueAs(BillItemPackage other) {
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

		final BillItemPackage other = (BillItemPackage) obj;
		return sameValueAs(other);
	}
	
	
	
}

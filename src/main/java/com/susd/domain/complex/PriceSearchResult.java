package com.susd.domain.complex;

import java.math.BigDecimal;

public class PriceSearchResult {

	/**
	 * 序号
	 */
	private int seqNo;

	private byte type;

	/**
	 * 快递公司
	 */
	public String express;
	
	/**
	 * 结算重量
	 */
	public float weight;
	
	/**
	 * 价格
	 */
	private float price;

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public float getWeight() {
		BigDecimal p = new BigDecimal(weight);
		return p.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getPrice() {
		BigDecimal p = new BigDecimal(price);
		return p.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}

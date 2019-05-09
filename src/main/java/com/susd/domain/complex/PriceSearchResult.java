package com.susd.domain.complex;

public class PriceSearchResult {

	/**
	 * 序号
	 */
	private int seqNo;
	
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

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}

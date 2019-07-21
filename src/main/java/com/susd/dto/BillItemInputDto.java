package com.susd.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BillItemInputDto {

	private int itemId;

	private int billId;

	private int userId;

	@NotNull(message="目的地省份不能为空")
	@Size(max=60,message="目的地省份最大长度为60个字符")
	private String province;

	@NotNull(message="目的地市不能为空")
	@Size(max=60,message="目的地市最大长度为60个字符")
	private String city;

	private byte type;

	// 其它费用
	private float cost;

	private String remark;

	@NotNull(message="快递公司不能为空")
	@Size(max=60,message="快递公司最大长度为60个字符")
	private String express;

	@NotNull(message="快递单号不能为空")
	@Size(max=100,message="快递单号最大长度为100个字符")
	private String expressNo;

	private float volumeWeight;

	private float actualWeight;

	private float weight;

	private float totalPrice;

	private int totalCount;
	
	private int version;

	private List<PackageDto> packageDtos;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public float getCost() {
		return (float)(Math.round(cost*100))/100;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public float getVolumeWeight() {
		return volumeWeight;
	}

	public void setVolumeWeight(float volumeWeight) {
		this.volumeWeight = volumeWeight;
	}

	public float getActualWeight() {
		return (float)(Math.round(actualWeight*100))/100;
	}

	public void setActualWeight(float actualWeight) {
		this.actualWeight = actualWeight;
	}

	public float getWeight() {
		return (float)(Math.round(weight*100))/100;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getTotalPrice() {
		return (float)(Math.round(totalPrice*100))/100;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<PackageDto> getPackageDtos() {
		return packageDtos;
	}

	public void setPackageDtos(List<PackageDto> packageDtos) {
		this.packageDtos = packageDtos;
	}

	public float calculateTotalVolume() {
		float totalVolume = 0;
		
		for (int i=0;i<packageDtos.size();i++) {
			PackageDto dto=packageDtos.get(i);
			totalVolume += dto.calculatedVolume();
			totalCount += dto.getCount();
		}

		return (float)(Math.round(totalVolume*100))/100;
	}

}

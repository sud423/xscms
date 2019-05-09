package com.susd.dto;

public class PackageDto {

	private int packageId;

	private int len;

	private int width;

	private int height;

	private int count;

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
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

	/**
	 * 计算每项体积
	 * 
	 * @return
	 */
	public int calculatedVolume() {
		return len * width * height * count;
	}

}

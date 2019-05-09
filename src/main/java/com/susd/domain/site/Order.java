package com.susd.domain.site;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.susd.domain.Entity;
import com.susd.domain.complex.Car;

public class Order implements Entity<Order> {
	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id;
	
	private int tenantId;
	
	private int userId;
	
	//下单人类型 1：用户 10：业务人员
	private byte type;
	
	//托寄物
	private String goods;
	
	//总件数
	private int num;
	
	private float weight;
	
	private String addr;
	
	private float price;
	
	private int driverId;
	
	private int carId;
	
	private String remark;
	
	private byte status;
	
	private Date addTime;
	
	private int version;
	
	private User client;
	
	private User driver;
	
	private Car car;

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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
	
	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public boolean sameIdentityAs(Order other) {
		return other != null && id == other.getId();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		
		final Order other=(Order)object;

		return sameIdentityAs(other);
	}
	
	@Override
	public int hashCode() {
		return Integer.toString(id).hashCode();
	}
	
}

package com.susd.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CarDto {
	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private int id;
	
	private String carNumber;

	private String sim;

	private byte type;
	
	private float load;
	
	private byte status;
		
	private Date addTime;
	
	private String driverName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public float getLoad() {
		return load;
	}

	public void setLoad(float load) {
		this.load = load;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getAddTime() {
		if(this.addTime==null)
			return "";
		return dateFormat.format(addTime);
	}

	public void setAddTime(String addTime) throws ParseException {
		this.addTime = dateFormat.parse(addTime);;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
}

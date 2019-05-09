package com.susd.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DriverDto {

	private static final SimpleDateFormat dateFormat
    = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private int id;
	
	private String name;
	
	private String cell;
	
	private String nickName;
	
	private String idNumber;
	
	private List<String> attach;
	
	private byte status;
	
	private Date addTime;
	
	private String carNumber;
	
	private byte carStatus;

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

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public List<String> getAttach() {
		return attach;
	}

	public void setAttach(List<String> attach) {
		this.attach = attach;
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
		this.addTime = dateFormat.parse(addTime);
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public byte getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(byte carStatus) {
		this.carStatus = carStatus;
	}
	
	
	
	
}

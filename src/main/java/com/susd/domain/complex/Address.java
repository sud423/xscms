package com.susd.domain.complex;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.susd.domain.ValueObject;

public class Address implements ValueObject<Address> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String province;
	
	private String city;
	
	private String area;
	
	private String address;

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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public boolean sameValueAs(Address other) {
		return other != null && new EqualsBuilder().
				append(this.province, other.province).
				append(this.city, other.city).
				append(this.area, other.area).
				append(this.address, other.address).
				isEquals();
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder().
				 append(province).
				 append(city).
				 append(area).
				 append(address).
				 toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    Address other=(Address)obj;
	    
	    return sameValueAs(other);
	}
}

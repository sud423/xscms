package com.susd.domain.complex;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.susd.domain.ValueObject;

public class Cube implements ValueObject<Cube> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float len;

	private float width;

	private float height;

	public float getLen() {
		return len;
	}

	public void setLen(float len) {
		this.len = len;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float volume() {
		return len * width * height;
	}

	@Override
	public boolean sameValueAs(Cube other) {
		return other != null && new EqualsBuilder().
				append(this.len, other.len).
				append(this.width, other.width).
				append(this.height, other.height).
				isEquals();
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder().
				 append(len).
				 append(width).
				 append(height).
				 toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    Cube other=(Cube)obj;
	    
	    return sameValueAs(other);
	}

	
	
}

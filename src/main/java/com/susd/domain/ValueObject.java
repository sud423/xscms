package com.susd.domain;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {

	/**
	 * 值对象通过其属性值进行比较，它们没有标识。
	 * @param other 其它值对象
	 * @return 如果给定值对象和此值对象的属性相同就返回true
	 */
	boolean sameValueAs(T other);
}
